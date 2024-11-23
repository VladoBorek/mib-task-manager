package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.DeleteAction;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


/**
 * Dialog that will open when clicking on manage Time units button.
 * It offers the user to select which time unit to edit or delete.
 */
public class LogTimeDialog extends EntityDialog<Integer> {

    private final JIntegerTextField timeField = new JIntegerTextField();
    private final JComboBox<TimeUnit> timeUnitComboBox;

    public LogTimeDialog(DataManager data, Task task) {
        timeUnitComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnits()));
        timeUnitComboBox.setSelectedItem(task.getTimeUnit());
        add("Time", timeField);
        add("Time Unit", timeUnitComboBox);

        setPanel();
    }


    @Override
    public Integer getEntity() {
        return timeField.getValue() * ((TimeUnit) Objects.requireNonNull(timeUnitComboBox.getSelectedItem())).getRate();
    }
}