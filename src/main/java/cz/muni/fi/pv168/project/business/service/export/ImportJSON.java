package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class for importing Tasks from JSON file
 * @author Nikol Otáhalů
 */
public class ImportJSON {

    /**
     * Validates whether the imported JSON file contains all required keys
     * @param jsonArray JSON array from the file
     * @return true if file contains all required keys
     */
    private static boolean validateJSONFormat(JSONArray jsonArray)
    {
        String[] requiredKeys = {"LOGGED TIME CLEAR",
            "EMPLOYEE NAME",
            "EMPLOYEE ID",
            "TIME UNIT",
            "ALLOCATED TIME CLEAR",
            "ASSIGNED TO",
            "TASK NAME",
            "STATUS",
            "LOGGED TIME",
            "DESCRIPTION",
            "CATEGORY",
            "TIME UNIT RATE",
            "CATEGORY COLOR",
            "DUE DATE",
            "CUSTOMER"};

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (String key: requiredKeys) {
                if (!jsonObject.has(key)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates dialogue for asking the user, whether to add tasks or override them
     * @return chosen option by teh user, 0 == Override Tasks
     */
    private static int getImportOption() {
        return JOptionPane.showOptionDialog(null,
                "Do you want to override existing tasks or add new ones?",
                "Import Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Override", "Add"}, "Override");
    }

    /**
     * Imports data from JSON file and adds them to file manager
     * @param data DataManager where the data will be imported
     */
    public static void importTaskTableFromJson(DataManager data){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select JSON file with tasks to import");
        JSONArray jsonArray = null;

        //Override or add tasks from import
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".json")) {
                JOptionPane.showMessageDialog(null,
                        "Please select a valid JSON file.",
                        "Invalid File",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int importChoice = getImportOption();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
                jsonArray = new JSONArray(jsonContent.toString());


            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!validateJSONFormat(jsonArray))
            {
                JOptionPane.showMessageDialog(null,
                        "Chosen JSON file is not in required format!",
                        "Invalid file content",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (importChoice == 0){
                data.getTaskTableModel().deleteAllRows();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                data.getTaskTableModel().addRow(getTaskFromJSON(jsonArray.getJSONObject(i), data));
            }
            JOptionPane.showMessageDialog(null, "Imported successfully!");
        }
    }

    /**
     * Returns Task for import to the app
     * @param item JSONObject containing exported task
     * @param data DataManager containing app data
     * @return Task for import
     */
    private static Task getTaskFromJSON(JSONObject item, DataManager data){
        return new Task(null,
                getStatus(item),
                item.getString("DESCRIPTION"),
                getCategory(item, data),
                item.getString("CUSTOMER"),
                item.getString("CUSTOMER"),
                getEmployee(item, data),
                item.getInt("LOGGED TIME CLEAR"),
                item.getInt("ALLOCATED TIME CLEAR"),
                getTimeUnit(item, data),
                getDueDate(item));
    }

    /**
     * Returns Category for the task, creates new one if necessary
     * @param item JSONObject containing exported task
     * @param data DataManager containing app data
     * @return Category for the purpose of task creation
     */
    private static Category getCategory(JSONObject item, DataManager data){
        var categoryColor = new Color(item.getInt("CATEGORY COLOR"));
        var categoryName = item.getString("CATEGORY");
        //Equals on colour didn't really work
        for (Category c: data.getCategories().toArray()) {
            if(Objects.equals(c.getName(), item.getString("CATEGORY"))){
                return c;
            }
        }
        Category category = new Category(null, categoryName, categoryColor);
        data.getCategories().add(category);
        return category;
    }

    /**
     * Returns Employee for the task, creates new one if necessary
     * @param item JSONObject containing exported task
     * @param data DataManager containing app data
     * @return Employee for the purpose of task creation
     */
    private static Employee getEmployee(JSONObject item, DataManager data){
        var employeeId = item.getInt("EMPLOYEE ID");
        var employeeName = item.getString("EMPLOYEE NAME");
        for (Employee e: data.getEmployees().toArray()) {
            if(Objects.equals(e.getName(), employeeName)&&
                    e.getId() == employeeId){
                return e;
            }
        }
        Employee newEmployee = new Employee(null, employeeName, employeeId);
        data.getEmployees().add(newEmployee);
        return newEmployee;
    }
    /**
     * Returns Status for the task
     * @param item JSONObject containing exported task
     * @return Status for the purpose of task creation
     */
    private static Status getStatus(JSONObject item){
        for (Status s:Status.values()) {
            if(Objects.equals(s.toString(), item.getString("STATUS"))){
                return s;
            }
        }
        //Should not ever happen
        return Status.TO_DO;
    }

    /**
     * Returns Time Unit for the task, creates new one if necessary
     * @param item JSONObject containing exported task
     * @param data DataManager containing app data
     * @return Time unit for the purpose of task creation
     */
    private static TimeUnit getTimeUnit(JSONObject item, DataManager data){
        var timeUnitName = item.getString("TIME UNIT");
        var timeUnitRate = item.getInt("TIME UNIT RATE");
        var timeUnitShortName = item.getString("LOGGED TIME").split(" ")[1];
        for (TimeUnit tu: data.getTimeUnits().toArray()) {
            if(Objects.equals(tu.getName(), timeUnitName)&&
                    tu.getRate() == timeUnitRate){
                return tu;
            }
        }

        TimeUnit timeUnit = new CustomTimeUnit(null, timeUnitName, timeUnitShortName, timeUnitRate);
        data.getTimeUnits().add(timeUnit);
        return  timeUnit;
    }

    /**
     * Returns DueDate in LocalDate type from the JSONObject
     * @param item JSONObject containing exported task
     * @return LocalDate type for Task for the purpose of task creation
     */
    private static LocalDate getDueDate(JSONObject item){
        return LocalDate.parse(item.getString("DUE DATE"));
    }
}
