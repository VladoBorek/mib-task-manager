package cz.muni.fi.pv168.project.ui.actions.menu.export;

import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.ActionType;
import cz.muni.fi.pv168.project.util.Filter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author Nikol Otáhalů
 */
public class ImportAction extends AbstractAction {
    private final ImportService importService;
    private final Runnable callback;

    public ImportAction(ImportService importService, Runnable callback) {
        super("Import application data", Icons.IMPORT_ICON);
        this.importService = importService;
        this.callback = callback;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var userChoice = PopUp.optionDialog(
                "Select items to import",
                "Import Options",
                new String[]{"Tasks", "Categories", "Template","Time Units", "Work Logs"});
        if (userChoice < 0 || userChoice > 4) {
            return;
        }

        ActionType importOption = ActionType.values()[userChoice];

        int importChoice = PopUp.optionDialog("Do you want to override existing items or add new ones?",
                "Import Options",
                new String[]{"Override", "Add"});
        boolean deleteData = importChoice == 0;

        var fileChooser = new JFileChooser();
        importService.getFormats().forEach(f -> fileChooser.setFileFilter(new Filter(f)));
        int dialogResult = fileChooser.showOpenDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();

            try {

                importService.importData(importFile.getAbsolutePath(), importOption, deleteData);

            } catch (BatchOperationException ex){
                PopUp.infoDialog("Import has failed:\n" + ex.getMessage() + "\nNo items were imported.",
                        "Import status",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            PopUp.infoDialog("Import has successfully finished.",
                    "Import status",
                    JOptionPane.INFORMATION_MESSAGE);
            callback.run();
        }
    }
}
