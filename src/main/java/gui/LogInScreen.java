package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static java.lang.Integer.min;
import static java.lang.Math.max;

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
    private JTextField usernameTextField;
    private JLabel usernameLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton logInButton;

    //Bottom options
    private JButton registerButton;
    private JLabel registerPrompt;
    private JButton newPasswordButton;
    private JLabel newPasswordPrompt;


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
        topPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(max(mainFrame.getHeight() / 2 - 259, 36), 0, 0, 0), -1, -1));
        bottomPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, max(mainFrame.getHeight() / 2 - 259, 36), 0), -1, -1));
        leftPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, max(mainFrame.getWidth() / 2 - 168, 27), 0, 0), -1, -1));
        rightPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, max(mainFrame.getWidth() / 2 - 168, 27)), -1, -1));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        loginScreen = new JPanel();
        loginScreen.setLayout(new BorderLayout(0, 0));
        loginScreen.setBackground(new Color(-4934476));
        loginScreen.setForeground(new Color(-10033877));
        loginScreen.setName("");
        loginScreen.setOpaque(true);
        loginScreen.setPreferredSize(new Dimension(360, 520));
        topPadding = new JPanel();
        topPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 36, 0), -1, -1));
        topPadding.setBackground(new Color(-3618596));
        loginScreen.add(topPadding, BorderLayout.NORTH);
        bottomPadding = new JPanel();
        bottomPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(36, 0, 0, 0), -1, -1));
        bottomPadding.setBackground(new Color(-3618596));
        loginScreen.add(bottomPadding, BorderLayout.SOUTH);
        leftPadding = new JPanel();
        leftPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 27), -1, -1));
        leftPadding.setBackground(new Color(-3618596));
        loginScreen.add(leftPadding, BorderLayout.WEST);
        rightPadding = new JPanel();
        rightPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 27, 0, 0), -1, -1));
        rightPadding.setBackground(new Color(-3618596));
        loginScreen.add(rightPadding, BorderLayout.EAST);
        loginMenu = new JPanel();
        loginMenu.setLayout(new GridBagLayout());
        loginMenu.setBackground(new Color(-2302736));
        loginMenu.setEnabled(false);
        Font loginMenuFont = this.$$$getFont$$$(null, -1, -1, loginMenu.getFont());
        if (loginMenuFont != null) loginMenu.setFont(loginMenuFont);
        loginScreen.add(loginMenu, BorderLayout.CENTER);
        passwordLabel = new JLabel();
        passwordLabel.setForeground(new Color(-15461356));
        passwordLabel.setText("Password");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(16, 16, 0, 0);
        loginMenu.add(passwordLabel, gbc);
        passwordField = new JPasswordField();
        passwordField.setFocusable(true);
        passwordField.setHorizontalAlignment(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 16, 0, 16);
        loginMenu.add(passwordField, gbc);
        usernameTextField = new JTextField();
        usernameTextField.setAutoscrolls(true);
        usernameTextField.setFocusable(true);
        usernameTextField.setMaximumSize(new Dimension(2147483647, 2147483647));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 16, 0, 16);
        loginMenu.add(usernameTextField, gbc);
        usernameLabel = new JLabel();
        usernameLabel.setForeground(new Color(-15461356));
        usernameLabel.setText("Nome Utente");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(32, 16, 0, 0);
        loginMenu.add(usernameLabel, gbc);
        registerButton = new JButton();
        registerButton.setFocusable(false);
        registerButton.setText("Registrati");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 16, 16, 16);
        loginMenu.add(registerButton, gbc);
        logInButton = new JButton();
        logInButton.setEnabled(false);
        logInButton.setFocusable(false);
        logInButton.setText("Log In");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.gridheight = 5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 16, 0, 16);
        loginMenu.add(logInButton, gbc);
        registerPrompt = new JLabel();
        registerPrompt.setForeground(new Color(-15461356));
        registerPrompt.setText("<html>Non sei ancora<br>registrato?</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 16, 8, 0);
        loginMenu.add(registerPrompt, gbc);
        newPasswordButton = new JButton();
        newPasswordButton.setFocusable(false);
        newPasswordButton.setHorizontalAlignment(0);
        newPasswordButton.setHorizontalTextPosition(11);
        newPasswordButton.setText("Recupera Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 16, 16, 16);
        loginMenu.add(newPasswordButton, gbc);
        newPasswordPrompt = new JLabel();
        newPasswordPrompt.setAutoscrolls(false);
        newPasswordPrompt.setForeground(new Color(-15461356));
        newPasswordPrompt.setHorizontalAlignment(0);
        newPasswordPrompt.setMinimumSize(new Dimension(73, 34));
        newPasswordPrompt.setText("<html>Hai dimenticato<br>la password?</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 8, 77);
        loginMenu.add(newPasswordPrompt, gbc);
        pageTitle = new JLabel();
        Font pageTitleFont = this.$$$getFont$$$(null, Font.BOLD, 24, pageTitle.getFont());
        if (pageTitleFont != null) pageTitle.setFont(pageTitleFont);
        pageTitle.setForeground(new Color(-15132391));
        pageTitle.setHorizontalAlignment(0);
        pageTitle.setHorizontalTextPosition(0);
        pageTitle.setText("Log In");
        pageTitle.setVerticalTextPosition(3);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(16, 0, 0, 0);
        loginMenu.add(pageTitle, gbc);
        passwordLabel.setLabelFor(passwordField);
        usernameLabel.setLabelFor(usernameTextField);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return loginScreen;
    }


}