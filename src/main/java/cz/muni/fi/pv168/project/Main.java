package cz.muni.fi.pv168.project;

import com.formdev.flatlaf.FlatLightLaf;
import cz.muni.fi.pv168.project.ui.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The entry point of the application.
 */
public class Main {

    /**
     * Preventing the instantiation of the Main class
     */
    private Main() {
        throw new AssertionError("This class is not intended for instantiation.");
    }

    public static void main(String[] args) {
        initFlatLafLookAndFeel();
        EventQueue.invokeLater(() -> new LoginWindow().show());
    }

    private static void initFlatLafLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Nimbus layout initialization failed", ex);
        }
    }
}
