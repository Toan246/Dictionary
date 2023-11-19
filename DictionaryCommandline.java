class DictionaryCommandline {
    public void showAllWords(Dictionary dictionary) {
        System.out.println("No | English | Vietnamese");
        System.out.println("----------------------------");
        int index = 1;
        for (Word word : dictionary.words) {
            System.out.printf("%-3d| %-15s| %s%n", index++, word.word_target, word.word_explain);
        }
    }

    public void dictionaryBasic() {
        Dictionary dictionary = new Dictionary();
        DictionaryManagement management = new DictionaryManagement();

        // Insert words from command line
        management.insertFromCommandline(dictionary);

        // Show all words
        showAllWords(dictionary);
    }
}