package cz.muni.fi.pv168.project.model;

/**
 * Class for instantiaziation of custom {@link TimeUnit} objects.
 * @author Maroš Pavlík
 */
public class CustomTimeUnit extends TimeUnit{

    /**
     * Creates a custom TimeUnit
     * @param name name of the unit
     * @param rate conversion rate to the base time unit
     */
    public CustomTimeUnit(String name, Integer rate) {
        super(name, rate);
    }

    /**
     * Creates a new instance of base time unit
     */
    public CustomTimeUnit() {
        super();
    }
}
