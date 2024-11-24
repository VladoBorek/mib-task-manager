package cz.muni.fi.pv168.project.ui.dialog.manage;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.template.AddTemplateAction;
import cz.muni.fi.pv168.project.ui.actions.menu.template.DeleteTemplateAction;
import cz.muni.fi.pv168.project.ui.actions.menu.template.EditTemplateAction;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.ManageDialog;

import javax.swing.*;
import java.awt.*;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.createActionsButtonPanel;
import static cz.muni.fi.pv168.project.ui.utils.UIElements.createComboPanel;

public class ManageTemplatesDialog extends ManageDialog {

    public ManageTemplatesDialog(JFrame parent, DataManager data) {
        super(parent, "Manage templates");
        var comboBox = new JComboBox<>(new DefaultComboBoxModel<>(data.getTemplates().toArray(new Template[0])));
        add(createComboPanel("Select a Template: ", comboBox), BorderLayout.NORTH);
        add(createActionsButtonPanel(new AddTemplateAction(data, comboBox),
                new EditTemplateAction(data, comboBox),
                new DeleteTemplateAction(data, comboBox)), BorderLayout.SOUTH);
        set();
    }
}