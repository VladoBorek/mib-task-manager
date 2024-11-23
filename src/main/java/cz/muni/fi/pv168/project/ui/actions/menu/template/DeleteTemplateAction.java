package cz.muni.fi.pv168.project.ui.actions.menu.template;

import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Marcel Nadzam
 */
public class DeleteTemplateAction extends EntityBaseAction {

    public DeleteTemplateAction(DataManager data) {
        super("Delete Template", Icons.DELETE_ICON, data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deleteTemplate();
    }

    private void deleteTemplate() {
        var templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();
        Arrays.stream(data.getTemplateTable().getSelectedRows())
                .map(data.getTemplateTable()::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(templateTableModel::deleteRow);
    }
}
