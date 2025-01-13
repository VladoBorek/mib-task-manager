package cz.muni.fi.pv168.project.ui.actions.menu.template;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.TemplateDialog;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class EditTemplateAction extends EntityBaseAction {
    private final JComboBox<Template> comboBox;

    public EditTemplateAction(UIDataManager data, JComboBox<Template> comboBox) {
        super("Edit Template", Icons.MANAGE_ICON, data);
        this.comboBox = comboBox;
    }

    public EditTemplateAction(UIDataManager data) {
        this(data, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editTemplate();
    }

    private void editTemplate() {
        Template template;
        TemplateTableModel templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();

        if (comboBox != null) {
            template = (Template) comboBox.getSelectedItem();
            if (template == null) {
                return;
            }
        } else {
            var selectedRows = data.getTemplateTable().getSelectedRows();
            if (selectedRows.length != 1) {
                Logger.error("User tried to edit more than one (1) template.");
                PopUp.infoDialog("To edit template, please select exactly one (1) template.",
                        "Invalid selected rows",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int model = data.getTemplateTable().convertRowIndexToModel(selectedRows[0]);
            template = templateTableModel.getEntity(model);
        }

        var templateDialog = new TemplateDialog(data, template);
        templateDialog.show(comboBox, "Edit Template").ifPresent(newTemplate -> {
                    updateTemplate(template, newTemplate);
                    ExceptionHandler.exceptionPopUpHandler(
                            () -> templateTableModel.addRow(newTemplate),
                            "Input error",
                            null,
                            null
                    );
                }
        );
    }

    private static void updateTemplate(Template oldT, Template newT) {
        oldT.setTemplateName(newT.getTemplateName());
        oldT.setName(newT.getName());

        var alocTime = newT.getConvertedAllocatedTime();
        oldT.setTimeUnit(newT.getTimeUnit());
        oldT.setConvertedAllocatedTime(alocTime);
        oldT.setCategory(newT.getCategory());
    }
}
