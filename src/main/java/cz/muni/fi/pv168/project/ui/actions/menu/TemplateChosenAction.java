package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.model.DataManager;
import cz.muni.fi.pv168.project.model.Template;
import cz.muni.fi.pv168.project.ui.dialog.ChooseTemplateDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TemplateChosenAction extends AbstractAction {

    private final DataManager data;

    private final JTable contentTable;

    private final JFrame frame;
    private final Template selectedItem;

    public TemplateChosenAction(JTable contentTable,
                                DataManager data,
                                JFrame frame, Template selectedItem) {
        super("Choose a template", Icons.ADD_ICON);
        this.data = data;
        this.frame = frame;
        this.contentTable = contentTable;
        this.selectedItem = selectedItem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        var wha = new ChooseTemplateDialog(frame, data, contentTable);
        wha.setVisible(true);
    }
}
