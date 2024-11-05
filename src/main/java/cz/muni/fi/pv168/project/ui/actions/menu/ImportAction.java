package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.service.export.ImportJSON;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Nikol Otáhalů
 */
public class ImportAction extends AbstractAction {
    private final DataManager dataManager;

    public ImportAction(DataManager dataManager){
        super("Import tasks", Icons.IMPORT_ICON);
        this.dataManager = dataManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ImportJSON.importTaskTableFromJson(dataManager);
    }
}
