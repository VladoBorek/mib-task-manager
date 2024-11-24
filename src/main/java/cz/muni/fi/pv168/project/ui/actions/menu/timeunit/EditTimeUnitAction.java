package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class EditTimeUnitAction extends EntityBaseAction {
    private final JComboBox<TimeUnit> comboBox;

    public EditTimeUnitAction(DataManager data, JComboBox<TimeUnit> comboBox) {
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

        var timeUnitDialog = new TimeUnitDialog(timeunit);
        timeUnitDialog.show(comboBox, "Edit Time Unit").ifPresent(newTimeUnit -> {
            timeunit.setName(newTimeUnit.getName());
            timeunit.setRate(newTimeUnit.getRate());
            timeunit.setShortName(newTimeUnit.getShortName());
        });

        data.getTimeUnits().update(timeunit);
        comboBox.setSelectedIndex(0);
    }
}
