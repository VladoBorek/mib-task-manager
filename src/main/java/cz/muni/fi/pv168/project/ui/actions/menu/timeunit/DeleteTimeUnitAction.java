package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.Constants;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class DeleteTimeUnitAction extends EntityBaseAction {
    private final JComboBox<TimeUnit> comboBox;

    public DeleteTimeUnitAction(UIDataManager data, JComboBox<TimeUnit> comboBox) {
        super("Delete TimeUnit", Icons.DELETE_ICON, data);
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deleteTimeUnit();
    }

    private void deleteTimeUnit() {
        var timeUnit = (TimeUnit) comboBox.getSelectedItem();
        if (timeUnit == null) {
            return;
        }

        if (timeUnit.equals(Constants.BASE_TIME_UNIT)) {
            PopUp.infoDialog(
                    "You cannot delete " + Constants.BASE_TIME_UNIT_NAME + " Time Unit!",
                    "Forbidden action",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (data.getDependencyProvider().getTimeUnitService().isTimeUnitInUse(timeUnit)) {
            Logger.info("User tried to delete " + timeUnit + " which is currently in use.");
            PopUp.infoDialog(
                    "You can't delete this time unit, it is currently in use.",
                    "Action blocked",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        data.getTimeUnitListModel().remove(timeUnit);
        comboBox.setSelectedItem(null);
    }
}
