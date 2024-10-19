package cz.muni.fi.pv168.project.model;

public abstract class TimeUnit {
    private static final String baseUnitName = "Hour";

    private String name;

    private Integer rate;


    /**
     * Creates a new instance of base time unit
     */
    public TimeUnit() {
        this("Hour", 1);
    }

    /**
     * Creates a custom TimeUnit
     * @param name name of the unit
     * @param rate conversion rate to the base time unit
     */
    public TimeUnit(String name, Integer rate) {
        this.name = name;
        this.rate = rate;
    }



    public static String getBaseUnit() {
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
