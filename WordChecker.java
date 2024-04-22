import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Scanner;

/**
 * The WordChecker class checks the spelling of words and suggests corrections.
 */
public class WordChecker {

    private ArrayList<String> wordlist;
    private Scanner scan;

    /**
     * Constructs a WordChecker object and initializes the word list.
     */
    public WordChecker() {
        importWords();
        scan = new Scanner(System.in);
    }

    /**
     * Checks the spelling of the given word and suggests corrections if needed.
     * @param word The word to be checked for spelling.
     */
    public void checkWordSpelling(String word) {
        Stack<String> possibleWords = new Stack<>();
        boolean isCorrectWord = false;
        int maxMatchingLetters = Integer.MIN_VALUE;

        // Iterating through the word list to find potential corrections
        for (int i = 0; i < wordlist.size(); i++) {
            String str = wordlist.get(i);
            if (str.equals(word)) {
                isCorrectWord = true;
                break;
            } else {
                int num = compareWords(str, word);
                if (num > maxMatchingLetters) {
                    maxMatchingLetters = num;
                    while (!possibleWords.isEmpty()) {
                        possibleWords.pop();
                    }
                    possibleWords.push(str);
                } else if (num == maxMatchingLetters) {
                    possibleWords.push(str);
                }
            }
        }

        // Printing suggestions or informing if the word is already correct
        if (isCorrectWord) {
            System.out.println("Your word is already spelled correctly!");
            System.exit(0);
        } else if (possibleWords.isEmpty()) {
            System.out.println("Unable to correct \"" + word + "\"");
            System.exit(0);
        } else {
            System.out.print("Did you mean any of these words?: [");
            while (!possibleWords.isEmpty()) {
                System.out.print(possibleWords.pop());
                if (possibleWords.isEmpty()) {
                    System.out.print("]");
                } else {
                    System.out.print(", ");
                }
            }
        }

        // Asking user for confirmation and option to add the correct word to the list
        System.out.print("\nY/N?: ");
        String ans = scan.nextLine();
        if (ans.equals("N")) {
            System.out.print("Would you like to add the correct word to the list? (Y/N): ");
            String ansTwo = scan.nextLine();
            if (ansTwo.equals("Y")) {
                System.out.print("What was the correct word?: ");
                String correctWord = scan.nextLine();
                if (!checkIfListContains(correctWord)) {
                    wordlist.add(correctWord);
                    System.out.println("Word Added!");
                } else {
                    System.out.println("Word was already in the list?!");
                }
            }
        }

        System.out.println();
        saveWords();
    }

    /**
     * Checks if the word list contains the given word.
     * @param word The word to be checked.
     * @return true if the word is in the list, false otherwise.
     */
    private boolean checkIfListContains(String word) {
        for (String wordInList : wordlist) {
            if (wordInList.equals(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Saves the current word list to a file.
     */
    private void saveWords() {
        try {
            PrintWriter dataFile = new PrintWriter("src//wordlist.txt");
            for (String word : wordlist) {
                dataFile.println(word);
            }
            dataFile.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Compares two words and returns the number of matching letters.
     * @param word1 The first word.
     * @param word2 The second word.
     * @return The number of matching letters between the two words.
     */
    private int compareWords(String word1, String word2) {
        int matchingLetters = 0;
        if (word1.length() >= word2.length() + 2 || word1.length() <= word2.length() - 2) {
            return 0;
        }
        if (word1.length() > word2.length()) {
            for (int i = 0; i < word2.length(); i++) {
                String firstWordChar = word1.substring(i, i + 1);
                String secWordChar = word2.substring(i, i + 1);
                if (firstWordChar.equals(secWordChar)) {
                    matchingLetters++;
                }
            }
        } else {
            for (int i = 0; i < word1.length(); i++) {
                String firstWordChar = word1.substring(i, i + 1);
                String secWordChar = word2.substring(i, i + 1);
                if (firstWordChar.equals(secWordChar)) {
                    matchingLetters++;
                }
            }
        }
        return matchingLetters;
    }

    /**
     * Imports words from a file into the word list.
     */
    private void importWords() {
        String[] tmp = null;
        try {
            FileReader fileReader = new FileReader("src//wordlist.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<String> lines = new ArrayList<String>();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();
            tmp = lines.toArray(new String[lines.size()]);
        } catch (IOException e) {
            System.out.println("Error importing file; unable to access " + e.getMessage());
        }

        wordlist = new ArrayList<String>(Arrays.asList(tmp));
    }
}
