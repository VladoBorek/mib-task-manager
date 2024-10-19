package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.TimeUnit;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * List of {@link TimeUnit} objects.
 */
public class TimeUnitListModel extends AbstractListModel<TimeUnit> {

    private final List<TimeUnit> timeUnits;

    public TimeUnitListModel() {
        this.timeUnits = new ArrayList<>();
    }

    public void addUnit(TimeUnit unit) {
        timeUnits.add(unit);
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
