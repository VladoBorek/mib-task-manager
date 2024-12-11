package cz.muni.fi.pv168.project.ui.actions.menu.export;

import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.workers.AsyncExporter;
import cz.muni.fi.pv168.project.util.Filter;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Nikol Otáhalů
 */
public class ExportAction extends AbstractAction {
    private final Exporter exporter;

    public ExportAction(ExportService exportService){
        super("Export application data", Icons.EXPORT_ICON);
        this.exporter = new AsyncExporter(exportService,
                () -> JOptionPane.showMessageDialog(
                        null, "Export has successfully finished."));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        exporter.getFormats().forEach(f -> fileChooser.setFileFilter(new Filter(f)));
        int dialogResult = fileChooser.showSaveDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            var filter = fileChooser.getFileFilter();
            if (filter instanceof Filter) {
                exportFilePath = ((Filter) filter).decorate(exportFilePath);
            }
            try {

                exporter.exportData(exportFilePath);

            } catch (BatchOperationException ex){
                Logger.error("Export has failed.");
                PopUp.infoDialog(
                        "Export has failed:\n" + ex.getMessage(),
                        "Export status",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Logger.info("Export  has finished" + exportFilePath);
            PopUp.infoDialog(
                    "Export has successfully finished.",
                    "Export status",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

