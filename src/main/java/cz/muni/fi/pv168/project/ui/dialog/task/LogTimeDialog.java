package cz.muni.fi.pv168.project.ui.dialog.task;

import com.github.lgooddatepicker.zinternaltools.Pair;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.utils.UIElements;

import javax.swing.*;
import java.util.Objects;


/**
 * Dialog that will open when clicking on manage Time units button.
 * It offers the user to select which time unit to edit or delete.
 */
public class LogTimeDialog extends EntityDialog<Pair<Double, TimeUnit>> {

    private final JFormattedTextField timeField;
    private final JComboBox<TimeUnit> timeUnitComboBox;

    public LogTimeDialog(UIDataManager data, Task task) {
        timeField = UIElements.createDecimalFormattedTextField();

        timeUnitComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnitListModel()));
        timeUnitComboBox.setSelectedItem(task.getTimeUnit());

        add("Time", timeField);
        add("Time Unit", timeUnitComboBox);

        setPanel();
    }

    @Override
    public Pair<Double, TimeUnit> getEntity() {
        double time = ((Number) timeField.getValue()).doubleValue();
        TimeUnit selectedUnit = (TimeUnit) Objects.requireNonNull(timeUnitComboBox.getSelectedItem());
        return new Pair<>(time, selectedUnit);
    }


}