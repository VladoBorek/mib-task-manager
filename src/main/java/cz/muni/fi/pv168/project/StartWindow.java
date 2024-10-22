package cz.muni.fi.pv168.project;

import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Maroš Pavlík
 */
public class StartWindow {
    private final static String VERSION = "0.000009 Alpha";
    private final JFrame frame;

    public StartWindow() {
        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 430);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setIconImage(Icons.APP_ICON.getImage());
        frame.setResizable(false);

        frame.add(createTitlePanel(), BorderLayout.NORTH);
        frame.add(createMainPanel(), BorderLayout.CENTER);
        frame.add(createExitPanel(), BorderLayout.SOUTH);

        frame.getContentPane().setBackground(Color.WHITE);

        frame.setLocationRelativeTo(null);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("MIB Task Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setIcon(Icons.MIB_ICON);
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.WHITE);
        return titlePanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createImagePanel(), BorderLayout.NORTH);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        mainPanel.setBackground(Color.WHITE);
        return mainPanel;
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel(Icons.MIB_TM_IMAGE);
        imagePanel.add(imageLabel);
        imagePanel.setBackground(Color.WHITE);
        return imagePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        loginButton.setPreferredSize(new Dimension(90, 30));
        registerButton.setPreferredSize(new Dimension(90, 30));

        loginButton.addActionListener(e -> {
            new MainWindow().show();
            frame.dispose();
        });
        registerButton.addActionListener(e -> System.out.println("Register button clicked"));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.setBackground(Color.WHITE);

        return buttonPanel;
    }

    private JPanel createExitPanel() {
        JPanel exitPanel = new JPanel(new BorderLayout());

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(60, 25));
        exitButton.addActionListener(e -> System.exit(0));

        JLabel versionLabel = new JLabel("  Version " + VERSION);
        exitPanel.add(versionLabel, BorderLayout.WEST);
        exitPanel.add(exitButton, BorderLayout.EAST);
        exitPanel.setBackground(Color.WHITE);

        return exitPanel;
    }

    public void show(){
        this.frame.setVisible(true);
    }
}
