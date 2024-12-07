package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.task.ChooseTemplateDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChooseTemplateAction extends AbstractAction {

    private final UIDataManager data;

    private final JFrame frame;

    public ChooseTemplateAction(UIDataManager data,
                                JFrame frame) {
        super("Choose a template", Icons.ADD_ICON);
        this.data = data;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var wha = new ChooseTemplateDialog(frame, data);
        wha.setVisible(true);
    }
}
