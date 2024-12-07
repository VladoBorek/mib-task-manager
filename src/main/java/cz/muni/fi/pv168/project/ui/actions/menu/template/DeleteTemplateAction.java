package cz.muni.fi.pv168.project.ui.actions.menu.template;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Marcel Nadzam
 */
public class DeleteTemplateAction extends EntityBaseAction {
    JComboBox<Template> comboBox;

    public DeleteTemplateAction(UIDataManager data, JComboBox<Template> comboBox) {
        super("Delete Template", Icons.DELETE_ICON, data);
        this.comboBox = comboBox;
    }

    public DeleteTemplateAction(UIDataManager data) {
        this(data, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deleteTemplate();
    }

    private void deleteTemplate() {
        var templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();
        if (comboBox != null) {
            var template = (Template) comboBox.getSelectedItem();
            if (template == null) {
                return;
            }
            data.getTemplateTableModel().remove(template);
            comboBox.setSelectedItem(null);
            return;
        }

        Arrays.stream(data.getTemplateTable().getSelectedRows())
                .map(data.getTemplateTable()::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(templateTableModel::deleteRow);
    }
}