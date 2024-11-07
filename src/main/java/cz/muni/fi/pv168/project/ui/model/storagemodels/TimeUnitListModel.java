package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseListModel;

import javax.swing.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * List of {@link TimeUnit} objects.
 */
public class TimeUnitListModel extends BaseListModel<TimeUnit> {

    public TimeUnitListModel(ArrayList<TimeUnit> timeUnits, CrudService<TimeUnit> timeUnitCrudService) {
        super(timeUnits, timeUnitCrudService);
    }

    public TimeUnit[] toArray() {
        return items.toArray(new TimeUnit[0]);
    }
}
