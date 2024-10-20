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
