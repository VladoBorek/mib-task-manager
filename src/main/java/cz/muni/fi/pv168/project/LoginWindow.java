package cz.muni.fi.pv168.project;

import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author Maroš Pavlík
 */
public class LoginWindow {
    private final JFrame frame;
    private final JTextField usernameField;
    private final JTextField passwordField;
    public LoginWindow() {
        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 230);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setTitle("Login");
        frame.setIconImage(Icons.APP_ICON.getImage());
        frame.setResizable(false);

        this.usernameField = new JTextField();
        this.passwordField = new JPasswordField();

        frame.add(createTitlePanel(), BorderLayout.NORTH);
        frame.add(createMainPanel(), BorderLayout.CENTER);
        frame.add(createButtonPanel(), BorderLayout.SOUTH);

        frame.getContentPane().setBackground(Color.WHITE);

        frame.setLocationRelativeTo(null);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Please login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
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
        panel.add(new JLabel("Password:"), "align right");
        panel.add(passwordField, "growx, wrap");
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");
        loginButton.setPreferredSize(new Dimension(90, 30));
        exitButton.setPreferredSize(new Dimension(90, 30));

        loginButton.addActionListener(e -> {
            login();
        });
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginButton, BorderLayout.WEST);
        buttonPanel.add(exitButton, BorderLayout.EAST);
        buttonPanel.setBackground(Color.WHITE);

        return buttonPanel;
    }

    private void login() {
        new MainWindow().show();
        frame.dispose();
    }
    public void show(){
        this.frame.setVisible(true);
    }

}
