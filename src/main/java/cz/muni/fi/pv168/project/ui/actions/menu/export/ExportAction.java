package cz.muni.fi.pv168.project.ui.actions.menu.export;

import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.util.ActionType;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.Filter;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Nikol Otáhalů
 */
public class ExportAction extends AbstractAction {
    private final ExportService exportService;

    public ExportAction(ExportService exportService){
        super("Export application data", Icons.EXPORT_ICON);
        this.exportService = exportService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var userChoice = PopUp.optionDialog(
                "Select items to export",
                "Export Options",
                new String[]{"Tasks", "Categories", "Template","Time Units", "Work Logs"});
        if (userChoice < 0 || userChoice > 4) {
            return;
        }

        ActionType exportOption = ActionType.values()[userChoice];

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        exportService.getFormats().forEach(f -> fileChooser.setFileFilter(new Filter(f)));
        int dialogResult = fileChooser.showSaveDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            var filter = fileChooser.getFileFilter();
            if (filter instanceof Filter) {
                exportFilePath = ((Filter) filter).decorate(exportFilePath);
            }
            try {

                exportService.exportData(exportFilePath, exportOption);

            } catch (BatchOperationException ex){
                Logger.error("Export of " + exportOption.name() + "S has failed.");
                PopUp.infoDialog(
                        "Export has failed:\n" + ex.getMessage(),
                        "Export status",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Logger.info("Export of" + exportOption.name() + "S has finished" + exportFilePath);
            PopUp.infoDialog(
                    "Export has successfully finished.",
                    "Export status",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

