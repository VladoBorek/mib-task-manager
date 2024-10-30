package cz.muni.fi.pv168.project.business.model;

public abstract class TimeUnit extends Entity {
    public static final String BASE_UNIT = "Minute";
    public static final String BASE_UNIT_SHORT = "min";

    private String name;
    private String shortName;
    private Integer rate;


    /**
     * Creates a new instance of base time unit
     */
    public TimeUnit() {
        this(0L, BASE_UNIT, BASE_UNIT_SHORT, 1);
    }

    /**
     * Creates a custom TimeUnit
     * @param name name of the unit
     * @param rate conversion rate to the base time unit
     */
    public TimeUnit(Long id, String name, String shortName, Integer rate) {
        super(id);
        this.name = name;
        this.shortName = shortName;
        this.rate = rate;
    }



    public static String getBaseUnit() {
        return BASE_UNIT;
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

    public String getShortName() {
        return shortName;
    }
}
