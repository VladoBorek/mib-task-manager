package cz.muni.fi.pv168.project.ui.actions.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageAction extends AbstractAction {

    private final Type type;
    public ManageAction(Type type){
        super("Manage " + type.toString().toLowerCase().replace('_', ' ') + "s");
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO to open the actual dialogue window
        switch (type){
            case TEMPLATE -> System.out.println("User clicked on Manage Template button");
            case CATEGORY -> System.out.println("User clicked on Manage Category button");
            case TIME_UNIT -> System.out.println("User clicked on Manage Time Units button");
        }
    }
}
