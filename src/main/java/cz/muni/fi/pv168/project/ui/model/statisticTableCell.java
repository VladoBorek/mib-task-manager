package cz.muni.fi.pv168.project.ui.model;

/**
 * @author Vladimir Borek
 */
public class statisticTableCell {
    private final String name;
    private final String globalStatistic;
    private final String filteredStatistic;

    public statisticTableCell(String name, int globalStatistic, int filteredStatistic) {
        this.name = name;
        this.globalStatistic = String.valueOf(globalStatistic);
        this.filteredStatistic = String.valueOf(filteredStatistic);
    }

    public statisticTableCell(String name, String globalStatistic, String filteredStatistic) {
        this.name = name;
        this.globalStatistic = globalStatistic;
        this.filteredStatistic = filteredStatistic;
    }


    public String getName() {
        return this.name;
    }

    public String getGlobalStatistic() {
        return this.globalStatistic;
    }

    public String getFilteredStatistic() {
        return this.filteredStatistic;
    }
}
