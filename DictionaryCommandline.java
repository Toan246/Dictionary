class DictionaryCommandline {
    public void showAllWords(Dictionary dictionary) {
        System.out.println("No | English | Vietnamese");
        System.out.println("----------------------------");
        int index = 1;
        for (Word word : dictionary.words) {
            System.out.printf("%-3d| %-15s| %s%n", index++, word.word_target, word.word_explain);
        }
    }
}