package gui;

import com.formdev.flatlaf.FlatClientProperties;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;

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

    public RegisterScreen(ArrayList<DisposableObject> callingObjects, Controller controller) {

        this.setMainFrame(callingObjects, controller);
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
                mainFrame.setVisible(false);
                new LogInScreen(callingObjects, controller);
                doOnDispose(callingObjects, controller);
                mainFrame.dispose();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordCode pc = passwordField.isValidPassword(passwordField.getPassword());
                String warningMessage = null;
                switch (pc){
                    case tooShort -> warningMessage = "La password deve contenere almeno " + PasswordHandler.minimumPasswordLength + " caratteri";
                    case tooLong -> warningMessage = "La password può contenere al più " + PasswordHandler.maximumPasswordLength + " caratteri";
                    case mustContainLowercase -> warningMessage = "La password deve contenere almeno un carattere minuscolo";
                    case mustContainUppercase -> warningMessage = "La password deve contenere almeno un carattere maiuscolo";
                    case mustContainDigit -> warningMessage = "La password deve contenere almeno una cifra";
                    case mustContainSpecial -> warningMessage = "La password deve contenere almeno uno dei seguenti caratteri: <br>" + PasswordHandler.allowedSpecialCharacters;
                    case characterNotAllowed -> warningMessage = "La password inserita contiene un carattere non valido. <br>" +
                            "I caratteri validi sono: <br>" + PasswordHandler.allowedCharacterSet;
                }

                if(pc == PasswordCode.validPassword){
                    if(usernameTextField.getText().length() < 4 || usernameTextField.getText().length() > 20){
                        warningMessage = "L'username deve avere tra i 4 e i 20 caratteri";
                        new FloatingMessage(warningMessage, registerButton, FloatingMessage.WARNING_MESSAGE);
                    } else if(!isValidMail()){
                        warningMessage = "Mail non valida, accettiamo i domini <br>" +
                                "@aeroportodinapoli.it (riservata)<br>" +
                                "@adn.it (riservata)<br>" +
                                "@gmail.com";
                        new FloatingMessage(warningMessage, registerButton, FloatingMessage.WARNING_MESSAGE);
                    } else{
                        //TODO: inserisci l'utente nel DAO (se nome utente (e mail) vanno bene)
                    }
                }else {
                    new FloatingMessage(warningMessage, registerButton, FloatingMessage.WARNING_MESSAGE);
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

    private void setMainFrame(ArrayList<DisposableObject> callingObjects, Controller controller) {
        mainFrame = new JFrame("RegisterScreen");
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(registerScreen);
        mainFrame.pack();
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

    private boolean isValidMail(){
        //this would be a more robust function in a real world application
        return mailTextField.getText().isEmpty() || mailTextField.getText().endsWith("@aeroportodinapoli.it") ||
               mailTextField.getText().endsWith("@adn.it") || mailTextField.getText().endsWith("@gmail.com");
    }

    @Override
    public JFrame getFrame() {
        return mainFrame;
    }

}
