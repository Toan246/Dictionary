import com.sun.deploy.cache.JarSigningData;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import sun.net.www.http.HttpClient;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

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
        management.insertFromFile(dictionary,"dictionaries");

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

        // Add components to the main panel
        add(wordListScrollPane, BorderLayout.WEST);
        add(meaningScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Update word list on initialization
        updateWordList();

        // Set up action listeners
        addActionListeners();

        // Set a modern look and feel
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

        // Use FlatLaf styling for buttons
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
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prefix = JOptionPane.showInputDialog("Enter prefix to search:");
                dictionarySearcher(prefix);
            }
        });

        addWordButton.addActionListener(e -> addWordDialog());

        editWordButton.addActionListener(e -> editWordDialog());

        deleteWordButton.addActionListener(e -> deleteWordDialog());

        pronounceButton.addActionListener(e -> {
            // Add pronunciation logic
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

        wordListTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextArea textArea = (JTextArea) e.getSource();
                int offset = textArea.viewToModel(e.getPoint());
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
        StringBuilder translationBuilder = new StringBuilder();
        for (Word word : dictionary.words) {
            if (word.word_target.equalsIgnoreCase(selectedWord)) {
                String[] explanations = word.word_explain.split("\n");

                for (String explanation : explanations) {
                    translationBuilder.append(explanation.trim()).append("\n");
                }

                meaningTextArea.setText(translationBuilder.toString().trim());

                meaningTextArea.setCaretPosition(0);

                break;
            }
        }
    }

    private void dictionarySearcher(String prefix) {
        StringBuilder searchResults = new StringBuilder();
        boolean found = false;
        int count = 0;
        for (Word word : dictionary.words) {
            if (count>=5){
                break;
            }
            if (word.word_target.toLowerCase().startsWith(prefix.toLowerCase())) {
                found = true;
                searchResults.append(word.word_target).append(": ").append(word.word_explain).append("\n");
                searchResults.append("\n");
                count++;
            } else if (word.word_explain.toLowerCase().startsWith(prefix.toLowerCase())) {
                found = true;
                searchResults.append(word.word_explain).append(": ").append(word.word_target).append("\n");
                searchResults.append("\n");
                count++;
            }
        }
        if (found) {
            meaningTextArea.setText(searchResults.toString());
            meaningTextArea.setCaretPosition(0);
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
