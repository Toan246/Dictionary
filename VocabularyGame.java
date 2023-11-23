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
    private boolean quizCompleted;
    public int score;

    public VocabularyGame() {
        this.vocabularyPanel = vocabularyPanel;
        quizzes = new ArrayList<>();
        initializeQuizzes();
        Collections.shuffle(quizzes);  // Shuffle the quizzes
        currentQuestionIndex = 0;
    }

    private void initializeQuizzes() {
        // Add your own quiz questions here
        VocabularyQuiz quiz1 = new VocabularyQuiz("What ___ you doing?", new String[]{"A) are", "B) do", "C) is", "D) have"}, 0);
        VocabularyQuiz quiz2 = new VocabularyQuiz("He ____ basketball every weekend.", new String[]{"A) play", "B) plays", "C) played", "D) playing"}, 1);
        VocabularyQuiz quiz3 = new VocabularyQuiz("The capital of France is ____.", new String[]{"A) Berlin", "B) Madrid", "C) Paris", "D) Rome"}, 2);
        VocabularyQuiz quiz4 = new VocabularyQuiz("She ____ English three years ago.", new String[]{"A) studying", "B) study", "C) studied", "D) studies"}, 2);
        VocabularyQuiz quiz5 = new VocabularyQuiz("The sun ____ in the east.", new String[]{"A) rise", "B) rises", "C) rose", "D) rising"}, 1);
        VocabularyQuiz quiz6 = new VocabularyQuiz("How many ____ are there in a year?", new String[]{"A) day", "B) days", "C) week", "D) month"}, 1);
        VocabularyQuiz quiz7 = new VocabularyQuiz("I ____ to the store yesterday.", new String[]{"A) go", "B) goes", "C) went", "D) going"}, 2);
        VocabularyQuiz quiz8 = new VocabularyQuiz("Mount Everest is the ____ mountain in the world.", new String[]{"A) tall", "B) taller", "C) tallest", "D) more tall"}, 2);
        VocabularyQuiz quiz9 = new VocabularyQuiz("Can you ____ me the way to the train station?", new String[]{"A) say", "B) tell", "C) speak", "D) talk"}, 1);
        // Add more questions as needed

        quizzes.add(quiz1);
        quizzes.add(quiz2);
        quizzes.add(quiz3);
        quizzes.add(quiz4);
        quizzes.add(quiz5);
        quizzes.add(quiz6);
        quizzes.add(quiz7);
        quizzes.add(quiz8);
        quizzes.add(quiz9);
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

    public String getCorrectAnswer() {
        VocabularyQuiz currentQuiz = quizzes.get(currentQuestionIndex - 1);
        return currentQuiz.getCorrectAnswer();
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < quizzes.size();
    }

    public void resetGame() {
        quizzes.clear();  // Xóa danh sách câu hỏi
        initializeQuizzes();  // Khởi tạo lại câu hỏi
        Collections.shuffle(quizzes);  // Trộn câu hỏi
        currentQuestionIndex = 0;  // Đặt lại chỉ số của câu hỏi hiện tại
        quizCompleted = false;  // Đặt lại trạng thái hoàn thành
    }

    public boolean isQuizCompleted() {
        return quizCompleted;
    }

    public void setQuizCompleted(boolean quizCompleted) {
        this.quizCompleted = quizCompleted;
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

    public int getScore() {
        return score;
    }
}
