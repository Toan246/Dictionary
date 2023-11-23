import java.util.Scanner;

class DictionaryCommandline {
    DictionaryManagement management;

    public DictionaryCommandline() {
        this.management = new DictionaryManagement();
    }

    public void showAllWords(Dictionary dictionary) {
        System.out.println("No | English | Vietnamese");
        System.out.println("----------------------------");
        int index = 1;
        for (Word word : dictionary.words) {
            System.out.printf("%-3d| %-15s| %s%n", index++, word.word_target, word.word_explain);
        }
    }

    public void dictionarySearcher(Dictionary dictionary, String prefix) {
        System.out.println("Search results for prefix '" + prefix + "':");
        for (Word word : dictionary.words) {
            if (word.word_target.toLowerCase().startsWith(prefix.toLowerCase())) {
                System.out.println(word.word_target + ": " + word.word_explain);
            }
        }
    }

    public void dictionaryBasic() {
        Dictionary dictionary = new Dictionary();
        management.insertFromCommandline(dictionary);
        showAllWords(dictionary);
    }

    public void dictionaryAdvanced() {
        Scanner scanner = new Scanner(System.in);
        Dictionary dictionary = new Dictionary();
        management.insertFromFile(dictionary, "dictionaries");

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
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();  // Consume the invalid input
                System.out.println("Action not supported. Please enter a valid number.");
                continue;
            }

            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 0:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                case 1:
                    System.out.print("Enter English word to add: ");
                    String target = scanner.nextLine();
                    System.out.print("Enter Vietnamese explanation: ");
                    String explain = scanner.nextLine();
                    management.addWord(dictionary, target, explain);
                    break;
                case 2:
                    System.out.print("Enter English word to remove: ");
                    String targetToRemove = scanner.nextLine();
                    management.removeWord(dictionary, targetToRemove);
                    break;
                case 3:
                    System.out.print("Enter English word to update: ");
                    String targetToUpdate = scanner.nextLine();
                    System.out.print("Enter new Vietnamese explanation: ");
                    String newExplain = scanner.nextLine();
                    management.updateWord(dictionary, targetToUpdate, newExplain);
                    break;
                case 4:
                    showAllWords(dictionary);
                    break;
                case 5:
                    System.out.print("Enter word to lookup: ");
                    String wordToLookup = scanner.nextLine();
                    management.dictionaryLookup(dictionary, wordToLookup);
                    break;
                case 6:
                    System.out.print("Enter prefix to search: ");
                    String prefix = scanner.nextLine();
                    dictionarySearcher(dictionary, prefix);
                    break;
                case 7:
                    VocabularyGame game = new VocabularyGame();
                    game.startGame();
                    break;
                case 8:
                    System.out.print("Enter file name to import from: ");
                    String importFileName = scanner.nextLine();
                    management.insertFromFile(dictionary, importFileName);
                    break;
                case 9:
                    System.out.print("Enter file name to export to: ");
                    String exportFileName = scanner.nextLine();
                    management.dictionaryExportToFile(dictionary, exportFileName);
                    break;
                default:
                    System.out.println("Action not supported. Please enter a valid number.");
                    break;
            }
        }
    }

    public String dictionaryLookup(String wordToLookup) {
        Dictionary dictionary = new Dictionary();
        management.insertFromFile(dictionary, "dictionaries");

        for (Word word : dictionary.words) {
            if (word.word_target.equalsIgnoreCase(wordToLookup)) {
                return "Meaning: " + word.word_explain;
            }
        }

        return "Word not found in the dictionary.";
    }
}