package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.TimeUnit;

import javax.swing.*;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * List of {@link TimeUnit} objects.
 */
public class TimeUnitListModel extends AbstractListModel<TimeUnit> {

    private final List<TimeUnit> timeUnits;

    public TimeUnitListModel(ArrayList<TimeUnit> timeUnits) {
        this.timeUnits = timeUnits;
    }

    public void addUnit(TimeUnit unit) {
        timeUnits.add(unit);
    }

    public TimeUnit[] toArray() {
        return timeUnits.toArray(new TimeUnit[0]);
    }

    public void removeCategory(TimeUnit unit) {
        timeUnits.remove(unit);
    }

    @Override
    public int getSize() {
        return timeUnits.size();
    }

    @Override
    public TimeUnit getElementAt(int index) {
        return timeUnits.get(index);
    }
}
