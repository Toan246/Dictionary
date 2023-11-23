import javax.swing.*;
import java.awt.*;


public class DictionaryApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Dictionary Application");

        ImageIcon icon = new ImageIcon("res/logo.png"); // Thay đổi đường dẫn đến icon của bạn
        frame.setIconImage(icon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of VocabularyPanel
        VocabularyPanel vocabularyPanel = new VocabularyPanel();
        frame.getContentPane().add(vocabularyPanel);

        frame.pack();
        frame.setVisible(true);
    }
}
