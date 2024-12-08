package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.Constants;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class EditTimeUnitAction extends EntityBaseAction {
    private final JComboBox<TimeUnit> comboBox;

    public EditTimeUnitAction(UIDataManager data, JComboBox<TimeUnit> comboBox) {
        super("Edit TimeUnit", Icons.MANAGE_ICON, data);
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editTimeUnit();
    }

    private void editTimeUnit() {
        var timeunit = (TimeUnit) comboBox.getSelectedItem();
        if (timeunit == null) {
            return;
        }

        if (timeunit.equals(Constants.BASE_TIME_UNIT)) {
            PopUp.infoDialog(
                    "You cannot edit Base TimeUnit",
                    "Forbidden action",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        var timeUnitDialog = new TimeUnitDialog(timeunit);
        timeUnitDialog.show(comboBox, "Edit Time Unit").ifPresent(newTimeUnit -> {
            timeunit.setName(newTimeUnit.getName());
            timeunit.setRate(newTimeUnit.getRate());
            timeunit.setShortName(newTimeUnit.getShortName());
        });
        try {
            data.getTimeUnitListModel().update(timeunit);
        } catch (ValidationException e) {
            Logger.error("Edit of TimeUnit (id=" + timeunit.getId() +",name=" + timeunit.getName() + ") has failed." + e.getMessage());
            PopUp.infoDialog(
                    e.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
        Logger.info("Edited TimeUnit(id=" + timeunit.getId() +",name=" + timeunit.getName() + ")");

        comboBox.setSelectedIndex(0);
        data.getTemplateTableModel().refresh();
        data.getTaskTableModel().refresh();
    }
}
