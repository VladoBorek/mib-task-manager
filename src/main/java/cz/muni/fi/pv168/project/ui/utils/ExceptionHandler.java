package cz.muni.fi.pv168.project.ui.utils;

import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import org.tinylog.Logger;

import javax.swing.*;

public class ExceptionHandler {

    public static void exceptionPopUpHandler(Runnable runnable, String title, String success, String failure) {
        try {
            runnable.run();
        } catch (ValidationException e) {
            Logger.error(e.getValidationErrors());
            PopUp.infoDialog(
                    e.getValidationErrors(),
                    title,
                    JOptionPane.ERROR_MESSAGE);
            return;

        } catch (Exception e) {
            Logger.error(e.getMessage());
            PopUp.infoDialog(
                    failure + "\n" + e.getMessage(),
                    title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Logger.info(runnable + " has successfully finished.");
        if (success != null) {
            PopUp.infoDialog(
                    success,
                    title,
                    JOptionPane.INFORMATION_MESSAGE);
        }


    }
}
