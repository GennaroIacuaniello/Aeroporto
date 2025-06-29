package gui;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
<<<<<<< Updated upstream
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
=======
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static java.awt.event.KeyEvent.VK_ENTER;
>>>>>>> Stashed changes

public class LogInScreen {
    private JPanel loginScreen;
    private JPanel topPadding;
    private JPanel bottomPadding;
    private JPanel leftPadding;
    private JPanel rightPadding;
    private JPanel loginMenu;
    private JLabel pageTitle;
<<<<<<< Updated upstream
    private JTextField mailTextField;
    private JLabel mailLabel;
=======

    //Main fields
    private JTextField usernameTextField;
    private JLabel usernameLabel;
>>>>>>> Stashed changes
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton registerButton;
    private JButton logInButton;
    private JLabel registerPrompt;
    private JButton newPasswordButton;
    private JLabel newPasswordPrompt;

<<<<<<< Updated upstream
    public LogInScreen() {
=======
    public LogInScreen(ArrayList<JFrame> callingFrames, Controller controller) {

        if (!callingFrames.isEmpty()) {
            int size = callingFrames.size();

            for (JFrame callingFrame : callingFrames) {
                System.out.println(callingFrame.getName());
                callingFrame.dispose();
            }
            System.out.println("FINE");
            callingFrames.clear();

            this.setMainFrame(callingFrames, controller);
        }
        callingFrames.addLast(mainFrame);
>>>>>>> Stashed changes

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
<<<<<<< Updated upstream
                if (mailTextField.getText().equals("non esiste")) {
                    JOptionPane.showMessageDialog(loginScreen, "Questo utente non esiste");
                }
                if (passwordField.getText().equals("errata")) {
                    JOptionPane.showMessageDialog(loginScreen, "Password errata");
=======
                if (validateLogin(usernameTextField.getText(), passwordField.getPassword())) {
                    login(callingFrames, controller);
>>>>>>> Stashed changes
                }

            }
        });

<<<<<<< Updated upstream
        mailTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!mailTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(true);
                }
                if (mailTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
=======
        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!usernameTextField.getText().isEmpty() && !(passwordField.getPassword().length == 0)) {
                    logInButton.setEnabled(true);
                }
                if (usernameTextField.getText().isEmpty() || passwordField.getPassword().length == 0) {
>>>>>>> Stashed changes
                    logInButton.setEnabled(false);
                }
                if (e.getKeyCode() == VK_ENTER) {
                    if (validateLogin(usernameTextField.getText(), passwordField.getPassword())) {
                        login(callingFrames, controller);
                    }
                }
            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                System.out.println("Resized:" + mainFrame.getSize());
                topPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, mainFrame.getSize().height / 2 - 240, 0), -1, -1));
                bottomPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(mainFrame.getSize().height / 2 - 240, 0, 0, 0), -1, -1));
                leftPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, mainFrame.getSize().width / 2 - 168), -1, -1));
                rightPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, mainFrame.getSize().width / 2 - 168, 0, 0), -1, -1));

            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                System.out.println("Moved: " + mainFrame.getSize());
                topPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, mainFrame.getSize().height / 2 - 240, 0), -1, -1));
                bottomPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(mainFrame.getSize().height / 2 - 240, 0, 0, 0), -1, -1));
                leftPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, mainFrame.getSize().width / 2 - 168), -1, -1));
                rightPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, mainFrame.getSize().width / 2 - 168, 0, 0), -1, -1));

            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
<<<<<<< Updated upstream
                if (!mailTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    logInButton.setEnabled(true);
                }
                if (mailTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
=======
                if (!usernameTextField.getText().isEmpty() && !(passwordField.getPassword().length == 0)) {
                    logInButton.setEnabled(true);
                }
                if (usernameTextField.getText().isEmpty() || passwordField.getPassword().length == 0) {
>>>>>>> Stashed changes
                    logInButton.setEnabled(false);
                }
                if (e.getKeyCode() == VK_ENTER) {
                    if (validateLogin(usernameTextField.getText(), passwordField.getPassword())) {
                        login(callingFrames, controller);
                    }
                }
            }
        });
<<<<<<< Updated upstream
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Log In");
        frame.setContentPane(new LogInScreen().loginScreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

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
        loginScreen.setPreferredSize(new Dimension(360, 480));
        loginScreen.setToolTipText("");
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
        mailTextField = new JTextField();
        mailTextField.setAutoscrolls(true);
        mailTextField.setFocusable(true);
        mailTextField.setMaximumSize(new Dimension(2147483647, 2147483647));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 16, 0, 16);
        loginMenu.add(mailTextField, gbc);
        mailLabel = new JLabel();
        mailLabel.setForeground(new Color(-15461356));
        mailLabel.setText("Mail");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(32, 16, 0, 0);
        loginMenu.add(mailLabel, gbc);
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
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(64, 16, 0, 16);
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
        mailLabel.setLabelFor(mailTextField);
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
=======

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                new RegisterScreen(callingFrames, controller);
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
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
        private Customer getCustomer(JTextField username, JPasswordField password) {
            return new Customer(username.getText(), password.getText());
        }
    */
    private boolean isValidUsername(String username) {
        String validCharaters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@.-_";
        for (int i = 0; i < username.length(); i++) {
            if (validCharaters.indexOf(username.charAt(i)) == -1) { //indexOf returns -1 if string does not contain character
                return false;
>>>>>>> Stashed changes
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

<<<<<<< Updated upstream
=======
    private boolean validateLogin(String username, char[] password) {
        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(loginScreen, "Inserire nome utente e password");
            return false;
        }
        if (!isValidUsername(username)) {
            JOptionPane.showMessageDialog(loginScreen, "Nome utente non valido");
            return false;
        }
        if (Arrays.equals(password, new char[]{'n', 'o', 'n', ' ', 'e', 's', 'i', 's', 't', 'e'})) {
            JOptionPane.showMessageDialog(loginScreen, "Questo utente non esiste");
            return false;
        }
        if (Arrays.equals(password, new char[]{'e', 'r', 'r', 'a', 't', 'a'})) {
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
        loginScreen.setPreferredSize(new Dimension(360, 480));
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
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(64, 16, 0, 16);
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

>>>>>>> Stashed changes
    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return loginScreen;
    }

<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
