package gui;

import com.formdev.flatlaf.FlatClientProperties;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static java.lang.Math.max;

public class RegisterScreen extends DisposableObject {

    //Padding
    private JPanel topPadding;
    private JPanel bottomPadding;
    private JPanel leftPadding;
    private JPanel rightPadding;

    //Main scene
    private static JFrame mainFrame;
    private JPanel registerScreen;
    private JScrollPane registerMenuScrollContainer;
    private JPanel registerMenu;
    private JLabel pageTitle;

    //Main fields
    private JTextField mailTextField;
    private JLabel mailLabel;
    private JTextField usernameTextField;
    private JLabel usernameLabel;
    private PasswordHandler passwordField;
    private JLabel passwordLabel;
    private JButton registerButton;

    //Bottom options
    private JButton loginButton;

    public RegisterScreen(List<DisposableObject> callingObjects, Controller controller, Dimension startingSize) {

        this.setMainFrame(callingObjects, startingSize);
        mainFrame.setVisible(true);

        registerMenuScrollContainer.getVerticalScrollBar().setUnitIncrement(4);
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        resizeFrame();

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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToLoginPage(callingObjects, controller);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(passwordField.isValidPassword()){
                    if(controller.getUserController().registerUser(mailTextField.getText(), usernameTextField.getText(), passwordField.getHashedPassword(), registerButton)){
                        goToLoginPage(callingObjects, controller);
                    }
                } else {
                    passwordField.showInvalidPasswordMessage(registerButton);
                }
            }
        });

        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                toggleRegisterButton();
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                toggleRegisterButton();
            }
        });
    }

    private void setMainFrame(List<DisposableObject> callingObjects, Dimension startingSize) {
        mainFrame = new JFrame("RegisterScreen");
        mainFrame.setSize(startingSize);
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.add(registerScreen);

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

    private void toggleRegisterButton(){
        registerButton.setEnabled(!usernameTextField.getText().isEmpty() && passwordField.getPassword().length != 0);
    }

    private void goToLoginPage(List<DisposableObject> callingObjects, Controller controller) {
        mainFrame.setVisible(false);
        new LogInScreen(callingObjects, controller, mainFrame.getSize());
        doOnDispose(callingObjects, controller);
        mainFrame.dispose();
    }

    @Override
    public JFrame getFrame() {
        return mainFrame;
    }

}
