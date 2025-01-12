package cz.muni.fi.pv168.project.ui;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.ui.actions.menu.db.NuclearQuitAction;
import cz.muni.fi.pv168.project.ui.actions.menu.db.QuitAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.wiring.ProductionDependencyProvider;
import net.miginfocom.swing.MigLayout;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Maroš Pavlík
 */
public class LoginWindow {
    private final JFrame frame;
    private final JTextField usernameField;
    private final JIntegerTextField idField;

    public LoginWindow() {

        this.frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 450);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setIconImage(Icons.APP_ICON.getImage());

        this.usernameField = new JTextField();
        this.idField = new JIntegerTextField();

        frame.add(createTitlePanel(), BorderLayout.NORTH);
        frame.add(createMainPanel(), BorderLayout.CENTER);
        frame.add(createButtonPanel(), BorderLayout.SOUTH);

        frame.getContentPane().setBackground(Color.WHITE);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        addEnterKeyListener(usernameField);
        addEnterKeyListener(idField);
    }

    private boolean login() {
        if (usernameField.getText().isEmpty()) {
            Logger.error("Application user did not fill all login information");
            PopUp.infoDialog(
                    "Please fill all login information.",
                    "Missing credentials",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            MainWindow mainWindow = new MainWindow(
                    new User(usernameField.getText(), (long) idField.getValue()),
                    new ProductionDependencyProvider());
            Logger.info("Logged as " + usernameField.getText() + " with ID: " + (long) idField.getValue());
            mainWindow.show();
        } catch (Exception ex) {
            Logger.error("Fatal application error " + ex);
            showInitializationFailedDialog(ex);
        }
        return true;
    }

    public void show() {
        this.frame.setVisible(true);
    }

    private static void showInitializationFailedDialog(Exception ex) {
        EventQueue.invokeLater(() -> {
            ex.printStackTrace();
            Object[] options = {
                    new JButton(new QuitAction()),
                    new JButton(new NuclearQuitAction())
            };
            JOptionPane.showOptionDialog(
                    null,
                    "Application initialization failed.\nWhat do you want to do?",
                    "Initialization Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );
        });
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();

        JLabel titleLabel = new JLabel("MIB Task Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setIcon(Icons.MIB_ICON);
        titlePanel.add(titleLabel);

        JLabel imageLabel = new JLabel(Icons.MIB_TM_IMAGE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(imageLabel);
        titlePanel.setBackground(Color.WHITE);

        return titlePanel;
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout(
                "fill, insets 20",
                "[60:60:60][grow, fill]",
                "[]10[]10[]"
        ));

        panel.add(new JLabel("Username:"), "align right");
        panel.add(usernameField, "growx, wrap");
        panel.add(new JLabel("Id:"), "align right");
        panel.add(idField, "growx, wrap");
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(90, 30));

        loginButton.addActionListener(e -> {
            if (login()) {
                frame.dispose();
            }
        });

        buttonPanel.add(loginButton, BorderLayout.CENTER);
        buttonPanel.setBackground(Color.WHITE);

        return buttonPanel;
    }

    private void addEnterKeyListener(JComponent component) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (login()) {
                        frame.dispose();
                    }
                }
            }
        });
    }
}
