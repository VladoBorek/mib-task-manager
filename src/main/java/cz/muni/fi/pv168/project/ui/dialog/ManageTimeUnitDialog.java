package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;


import javax.swing.*;


/**
 * Dialog that will open when clicking on manage Time units button.
 * It offers the user to select which time unit to edit or delete.
 */
public class ManageTimeUnitDialog extends ManageDialog {

    public ManageTimeUnitDialog(JFrame parent, DataManager data) {
        super(parent, data, ActionType.TIME_UNIT, "Manage time units", "Select time unit:",
                new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnits())));
    }
}