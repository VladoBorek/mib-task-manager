package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Maroš Pavlík
 */
public class ResetFilterAction extends AbstractAction {

    private final Type type;
    public ResetFilterAction(Type type){
        super("Reset " + type.toString().toLowerCase().replace('_', ' ') + 's', Icons.DELETE_ICON);
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO to open the actual dialogue window
        switch (type){
            case TASK -> System.out.println("User clicked on Add Task button");
            case TEMPLATE -> System.out.println("User clicked on Add Template button");
            case CATEGORY -> System.out.println("User clicked on Add Category button");
            case TIME_UNIT -> System.out.println("User clicked on Add Time Unit button");
            case FILTER -> System.out.println("User clicked on Reset Filters button");
        }
    }

}
