package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.ui.dialog.ChooseTemplateDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageCategoriesDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTemplatesDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChooseTemplateAction extends AbstractAction {

    private final TimeUnitListModel timeUnits;
    private final CategoryListModel categories;
    private final TemplateListModel templates;

    private final JTable contentTable;

    private final JFrame frame;

    public ChooseTemplateAction(JTable contentTable,
                                CategoryListModel categories,
                                TimeUnitListModel timeUnits,
                                TemplateListModel templates,
                                JFrame frame) {
        super("Choose a template", Icons.ADD_ICON);
        this.timeUnits = timeUnits;
        this.categories = categories;
        this.frame = frame;
        this.templates = templates;
        this.contentTable = contentTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var wha = new ChooseTemplateDialog(frame, categories, timeUnits, templates, contentTable);
        wha.setVisible(true);
    }
}
