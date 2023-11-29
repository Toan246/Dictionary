class VocabularyQuiz {
    private String question;
    private String[] choices;
    private int correctIndex;

    public VocabularyQuiz(String question, String[] choices, int correctIndex) {
        this.question = question;
        this.choices = choices;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        if (correctIndex >= 0 && correctIndex < choices.length) {
            return choices[correctIndex];
        }
        return "N/A"; // or handle the case when correctIndex is out of bounds
    }

    public boolean isCorrect(int userChoice) {
        return userChoice == correctIndex;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}