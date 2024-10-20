package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
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
    private final List<Object[]> statisticsData;
    public StatisticsTableModel(List<Object[]> statisticsData) {
        this.statisticsData = statisticsData;
    }

    public StatisticsTableModel(){
        this.statisticsData = new ArrayList<>(); // FOR NOW
        statisticsData.add(new Object[]{"Total Tasks", 100, 50});
        statisticsData.add(new Object[]{"Completed Tasks", 60, 30});
        statisticsData.add(new Object[]{"Overdue Tasks", 20, 5});
        statisticsData.add(new Object[]{"Pending Tasks", 40, 20});
        statisticsData.add(new Object[]{"Tasks Due Today", 15, 7});
        statisticsData.add(new Object[]{"Tasks Due This Week", 25, 12});
        statisticsData.add(new Object[]{"High Priority Tasks", 10, 4});
        statisticsData.add(new Object[]{"Low Priority Tasks", 30, 18});
        statisticsData.add(new Object[]{"Tasks Created This Month", 50, 25});
        statisticsData.add(new Object[]{"Tasks Assigned to Team A", 35, 15});
        statisticsData.add(new Object[]{"Tasks Assigned to Team B", 25, 10});
        statisticsData.add(new Object[]{"Tasks with Comments", 45, 20});
        statisticsData.add(new Object[]{"Tasks Requiring Follow-up", 12, 6});
        statisticsData.add(new Object[]{"Long-term Tasks (Over 1 Month)", 8, 3});
        statisticsData.add(new Object[]{"Short-term Tasks (Less Than a Week)", 55, 28});
        statisticsData.add(new Object[]{"Tasks in Progress", 35, 16});
        statisticsData.add(new Object[]{"Tasks Awaiting Review", 18, 9});
        statisticsData.add(new Object[]{"Tasks Blocked", 10, 4});
        statisticsData.add(new Object[]{"Tasks Rescheduled", 22, 11});
        statisticsData.add(new Object[]{"Tasks with Attachments", 40, 18});
        statisticsData.add(new Object[]{"Tasks Reopened", 5, 2});
        statisticsData.add(new Object[]{"Tasks with Deadlines Extended", 12, 6});
        statisticsData.add(new Object[]{"Tasks with External Dependencies", 15, 7});
        statisticsData.add(new Object[]{"Tasks Delayed by More Than 1 Week", 9, 3});
        statisticsData.add(new Object[]{"Tasks with Multiple Assignees", 25, 13});
        statisticsData.add(new Object[]{"Tasks Closed Today", 7, 3});
        statisticsData.add(new Object[]{"Tasks Created This Week", 22, 11});
        statisticsData.add(new Object[]{"Tasks with Time Estimates", 30, 15});
        statisticsData.add(new Object[]{"Tasks Exceeding Estimated Time", 12, 5});
        statisticsData.add(new Object[]{"Tasks Without Deadlines", 14, 8});
        statisticsData.add(new Object[]{"Tasks Completed Ahead of Schedule", 10, 4});
        statisticsData.add(new Object[]{"Tasks Updated Recently", 20, 12});
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
        return statisticsData.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }


}
