package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.ui.dialog.AddTaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.InspectTaskDialog;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Vladimir Borek
 */
public class InspectAction extends AbstractAction {
    private final JComboBox comboBox;
    private final DataManager data;
    private final ActionType type;

    public InspectAction (ActionType type, JComboBox comboBox, DataManager data) {
        super("Inspect Task", Icons.INSPECT_ICON);
        this.type = type;
        this.data = data;
        this.comboBox = comboBox;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(type) {
            case TASK:
                var selectedRows = data.getTaskTable().getSelectedRows();
                if (selectedRows.length != 1) {
                    throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
                }
                var taskTableModel = (TaskTableModel) data.getTaskTable().getModel();
                int modelRow = data.getTaskTable().convertRowIndexToModel(selectedRows[0]);
                var task = taskTableModel.getEntity(modelRow);

                var tDialog = new InspectTaskDialog(task, data);
                tDialog.show(data.getTaskTable(), "Inspect Task").ifPresent(taskTableModel::updateRow);
    }
}
}

