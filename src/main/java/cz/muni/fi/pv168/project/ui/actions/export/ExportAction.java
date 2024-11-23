package cz.muni.fi.pv168.project.ui.actions.export;

import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.Filter;

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
                new String[]{"Tasks", "Categories", "Template","Time Units"});
        if (userChoice < 0 || userChoice > 3) {
            return;
        }

        ActionType exportOption = ActionType.values()[userChoice];

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        exportService.getFormats().forEach(f -> fileChooser.setFileFilter(new Filter(f)));
        int dialogResult = fileChooser.showSaveDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportFile = fileChooser.getSelectedFile().getAbsolutePath();
            var filter = fileChooser.getFileFilter();
            if (filter instanceof Filter) {
                exportFile = ((Filter) filter).decorate(exportFile);
            }

            exportService.exportData(exportFile, exportOption);
            PopUp.infoDialog("Export has successfully finished.",
                    "Export status",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

