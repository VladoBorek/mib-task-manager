package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.business.model.TimeUnit;

import javax.swing.*;

/**
 * Dialog windows that will open when creating or editing time units
 */
public class TimeUnitDialog extends EntityDialog<TimeUnit> {

    private final JTextField timeUnitNameField = new JTextField();
    private final JTextField shortNameField = new JTextField();
    private final JIntegerTextField conversionRateField = new JIntegerTextField();

    public TimeUnitDialog() {
        conversionRateField.setValue(1);

        add("Time unit name", timeUnitNameField);
        add("Time unit short name", shortNameField);
        add("Conversion rate to " + TimeUnit.getBaseUnit() + "  ", conversionRateField);
        setPanel();
    }

    public TimeUnitDialog(TimeUnit unit) {
        timeUnitNameField.setText(unit.getName());
        shortNameField.setText(unit.getShortName());
        conversionRateField.setValue(unit.getRate());


        add("Time unit name", timeUnitNameField);
        add("Conversion rate to " + TimeUnit.getBaseUnit() + "  ", conversionRateField);
        setPanel();
    }

    @Override
    TimeUnit getEntity() {
        return new CustomTimeUnit(timeUnitNameField.getText(), shortNameField.getText(), conversionRateField.getValue());
    }
}
