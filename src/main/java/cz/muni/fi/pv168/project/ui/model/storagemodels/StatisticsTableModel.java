package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.Statistics.Statistics;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.model.statisticTableRow;
import cz.muni.fi.pv168.project.ui.utils.StatisticsService;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Vladimir Borek
 */
public class StatisticsTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Statistic Name", "Global Statistic", "Filtered Statistic"};
    private final List<statisticTableRow> statisticsData = new ArrayList<>();
    private final UIDataManager data;
    private Statistics globalStatistics;
    private Statistics filteredStatistics;

    public StatisticsTableModel(UIDataManager data) {
        this.data = data;
        refreshStatistics();
    }

    private void addStatisticsToTable() {
        statisticsData.clear();
        statisticsData.add(new statisticTableRow("Total Tasks", globalStatistics.total(), filteredStatistics.total()));
        statisticsData.add(new statisticTableRow("Completed Tasks", globalStatistics.completed(), filteredStatistics.completed()));
        statisticsData.add(new statisticTableRow("Overdue Tasks", globalStatistics.overdue(), filteredStatistics.overdue()));
        statisticsData.add(new statisticTableRow("Unfinished Tasks", globalStatistics.inProgress(), filteredStatistics.inProgress()));
        statisticsData.add(new statisticTableRow("Logged Time / Allocated Time", globalStatistics.logAlloc(), filteredStatistics.logAlloc())
        );
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
        statisticTableRow row = statisticsData.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> row.name();
            case 1 -> row.globalStatistic();
            case 2 -> row.filteredStatistic();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public void refreshStatistics() {
        var taskTableModel = (TaskTableModel) data.getTaskTable().getModel();
        var rowSorter = (TableRowSorter<TaskTableModel>) data.getTaskTable().getRowSorter();

        globalStatistics = StatisticsService.calculateGlobalStatistics(taskTableModel);
        filteredStatistics = StatisticsService.calculateFilteredStatistics(taskTableModel, rowSorter);

        addStatisticsToTable();
        fireTableDataChanged();
    }

}
