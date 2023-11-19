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
}
