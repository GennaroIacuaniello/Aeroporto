package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LogInScreen {

    //Padding
    private JPanel topPadding;
    private JPanel bottomPadding;
    private JPanel leftPadding;
    private JPanel rightPadding;

    //Main scene
    private static JFrame mainFrame;
    private JPanel loginScreen;
    private JPanel loginMenu;
    private JLabel pageTitle;

    //Main fields
    private JTextField nickTextField;
    private JLabel nickLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton logInButton;

    //Bottom options
    private JButton registerButton;
    private JLabel registerPrompt;
    private JButton newPasswordButton;
    private JLabel newPasswordPrompt;

    private static Controller controller;

    public LogInScreen(ArrayList<JFrame> callingFrames, Controller controller) {

        if (!callingFrames.isEmpty()) {
            int size = callingFrames.size();

            for (int i = 0; i < size; i++) {
                System.out.println(callingFrames.get(i).getName());
                callingFrames.get(i).dispose();
            }
            System.out.println("FINE");
            callingFrames.clear();

            this.setMainFrame(callingFrames, controller);
        }
        callingFrames.addLast(mainFrame);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isValidNick(nickTextField.getText())) {
                    JOptionPane.showMessageDialog(loginScreen, "La mail inserita non è valida");
                    return;
                }
                if (nickTextField.getText().equals("non esiste")) {
                    JOptionPane.showMessageDialog(loginScreen, "Questo utente non esiste");
                    return;
                }
                if (passwordField.getText().equals("errata")) {
                    JOptionPane.showMessageDialog(loginScreen, "Password errata");
                    return;
                }

                controller.setCustomerNUser(nickTextField.getText(), Arrays.toString(passwordField.getPassword()));
                //Customer customer = getCustomer(nickTextField, passwordField);
                nickTextField.setText("");
                passwordField.setText("");
                logInButton.setEnabled(false);
                mainFrame.setVisible(false);
                new MainCustomerScreen(callingFrames, controller);
                
            }
        });

        nickTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!nickTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(true);
                }
                if (nickTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(false);
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!nickTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(true);
                }
                if (nickTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(false);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                new RegisterScreen(callingFrames, controller);
            }
        });
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        mainFrame = new JFrame("LogIn");
        mainFrame.setContentPane(new LogInScreen(new ArrayList<JFrame>(), controller).loginScreen);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    private void setMainFrame(ArrayList<JFrame> callingFrames, Controller controller) {
        mainFrame = new JFrame("LogIn");
        mainFrame.setContentPane(new LogInScreen(callingFrames, controller).loginScreen);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
/*
    private Customer getCustomer(JTextField nick, JPasswordField password) {
        return new Customer(nick.getText(), password.getText());
    }
*/
    private boolean isValidNick(String nick) {
        String validCharaters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@.-_";
        for (int i = 0; i < nick.length(); i++) {
            if (validCharaters.indexOf(nick.charAt(i)) == -1) { //indexOf returns -1 if string does not contain character
                return false;
            }
        }
        return true;
    }

}