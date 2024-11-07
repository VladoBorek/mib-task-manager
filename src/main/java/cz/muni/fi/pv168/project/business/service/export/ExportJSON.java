package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Nikol Otáhalů
 */
public class ExportJSON {
    public static void exportTaskTableToJson(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fileWriter = new FileWriter(fileChooser.getSelectedFile() + ".json")) {
                TaskTableModel model = (TaskTableModel) table.getModel();
                JSONArray jsonArray = new JSONArray();

                for (int i = 0; i < model.getRowCount(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        jsonObject.put(model.getColumnName(j), model.getValueAt(i, j));
                    }
                    jsonObject.put("LOGGED TIME CLEAR", model.getEntity(i).getConvertedLoggedTime());
                    jsonObject.put("ALLOCATED TIME CLEAR", model.getEntity(i).getConvertedAllocatedTime());

                    jsonObject.put("DESCRIPTION", model.getEntity(i).getDescription());
                    jsonObject.put("EMPLOYEE NAME", model.getEntity(i).getAssignedTo().getName());
                    jsonObject.put("EMPLOYEE ID", model.getEntity(i).getAssignedTo().getId());
                    jsonObject.put("CATEGORY COLOR", model.getEntity(i).getCategory().getColor().getRGB());
                    jsonObject.put("TIME UNIT RATE", model.getEntity(i).getTimeUnit().getRate());

                    jsonObject.put("TIME UNIT", model.getEntity(i).getTimeUnit().getName());
                    jsonArray.put(jsonObject);
                }

                fileWriter.write(jsonArray.toString(4)); // Pretty print with an indent of 4 spaces
                JOptionPane.showMessageDialog(null, "Exported successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error exporting data: " + ex.getMessage());
            }
        }
    }
}
