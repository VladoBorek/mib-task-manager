package cz.muni.fi.pv168.project.ui.utils;

import cz.muni.fi.pv168.project.business.model.Statistics.Statistic;
import cz.muni.fi.pv168.project.business.model.Statistics.Statistics;
import cz.muni.fi.pv168.project.business.model.Statistics.TimeStatistic;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;

import javax.swing.table.TableRowSorter;
import java.time.LocalDate;

/**
 * @author Vladimir Borek
 */
public class StatisticsService {

    public static Statistics calculateGlobalStatistics(TaskTableModel taskTableModel) {
        return calculateStatistics(taskTableModel, null);
    }

    public static Statistics calculateFilteredStatistics(TaskTableModel taskTableModel, TableRowSorter<TaskTableModel> sorter) {
        return calculateStatistics(taskTableModel, sorter);
    }

    private static Statistics calculateStatistics(TaskTableModel taskTableModel, TableRowSorter<TaskTableModel> sorter) {
        int total = (sorter == null) ? taskTableModel.getRowCount() : sorter.getViewRowCount();
        int completed = 0, inProgress = 0, overdue = 0, logged = 0, allocated = 0;

        for (int i = 0; i < total; i++) {
            int modelIndex = (sorter == null) ? i : sorter.convertRowIndexToModel(i);
            Task task = taskTableModel.getEntity(modelIndex);

            logged += task.getLoggedTime();
            allocated += task.getAllocatedTime();

            if (task.getStatus() == Status.COMPLETED) {
                completed++;
            } else {
                inProgress++;
            }

            LocalDate dueDate = task.getDueDate();
            if (dueDate != null && LocalDate.now().isAfter(dueDate)) {
                overdue++;
            }
        }

        return new Statistics(
                new Statistic(total),
                new Statistic(completed),
                new Statistic(inProgress),
                new Statistic(overdue),
                new TimeStatistic(logged, allocated)
        );

    }
}

