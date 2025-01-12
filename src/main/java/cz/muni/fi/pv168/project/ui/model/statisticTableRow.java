package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Statistics.Statistic;

/**
 * @author Vladimir Borek
 */
public record statisticTableRow(String name, Statistic globalStatistic, Statistic filteredStatistic) {
}
