package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.model.DataManager;
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

    private final DataManager data;

    private final JTable contentTable;

    private final JFrame frame;

    public ChooseTemplateAction(JTable contentTable, DataManager data,
                                JFrame frame) {
        super("Choose a template", Icons.ADD_ICON);
        this.data = data;
        this.frame = frame;
        this.contentTable = contentTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var wha = new ChooseTemplateDialog(frame, data, contentTable);
        wha.setVisible(true);
    }
}
