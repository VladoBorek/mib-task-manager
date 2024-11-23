package cz.muni.fi.pv168.project.ui.actions.menu.abstracts;

import cz.muni.fi.pv168.project.ui.DataManager;

import javax.swing.*;

/**
 * @author Marcel Nadzam
 */
public abstract class EntityBaseAction extends AbstractAction {

    protected DataManager data;

    public EntityBaseAction(String name, Icon icon, DataManager data) {
        super(name, icon);
        this.data = data;
    }
}
