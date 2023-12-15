package src.Dictionary;

public interface DictionaryManager {
    void insertFromCommandline(Dictionary dictionary);
    void insertFromFile(Dictionary dictionary, String fileName);
    void dictionaryLookup(Dictionary dictionary, String word);
    void addWord(Dictionary dictionary, String target, String explain);
    void removeWord(Dictionary dictionary, String target);
    void updateWord(Dictionary dictionary, String target, String newExplain);
    void dictionaryExportToFile(Dictionary dictionary, String fileName);
}

