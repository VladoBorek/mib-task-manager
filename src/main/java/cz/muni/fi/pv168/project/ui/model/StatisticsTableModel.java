package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Statistic;
import cz.muni.fi.pv168.project.model.Status;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.TimeUnit;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Borek
 */
public class StatisticsTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Statistic Name", "Global Statistic", "Filtered Statistic"};
    private final List<Statistic> statisticsData;
    public StatisticsTableModel(List<Statistic> statisticsData) {
        this.statisticsData = statisticsData;
    }

    public StatisticsTableModel(){
        this.statisticsData = new ArrayList<>(); // FOR NOW
        statisticsData.add(new Statistic("Total Tasks", 100, 50));
        statisticsData.add(new Statistic("Completed Tasks", 60, 30));
        statisticsData.add(new Statistic("Overdue Tasks", 20, 5));
        statisticsData.add(new Statistic("Pending Tasks", 40, 20));
        statisticsData.add(new Statistic("Tasks Due Today", 15, 7));
        statisticsData.add(new Statistic("Tasks Due This Week", 25, 12));
        statisticsData.add(new Statistic("High Priority Tasks", 10, 4));
        statisticsData.add(new Statistic("Low Priority Tasks", 30, 18));
        statisticsData.add(new Statistic("Tasks Created This Month", 50, 25));
        statisticsData.add(new Statistic("Tasks Assigned to Team A", 35, 15));
        statisticsData.add(new Statistic("Tasks Assigned to Team B", 25, 10));
        statisticsData.add(new Statistic("Tasks with Comments", 45, 20));
        statisticsData.add(new Statistic("Tasks Requiring Follow-up", 12, 6));
        statisticsData.add(new Statistic("Long-term Tasks (Over 1 Month)", 8, 3));
        statisticsData.add(new Statistic("Short-term Tasks (Less Than a Week)", 55, 28));
        statisticsData.add(new Statistic("Tasks in Progress", 35, 16));
        statisticsData.add(new Statistic("Tasks Awaiting Review", 18, 9));
        statisticsData.add(new Statistic("Tasks Blocked", 10, 4));
        statisticsData.add(new Statistic("Tasks Rescheduled", 22, 11));
        statisticsData.add(new Statistic("Tasks with Attachments", 40, 18));
        statisticsData.add(new Statistic("Tasks Reopened", 5, 2));
        statisticsData.add(new Statistic("Tasks with Deadlines Extended", 12, 6));
        statisticsData.add(new Statistic("Tasks with External Dependencies", 15, 7));
        statisticsData.add(new Statistic("Tasks Delayed by More Than 1 Week", 9, 3));
        statisticsData.add(new Statistic("Tasks with Multiple Assignees", 25, 13));
        statisticsData.add(new Statistic("Tasks Closed Today", 7, 3));
        statisticsData.add(new Statistic("Tasks Created This Week", 22, 11));
        statisticsData.add(new Statistic("Tasks with Time Estimates", 30, 15));
        statisticsData.add(new Statistic("Tasks Exceeding Estimated Time", 12, 5));
        statisticsData.add(new Statistic("Tasks Without Deadlines", 14, 8));
        statisticsData.add(new Statistic("Tasks Completed Ahead of Schedule", 10, 4));
        statisticsData.add(new Statistic("Tasks Updated Recently", 20, 12));

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
        Statistic statistic = statisticsData.get(rowIndex);
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


    public void addStatistic(Statistic statistic) {
        statisticsData.add(statistic);
        fireTableDataChanged();
    }



}
