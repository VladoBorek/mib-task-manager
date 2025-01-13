package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class AddTimeUnitAction extends EntityBaseAction {

    private final JComboBox<TimeUnit> comboBox;

    public AddTimeUnitAction(UIDataManager data, JComboBox<TimeUnit> comboBox) {
        super("Add TimeUnit", Icons.ADD_ICON, data);
        this.comboBox = comboBox;
    }

    public AddTimeUnitAction(UIDataManager data) {
        this(data, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addTimeUnit();
    }

    private void addTimeUnit() {
        var dialog = new TimeUnitDialog(data.getDependencyProvider());
        dialog.show(null, "Add new time unit").ifPresent(newTimeUnit -> {
            ExceptionHandler.exceptionPopUpHandler(
                    () -> data.getTimeUnitListModel().add(newTimeUnit),
                    "Input error",
                    null,
                    null
            );
            if (comboBox != null) {
                comboBox.setSelectedItem(newTimeUnit);
            } else {
                PopUp.infoDialog(
                        "Time Unit " + newTimeUnit.getName() + " was added",
                        "New time unit added",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
