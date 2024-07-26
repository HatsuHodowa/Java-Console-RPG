package main;

import java.util.Scanner;
import main.GameObjects.*;

public class Main {

    // Constant values
    public static final String SEPARATOR_LINE = "------------------------------------------------------------------------------------";
    public static final String UNFILLED_BAR = "_";
    public static final String FILLED_BAR = "#";

    // Static values
    public static Scanner scanner = new Scanner(System.in);
    public static Game currentGame;

    // Main method
    public static void main(String[] args) {
        mainMenu();
    }

    // Printing separator
    public static void printSeparator() {
        System.out.println("\n" + SEPARATOR_LINE);
    }

    // Displaying progress bar
    public static void printProgressBar(float alpha) {
        
        // Ensuring alpha50 is between 0-50
        alpha = Math.max(Math.min(alpha, 1), 0);
        int alpha50 = (int) (alpha * 100 / 2);

        // Creating bar
        String toPrint = "";
        for (int i = 1; i < alpha50; i++) {
            toPrint += FILLED_BAR;
        }
        for (int i = alpha50; i < 50; i++) {
            toPrint += UNFILLED_BAR;
        }

        // Outputting
        System.out.println(toPrint);

    }

    // Main menu
    public static void mainMenu() {

        // Introduction
        printSeparator();
        System.out.println("\nWelcome to Java Fantasy RPG! To get started, select one of the options below.");
        System.out.println("1: New game\n2: Load game\n0: Exit program");
        int choice = scanner.nextInt();
        scanner.nextLine();

        // Processing choice
        switch(choice) {
            case 1: newGame(); break;
            case 2: loadGame(); break;
            case 0: exitProgram(); break;
            default: mainMenu(); break;
        }
    }

    // New game
    public static void newGame() {
        currentGame = new Game();
    }

    // Load game
    public static void loadGame() {
        System.out.println("load game");
    }

    // Exit program
    public static void exitProgram() {
        System.out.println("Exiting program . . . ");
    }
}