package cz.muni.fi.pv168.project.ui.actions.menu.template;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.TemplateDialog;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class AddTemplateAction extends EntityBaseAction {

    private final JComboBox<Template> comboBox;

    public AddTemplateAction(UIDataManager data, JComboBox<Template> comboBox) {
        super("Add Template", Icons.ADD_ICON, data);
        this.comboBox = comboBox;
    }

    public AddTemplateAction(UIDataManager data) {
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
            try {
                templateTableModel.addRow(newTemplate);
            } catch (ValidationException exception) {
                Logger.error("Template was not added: " + exception.getMessage());
                PopUp.infoDialog(
                        exception.getValidationErrors(),
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Logger.info("Added new Template (id=" + newTemplate.getId() + ",name=" + newTemplate.getName() + ")");

            if (comboBox != null) {
                comboBox.setSelectedItem(newTemplate);
            } else {
                PopUp.infoDialog(
                        "Template " + newTemplate.getName() + " was added",
                        "New template added",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
