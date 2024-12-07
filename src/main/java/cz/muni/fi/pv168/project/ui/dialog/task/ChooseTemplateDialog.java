package cz.muni.fi.pv168.project.ui.dialog.task;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.task.AddTaskAction;

import javax.swing.*;
import java.awt.*;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.createComboPanel;
import static cz.muni.fi.pv168.project.ui.utils.UIElements.createDialogClosingButton;


public class ChooseTemplateDialog extends JDialog {

    public ChooseTemplateDialog(JFrame parent, UIDataManager data) {
        super(parent, "Choose a template", true);
        setLayout(new BorderLayout());

        var comboBox = setupComboBox(data);
        var comboPanel = createComboPanel("Select a template:", comboBox);
        var okButton = createDialogClosingButton("OK", new AddTaskAction(data, comboBox), this);

        addComponents(comboPanel, okButton);
        finalizeDialogSetup(parent);
    }

    private JComboBox<Template> setupComboBox(UIDataManager data) {
        var comboBox = new JComboBox<>(new DefaultComboBoxModel<>(data.getTemplateTableModel().getAllRows().toArray(new Template[0])));
        var emptyTemplate = new Template();

        comboBox.addItem(emptyTemplate);
        comboBox.setSelectedItem(emptyTemplate);

        return comboBox;
    }

    private void addComponents(JPanel comboPanel, JButton okButton) {
        add(comboPanel, BorderLayout.NORTH);
        add(okButton, BorderLayout.SOUTH);
    }

    private void finalizeDialogSetup(JFrame parent) {
        pack();
        setLocationRelativeTo(parent);
    }
}