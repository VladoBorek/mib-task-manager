package cz.muni.fi.pv168.project.business.model;

/**
 * @author Vladimir Borek
 */
public record Statistic(int total, int completed, int overdue, int inProgress, int logged, int allocated) {
}

