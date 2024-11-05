package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.export.CsvImport;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

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
        CsvImport.importTaskTableFromJson(dataManager);
    }
}
