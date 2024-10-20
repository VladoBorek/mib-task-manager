package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.ui.dialog.ManageCategoriesDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTemplatesDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageAction extends AbstractAction {

    private final TimeUnitListModel timeUnits;
    private final CategoryListModel categories;
    private final TemplateListModel templates;

    private final ActionType type;

    private final JFrame frame;

    public ManageAction(ActionType type, TimeUnitListModel timeUnits,
                        CategoryListModel categories,
                        TemplateListModel templates,
                        JFrame frame) {
        super(getText(type), Icons.MANAGE_ICON);
        this.type = type;
        this.timeUnits = timeUnits;
        this.categories = categories;
        this.frame = frame;
        this.templates = templates;
    }

    private static String getText(ActionType type){
        if (type == ActionType.CATEGORY) {
            return "Manage categories";
        }
        return  "Manage " + type.toString().toLowerCase().replace('_', ' ') + "s";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO to open the actual dialogue window
        switch (type){
            case TASK -> System.out.println("User clicked on Manage Task Button");
            case CATEGORY -> {
                var yo = new ManageCategoriesDialog(frame, categories);
                yo.setVisible(true);
            }
            case TIME_UNIT -> {
                var ej = new ManageTimeUnitDialog(frame, timeUnits);
                ej.setVisible(true);
            }
            case TEMPLATE -> {
                var sup = new ManageTemplatesDialog(frame, templates, categories, timeUnits);
                sup.setVisible(true);
            }
        }
    }
}
