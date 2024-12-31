package cz.muni.fi.pv168.project.ui.dialog.task;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;


/**
 * Dialog that will open when clicking on manage Time units button.
 * It offers the user to select which time unit to edit or delete.
 */
public class LogTimeDialog extends EntityDialog<Double> {

    private final JFormattedTextField  timeField;
    private final JComboBox<TimeUnit> timeUnitComboBox;

    public LogTimeDialog(UIDataManager data, Task task) {
        NumberFormat format = new DecimalFormat("#.##");
        format.setGroupingUsed(false);
        timeField = new JFormattedTextField(format);
        timeField.setValue(task.getLoggedTime() != null ? task.getLoggedTime() : 0.0);

        timeUnitComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnitListModel()));
        timeUnitComboBox.setSelectedItem(task.getTimeUnit());
        add("Time", timeField);
        add("Time Unit", timeUnitComboBox);

        setPanel();
    }


    @Override
    public Double getEntity() {
        double time = ((Number) timeField.getValue()).doubleValue();
        return time * ((TimeUnit) Objects.requireNonNull(timeUnitComboBox.getSelectedItem())).getRate();
    }
}