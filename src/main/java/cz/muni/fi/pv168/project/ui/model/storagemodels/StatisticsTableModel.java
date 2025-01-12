package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.Statistic;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.model.statisticTableCell;
import cz.muni.fi.pv168.project.ui.utils.StatisticsService;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

import static cz.muni.fi.pv168.project.ui.utils.StatisticsService.formatLoggedAllocatedTime;


/**
 * @author Vladimir Borek
 */
public class StatisticsTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Statistic Name", "Global Statistic", "Filtered Statistic"};
    private final List<statisticTableCell> statisticsData = new ArrayList<>();
    private final UIDataManager data;
    private Statistic globalStatistics;
    private Statistic filteredStatistics;

    public StatisticsTableModel(UIDataManager data) {
        this.data = data;
        refreshStatistics();
    }

    private void addStatisticsToTable() {
        statisticsData.clear();
        statisticsData.add(new statisticTableCell("Total Tasks", globalStatistics.total(), filteredStatistics.total()));
        statisticsData.add(new statisticTableCell("Completed Tasks", globalStatistics.completed(), filteredStatistics.completed()));
        statisticsData.add(new statisticTableCell("Overdue Tasks", globalStatistics.overdue(), filteredStatistics.overdue()));
        statisticsData.add(new statisticTableCell("Unfinished Tasks", globalStatistics.inProgress(), filteredStatistics.inProgress()));
        statisticsData.add(new statisticTableCell("Logged Time / Allocated Time",
                formatLoggedAllocatedTime(globalStatistics), formatLoggedAllocatedTime(filteredStatistics))
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
        var taskTableModel = (TaskTableModel) data.getTaskTable().getModel();
        var rowSorter = (TableRowSorter<TaskTableModel>) data.getTaskTable().getRowSorter();

        globalStatistics = StatisticsService.calculateGlobalStatistics(taskTableModel);
        filteredStatistics = StatisticsService.calculateFilteredStatistics(taskTableModel, rowSorter);

        addStatisticsToTable();
        fireTableDataChanged();
    }

}
