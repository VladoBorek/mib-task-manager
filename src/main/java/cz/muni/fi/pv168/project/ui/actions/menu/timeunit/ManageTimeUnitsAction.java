package cz.muni.fi.pv168.project.ui.actions.menu.timeunit;

import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.ManageAction;
import cz.muni.fi.pv168.project.ui.dialog.manage.ManageTimeUnitDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageTimeUnitsAction extends ManageAction {

    public ManageTimeUnitsAction(UIDataManager data, JFrame frame) {
        super("Manage TimeUnits", data, frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var ej = new ManageTimeUnitDialog(frame, data);
        ej.setVisible(true);
    }
}
