package cz.muni.fi.pv168.project.ui.actions.menu.template;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.TemplateDialog;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class AddTemplateAction extends EntityBaseAction {

    private final JComboBox<Template> comboBox;

    public AddTemplateAction(DataManager data, JComboBox<Template> comboBox) {
        super("Add Template", Icons.ADD_ICON, data);
        this.comboBox = comboBox;
    }

    public AddTemplateAction(DataManager data) {
        this(data, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addTemplate();
    }

    private void addTemplate() {
        TemplateTableModel templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();
        TemplateDialog dialog = new TemplateDialog(data, null);

        dialog.show(data.getTaskTable(), "Add new Template").ifPresent(newTemplate -> {
            templateTableModel.addRow(newTemplate);
            // TODO: Hmm
            if (comboBox != null) {
                DefaultComboBoxModel<Template> model = (DefaultComboBoxModel<Template>) comboBox.getModel();
                model.addElement(newTemplate);
                comboBox.setSelectedItem(newTemplate);
            }
        });
    }
}
