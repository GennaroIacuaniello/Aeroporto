package gui;

import javax.swing.*;
import java.awt.*;

public class ErrorMessage {

    private JWindow errorWindow;
    private RoundedPanel errorPanel;
    private JLabel errorLabel;

    public ErrorMessage (String msg, JButton callingButton){

        setWindow(callingButton);
        setPanel(msg);

        new DisposeTimers(errorWindow);

        errorWindow.setVisible(true);
    }

    private void setWindow (JButton callingButton) {

        errorWindow = new JWindow();

        errorWindow.setAlwaysOnTop(true);
        errorWindow.setOpacity(0.75f);
        errorWindow.setBackground(new Color(0, 0, 0, 0));
        JPanel contentPanel = (JPanel) errorWindow.getContentPane();
        contentPanel.setOpaque(false);
        errorWindow.setSize(300, 100);
        Point point = new Point(callingButton.getLocationOnScreen());
        errorWindow.setLocation((int)point.getX() + (callingButton.getWidth() - errorWindow.getWidth()) / 2, (int)point.getY() - errorWindow.getHeight() - 10);
    }

    private void setPanel (String msg) {

        errorPanel = new RoundedPanel(new BorderLayout());
        errorPanel.setBackground(new Color(200, 60, 60));
        errorPanel.setRoundBorderColor (new Color(120, 0, 10));

        errorLabel = new JLabel("<html><center>" + msg + "</center></html>", SwingConstants.CENTER);
        errorLabel.setForeground(Color.BLACK);

        errorPanel.add(errorLabel, BorderLayout.CENTER);

        errorWindow.add(errorPanel);
    }
}
