package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

public class RegisterScreen {

    //Padding
    private JPanel topPadding;
    private JPanel bottomPadding;
    private JPanel leftPadding;
    private JPanel rightPadding;

    //Main scene
    private static JFrame mainFrame;
    private JPanel registerScreen;
    private JPanel registerMenu;
    private JLabel pageTitle;

    //Main fields
    private JTextField mailTextField;
    private JLabel mailLabel;
    private JTextField usernameTextField;
    private JLabel usernameLabel;

    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton registerButton;

    //Bottom options
    private JButton loginButton;
    private JLabel loginPrompt;

    public RegisterScreen(ArrayList<JFrame> callingFrames, Controller controller) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogInScreen(callingFrames, controller);
            }
        });

        this.setMainFrame(callingFrames, controller);
        mainFrame.setVisible(true);

    }

    private void setMainFrame(ArrayList<JFrame> callingFrames, Controller controller) {
        mainFrame = new JFrame("RegisterScreen");
        callingFrames.addLast(mainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(registerScreen);
        mainFrame.pack();
    }

}
