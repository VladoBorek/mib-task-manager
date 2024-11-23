package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;

import javax.swing.*;


public class ManageTemplatesDialog extends ManageDialog {

    public ManageTemplatesDialog(JFrame parent, DataManager data) {
        super(parent, data, ActionType.TEMPLATE, "Manage templates", "Select a template:",
                new JComboBox<>(new DefaultComboBoxModel<>(data.getTemplates().toArray(new Template[0])))
                );
    }
}