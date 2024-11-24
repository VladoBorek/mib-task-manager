package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;

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

        // TODO: hmm
        if (Objects.equals(timeUnit.getName(), TimeUnit.getBaseUnit())) {
            PopUp.infoDialog(
                    "You cannot delete " + TimeUnit.getBaseUnit() + " Time Unit!",
                    "Forbidden action",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        data.getTimeUnits().remove(timeUnit);
        comboBox.removeItem(timeUnit);
    }
}
