import java.io.*;
import java.util.Scanner;

class DictionaryManagement {
    public void insertFromCommandline(Dictionary dictionary) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of words: ");
        int n = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        for (int i = 0; i < n; i++) {
            System.out.print("Enter English word #" + (i + 1) + ": ");
            String target = scanner.nextLine();
            System.out.print("Enter Vietnamese explanation #" + (i + 1) + ": ");
            String explain = scanner.nextLine();

            Word word = new Word(target, explain);
            dictionary.words.add(word);
        }
    }

    public void insertFromFile(Dictionary dictionary, String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    Word word = new Word(parts[0], parts[1]);
                    dictionary.words.add(word);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dictionaryLookup(Dictionary dictionary, String word) {
        for (Word w : dictionary.words) {
            if (w.word_target.equalsIgnoreCase(word)) {
                System.out.println("Meaning: " + w.word_explain);
                return;
            } else if (w.word_explain.equalsIgnoreCase(word)) {
                System.out.println("Meaning: " + w.word_target);
                return;
            }
        }
        System.out.println("Word not found in the dictionary.");
    }

    public void addWord(Dictionary dictionary, String target, String explain) {
        Word word = new Word(target, explain);
        dictionary.words.add(word);
        System.out.println("Word added successfully.");
    }

    public void removeWord(Dictionary dictionary, String target) {
        for (Word w : dictionary.words) {
            if (w.word_target.equalsIgnoreCase(target)) {
                dictionary.words.remove(w);
                System.out.println("Word removed successfully.");
                return;
            }
        }
        System.out.println("Word not found in the dictionary.");
    }

    public void updateWord(Dictionary dictionary, String target, String newExplain) {
        for (Word w : dictionary.words) {
            if (w.word_target.equalsIgnoreCase(target)) {
                w.word_explain = newExplain;
                System.out.println("Word updated successfully.");
                return;
            }
        }
        System.out.println("Word not found in the dictionary.");
    }

    public void dictionaryExportToFile(Dictionary dictionary, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Word w : dictionary.words) {
                writer.write(w.word_target + "\t" + w.word_explain);
                writer.newLine();
            }
            writer.close();
            System.out.println("Dictionary exported to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
