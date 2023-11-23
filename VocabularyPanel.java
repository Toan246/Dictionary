import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VocabularyPanel extends JPanel {
    public JTextArea wordListTextArea;
    public JTextArea meaningTextArea;
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

        wordListTextArea = new JTextArea(25, 30);
        wordListTextArea.setEditable(false);
        JScrollPane wordListScrollPane = new JScrollPane(wordListTextArea);

        meaningTextArea = new JTextArea(25, 30);
        meaningTextArea.setEditable(false);
        JScrollPane meaningScrollPane = new JScrollPane(meaningTextArea);

        Font font = new Font("Times New Roman", Font.PLAIN, 16);
        wordListTextArea.setFont(font);
        meaningTextArea.setFont(font);

        JPanel buttonPanel = new JPanel();
        searchButton = new JButton("Search");
        addWordButton = new JButton("Add Word");
        editWordButton = new JButton("Edit Word");
        deleteWordButton = new JButton("Delete Word");
        pronounceButton = new JButton("Pronounce");
        quizButton = new JButton("Quiz");

        buttonPanel.add(searchButton);
        buttonPanel.add(addWordButton);
        buttonPanel.add(editWordButton);
        buttonPanel.add(deleteWordButton);
        buttonPanel.add(pronounceButton);
        buttonPanel.add(quizButton);

        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 16);
        searchButton.setFont(buttonFont);
        addWordButton.setFont(buttonFont);
        editWordButton.setFont(buttonFont);
        deleteWordButton.setFont(buttonFont);
        pronounceButton.setFont(buttonFont);
        quizButton.setFont(buttonFont);


        add(wordListScrollPane, BorderLayout.WEST);
        add(meaningScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateWordList();

        // Set up action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prefix = JOptionPane.showInputDialog("Enter prefix to search:");
                dictionarySearcher(prefix);
            }
        });

        addWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWordDialog();
            }
        });

        editWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editWordDialog();
            }
        });

        deleteWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteWordDialog();
            }
        });

        pronounceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add pronunciation logic
                JOptionPane.showMessageDialog(null, "Pronouncing the word.");
            }
        });

        quizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runQuizGame();
            }
        });
    }

    private void updateWordList() {
        StringBuilder wordListBuilder = new StringBuilder();
        for (Word word : dictionary.words) {
            wordListBuilder.append(word.word_target).append("\n");
        }
        wordListTextArea.setText(wordListBuilder.toString());
    }

    private void dictionarySearcher(String prefix) {
        StringBuilder searchResults = new StringBuilder();
        for (Word word : dictionary.words) {
            if (word.word_target.toLowerCase().startsWith(prefix.toLowerCase())) {
                searchResults.append(word.word_target).append(": ").append(word.word_explain).append("\n");
            } else if (word.word_explain.toLowerCase().startsWith(prefix.toLowerCase())) {
                searchResults.append(word.word_explain).append(": ").append(word.word_target).append("\n");
            }
        }
        meaningTextArea.setText(searchResults.toString());
    }

    private void addWordDialog() {
        String target = JOptionPane.showInputDialog("Enter English word to add:");
        String explain = JOptionPane.showInputDialog("Enter Vietnamese explanation:");
        management.addWord(dictionary, target, explain);
        updateWordList();
    }

    private void editWordDialog() {
        String target = JOptionPane.showInputDialog("Enter English word to edit:");
        String newExplain = JOptionPane.showInputDialog("Enter new Vietnamese explanation:");
        management.updateWord(dictionary, target, newExplain);
        updateWordList();
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
