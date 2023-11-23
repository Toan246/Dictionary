import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class QuizDialog extends JDialog {
    private VocabularyGame game;
    private JTextArea questionTextArea;
    private ButtonGroup answerGroup;
    private JRadioButton[] answerOptions;
    private JButton submitButton;
    private int selectedAnswer;

    public QuizDialog(Frame parent, VocabularyGame game) {
        super(parent, true);
        this.game = game;

        setTitle("Vocabulary Quiz");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        questionTextArea = new JTextArea(5, 20);
        questionTextArea.setEditable(false);
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);

        answerGroup = new ButtonGroup();
        answerOptions = new JRadioButton[4];
        for (int i = 0; i < answerOptions.length; i++) {
            answerOptions[i] = new JRadioButton();
            answerGroup.add(answerOptions[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        quizPanel.add(questionScrollPane);

        for (int i = 0; i < answerOptions.length; i++) {
            quizPanel.add(answerOptions[i]);
        }

        quizPanel.add(submitButton);

        add(quizPanel, BorderLayout.CENTER);

        loadNextQuestion();

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadNextQuestion() {
        VocabularyQuiz quiz = game.getNextQuiz();
        if (quiz != null) {
            questionTextArea.setText(quiz.getQuestion());
            String[] choices = quiz.getChoices();
            for (int i = 0; i < answerOptions.length; i++) {
                answerOptions[i].setText(choices[i]);
                answerOptions[i].setSelected(false);
            }
        } else {
            // Handle the end of the quiz
            JOptionPane.showMessageDialog(this, "Quiz Completed!");
            dispose();
        }
    }

    private void checkAnswer() {
        for (int i = 0; i < answerOptions.length; i++) {
            if (answerOptions[i].isSelected()) {
                selectedAnswer = i;
                break;
            }
        }

        if (selectedAnswer != -1) {
            boolean isCorrect = game.checkAnswer(selectedAnswer);
            if (isCorrect) {
                JOptionPane.showMessageDialog(null, "Correct!");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect. The correct answer is " + game.getCorrectAnswer(), "Incorrect Answer", JOptionPane.ERROR_MESSAGE);
            }

            if (game.hasMoreQuestions()) {
                loadNextQuestion();
            } else {
                JOptionPane.showMessageDialog(null, "Quiz Completed!");
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an answer.", "No Answer Selected", JOptionPane.WARNING_MESSAGE);
        }
    }
}