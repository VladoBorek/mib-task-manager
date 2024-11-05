package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.service.export.ExportJSON;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Nikol Otáhalů
 */
public class ExportAction extends AbstractAction {
    private final JTable taskTable;
    public ExportAction(DataManager data){
        super("Export tasks", Icons.EXPORT_ICON);
        this.taskTable = data.getTaskTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExportJSON.exportTaskTableToJson(taskTable);
    }
}

