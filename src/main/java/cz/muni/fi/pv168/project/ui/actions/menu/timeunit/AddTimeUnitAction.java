package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class AddTimeUnitAction extends EntityBaseAction {

    private final JComboBox<TimeUnit> comboBox;

    public AddTimeUnitAction(DataManager data, JComboBox<TimeUnit> comboBox) {
        super("Add TimeUnit", Icons.ADD_ICON, data);
        this.comboBox = comboBox;
    }

    public AddTimeUnitAction(DataManager data) {
        this(data, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addTimeUnit();
    }

    private void addTimeUnit() {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(newTimeUnit -> {
            try {
                data.getTimeUnits().add(newTimeUnit);
            } catch (ValidationException exception) {
                PopUp.infoDialog(exception.getValidationErrors(), "Input error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboBox != null) {
                comboBox.setSelectedItem(newTimeUnit);;
            }
        });
    }
}
