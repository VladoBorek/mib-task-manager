package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;
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
        var taskTableModelTableModel = data.getTaskTableModel();
        Arrays.stream(data.getTaskTable().getSelectedRows())
                .map(data.getTaskTable()::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(taskTableModelTableModel::deleteRow);

        // TODO: umh..
        ((StatisticsTableModel) data.getStatisticsTable().getModel()).refreshStatistics();
    }
}
