package cz.muni.fi.pv168.project.ui.actions.menu.export;

import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;
import cz.muni.fi.pv168.project.ui.workers.AsyncExporter;
import cz.muni.fi.pv168.project.util.Filter;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Nikol Otáhalů
 */
public class ExportAction extends AbstractAction {
    private final Exporter exporter;

    public ExportAction(ExportService exportService) {
        super("Export application data", Icons.EXPORT_ICON);
        this.exporter = new AsyncExporter(exportService,
                () -> JOptionPane.showMessageDialog(
                        null, "Export has successfully finished."));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        exporter.getFormats().forEach(f -> fileChooser.addChoosableFileFilter(new Filter(f)));

        int dialogResult = fileChooser.showSaveDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            var filter = fileChooser.getFileFilter();
            if (filter instanceof Filter) {
                exportFilePath = ((Filter) filter).decorate(exportFilePath);
            }
            String finalExportFilePath = exportFilePath;
            ExceptionHandler.exceptionPopUpHandler(
                    () -> exporter.exportData(finalExportFilePath),
                    "Export status",
                    null,
                    "Export has failed"
            );
        }
    }
}

