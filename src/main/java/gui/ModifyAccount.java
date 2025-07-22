package gui;

import com.formdev.flatlaf.FlatClientProperties;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ModifyAccount extends JDialog {

    private final JPanel mainPanel;
    private JTextField usernameTextField;
    private JTextField mailTextField;
    private PasswordHandler newPasswordField;
    private PasswordHandler oldPasswordField;

    private JButton confirmButton;
    private JButton deleteButton;

    private final Constraints constraints = new Constraints();

    String oldUsername = null;
    String oldMail = null;

    public ModifyAccount(Frame owner, ArrayList<DisposableObject> callingObjects, Controller controller) {

        super(owner, "Modifica Account", true);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(240, 242, 245));

        //Setup for Title
        JLabel titleLabel = new JLabel("Modifica Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(0, 0, 16, 0));
        mainPanel.add(titleLabel, constraints.getConstraints());

        this.addMainForm(controller);

        setConfirmButton(controller, callingObjects);

        setDeleteButton(controller, callingObjects);

        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(owner);
        this.setResizable(false);
        this.getContentPane().requestFocusInWindow();


    }

    private void setDeleteButton(Controller controller, ArrayList<DisposableObject> callingObjects){
        deleteButton = new JButton("Elimina Account");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 16));


        deleteButton.setBackground(new Color(220, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(200, 40));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(oldPasswordField.isEmpty()){
                    new FloatingMessage("<html>Bisogna inserire la password</html>", confirmButton, FloatingMessage.ERROR_MESSAGE);
                    return;
                }
                if (!oldPasswordField.getHashedPassword().equals(controller.getUserController().getHashedPassword())) {
                    new FloatingMessage("<html>Password errata</html>", confirmButton, FloatingMessage.ERROR_MESSAGE);
                    return;
                }
                else{
                    String[] options = {"Annulla", "Elimina account"};
                    int action = JOptionPane.showOptionDialog(mainPanel, "<html>Sei sicuro di voler eliminare il tuo account?<br>" +
                                    "Questa azione non può è reversibile </html>", "Elimina account", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, null);
                    if(action == 1){
                        controller.getUserController().deleteAccount(deleteButton);
                        controller.goToLogin(callingObjects);
                    }
                }
            }
        });

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.PAGE_END, 1.0f, 0.0f, new Insets(16, 0, 0, 0));
        mainPanel.add(deleteButton, constraints.getConstraints());
    }

    private void setConfirmButton(Controller controller, ArrayList<DisposableObject> callingObjects){
        confirmButton = new JButton("Conferma");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        confirmButton.setBackground(new Color(0, 120, 215));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);

        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.setPreferredSize(new Dimension(200, 40));


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(oldPasswordField.isEmpty()){
                    new FloatingMessage("<html>Bisogna inserire la password</html>", confirmButton, FloatingMessage.ERROR_MESSAGE);
                    return;
                }
                if (!oldPasswordField.getHashedPassword().equals(controller.getUserController().getHashedPassword())) {
                    new FloatingMessage("<html>Password errata</html>", confirmButton, FloatingMessage.ERROR_MESSAGE);
                    return;
                }
                if(newPasswordField.isEmpty() ) {
                    if (controller.updateUser(mailTextField.getText(), usernameTextField.getText(),
                            oldPasswordField.getHashedPassword(), confirmButton)) {
                        controller.goHome(callingObjects);
                        dispose();
                    }
                }else if(newPasswordField.isValidPassword()){
                    if(controller.updateUser(mailTextField.getText(), usernameTextField.getText(),
                            newPasswordField.getHashedPassword(), confirmButton)){
                        controller.goHome(callingObjects);
                        dispose();
                    }
                } else{
                    newPasswordField.showInvalidPasswordMessage(confirmButton);
                }
            }});

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(16, 0, 0, 0));
        mainPanel.add(confirmButton, constraints.getConstraints());
    }

    private void addMainForm(Controller controller) {

        JPanel mainForm = new JPanel(new GridBagLayout());
        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f);
        mainPanel.add(mainForm, constraints.getConstraints());

        //username Label and field
        JLabel usernameLabel = new JLabel("Nome utente");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setLabelFor(usernameTextField);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(16, 8, 0, 0));
        mainForm.add(usernameLabel, constraints.getConstraints());

        oldUsername = controller.getUserController().getUsername();
        usernameTextField = new JTextField(oldUsername, 20);
        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(0, 8, 0, 8));
        mainForm.add(usernameTextField, constraints.getConstraints());
        setFieldAppearance(usernameTextField);

        //mail Label and Field
        JLabel mailLabel = new JLabel("Mail");
        mailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mailLabel.setLabelFor(mailTextField);

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(16, 8, 0, 0));
        mainForm.add(mailLabel, constraints.getConstraints());

        oldMail = controller.getUserController().getMail();
        mailTextField = new JTextField(oldMail, 20);
        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(0, 8, 0, 8));
        mainForm.add(mailTextField, constraints.getConstraints());
        setFieldAppearance(mailTextField);

        if (controller.isLoggedAdmin()) {
            mailTextField.setEditable(false);
            mailTextField.setFocusable(false);
            mailTextField.setForeground(new Color(80, 80, 80));
        }

        //newPassword Label and Field
        JLabel newPasswordLabel = new JLabel("Nuova password");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        newPasswordLabel.setLabelFor(newPasswordField);

        constraints.setConstraints(0, 4, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(16, 8, 0, 0));
        mainForm.add(newPasswordLabel, constraints.getConstraints());

        newPasswordField = new PasswordHandler(20);
        newPasswordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        constraints.setConstraints(0, 5, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(0, 8, 0, 8));
        mainForm.add(newPasswordField, constraints.getConstraints());
        setFieldAppearance(newPasswordField);

        //oldPassword Label and Field
        JLabel oldPasswordLabel = new JLabel("Vecchia password");
        oldPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        oldPasswordLabel.setLabelFor(oldPasswordField);

        constraints.setConstraints(1, 4, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(16, 8, 0, 0));
        mainForm.add(oldPasswordLabel, constraints.getConstraints());

        oldPasswordField = new PasswordHandler(20);
        oldPasswordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        constraints.setConstraints(1, 5, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(0, 8, 0, 8));
        mainForm.add(oldPasswordField, constraints.getConstraints());
        setFieldAppearance(oldPasswordField);

    }

    private void setFieldAppearance(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));

    }
}