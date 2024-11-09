package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.AddAction;
import cz.muni.fi.pv168.project.ui.actions.menu.DeleteAction;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;


public class ManageTemplatesDialog extends ManageDialog {

    public ManageTemplatesDialog(JFrame parent, DataManager data) {
        super(parent, data, ActionType.TEMPLATE, "Manage templates", "Select a template:",
                new JComboBox<>(new DefaultComboBoxModel<>(data.getTemplates().toArray(new Template[0])))
                );
    }
}