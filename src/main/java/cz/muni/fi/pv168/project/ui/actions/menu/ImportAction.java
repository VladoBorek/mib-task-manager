package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Nikol Otáhalů
 */
public class ImportAction extends AbstractAction {
    private final DataManager dataManager;

    public ImportAction(DataManager dataManager){
        super("Import tasks", Icons.IMPORT_ICON);
        this.dataManager = dataManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        importTableFromJson(dataManager);
    }
    private static void importTableFromJson(DataManager data){
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select JSON file with tasks to import");

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

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
                    Integer loggedTIme = jsonObject.getInt("LOGGED TIME");
                    Integer allocatedTime = jsonObject.getInt("ALLOCATED TIME");
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
                        category = new Category(categoryName, categoryColor);
                        data.getCategories().addCategory(category);
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
                        assignedTo = new Employee(employeeName, employeeId);
                        data.getEmployees().addEmployee(assignedTo);
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
                        timeUnit = new CustomTimeUnit(timeUnitName, timeUnitRate);
                        data.getTimeUnits().addUnit(timeUnit);
                    }


                    Task task = new Task(taskStatus, description, category, customer, nameOfTask, assignedTo, loggedTIme, allocatedTime, timeUnit, dueDate);
                    data.getTaskTableModel().addRow(task);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
