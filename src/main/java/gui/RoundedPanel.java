package gui;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private Color backgroundColor;
    private Color roundBorderColor;

    public RoundedPanel(LayoutManager   layout) {
        super (layout);
        setOpaque(false);
        this.backgroundColor = getBackground();
        this.roundBorderColor = getForeground();
    }

    @Override
    protected void paintComponent (Graphics g) {

        int cornerRadius = 30;
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

}
