import javax.swing.*;


public class DictionaryApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Dictionary Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of VocabularyPanel
        VocabularyPanel vocabularyPanel = new VocabularyPanel();
        frame.getContentPane().add(vocabularyPanel);

        frame.pack();
        frame.setVisible(true);
    }
}
