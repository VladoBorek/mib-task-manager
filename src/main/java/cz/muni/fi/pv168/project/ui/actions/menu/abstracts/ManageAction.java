package cz.muni.fi.pv168.project.ui.actions.menu.abstracts;

import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;

public abstract class ManageAction extends AbstractAction {

    protected final DataManager data;
    protected final JFrame frame;

    public ManageAction(String text, DataManager data,
                        JFrame frame) {
        super(text, Icons.MANAGE_ICON);
        this.data = data;
        this.frame = frame;
    }
}
