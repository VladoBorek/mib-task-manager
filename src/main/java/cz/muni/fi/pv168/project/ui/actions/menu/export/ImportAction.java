package cz.muni.fi.pv168.project.ui.actions.menu.export;

import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.workers.AsyncImporter;
import cz.muni.fi.pv168.project.util.Filter;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author Nikol Otáhalů
 */
public class ImportAction extends AbstractAction {
    private final Importer importer;

    public ImportAction(ImportService importService, Runnable callback) {
        super("Import application data", Icons.IMPORT_ICON);
        this.importer = new AsyncImporter(importService, callback);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int deleteData = getDataOverride();
        if(deleteData == -1){
            return;
        }

        var fileChooser = new JFileChooser();
        importer.getFormats().forEach(f -> fileChooser.setFileFilter(new Filter(f)));
        int dialogResult = fileChooser.showOpenDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();

            try {

                importer.importData(importFile.getAbsolutePath(), deleteData == 0);

            } catch (BatchOperationException ex){
                Logger.error("Import of " + importFile.getAbsolutePath() + " has failed.");
                PopUp.infoDialog(
                        "Import has failed:\n" + ex.getMessage() + "\nNo items were imported.",
                        "Import status",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } catch (Exception exception){
                Logger.error("Failed to store data." + exception.getMessage());
                PopUp.infoDialog(
                        "Import has failed:\n" + exception.getMessage() + "\nNo items were imported.",
                        "Import status",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Logger.info("Import of " + importFile.getAbsolutePath() + " has finished. Original data overwritten: " + deleteData);
            PopUp.infoDialog(
                    "Import has successfully finished.",
                    "Import status",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static int getDataOverride(){
        return PopUp.optionDialog("Do you want to override existing items or add new ones?",
                "Import Options",
                new String[]{"Override", "Add"});
    }
}
