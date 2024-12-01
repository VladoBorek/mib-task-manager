package cz.muni.fi.pv168.project.ui.dialog.abstracts;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vladimir Borek
 */
public abstract class ManageDialog extends JDialog {
    public ManageDialog(JFrame parent, String text) {
        super(parent, text, true);
        setLayout(new BorderLayout());
    }

    protected void set() {
        pack();
        setLocationRelativeTo(getParent());
    }
}