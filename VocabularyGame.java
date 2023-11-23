import java.util.Random;
import java.util.Scanner;

class VocabularyGame extends DictionaryCommandline {
    private VocabularyQuiz[] quizzes;
    private Random random;

    public VocabularyGame() {
        this.random = new Random();
        initializeQuizzes();
    }

    private void initializeQuizzes() {
        // Add your own quiz questions here
        quizzes = new VocabularyQuiz[]{
                new VocabularyQuiz("What _ you doing?", new String[]{"A) do", "B) are", "C) is", "D) have"}, 1),
                // Add more questions as needed
        };
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        System.out.println("Welcome to the Vocabulary Quiz Game!");
        System.out.println("You will be presented with multiple-choice questions.");

        for (int i = 0; i < quizzes.length; i++) {
            VocabularyQuiz quiz = quizzes[i];
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

        System.out.println("Game Over. Your score: " + score + "/" + quizzes.length);
    }
}
