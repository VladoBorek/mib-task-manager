package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.service.export.ExportService;
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
        ActionType exportOption = exportOption();
        System.out.println(exportOption.toString());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        exportService.getFormats().forEach(f -> fileChooser.addChoosableFileFilter(new Filter(f)));
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

    private ActionType exportOption(){
        String[] options = new String[]{"Tasks", "Categories", "Template","Time Units"};
        return ActionType.values()[
                PopUp.optionDialog(
                        "Select items to export",
                        "Export Options",
                        options)];
    }
}

