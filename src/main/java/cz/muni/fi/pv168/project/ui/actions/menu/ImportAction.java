package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author Nikol Otáhalů
 */
public class ImportAction extends AbstractAction {
    public ImportAction(){
        super("Import tasks", Icons.IMPORT_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a json file to import");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files (*.json)", "json"));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("File selected..");
        } else {
            System.out.println("File selection cancelled.");
        }
    }
}
