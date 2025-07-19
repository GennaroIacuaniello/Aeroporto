package gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import static java.lang.Math.max;

public class LogInScreen extends DisposableObject {

    //Padding
    private JPanel topPadding;
    private JPanel bottomPadding;
    private JPanel leftPadding;
    private JPanel rightPadding;

    //Main scene
    private static JFrame mainFrame;
    private JPanel loginScreen;
    private JScrollPane loginMenuScrollContainer;
    private JPanel loginMenu;
    private JLabel pageTitle;

    //Main fields
    private JTextField usernameTextField;
    private JLabel usernameLabel;
    private PasswordHandler passwordField;
    private JLabel passwordLabel;
    private JButton logInButton;

    //Bottom options
    private JButton registerButton;

    public LogInScreen(ArrayList<DisposableObject> callingObjects, Controller controller) {

        if (!callingObjects.isEmpty()) {
            int size = callingObjects.size();

            for (int i = size - 1; i >= 0; i--) {
                callingObjects.get(i).doOnDispose(callingObjects, controller);
            }
            callingObjects.clear();

            this.setMainFrame(callingObjects, controller);
        }
        callingObjects.addLast(this);

        loginMenuScrollContainer.getVerticalScrollBar().setUnitIncrement(4);
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        resizeFrame();

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateLogin(usernameTextField.getText(), passwordField)) {
                    login(callingObjects, controller);
                }

            }
        });

        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                toggleLoginButton();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (validateLogin(usernameTextField.getText(), passwordField)) {
                        login(callingObjects, controller);
                    }
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                toggleLoginButton();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (validateLogin(usernameTextField.getText(), passwordField)) {
                        login(callingObjects, controller);
                    }
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                new RegisterScreen(callingObjects, controller);
                doOnDispose(callingObjects, controller);
                mainFrame.dispose();
            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                resizeFrame();

            }
        });
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeFrame();
            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                resizeFrame();
            }
        });

        mainFrame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                resizeFrame();
            }
        });
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            String[] options = {"Continua", "Chiudi"};
            int action = JOptionPane.showOptionDialog(null, "<html><center>Il tuo device non supporta FlatLaf,<br>" +
                            "utilizzerai un'altra versione dell'app,<br>" +
                            "tutte le funzioni rimarranno invariate.</center></html>",
                    "Title", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
            if (action == 1 || action == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }

        mainFrame = new JFrame("LogIn");
        mainFrame.setContentPane(new LogInScreen(new ArrayList<DisposableObject>(), controller).loginScreen);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    private void setMainFrame(ArrayList<DisposableObject> callingObjects, Controller controller) {
        mainFrame = new JFrame("LogIn");
        mainFrame.setContentPane(new LogInScreen(callingObjects, controller).loginScreen);
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

    private boolean validateLogin(String username, PasswordHandler password) {
        //TODO: chiama verifyUserPassword()
        if (!isValidUsername(usernameTextField.getText())) {
            JOptionPane.showMessageDialog(loginScreen, "Username non valido");
            return false;
        }
        if (usernameTextField.getText().equals("non esiste")) {
            JOptionPane.showMessageDialog(loginScreen, "Questo utente non esiste");
            return false;
        }

        return true;
    }

    private void login(ArrayList<DisposableObject> callingObjects, Controller controller) {
        controller.setCustomerNUser(usernameTextField.getText(), passwordField.getHashedPassword(), 0);
        usernameTextField.setText("");
        passwordField.setText("");
        logInButton.setEnabled(false);
        mainFrame.setVisible(false);
        new MainCustomerScreen(callingObjects, controller);
    }

    private void resizeFrame() {
        /*the formulas make it so that the loginMenu is always 480 by 320
         /Dimension - 2paddingDimension = desiredDimension -> paddingDimension = Dimension/2 - desiredDimension/2
         /there are some adjustments because of the border and the decorator
         /the actual dimension of the loginMenufluctuate a little (max 2 in either direction)
         /(not only because of parity, but i have no idea what is it other than that)
         */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int desiredHeight = (int)screenSize.getHeight() * 2 / 3;
        int desiredWidth = desiredHeight * 7 / 10;
        mainFrame.setPreferredSize(new Dimension(desiredWidth, desiredHeight));
        int paddingHeight = max((mainFrame.getHeight() - desiredHeight) / 2, 36);
        int paddingWidth = max((mainFrame.getWidth() - desiredWidth) / 2, 27);

        topPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(paddingHeight, 0, 0, 0), -1, -1));
        bottomPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, paddingHeight, 0), -1, -1));
        leftPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, paddingWidth, 0, 0), -1, -1));
        rightPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, paddingWidth), -1, -1));

    }

    private void toggleLoginButton(){
        logInButton.setEnabled(!usernameTextField.getText().isEmpty() && passwordField.getPassword().length != 0);
    }

    @Override
    public JFrame getFrame() {
        return mainFrame;
    }

    /**
     * @noinspection ALL
     */

}