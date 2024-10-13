package cz.muni.fi.pv168.project.ui.resources;

import javax.swing.*;
import java.net.URL;
import javax.swing.ImageIcon;


public final class Icons {

    public static final Icon DELETE_ICON = createIcon("Crystal_Clear_action_button_cancel.png");
    public static final Icon MANAGE_ICON = createIcon("Crystal_Clear_action_edit.png");
    public static final Icon ADD_ICON = createIcon("Crystal_Clear_action_edit_add.png");
    public static final Icon QUIT_ICON = createIcon("Crystal_Clear_action_exit.png");

    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    /**
     *
     * @param name Name of the desired Icon PNG in resources directory
     * @return Icon from the path
     */
    private static ImageIcon createIcon(String name) {
        return  new ImageIcon("src/main/resources/cz.muni.fi.pv168.project.ui.resources/" + name);
    }
}