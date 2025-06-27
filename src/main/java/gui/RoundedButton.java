package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RoundedButton extends JButton {

    private int cornerRadius = 20;
    private Graphics2D graphics;
    private Color backgroundColor;
    private Color roundBorderColor;

    public RoundedButton(String title, Color backgroundColor, Color roundBorderColor) {
        super (title);
        setOpaque(false);
        this.backgroundColor = backgroundColor;
        this.roundBorderColor = roundBorderColor;
        setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 1.75)));
    }

    public RoundedButton(Color backgroundColor, Color roundBorderColor) {
        super ();
        setOpaque(false);
        this.backgroundColor = backgroundColor;
        this.roundBorderColor = roundBorderColor;
        setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 1.75)));
    }

    public RoundedButton(String title) {
        super (title);
        setOpaque(false);
        this.backgroundColor = new Color(219, 245, 255);
        this.roundBorderColor = new Color(170, 200, 250);
        setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 1.75)));
    }

    public RoundedButton() {
        super ();
        setOpaque(false);
        this.backgroundColor = new Color(219, 245, 255);
        this.roundBorderColor = new Color(170, 200, 250);
        setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), (int)(getPreferredSize().getHeight() * 1.75)));
    }

    @Override
    protected void paintComponent (Graphics g) {

        Dimension arcs = new Dimension(cornerRadius, cornerRadius);

        int width = getWidth();
        int height = getHeight();

        graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor (backgroundColor);
        graphics.fillRoundRect(0, 0, width - 1, height -1, arcs.width, arcs.height);

        graphics.setColor (roundBorderColor);
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        super.paintComponent (g);
    }

    @Override
    public void setBorder(Border border) {}

    public void setRoundBorderColor(Color color) {
        this.roundBorderColor = color;
        repaint();
    }

    @Override
    public void setBackground (Color bg) {
        this.backgroundColor = bg;
        super.setBackground (bg);
        repaint();
    }

    @Override
    public Color getBackground() {
        return backgroundColor;
    }

    public void setCornerRadius (int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }
}
