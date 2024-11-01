package cz.muni.fi.pv168.project.ui;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.User;
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
            login();
            frame.dispose();
        });

        buttonPanel.add(loginButton, BorderLayout.CENTER);
        buttonPanel.setBackground(Color.WHITE);

        return buttonPanel;
    }

    private void login() {
        MainWindow mainWindow = new MainWindow(new User(usernameField.getText(), (long) idField.getValue()));
        System.out.println("logged as: " + usernameField.getText());
        mainWindow.show();
    }
    public void show(){
        this.frame.setVisible(true);
    }

}
