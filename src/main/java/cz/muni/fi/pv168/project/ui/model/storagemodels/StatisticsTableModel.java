package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Statistic;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.ui.model.statisticTableCell;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Borek
 */
public class StatisticsTableModel extends AbstractTableModel {
    private static final int  STATUS_INDEX = 1;
    private static final int DUEDATE_INDEX = 8;
    private final String[] columnNames = {"Statistic Name", "Global Statistic", "Filtered Statistic"};
    private Statistic globalStatistics = new Statistic();
    private Statistic filteredStatistics = new Statistic();
    private List<statisticTableCell> statisticsData;
    private final DataManager data;

    public StatisticsTableModel(DataManager data){
        this.statisticsData = new ArrayList<>();
        this.data = data;

        calculateGlobalStatistics();
        calculateFilteredStatistics();
        addStatisticsToTable();
    }

    private void addStatisticsToTable(){
        statisticsData.add(new statisticTableCell(
                "Total Tasks", globalStatistics.getTotal(), filteredStatistics.getTotal()));
        statisticsData.add(new statisticTableCell(
                "Completed Tasks", globalStatistics.getCompleted(), filteredStatistics.getCompleted()));
        statisticsData.add(new statisticTableCell(
                "Overdue Tasks", globalStatistics.getOverdue(), filteredStatistics.getOverdue()));
        statisticsData.add(new statisticTableCell(
                "Unfinished Tasks", globalStatistics.getInProgress(), filteredStatistics.getInProgress()));
    }

    private void calculateGlobalStatistics(){
        TaskTableModel taskTableModel = (TaskTableModel) data.getTaskTable().getModel();
        int globalTotalTasks = taskTableModel.getRowCount();
        globalStatistics.setTotal(globalTotalTasks);

        for (int i = 0; i < taskTableModel.getRowCount(); i++) {
            Status taskStatus = (Status) taskTableModel.getValueAt(i, STATUS_INDEX);
            if (taskStatus == Status.COMPLETED) {
                globalStatistics.setCompleted(globalStatistics.getCompleted() + 1);

            } else{
                globalStatistics.setInProgress(globalStatistics.getInProgress() + 1);
            }

            LocalDate taskDueDate = (LocalDate) taskTableModel.getValueAt(i, DUEDATE_INDEX);
            if (LocalDate.now().isAfter(taskDueDate)){
                globalStatistics.setOverdue(globalStatistics.getOverdue() + 1);
            }
        }
    }

    private void calculateFilteredStatistics(){
        TaskTableModel taskTableModel = (TaskTableModel) data.getTaskTable().getModel();
        TableRowSorter<TaskTableModel> rowSorter = (TableRowSorter<TaskTableModel>) data.getTaskTable().getRowSorter();

        int filteredTotalTasks = rowSorter.getViewRowCount();
        filteredStatistics.setTotal(filteredTotalTasks);

        for (int i = 0; i < filteredTotalTasks; i++) {
            int modelIndex = rowSorter.convertRowIndexToModel(i);

            Status taskStatus = (Status) taskTableModel.getValueAt(modelIndex, STATUS_INDEX);
            if (taskStatus == Status.COMPLETED) {
                filteredStatistics.setCompleted(filteredStatistics.getCompleted() + 1);
            } else {
                filteredStatistics.setInProgress(filteredStatistics.getInProgress() + 1);
            }

            LocalDate taskDueDate = (LocalDate) taskTableModel.getValueAt(modelIndex, DUEDATE_INDEX);
            if (LocalDate.now().isAfter(taskDueDate)) {
                filteredStatistics.setOverdue(filteredStatistics.getOverdue() + 1);
            }
        }
    }
    @Override
    public int getRowCount() {
        return statisticsData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        statisticTableCell statistic = statisticsData.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> statistic.getName();
            case 1 -> statistic.getGlobalStatistic();
            case 2 -> statistic.getFilteredStatistic();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public void refreshStatistics() {
        globalStatistics = new Statistic();
        filteredStatistics = new Statistic();
        statisticsData = new ArrayList<>();

        calculateGlobalStatistics();
        calculateFilteredStatistics();

        addStatisticsToTable();
        fireTableDataChanged();
    }

}
