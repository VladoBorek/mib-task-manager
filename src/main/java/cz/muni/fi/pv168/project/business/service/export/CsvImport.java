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
 * @author Nikol Otáhalů
 */
public class CsvImport {
    public static void importTaskTableFromJson(DataManager data){
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select JSON file with tasks to import");

        //Override or add tasks from import
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".json")) {
                JOptionPane.showMessageDialog(null, "Please select a valid JSON file.", "Invalid File",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] options = {"Override", "Add"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Do you want to override existing tasks or add new ones?",
                    "Import Options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            //Override option, clear the table
            if (choice == 0){
                var totalRows = data.getTaskTableModel().getRowCount();
                for (int i = 0; i < totalRows; i++) {
                    data.getTaskTableModel().deleteRow(0);
                }
                data.getTaskTable().updateUI();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }

                JSONArray jsonArray = new JSONArray(jsonContent.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Status taskStatus = Status.TO_DO;
                    String description = jsonObject.getString("DESCRIPTION");
                    Category category = null;
                    String customer = jsonObject.getString("CUSTOMER");
                    String nameOfTask = jsonObject.getString("TASK NAME");
                    Employee assignedTo = null;
                    Integer loggedTIme = jsonObject.getInt("LOGGED TIME CLEAR");
                    Integer allocatedTime = jsonObject.getInt("ALLOCATED TIME CLEAR");
                    TimeUnit timeUnit = null;
                    LocalDate dueDate = LocalDate.parse(jsonObject.getString("DUE DATE"));

                    //Get category
                    boolean foundCategory = false;
                    var categoryColor = new Color(jsonObject.getInt("CATEGORY COLOR"));
                    var categoryName = jsonObject.getString("CATEGORY");
                    for (Category c: data.getCategories().toArray()) {
                        if(Objects.equals(c.getName(), jsonObject.getString("CATEGORY"))&&
                                c.getColor() == categoryColor){
                            category = c;
                            foundCategory = true;
                            break;
                        }
                    }
                    if (!foundCategory){
                        category = new Category(null, categoryName, categoryColor);
                        data.getCategories().add(category);
                    }
                    // Get status
                    for (Status s:Status.values()) {
                        if(Objects.equals(s.toString(), jsonObject.getString("STATUS"))){
                            taskStatus = s;
                            break;
                        }
                    }
                    //Get employee
                    var employeeId = jsonObject.getInt("EMPLOYEE ID");
                    var employeeName = jsonObject.getString("EMPLOYEE NAME");
                    boolean foundEmployee = false;
                    for (Employee e: data.getEmployees().toArray()) {
                        if(Objects.equals(e.getName(), employeeName)&&
                                e.getId() == employeeId){
                            assignedTo = e;
                            foundEmployee = true;
                            break;
                        }
                    }
                    if(!foundEmployee){
                        assignedTo = new Employee(null, employeeName, employeeId);
                        data.getEmployees().add(assignedTo);
                    }
                    //Get Time Unit
                    var timeUnitName = jsonObject.getString("TIME UNIT");
                    var timeUnitRate = jsonObject.getInt("TIME UNIT RATE");
                    boolean foundTimeUnit = false;
                    for (TimeUnit tu: data.getTimeUnits().toArray()) {
                        if(Objects.equals(tu.getName(), timeUnitName)&&
                                tu.getRate() == timeUnitRate){
                            timeUnit = tu;
                            foundTimeUnit = true;
                            break;
                        }
                    }
                    if(!foundTimeUnit){
                        timeUnit = new CustomTimeUnit(null, timeUnitName, "TODO", timeUnitRate);
                        data.getTimeUnits().add(timeUnit);
                    }

                    //New

                    Task task = new Task(null, taskStatus, description, category, customer, nameOfTask, assignedTo, loggedTIme, allocatedTime, timeUnit, dueDate);
                    data.getTaskTableModel().addRow(task);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
