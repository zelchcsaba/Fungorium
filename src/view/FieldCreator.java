package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class FieldCreator {

    /**
     * Létrehoz egy egyedi formázású JLabel példányt, amely adott szöveget jelenít meg.
     * A címke különféle paraméterekkel személyre szabottan van konfigurálva.
     *
     * @param text A címkében megjelenítendő szöveg.
     * @return A konfigurált JLabel objektum.
     */
    public static JLabel createLabel(String text) {
        JLabel field = new JLabel(text);
        field.setPreferredSize(new Dimension(300, 60));
        field.setFont(new Font("Arial", Font.BOLD, 15));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setOpaque(true);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
        field.setBorder(border);
        return field;
    }

    public static JLabel createTitle(String text) {
        JLabel field = new JLabel(text);
        field.setPreferredSize(new Dimension(300, 60));
        field.setFont(new Font("Arial", Font.BOLD, 50));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setOpaque(true);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 60));
        field.setFont(new Font("Arial", Font.BOLD, 15));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setOpaque(true);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
        field.setBorder(border);
        field.setCaretColor(Color.WHITE);
        return field;
    }

   public static JButton createButton(String text) {
    JButton field = new JButton(text);

    // Szöveg szélességének pontosabb kiszámítása
    Font font = new Font("Arial", Font.BOLD, 15);
    field.setFont(font);

    FontMetrics fm = field.getFontMetrics(font);
    int textWidth = fm.stringWidth(text);
    int padding = 30; // extra hely a margóknak

    int width = textWidth + padding;
    int height = 60;

    field.setPreferredSize(new Dimension(width, height));
    field.setMargin(new Insets(2, 5, 2, 5));
    field.setBackground(Color.BLACK);
    field.setForeground(Color.WHITE);
    field.setOpaque(true);
    field.setHorizontalAlignment(SwingConstants.CENTER);
    Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
    field.setBorder(border);

    return field;
}

}
