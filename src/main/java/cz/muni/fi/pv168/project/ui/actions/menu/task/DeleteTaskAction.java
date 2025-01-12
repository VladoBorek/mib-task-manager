package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.model.storagemodels.LogTimeInfoTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Marcel Nadzam
 */
public class DeleteTaskAction extends EntityBaseAction {

    public DeleteTaskAction(UIDataManager data) {
        super("Delete Task", Icons.DELETE_ICON, data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deleteTask();
    }

    private void deleteTask() {
        var selectedRows = data.getTaskTable().getSelectedRows();

        deleteAssociatedLogs(selectedRows);

        deleteSelectedTasks(selectedRows);

        data.getStatisticsTableModel().refreshStatistics();
    }

    private void deleteAssociatedLogs(int[] selectedRows) {
        var logModel = data.getLogTimeInfoTableModel();

        for (var row : selectedRows) {
            var task = data.getTaskTableModel().getEntity(row);
            deleteLogsForTask(logModel, task.getId());
        }
    }

    private void deleteLogsForTask(LogTimeInfoTableModel logModel, Long taskId) {
        for (int i = 0; i < logModel.getRowCount(); i++) {
            if (logModel.getValueAt(i, 0).equals(taskId)) {
                logModel.deleteRow(i);
                i = -1;
            }
        }
    }

    private void deleteSelectedTasks(int[] selectedRows) {
        var taskTableModel = data.getTaskTableModel();

        Arrays.stream(selectedRows)
                .map(data.getTaskTable()::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(taskTableModel::deleteRow);
    }
}
