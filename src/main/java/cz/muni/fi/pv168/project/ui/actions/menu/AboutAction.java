package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.ui.dialog.PopUp;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AboutAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        PopUp.infoDialog(
                """
                        App Name: MIB Task Manager
                        Version: 0.5 alpha
                                                
                        Description:
                        MIB Task Manager is designed to help users efficiently create, delete, and edit tasks. It allows tasks to be assigned to individuals and enables users to log work hours for each task, making it an ideal tool for tracking project progress and resource management.
                                                
                        Contact:
                        support@mib.com
                        
                        Developed by:
                        MIB IT production team
                        © 2024 Mouse in Black. All rights reserved.
                        """,
                "About this application",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
