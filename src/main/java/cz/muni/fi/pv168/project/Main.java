package cz.muni.fi.pv168.project;

import cz.muni.fi.pv168.project.ui.MainWindow;

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
        new StartWindow().show();
    }
}
