package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Nikol Otáhalů
 */
public class ExportAction extends AbstractAction {
    public ExportAction(){
        super("Export tasks", Icons.EXPORT_ICON);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO Export logic
    }
}
