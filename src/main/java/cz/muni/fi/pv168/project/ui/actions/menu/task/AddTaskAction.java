package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.task.AddTaskDialog;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class AddTaskAction extends EntityBaseAction {

    private final JComboBox<Template> comboBox;

    public AddTaskAction(UIDataManager data, JComboBox<Template> comboBox) {
        super("Add Task", Icons.ADD_ICON, data);
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addTask();
    }

    private void addTask() {
        TaskTableModel taskTableModel = (TaskTableModel) data.getTaskTable().getModel();
        Template template = (Template) comboBox.getSelectedItem();

        AddTaskDialog dialog;
        if (template == null
                || template.getTemplateName().compareTo("<Don't use a template>") == 0) {
            dialog = new AddTaskDialog(null, data);
        } else {
            dialog = new AddTaskDialog(new Task(template), data);
        }

        dialog.show(data.getTaskTable(), "Add new Task").ifPresent(newTask -> {
            ExceptionHandler.exceptionPopUpHandler(
                    () -> taskTableModel.addRow(newTask),
                    "Input error",
                    null,
                    "Edit was not successful."
            );
        });

        data.getStatisticsTableModel().refreshStatistics();
    }
}
