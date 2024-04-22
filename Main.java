import java.util.Scanner;

/**
 * The Main class contains the main method to run the Auto-Corrector program.
 * @author Diancheng Tang
 */
public class Main {

    /**
     * The main method to start the Auto-Corrector program.
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Auto-Corrector!");
        Scanner scan = new Scanner(System.in);
        WordChecker wordChecker = new WordChecker();
        String word = "";

        // Main loop to accept user input until "q" is entered
        while (!word.equals("q")) {
            System.out.print("Please enter a word and the computer will try to correct the spelling (press \"q\" to quit): ");
            word = scan.nextLine();
            if (word.equals("q")) {
                break;
            }
            wordChecker.checkWordSpelling(word);
        }

        System.out.println("Thanks for using the Auto-Corrector!");
    }
}
