package cz.muni.fi.pv168.project.ui.actions.menu.abstracts;

import cz.muni.fi.pv168.project.ui.UIDataManager;

import javax.swing.*;

/**
 * @author Marcel Nadzam
 */
public abstract class EntityBaseAction extends AbstractAction {

    protected UIDataManager data;

    public EntityBaseAction(String name, Icon icon, UIDataManager data) {
        super(name, icon);
        this.data = data;
    }
}
