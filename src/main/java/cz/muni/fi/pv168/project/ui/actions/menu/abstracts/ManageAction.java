package cz.muni.fi.pv168.project.ui.actions.menu.abstracts;

import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;

public abstract class ManageAction extends AbstractAction {

    protected final UIDataManager data;
    protected final JFrame frame;

    public ManageAction(String text, UIDataManager data,
                        JFrame frame) {
        super(text, Icons.MANAGE_ICON);
        this.data = data;
        this.frame = frame;
    }
}
