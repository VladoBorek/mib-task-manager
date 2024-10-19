package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.data.DemoDataGenerator;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.dialog.TaskDialog;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AddAction extends AbstractAction {
    private final Type type;
    private final JTable contentTable;
    private final List<Category> categories;
    public AddAction(Type type, JTable contentTable, List<Category> categories){
        super("Add new " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
        this.type = type;
        this.contentTable = contentTable;
        this.categories = categories;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO dialog switch for different types
        var editClass = contentTable.getModel().getClass();
        if (editClass == TaskTableModel.class && type == Type.TASK){
            var taskTableModel = (TaskTableModel) contentTable.getModel();
            var dialog = new TaskDialog(new DemoDataGenerator().getTasks().get(0), categories.toArray());
            dialog.show(contentTable, "Add new Task").ifPresent(taskTableModel::addRow);
        }


    }
}
