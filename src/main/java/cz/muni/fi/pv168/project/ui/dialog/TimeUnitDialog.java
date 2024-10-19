package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.TimeUnit;

import javax.swing.*;

/**
 * @author Maroš Pavlík
 */
public class TimeUnitDialog extends EntityDialog<TimeUnit> {

    private final JTextField timeUnitNameField = new JTextField();
    private final JIntegerTextField conversionRateField = new JIntegerTextField();

    public TimeUnitDialog() {
        conversionRateField.setValue(1);
        add("Time unit name", timeUnitNameField);
        String baseUnitName = TimeUnit.getBaseUnit();
        add("Conversion rate to base unit (" + baseUnitName + ")", conversionRateField);
    }


    @Override
    TimeUnit getEntity() {
        return new CustomTimeUnit(timeUnitNameField.getText(), conversionRateField.getValue());
    }
}
