package cz.muni.fi.pv168.project.ui.actions.menu.category;

import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.ManageAction;
import cz.muni.fi.pv168.project.ui.dialog.manage.ManageCategoriesDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageCategoriesAction extends ManageAction {

    public ManageCategoriesAction(DataManager data, JFrame frame) {
        super("Manage Categories", data, frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var ej = new ManageCategoriesDialog(frame, data);
        ej.setVisible(true);
    }
}
