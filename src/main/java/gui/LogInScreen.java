package gui;

import controller.Controller;
import model.Customer;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LogInScreen {
    private static JFrame mainFrame;
    private JPanel loginScreen;
    private JPanel topPadding;
    private JPanel bottomPadding;
    private JPanel leftPadding;
    private JPanel rightPadding;
    private JPanel loginMenu;
    private JLabel pageTitle;
    private JTextField mailTextField;
    private JLabel mailLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton logInButton;
    private JButton registerButton;
    private JLabel registerPrompt;
    private JButton newPasswordButton;
    private JLabel newPasswordPrompt;
    private static Controller controller;

    public LogInScreen(ArrayList<JFrame> callingFrames, Controller controller) {

        if (!(callingFrames.isEmpty())) {
            int size = callingFrames.size();

            for (int i = 0; i < size; i++) {
                callingFrames.get(i).dispose();
            }

            callingFrames.clear();

            this.setMainFrame(controller);
        }

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isValidMail(mailTextField.getText())) {
                    JOptionPane.showMessageDialog(loginScreen, "La mail inserita non è valida");
                    return;
                }
                if (mailTextField.getText().equals("non esiste")) {
                    JOptionPane.showMessageDialog(loginScreen, "Questo utente non esiste");
                    return;
                }
                if (passwordField.getText().equals("errata")) {
                    JOptionPane.showMessageDialog(loginScreen, "Password errata");
                    return;
                }

                if (callingFrames.isEmpty()) {
                    callingFrames.addLast(mainFrame);
                }
                mainFrame.setVisible(false);

                Customer customer = getCustomer(mailTextField, passwordField);
                mailTextField.setText("");
                passwordField.setText("");
                new MainCustomerScreen(callingFrames, controller, customer);
                
            }
        });

        mailTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!mailTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(true);
                }
                if (mailTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(false);
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!mailTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(true);
                }
                if (mailTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(false);
                }
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

    private void setMainFrame(Controller controller) {
        mainFrame = new JFrame("LogIn");
        mainFrame.setContentPane(new LogInScreen(new ArrayList<JFrame>(), controller).loginScreen);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private Customer getCustomer(JTextField mail, JPasswordField password) {
        return new Customer(mail.getText(), password.getText());
    }

    private boolean isValidMail(String mail) {
        String validCharaters = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@.-_");
        for (int i = 0; i < mail.length(); i++) {
            if (validCharaters.indexOf(mail.charAt(i)) == -1) { //indexOf returns -1 if string does not contain character
                return false;
            }
        }
        return true;
    }

}