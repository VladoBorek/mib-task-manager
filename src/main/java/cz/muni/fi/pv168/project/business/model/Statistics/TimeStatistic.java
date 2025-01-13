package cz.muni.fi.pv168.project.business.model.Statistics;

/**
 * @author Vladimir Borek
 */
public class TimeStatistic extends Statistic {
    private final double logged;
    private final double allocated;

    public TimeStatistic(double logged, double allocated) {
        super(0);
        this.logged = logged;
        this.allocated = allocated;
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f min", logged, allocated);
    }
}
