package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;
import cz.muni.fi.pv168.project.util.Constants;

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

        var timeUnitDialog = new TimeUnitDialog(timeunit, data.getDependencyProvider());
        timeUnitDialog.show(comboBox, "Edit Time Unit").ifPresent(newTimeUnit -> {
            timeunit.setName(newTimeUnit.getName());
            timeunit.setRate(newTimeUnit.getRate());
            timeunit.setShortName(newTimeUnit.getShortName());
        });

        ExceptionHandler.exceptionPopUpHandler(
                () -> data.getTimeUnitListModel().update(timeunit),
                "Input error",
                null,
                null
        );

        comboBox.setSelectedIndex(0);
        data.getTemplateTableModel().refresh();
        data.getTaskTableModel().refresh();
    }
}
