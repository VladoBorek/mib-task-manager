package cz.muni.fi.pv168.project.model;

/**
 * @author Vladimir Borek
 */
public class Statistic {
    private final String name;
    private final int globalStatistic;
    private final int filteredStatistic;

    public Statistic(String name, int globalStatistic, int filteredStatistic){
        this.name = name;
        this.globalStatistic = globalStatistic;
        this.filteredStatistic = filteredStatistic;
    }

//    public Statistic(String name){
//        this. globalStatistic =  function;
//    }

    public String getName(){
        return this.name;
    }

    public int getGlobalStatistic(){
        return this.globalStatistic;
    }

    public int getFilteredStatistic(){
        return this.filteredStatistic;
    }
}
