package src.Dictionary;

public class DictionaryStats {

    public static int countWords(Dictionary dictionary) {
        return dictionary.words.size();
    }

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        DictionaryManagement management = new DictionaryManagement();
        management.insertFromFile(dictionary, "data/dictionaries");

        int wordCount = countWords(dictionary);
        System.out.println("Number of words in the dictionary: " + wordCount);
    }
}