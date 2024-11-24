package cz.muni.fi.pv168.project.ui.actions.menu.abstracts;

import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;

public abstract class ManageAction extends AbstractAction {

    protected final DataManager data;
    protected final JFrame frame;

    public ManageAction(String text, DataManager data,
                        JFrame frame) {
        super(text, Icons.MANAGE_ICON);
        this.data = data;
        this.frame = frame;
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        switch (type){
//            case TASK -> System.out.println("User clicked on Manage Task Button");
//            case CATEGORY -> {
//                var yo = new ManageCategoriesDialog(frame, data);
//                yo.setVisible(true);
//            }
//            case TIME_UNIT -> {
//                var ej = new ManageTimeUnitDialog(frame, data);
//                ej.setVisible(true);
//            }
//            case TEMPLATE -> {
//                var sup = new ManageTemplatesDialog(frame, data);
//                sup.setVisible(true);
//            }
//        }
//    }
}
