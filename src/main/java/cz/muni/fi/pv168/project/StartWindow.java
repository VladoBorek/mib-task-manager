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
    private final static String VERSION = "0.9 Beta";
    private final JFrame frame;

    public StartWindow() {
        this.frame = new JFrame("MIB Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 430);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setIconImage(Icons.APP_ICON.getImage());
        frame.setResizable(false);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("MIB Task Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setIcon(Icons.MIB_ICON);
        titlePanel.add(titleLabel);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel(Icons.MIB_TM_IMAGE);
        imagePanel.add(imageLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        mainPanel.add(imagePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        JPanel exitPanel = new JPanel(new BorderLayout());


        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");
        loginButton.setPreferredSize(new Dimension(90, 30));
        registerButton.setPreferredSize(new Dimension(90, 30));
        exitButton.setPreferredSize(new Dimension(60, 25));

        loginButton.addActionListener(e -> {
            new MainWindow().show();
            frame.dispose();
        });
        registerButton.addActionListener(e -> System.out.println("Register button clicked"));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        exitPanel.add(exitButton, BorderLayout.EAST);

        JLabel versionLabel = new JLabel("  Version " + VERSION);
        exitPanel.add(versionLabel, BorderLayout.WEST);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(exitPanel, BorderLayout.SOUTH);


        frame.setLocationRelativeTo(null);
    }

    public void show(){
        this.frame.setVisible(true);
    }
}
