import javax.swing.*;
import java.util.*;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

class VocabularyGame {
    private List<VocabularyQuiz> quizzes;
    private int currentQuestionIndex;
    private String question;
    private String[] choices;
    private int correctIndex;
    private VocabularyPanel vocabularyPanel;


    public VocabularyGame() {
        this.vocabularyPanel = vocabularyPanel;
        quizzes = new ArrayList<>();
        initializeQuizzes();
        Collections.shuffle(quizzes);  // Shuffle the quizzes
        currentQuestionIndex = 0;
    }

    private void initializeQuizzes() {
        // Add your own quiz questions here
        VocabularyQuiz quiz1 = new VocabularyQuiz("What _ you doing?", new String[]{"A) are", "B) do", "C) is", "D) have"}, 0);
        VocabularyQuiz quiz2 = new VocabularyQuiz("Your next question?", new String[]{"A) option1", "B) option2", "C) option3", "D) option4"}, 0);

        // Add more questions as needed

        quizzes.add(quiz1);
        quizzes.add(quiz2);
    }

    public VocabularyQuiz getNextQuiz() {
        if (currentQuestionIndex < quizzes.size()) {
            VocabularyQuiz nextQuiz = quizzes.get(currentQuestionIndex);
            currentQuestionIndex++;
            return nextQuiz;
        }
        return null;
    }

    public boolean checkAnswer(int userChoice) {
        if (currentQuestionIndex > 0 && userChoice < quizzes.get(currentQuestionIndex - 1).getChoices().length) {
            boolean isCorrect = quizzes.get(currentQuestionIndex - 1).isCorrect(userChoice);
            if (isCorrect) {
                return true;
            }
        }
        return false;
    }

    private void loadNextQuestion() {
        VocabularyQuiz quiz = getNextQuiz();
        if (quiz != null) {
            vocabularyPanel.getQuestionTextArea().setText(quiz.getQuestion());
            String[] choices = quiz.getChoices();
            for (int i = 0; i < vocabularyPanel.getAnswerOptions().length; i++) {
                vocabularyPanel.getAnswerOptions()[i].setText(choices[i]);
                vocabularyPanel.getAnswerOptions()[i].setSelected(false);
            }
        }
    }

    public String getCorrectAnswer() {
        VocabularyQuiz currentQuiz = quizzes.get(currentQuestionIndex - 1);
        return currentQuiz.getCorrectAnswer();
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < quizzes.size();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        System.out.println("Welcome to the Vocabulary Quiz Game!");
        System.out.println("You will be presented with multiple-choice questions.");

        for (int i = 0; i < quizzes.size(); i++) {
            VocabularyQuiz quiz = quizzes.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + quiz.getQuestion());

            for (String choice : quiz.getChoices()) {
                System.out.println(choice);
            }

            System.out.print("Your choice [A/B/C/D]: ");
            char userChoice = scanner.next().toUpperCase().charAt(0);

            if (userChoice >= 'A' && userChoice <= 'D') {
                int choiceIndex = userChoice - 'A';
                if (quiz.isCorrect(choiceIndex)) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer is " + quiz.getChoices()[quiz.getCorrectIndex()] + "\n");
                }
            } else {
                System.out.println("Invalid choice. Skipping to the next question.\n");
            }
        }
        System.out.println("Game Over. Your score: " + score + "/" + quizzes.size());
    }
}
