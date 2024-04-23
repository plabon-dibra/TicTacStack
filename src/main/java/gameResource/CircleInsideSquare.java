package gameResource;

import javax.swing.*;
import java.awt.*;

public class CircleInsideSquare extends JButton {

    private Color circleColor;
    private Color backgroundColor;
    private int squareSize;
    private int circleDiameter;

    public CircleInsideSquare(Color circleColor, int circleDiameter, Color backgroundColor, int squareSize) {
        this.circleColor = circleColor;
        this.backgroundColor = backgroundColor;
        this.squareSize = squareSize - 5;
        this.circleDiameter = circleDiameter;

        // Set the button's properties
        setContentAreaFilled(false); // Make content area transparent
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false); // Make the button transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw background
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, squareSize, squareSize);

        // Calculate position to center the circular portion
        int x = (squareSize - circleDiameter) / 2;
        int y = (squareSize - circleDiameter) / 2;

        // Draw circular portion
        g2d.setColor(circleColor);
        g2d.fillOval(x, y, circleDiameter, circleDiameter);

        g2d.dispose();

        // Draw text
        String text = getText();
        if (text != null && !text.isEmpty()) {
            g2d = (Graphics2D) g.create();
            g2d.setColor(getForeground());
            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int textX = (getWidth() - textWidth) / 2;
            int textY = ((getHeight() - textHeight) / 2) + fm.getAscent();
            g2d.drawString(text, textX, textY);
            g2d.dispose();
        }
    }
    public void setCircleColor(Color circleColor) {
        this.circleColor = circleColor;
        repaint(); // Repaint the button to reflect the changes
    }
    public void setRedius(int radius) {
        this.circleDiameter = radius;
        repaint(); // Repaint the button to reflect the changes
    }

    public Color getCircleColor() {
       return  this.circleColor;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(squareSize, squareSize);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Circle Inside Square Button");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a custom button with green circle, diameter 50, gray background, and square size 100x100
            CircleInsideSquare button = new CircleInsideSquare(Color.GREEN, 80, Color.GRAY, 100);

            // Set the text on the button
            button.setText("Click Me");
            button.setForeground(Color.BLACK); // Set text color

            frame.getContentPane().setLayout(new FlowLayout()); // Set layout to FlowLayout
            frame.getContentPane().add(button);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
