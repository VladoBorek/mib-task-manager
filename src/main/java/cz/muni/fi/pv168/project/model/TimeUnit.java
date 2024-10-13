package cz.muni.fi.pv168.project.model;

public class TimeUnit {
    private final Integer baseUnitValue = 1;
    private  final String baseUnitName = "Hour";

    private String name;

    private Integer rate;


    public TimeUnit(String name, Integer rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getBaseUnit() {
        return baseUnitName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return name;
    }
}
