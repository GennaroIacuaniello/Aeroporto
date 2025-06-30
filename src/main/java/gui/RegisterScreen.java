package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegisterScreen extends DisposableObject{

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
    private JTextField nickTextField;
    private JLabel nickLabel;

    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton registerButton;

    //Bottom options
    private JButton loginButton;
    private JLabel loginPrompt;

    public RegisterScreen(ArrayList<DisposableObject> callingObjects, Controller controller) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                new LogInScreen(callingObjects, controller);
                doOnDispose(callingObjects, controller);
                mainFrame.dispose();
            }
        });

        this.setMainFrame(callingObjects, controller);
        mainFrame.setVisible(true);

    }

    private void setMainFrame(ArrayList<DisposableObject> callingObjects, Controller controller){
        mainFrame = new JFrame("RegisterScreen");
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(registerScreen);
        mainFrame.pack();
    }

    @Override
    public void doOnDispose(ArrayList<DisposableObject> callingObjects, Controller controller) {}

    @Override
    public JFrame getFrame() {
        return mainFrame;
    }
}
