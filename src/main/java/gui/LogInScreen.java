package gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.max;

public class LogInScreen {

    //Padding
    private JPanel topPadding;
    private JPanel bottomPadding;
    private JPanel leftPadding;
    private JPanel rightPadding;

    //Main scene
    private static JFrame mainFrame;
    private JScrollPane loginMenuScrollContainer;
    private JPanel loginScreen;
    private JPanel loginMenu;
    private JLabel pageTitle;

    //Main fields
    private JTextField usernameTextField;
    private JLabel usernameLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton logInButton;

    //Bottom options
    private JButton registerButton;
    private JButton newPasswordButton;

    public LogInScreen(ArrayList<JFrame> callingFrames, Controller controller) {
        if (!callingFrames.isEmpty()) {
            int size = callingFrames.size();

            for (JFrame callingFrame : callingFrames) {
                callingFrame.dispose();
            }
            callingFrames.clear();

            this.setMainFrame(callingFrames, controller);
        }
        callingFrames.addLast(mainFrame);

        loginMenuScrollContainer.getVerticalScrollBar().setUnitIncrement(4);
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        registerButton.setPreferredSize(newPasswordButton.getPreferredSize());

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateLogin(usernameTextField.getText(), passwordField.getPassword())) {
                    login(callingFrames, controller);
                }
            }
        });

        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!usernameTextField.getText().isEmpty() && passwordField.getPassword().length != 0) {
                    logInButton.setEnabled(true);
                }
                if (usernameTextField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                    logInButton.setEnabled(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (validateLogin(usernameTextField.getText(), passwordField.getPassword())) {
                        login(callingFrames, controller);
                    }
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!usernameTextField.getText().isEmpty() && passwordField.getPassword().length != 0) {
                    logInButton.setEnabled(true);
                }
                if (usernameTextField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                    logInButton.setEnabled(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (validateLogin(usernameTextField.getText(), passwordField.getPassword())) {
                        login(callingFrames, controller);
                    }
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

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizePadding();
            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                resizePadding();
            }
        });

        mainFrame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                resizePadding();
            }
        });
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }
        catch (UnsupportedLookAndFeelException e){
            String[] options = {"Continua", "Chiudi"};
            int action = JOptionPane.showOptionDialog(null,  "<html><center>Il tuo device non supporta FlatLaf,<br>" +
                            "utilizzerai un'altra versione dell'app,<br>" +
                            "tutte le funzioni rimarranno invariate.</center></html>",
                        "Title", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, null);
            if(action == 1 || action == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }

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

    private boolean isValidUsername(String username) {
        String validCharaters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@.-_";
        for (int i = 0; i < username.length(); i++) {
            if (validCharaters.indexOf(username.charAt(i)) == -1) { //indexOf returns -1 if string does not contain character
                return false;
            }
        }
        return true;
    }

    private boolean validateLogin(String username, char[] password) {
        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(loginScreen, "Inserire username e password");
            return false;
        }
        if (!isValidUsername(usernameTextField.getText())) {
            JOptionPane.showMessageDialog(loginScreen, "Username non valido");
            return false;
        }
        if (usernameTextField.getText().equals("non esiste")) {
            JOptionPane.showMessageDialog(loginScreen, "Questo utente non esiste");
            return false;
        }
        if (Arrays.equals(passwordField.getPassword(), new char[]{'e', 'r', 'r', 'a', 't', 'a'})) {
            JOptionPane.showMessageDialog(loginScreen, "Password errata");
            return false;
        }
        return true;
    }

    private void login(ArrayList<JFrame> callingFrames, Controller controller) {
        controller.setCustomerNUser(usernameTextField.getText(), passwordField.getPassword());
        usernameTextField.setText("");
        passwordField.setText("");
        logInButton.setEnabled(false);
        mainFrame.setVisible(false);
        new MainCustomerScreen(callingFrames, controller);
    }

    private void resizePadding() {
        /*the formulas make it so that the loginMenu is always 480 by 320
         /Dimension - 2paddingDimension = desiredDimension -> paddingDimension = Dimension/2 - desiredDimension/2
         /there are some adjustments because of the border and the decorator
         /the actual dimension of the loginMenufluctuate a little (max 2 in either direction)
         /(not only because of parity, but i have no idea what is it other than that)
         */
        topPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(max(mainFrame.getHeight() / 2 - 239, 36), 0, 0, 0), -1, -1));
        bottomPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, max(mainFrame.getHeight() / 2 - 239, 36), 0), -1, -1));
        leftPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, max(mainFrame.getWidth() / 2 - 168, 27), 0, 0), -1, -1));
        rightPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, max(mainFrame.getWidth() / 2 - 168, 27)), -1, -1));

    }

}