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

    public LogInScreen(ArrayList<DisposableObject> callingObjects, Controller controller, Dimension startingSize) {

        if (!callingObjects.isEmpty()) {
            int size = callingObjects.size();

            for (int i = size - 1; i >= 0; i--) {
                callingObjects.get(i).doOnDispose(callingObjects, controller);
            }
            callingObjects.clear();

            this.setMainFrame(callingObjects, controller, startingSize);
        }
        callingObjects.addLast(this);
        //some additional manual setup
        loginMenuScrollContainer.getVerticalScrollBar().setUnitIncrement(4);
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        resizeFrame();


        //todo: ELIMINA QUESTE DUE RIGHE
        //usernameTextField.setText("customer_user1");
        //passwordField.setText("Customer_User1");
        usernameTextField.setText("admin_user1");
        passwordField.setText("Admin_User1");

        //Logging in logic
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!passwordField.isValidPassword()) {
                    passwordField.showInvalidPasswordMessage(logInButton);
                } else if(controller.verifyUser(usernameTextField.getText(), passwordField.getHashedPassword(), logInButton)) {
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
                    if (!passwordField.isValidPassword()) {
                        passwordField.showInvalidPasswordMessage(logInButton);
                    } else if(controller.verifyUser(usernameTextField.getText(), passwordField.getHashedPassword(), logInButton)) {
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
                    if (!passwordField.isValidPassword()) {
                        passwordField.showInvalidPasswordMessage(logInButton);
                    } else if(controller.verifyUser(usernameTextField.getText(), passwordField.getHashedPassword(), logInButton)) {
                        login(callingObjects, controller);
                    }
                }
            }
        });

        //Go to Register Screen
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                new RegisterScreen(callingObjects, controller, mainFrame.getSize());
                doOnDispose(callingObjects, controller);
                mainFrame.dispose();
            }
        });


        //resizing logic
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
                    "Errore nel caricamento grafica", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
            if (action == 1 || action == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }

        mainFrame = new JFrame("LogIn");
        mainFrame.setContentPane(new LogInScreen(new ArrayList<DisposableObject>(), controller, mainFrame.getPreferredSize()).loginScreen);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    private void setMainFrame(ArrayList<DisposableObject> callingObjects, Controller controller, Dimension startingSize) {
        mainFrame = new JFrame("LogIn");
        mainFrame.setSize(startingSize);
        mainFrame.setContentPane(new LogInScreen(callingObjects, controller, startingSize).loginScreen);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.pack();
        mainFrame.setVisible(true);
    }



    private void login(ArrayList<DisposableObject> callingObjects, Controller controller) {
        usernameTextField.setText("");
        passwordField.setText("");
        logInButton.setEnabled(false);
        mainFrame.setVisible(false);
        if(controller.isLoggedAdmin()){
            new HomePageAdmin(callingObjects, controller);
        }else{
            new MainCustomerScreen(callingObjects, controller);
        }

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