import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatDarkLaf;

public class DictionaryApplication {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Dictionary Application");

        ImageIcon icon = new ImageIcon("res/logo.png");
        frame.setIconImage(icon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        VocabularyPanel vocabularyPanel = new VocabularyPanel();
        frame.getContentPane().add(vocabularyPanel);

        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.getContentPane().setBackground(UIManager.getColor("Panel.background"));
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
