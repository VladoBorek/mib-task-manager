package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.util.List;

/**
 * Class for handling popupMessages for user
 *
 * @author Nikol Otáhalů
 */
public class PopUp {

    private PopUp() {
    }

    /**
     * Creates dialogue informing user about some action
     *
     * @param message     Error messages
     * @param title       Title of the popup window
     * @param messageType JOptionPane type of message to be displayed
     */
    public static void infoDialog(String message, String title, int messageType) {
        if (title == null) {
            title = "User message";
        }
        JOptionPane.showMessageDialog(null,
                message,
                title,
                messageType);
    }

    /**
     * Creates dialogue informing user about some action
     *
     * @param messages    List of error messages
     * @param title       Title of the popup window
     * @param messageType JOptionPane type of message to be displayed
     */
    public static void infoDialog(List<String> messages, String title, int messageType) {
        if (title == null) {
            title = "User message";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : messages) {
            stringBuilder.append(message);
            stringBuilder.append("\n");
        }
        JOptionPane.showMessageDialog(null,
                stringBuilder.toString(),
                title,
                messageType);
    }

    /**
     * Creates dialogue for asking the user, whether to add tasks or override them
     *
     * @param message Message for the user
     * @param title   Title of the popup window
     * @param options String option for two buttons
     * @return chosen option by teh user, 0 == Override Tasks
     */
    public static int optionDialog(String message, String title, String[] options) {
        return JOptionPane.showOptionDialog(null,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }
}
