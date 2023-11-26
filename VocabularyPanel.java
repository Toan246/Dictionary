import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;

public class VocabularyPanel extends JPanel {
    private JTextArea wordListTextArea;
    private JTextArea meaningTextArea;
    private JButton searchButton;
    private JButton addWordButton;
    private JButton editWordButton;
    private JButton deleteWordButton;
    private JButton pronounceButton;
    private JButton quizButton;
    private VocabularyGame game;
    private Dictionary dictionary;
    private DictionaryManagement management;

    public VocabularyPanel() {
        this.game = new VocabularyGame();
        this.dictionary = new Dictionary();
        this.management = new DictionaryManagement();
        management.insertFromFile(dictionary, "dictionaries");

        setLayout(new BorderLayout());

        wordListTextArea = new JTextArea(15, 10);
        wordListTextArea.setEditable(false);
        JScrollPane wordListScrollPane = new JScrollPane(wordListTextArea);

        meaningTextArea = new JTextArea(15, 20);
        meaningTextArea.setEditable(false);
        JScrollPane meaningScrollPane = new JScrollPane(meaningTextArea);

        Font font = new Font("Segoe UI", Font.PLAIN, 25);
        wordListTextArea.setFont(font);
        meaningTextArea.setFont(font);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        searchButton = createStyledButton("Search", "res/search.png", 30);
        addWordButton = createStyledButton("Add Word", "res/add.png", 30);
        editWordButton = createStyledButton("Edit Word", "res/edit.png", 30);
        deleteWordButton = createStyledButton("Delete Word", "res/delete.png", 30);
        pronounceButton = createStyledButton("Pronounce", "res/speaker.png", 30);
        quizButton = createStyledButton("Quiz", "res/quiz.png", 30);

        addButtonsToPanel(buttonPanel, gbc);

        add(wordListScrollPane, BorderLayout.WEST);
        add(meaningScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateWordList();

        addActionListeners();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private JButton createStyledButton(String text, String iconPath, int iconSize) {
        JButton button = new JButton(text);

        button.putClientProperty("JButton.buttonType", "roundRect");

        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledIcon));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return button;
    }

    private void addButtonsToPanel(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(searchButton, gbc);
        gbc.gridx++;
        panel.add(addWordButton, gbc);
        gbc.gridx++;
        panel.add(editWordButton, gbc);
        gbc.gridx++;
        panel.add(deleteWordButton, gbc);
        gbc.gridx++;
        panel.add(pronounceButton, gbc);
        gbc.gridx++;
        panel.add(quizButton, gbc);
    }

    private void addActionListeners() {
        searchButton.addActionListener(e -> {
            String query = JOptionPane.showInputDialog("Enter query to search:");
            dictionarySearcher(query);
        });

        addWordButton.addActionListener(e -> addWordDialog());

        editWordButton.addActionListener(e -> editWordDialog());

        deleteWordButton.addActionListener(e -> deleteWordDialog());

        pronounceButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Pronouncing the word.");
        });

        quizButton.addActionListener(e -> runQuizGame());
    }

    private void updateWordList() {
        StringBuilder wordListBuilder = new StringBuilder();
        for (Word word : dictionary.words) {
            wordListBuilder.append(word.word_target).append("\n");
        }
        wordListTextArea.setText(wordListBuilder.toString());

        wordListTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTextArea textArea = (JTextArea) evt.getSource();
                int offset = textArea.viewToModel(evt.getPoint());
                try {
                    int rowStart = Utilities.getRowStart(textArea, offset);
                    int rowEnd = Utilities.getRowEnd(textArea, offset);
                    String selectedWord = textArea.getText().substring(rowStart, rowEnd).trim();
                    displayTranslation(selectedWord);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void displayTranslation(String selectedWord) {
        for (Word word : dictionary.words) {
            if (word.word_target.equalsIgnoreCase(selectedWord)) {
                meaningTextArea.setText(word.word_explain);
                break;
            }
        }
    }

    private void dictionarySearcher(String query) {
        StringBuilder searchResults = new StringBuilder();
        boolean found = false;
        for (Word word : dictionary.words) {
            if (word.word_target.toLowerCase().contains(query.toLowerCase())) {
                found = true;
                searchResults.append(word.word_target).append(": ").append(word.word_explain).append("\n");
            } else if (word.word_explain.contains(query.toLowerCase())) {
                found = true;
                searchResults.append(word.word_explain).append(": ").append(word.word_target).append("\n");
            }
        }
        if (found) {
            meaningTextArea.setText(searchResults.toString());
        } else {
            meaningTextArea.setText("No matching words found.");
        }
    }

    private void addWordDialog() {
        String target = JOptionPane.showInputDialog("Enter English word to add:");
        String explain = JOptionPane.showInputDialog("Enter Vietnamese explanation:");
        if (target != null && explain != null) {
            management.addWord(dictionary, target, explain);
            updateWordList();
        }
    }

    private void editWordDialog() {
        String target = JOptionPane.showInputDialog("Enter English word to edit:");
        String newExplain = JOptionPane.showInputDialog("Enter new Vietnamese explanation:");
        if (target != null) {
            management.updateWord(dictionary, target, newExplain);
            updateWordList();
        }
    }

    private void deleteWordDialog() {
        String target = JOptionPane.showInputDialog("Enter English word to delete:");
        management.removeWord(dictionary, target);
        updateWordList();
    }

    private void runQuizGame() {
        new QuizDialog((Frame) SwingUtilities.getWindowAncestor(this), game);
    }
}
