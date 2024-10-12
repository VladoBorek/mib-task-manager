package cz.muni.fi.pv168.project.ui.actions.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageAction extends AbstractAction {

    private Type type;
    public ManageAction(Type type){
        super("Manage " + type.toString().toLowerCase().replace('_', ' ') + "s");
        this.type = type;
    }

    private void Nothing(){}

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO to open the actual dialogue window
        switch (type){
            case TASK -> Nothing();
            case TEMPLATE -> Nothing();
            case CATEGORY -> Nothing();
            case TIME_UNIT -> Nothing();
        }
    }
}
