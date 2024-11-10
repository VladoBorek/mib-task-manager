package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class DeleteAction extends AbstractAction {
    private final JComboBox comboBox;
    private final DataManager data;
    private final ActionType type;

    // TODO: SOMEHOW FIX RAW USE OF JComboBox
    public DeleteAction(ActionType type, JComboBox comboBox, DataManager data) {
        super("Delete", Icons.DELETE_ICON);
        this.type = type;
        this.data = data;
        this.comboBox = comboBox;
    }
    @Override
    public void actionPerformed(ActionEvent e) {


        switch(type) {
            case TASK:
                var taskTableModelTableModel = (TaskTableModel) data.getTaskTable().getModel();
                Arrays.stream(data.getTaskTable().getSelectedRows())
                        .map(data.getTaskTable()::convertRowIndexToModel)
                        .boxed()
                        .sorted(Comparator.reverseOrder())
                        .forEach(taskTableModelTableModel::deleteRow);
                return;
            case TEMPLATE:
                var templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();
                Arrays.stream(data.getTemplateTable().getSelectedRows())
                        .map(data.getTemplateTable()::convertRowIndexToModel)
                        .boxed()
                        .sorted(Comparator.reverseOrder())
                        .forEach(templateTableModel::deleteRow);
                return;
            case CATEGORY:
                var category = (Category) comboBox.getSelectedItem();
                data.getCategories().remove(category);
                comboBox.removeItem(category);
                return;
            case TIME_UNIT:
                var timeUnit = (TimeUnit) comboBox.getSelectedItem();

                assert timeUnit != null;
                if (Objects.equals(timeUnit.getName(), TimeUnit.getBaseUnit())){
                    PopUp.infoDialog(
                            "You cannot delete " + TimeUnit.getBaseUnit() + " Time Unit!",
                            "Forbidden action",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                data.getTimeUnits().remove(timeUnit);

                comboBox.removeItem(timeUnit);
                return;
        }
    }
}
