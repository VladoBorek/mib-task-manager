package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.DeleteAction;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;


import javax.swing.*;
import java.awt.*;


/**
 * Dialog that will open when clicking on manage Time units button.
 * It offers the user to select which time unit to edit or delete.
 */
public class ManageTimeUnitDialog extends ManageDialog {

    public ManageTimeUnitDialog(JFrame parent, DataManager data) {
        super(parent, data, ActionType.TIME_UNIT, "Manage time units", "Select time unit:",
                new JComboBox<>(new DefaultComboBoxModel<>(data.getTimeUnits().toArray())));
    }
}