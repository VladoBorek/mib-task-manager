package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.util.Constants;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;
import org.tinylog.Logger;

import javax.swing.*;

/**
 * Dialog windows that will open when creating or editing time units
 */
public class TimeUnitDialog extends EntityDialog<TimeUnit> {

    private final DependencyProvider provider;
    private final JTextField timeUnitNameField = new JTextField();
    private final JTextField shortNameField = new JTextField();
    private final JIntegerTextField conversionRateField = new JIntegerTextField();

    public TimeUnitDialog(DependencyProvider provider) {
        this.provider = provider;
        conversionRateField.setValue(1);

        addTimeUnitFields();
        setPanel();
    }

    public TimeUnitDialog(TimeUnit unit, DependencyProvider provider) {
        this.provider = provider;
        timeUnitNameField.setText(unit.getName());
        shortNameField.setText(unit.getShortName());
        conversionRateField.setValue(unit.getRate());

        addTimeUnitFields();
        setPanel();
    }

    private void addTimeUnitFields() {
        add("Time unit name", timeUnitNameField);
        add("Time unit short name", shortNameField);
        add("Conversion rate to " + Constants.BASE_TIME_UNIT_NAME + "  ", conversionRateField);
        setPanel();
    }

    @Override
    public TimeUnit getEntity() {
        Validator<TimeUnit> timeUnitValidator = provider.getTimeUnitValidator();

        TimeUnit newTimeUnit = new TimeUnit(
                null,
                timeUnitNameField.getText(),
                shortNameField.getText(),
                conversionRateField.getValue());

        var validation = timeUnitValidator.validate(newTimeUnit);

        if (!validation.isValid()) {
            Logger.error("TimeUnit failed Validation " + validation.getValidationErrors());
            PopUp.infoDialog(
                    validation.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return newTimeUnit;
    }
}
