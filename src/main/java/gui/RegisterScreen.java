package gui;

import com.formdev.flatlaf.FlatClientProperties;
import controller.Controller;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Math.max;

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
    private JTextField usernameTextField;
    private JLabel usernameLabel;

    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton registerButton;

    //Bottom options
    private JButton loginButton;

    public RegisterScreen(ArrayList<DisposableObject> callingObjects, Controller controller) {

        this.setMainFrame(callingObjects, controller);
        mainFrame.setVisible(true);

        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");


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

    @Override
    public JFrame getFrame() {
        return mainFrame;
    }
}
