package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private int cornerRadius = 30;
    private Color backgroundColor;
    private Color roundBorderColor;

    public RoundedPanel(LayoutManager layout, int cornerRadius) {
        super (layout);
        this.cornerRadius = cornerRadius;
        setOpaque(false);
        this.backgroundColor = getBackground();
        this.roundBorderColor = getForeground();
    }

    public RoundedPanel(int cornerRadius) {
        super ();
        this.cornerRadius = cornerRadius;
        setOpaque(false);
        this.backgroundColor = getBackground();
        this.roundBorderColor = getForeground();
    }

    public RoundedPanel(LayoutManager   layout) {
        super (layout);
        setOpaque(false);
        this.backgroundColor = getBackground();
        this.roundBorderColor = getForeground();
    }

    public RoundedPanel() {
        super ();
        setOpaque(false);
        this.backgroundColor = getBackground();
        this.roundBorderColor = getForeground();
    }

    @Override
    protected void paintComponent (Graphics g) {

        Dimension arcs = new Dimension(cornerRadius, cornerRadius);

        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor (backgroundColor);
        graphics.fillRoundRect(0, 0, width - 1, height -1, arcs.width, arcs.height);

        graphics.setColor (roundBorderColor);
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
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
