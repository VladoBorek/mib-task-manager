package cz.muni.fi.pv168.project.ui.actions.menu.template;

import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.ManageAction;
import cz.muni.fi.pv168.project.ui.dialog.manage.ManageTemplatesDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageTemplatesAction extends ManageAction {

    public ManageTemplatesAction(UIDataManager data, JFrame frame) {
        super("Manage Templates", data, frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var ej = new ManageTemplatesDialog(frame, data);
        ej.setVisible(true);
    }
}
