package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.ManageTimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageAction extends AbstractAction {

    private final TimeUnitListModel timeUnits;

    private final ActionType type;
    public ManageAction(ActionType type, TimeUnitListModel timeUnits){
        super(getText(type), Icons.MANAGE_ICON);
        this.type = type;
        this.timeUnits = timeUnits;
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
            case TEMPLATE -> System.out.println("User clicked on Manage Template button");
            case CATEGORY -> System.out.println("User clicked on Manage Category button");
            case TIME_UNIT -> {
                timeUnits.addUnit(new CustomTimeUnit());
                timeUnits.addUnit(new CustomTimeUnit("1", 2));
                var ej = new ManageTimeUnitDialog(null, timeUnits);
                ej.setVisible(true);
            }
        }
    }
}
