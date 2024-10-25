package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.ui.dialog.ManageCategoriesDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTemplatesDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTimeUnitDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageAction extends AbstractAction {

    private final DataManager data;

    private final ActionType type;

    private final JFrame frame;

    public ManageAction(ActionType type, DataManager data,
                        JFrame frame) {
        super(getText(type), Icons.MANAGE_ICON);
        this.type = type;
        this.data = data;
        this.frame = frame;
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
                var yo = new ManageCategoriesDialog(frame, data);
                yo.setVisible(true);
            }
            case TIME_UNIT -> {
                var ej = new ManageTimeUnitDialog(frame, data);
                ej.setVisible(true);
            }
            case TEMPLATE -> {
                var sup = new ManageTemplatesDialog(frame, data);
                sup.setVisible(true);
            }
        }
    }
}
