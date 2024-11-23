package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.TimeUnitValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

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
        add("Time unit short name", shortNameField);
        add("Conversion rate to " + TimeUnit.getBaseUnit() + "  ", conversionRateField);
        setPanel();
    }

    @Override
    public TimeUnit getEntity() {
        Validator<TimeUnit> timeUnitValidator = new TimeUnitValidator();
        var validation = timeUnitValidator.validate(new TimeUnit(
                null,
                timeUnitNameField.getText(),
                shortNameField.getText(),
                conversionRateField.getValue()));
        if (!validation.isValid()) {
            PopUp.infoDialog(
                    validation.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new TimeUnit(
                null,
                timeUnitNameField.getText(),
                shortNameField.getText(),
                conversionRateField.getValue());
    }
}
