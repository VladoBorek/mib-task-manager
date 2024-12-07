package cz.muni.fi.pv168.project.ui.resources;

import javax.swing.*;

/**
 * Icons class that loads icon images for the application from the resources.
 * This class is not instantiable and only provides static access to icons.
 */
public final class Icons {

    private static final String RESOURCE_PATH = "src/main/resources/cz.muni.fi.pv168.project.ui.resources/";
    public static final Icon DELETE_ICON = createIcon("Crystal_Clear_action_button_cancel.png");
    public static final Icon RESET_ICON = createIcon("Reset_Icon.png");
    public static final Icon MANAGE_ICON = createIcon("Crystal_Clear_action_edit.png");
    public static final Icon ADD_ICON = createIcon("Crystal_Clear_action_edit_add.png");
    public static final Icon QUIT_ICON = createIcon("Crystal_Clear_action_exit.png");
    public static final Icon IMPORT_ICON = createIcon("Crystal_Clear_action_import.png");
    public static final Icon EXPORT_ICON = createIcon("Crystal_Clear_action_export.png");
    public static final Icon INSPECT_ICON = createIcon("Inspect_Icon.png");
    public static final Icon MIB_ICON = createIcon("MIB_Icon.png");
    public static final Icon NUCLEAR_QUIT_ICON = createIcon("Nuclear.png");

    public static final ImageIcon MIB_TM_IMAGE = new ImageIcon(RESOURCE_PATH + "MIB_TM.png");

    public static final ImageIcon APP_ICON = new ImageIcon(RESOURCE_PATH + "AppIcon.png");


    /**
     * Private constructor to prevent instantiation.
     */
    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    /**
     * @param name Name of the desired Icon PNG in resources directory
     * @return Icon from the path
     */
    private static ImageIcon createIcon(String name) {
        return new ImageIcon(RESOURCE_PATH + name);
    }
}