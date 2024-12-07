package cz.muni.fi.pv168.project.ui.dialog.manage;

import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.timeunit.AddTimeUnitAction;
import cz.muni.fi.pv168.project.ui.actions.menu.timeunit.DeleteTimeUnitAction;
import cz.muni.fi.pv168.project.ui.actions.menu.timeunit.EditTimeUnitAction;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.ManageDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;

import javax.swing.*;
import java.awt.*;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.createActionsButtonPanel;
import static cz.muni.fi.pv168.project.ui.utils.UIElements.createComboPanel;


/**
 * Dialog that will open when clicking on manage Time units button.
 * It offers the user to select which time unit to edit or delete.
 */
public class ManageTimeUnitDialog extends ManageDialog {

    public ManageTimeUnitDialog(JFrame parent, UIDataManager data) {
        super(parent, "Manage time units");
        var comboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnitListModel()));
        add(createComboPanel("Select a TimeUnit: ", comboBox), BorderLayout.NORTH);
        add(createActionsButtonPanel(new AddTimeUnitAction(data, comboBox),
                new EditTimeUnitAction(data, comboBox),
                new DeleteTimeUnitAction(data, comboBox)), BorderLayout.SOUTH);
        set();
    }
}