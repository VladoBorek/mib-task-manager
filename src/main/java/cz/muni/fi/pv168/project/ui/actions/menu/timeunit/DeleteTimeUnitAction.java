package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

/**
 * @author Marcel Nadzam
 */
public class DeleteTimeUnitAction extends EntityBaseAction {
    private final JComboBox<TimeUnit> comboBox;

    public DeleteTimeUnitAction(DataManager data, JComboBox<TimeUnit> comboBox) {
        super("Delete TimeUnit", Icons.DELETE_ICON, data);
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deleteTimeUnit();
    }

    private void deleteTimeUnit() {
        var timeUnit = (TimeUnit) comboBox.getSelectedItem();
        assert timeUnit != null;

        if (Objects.equals(timeUnit.getName(), Constants.BASE_TIME_UNIT)) {
            PopUp.infoDialog(
                    "You cannot delete " + Constants.BASE_TIME_UNIT + " Time Unit!",
                    "Forbidden action",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        data.getTimeUnits().remove(timeUnit);
        comboBox.removeItem(timeUnit);
    }
}
