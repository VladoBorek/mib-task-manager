package cz.muni.fi.pv168.project.business.model.Statistics;

/**
 * @author Vladimir Borek
 */
public record Statistics(
        Statistic total,
        Statistic completed,
        Statistic inProgress,
        Statistic overdue,
        TimeStatistic logAlloc
) {
}
