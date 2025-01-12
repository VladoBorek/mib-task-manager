package cz.muni.fi.pv168.project.business.model.Statistics;

/**
 * @author Vladimir Borek
 */

public class Statistic {
    private final int value;

    public Statistic(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
