package src.game;

import src.ui.VocabularyPanel;

import java.util.List;

public abstract class VocabularyGameBase {
    protected List<VocabularyQuiz> quizzes;
    protected int currentQuestionIndex;
    protected VocabularyPanel vocabularyPanel;
    protected boolean quizCompleted;
    public int score;
    public abstract void initializeQuizzes();
    public abstract VocabularyQuiz getNextQuiz();
    public abstract boolean checkAnswer(int userChoice);
    public abstract String getCorrectAnswer();
    public abstract boolean hasMoreQuestions();
    public abstract void resetGame();
    public abstract boolean isQuizCompleted();
    public abstract void setQuizCompleted(boolean quizCompleted);
    public abstract void startGame();
    public abstract int getScore();
}
