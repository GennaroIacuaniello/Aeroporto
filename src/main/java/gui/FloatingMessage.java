package gui;

import javax.swing.*;
import java.awt.*;

public class FloatingMessage {

    public static final int ERROR_MESSAGE = 1;
    public static final int WARNING_MESSAGE = 2;
    public static final int SUCCESS_MESSAGE = 3;
    
    private JWindow messageWindow;
    private RoundedPanel messagePanel;
    private JLabel messageLabel;

    public FloatingMessage (String msg, JButton callingButton, int messageType){

        setWindow(callingButton);
        setPanel(msg, messageType);

        
        new DisposeTimers(messageWindow);

        messageWindow.setVisible(true);
    }

    private void setWindow (JButton callingButton) {

        messageWindow = new JWindow();

        messageWindow.setAlwaysOnTop(true);
        messageWindow.setOpacity(0.75f);
        messageWindow.setBackground(new Color(0, 0, 0, 0));

        JPanel contentPanel = (JPanel) messageWindow.getContentPane();
        contentPanel.setOpaque(false);
        messageWindow.setSize(300, 100);

        Point callingButtonLocation = new Point(callingButton.getLocationOnScreen());
        Point messageLocation = new Point((int)callingButtonLocation.getX() + (callingButton.getWidth() - messageWindow.getWidth()) / 2,
                (int)callingButtonLocation.getY() - messageWindow.getHeight() - 10);

        if (messageLocation.getX() < 0)
            messageLocation.setLocation(5, (int) messageLocation.getY());
        else if (messageLocation.getX() + 300 > Toolkit.getDefaultToolkit().getScreenSize().getWidth())
            messageLocation.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 305),
                    (int) messageWindow.getLocationOnScreen().getY());

        messageWindow.setLocation(messageLocation);
    }

    private void setPanel (String msg, int messageType) {

        messagePanel = new RoundedPanel(new BorderLayout());
        setColor(messageType);

        messageLabel = new JLabel("<html><center>" + msg + "</center></html>", SwingConstants.CENTER);
        messageLabel.setForeground(Color.BLACK);

        messagePanel.add(messageLabel, BorderLayout.CENTER);

        messageWindow.add(messagePanel);
    }
    
    private void setColor(int messageType){

        if(messageType == ERROR_MESSAGE){
            messagePanel.setBackground(new Color(200, 60, 60));
            messagePanel.setRoundBorderColor (new Color(120, 0, 10));
        } else if (messageType == WARNING_MESSAGE) {
            messagePanel.setBackground(new Color(240, 220, 50));
            messagePanel.setRoundBorderColor (new Color(160, 140, 10));
        } else if (messageType == SUCCESS_MESSAGE) {
            messagePanel.setBackground(new Color(139, 255, 104));
            messagePanel.setRoundBorderColor (new Color(55, 142, 5));
        }
        else{
            throw new RuntimeException("FloatingMessage: messageType must be one of" +
                    "FloatingMessage.ERROR_MESSAGE, FloatingMessage.WARNING_MESSAGE, FloatingMessage.SUCCESS_MESSAGE");
        }
    }
}
