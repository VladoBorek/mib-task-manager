package cz.muni.fi.pv168.project.util;

import cz.muni.fi.pv168.project.business.model.TimeUnit;

/**
 * @author Marcel Nadzam
 */
public class Constants {
    public static final String BASE_TIME_UNIT_NAME = "Minute";
    public static final String BASE_TIME_UNIT_SHORT = "min";
    public static final Long BASE_TIME_UNIT_ID = -1L;
    public static final TimeUnit BASE_TIME_UNIT =
            new TimeUnit(BASE_TIME_UNIT_ID, BASE_TIME_UNIT_NAME, BASE_TIME_UNIT_SHORT, 1);
}
