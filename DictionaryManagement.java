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

    public void insertFromFile(Dictionary dictionary, String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String target = parts[0];
                String explain = parts[1];
                Word word = new Word(target, explain);
                dictionary.words.add(word);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dictionaryLookup(Dictionary dictionary, String word) {
        for (Word w : dictionary.words) {
            if (w.word_target.equalsIgnoreCase(word)) {
                System.out.println("Meaning: " + w.word_explain);
                return;
            }
        }
        System.out.println("Word not found in the dictionary.");
    }

    public void dictionarySearcher(Dictionary dictionary, String prefix) {
        System.out.println("Search results:");
        for (Word w : dictionary.words) {
            if (w.word_target.toLowerCase().startsWith(prefix.toLowerCase())) {
                System.out.println(w.word_target);
            }
        }
    }

    public void dictionaryExportToFile(Dictionary dictionary, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (Word w : dictionary.words) {
                bw.write(w.word_target + "\t" + w.word_explain + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dictionaryAdvanced() {
        Dictionary dictionary = new Dictionary();
        DictionaryManagement management = new DictionaryManagement();
        DictionaryCommandline commandLine = new DictionaryCommandline();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to My Application!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");
            System.out.print("Your action: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Action not supported");
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting the application.");
                    return;
                case 1:
                    management.insertFromCommandline(dictionary);
                    break;
                case 2:
                    // Implement remove functionality
                    break;
                case 3:
                    // Implement update functionality
                    break;
                case 4:
                    commandLine.showAllWords(dictionary);
                    break;
                case 5:
                    System.out.print("Enter the word to lookup: ");
                    String lookupWord = scanner.nextLine();
                    management.dictionaryLookup(dictionary, lookupWord);
                    break;
                case 6:
                    System.out.print("Enter the prefix to search: ");
                    String searchPrefix = scanner.nextLine();
                    management.dictionarySearcher(dictionary, searchPrefix);
                    break;
                case 7:
                    // Implement game functionality
                    break;
                case 8:
                    System.out.print("Enter the filename to import from: ");
                    String importFilename = scanner.nextLine();
                    management.insertFromFile(dictionary, importFilename);
                    break;
                case 9:
                    System.out.print("Enter the filename to export to: ");
                    String exportFilename = scanner.nextLine();
                    management.dictionaryExportToFile(dictionary, exportFilename);
                    break;
                default:
                    System.out.println("Action not supported");
            }
        }
    }
}
