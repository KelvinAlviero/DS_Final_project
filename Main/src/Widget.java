import java.awt.*;

class Widget {
    private String eventText;

    public Widget(String eventText) {
        this.eventText = eventText;
    }

    public String getEvent() {
        return eventText;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        // Draw the widget
        g.setColor(Color.BLUE);
        g.drawRect(x + width / 4, y + height / 4, width / 2, height / 2);

        // Draw the event text
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(eventText);
        int textX = x + (width - textWidth) / 2;
        int textY = y + height / 2;
        g.drawString(eventText, textX, textY);
    }
}
