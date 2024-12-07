package cz.muni.fi.pv168.project.ui.dialog.task;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;

import javax.swing.*;
import java.util.Objects;


/**
 * Dialog that will open when clicking on manage Time units button.
 * It offers the user to select which time unit to edit or delete.
 */
public class LogTimeDialog extends EntityDialog<Integer> {

    private final JIntegerTextField timeField = new JIntegerTextField();
    private final JComboBox<TimeUnit> timeUnitComboBox;

    public LogTimeDialog(UIDataManager data, Task task) {
        timeUnitComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnitListModel()));
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