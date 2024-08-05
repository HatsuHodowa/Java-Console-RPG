package src;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import src.GameObjects.*;

public class Main {

    // Constant values
    static final String SEPARATOR_LINE = "------------------------------------------------------------------------------------";
    static final String UNFILLED_BAR = "_";
    static final String FILLED_BAR = "#";
    static final File DATA_DIRECTORY = new File("data");

    // Static values
    public static Scanner scanner = new Scanner(System.in);
    public static Gson gson = new Gson();
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
        System.out.println("1: New game\n2: Load game\n3: Delete game\n0: Exit program");
        int choice = promptIntegerInput();

        // Processing choice
        switch(choice) {
            case 1: newGame(); break;
            case 2: loadGame(); break;
            case 3: deleteGame(); break;
            case 0: exitProgram(); break;
            default: mainMenu(); break;
        }
    }

    // Prompting integer choice
    public static int promptIntegerInput() {
        try {
            int input = scanner.nextInt(); scanner.nextLine();
            return input;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return promptIntegerInput();
        }
    }

    // Confirmation messages
    public static boolean confirmationMessage(String message) {

        // Confirmation prompt
        System.out.println(message);
        System.out.println("1: Confirm\n0: Cancel");
        int input = promptIntegerInput();

        // Checking input
        switch (input) {
            case 1: return true;
            case 0: return false;
            default: return confirmationMessage(message);
        }

    }

    // Deleting game file
    public static void deleteGame() {

        // Getting game files
        printSeparator();
        List<File> gameFiles = getGameFiles();
        if (gameFiles == null) {
            System.out.println("\nThere are no current game files. Returning to menu.");
            mainMenu();
            return;
        }

        // Choosing game file
        int choiceId = getFileChoice(gameFiles, "\nPlease choose an option from the list to delete:");
        if (choiceId == 0) {
            mainMenu();
            return;
        }
        File chosenFile = gameFiles.get(choiceId - 1);

        // Confirming
        if (confirmationMessage("\nAre you sure you want to delete the file " + chosenFile.getName() + "? You cannot undo this action.")) {
            chosenFile.delete();
            System.out.println("\nThe file has been successfully deleted.");
        }

        // Returning
        mainMenu();

    }

    // New game
    public static void newGame() {
        currentGame = new Game();
        currentGame.initializeGame();
    }

    // Extracting the integer IDs from game file names
    public static int extractIdFromFileName(String fileName) {
        try {
            String fileId = fileName.replaceAll("file_(\\d+).*.json", "$1");
            return Integer.parseInt(fileId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Getting a sorted list of all available game files
    public static List<File> getGameFiles() {

        // Checking if directory is valid
        if (DATA_DIRECTORY.exists() && DATA_DIRECTORY.isDirectory()) {

            // Gathering array of files
            FilenameFilter jsonFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".json");
                }
            };
            File[] files = DATA_DIRECTORY.listFiles(jsonFilter);

            // Looping through files
            if (files != null && files.length > 0) {

                // Sorting list
                List<File> fileList = new ArrayList<>();
                Collections.addAll(fileList, files);

                Collections.sort(fileList, new Comparator<File>() {
                    @Override
                    public int compare(File f1, File f2) {
                        int f1ID = extractIdFromFileName(f1.getName());
                        int f2ID = extractIdFromFileName(f2.getName());
                        return Integer.compare(f1ID, f2ID);
                    }
                });

                // Returning
                return fileList;

            }

        }

        // No files found
        return null;

    }

    // Getting a file choice out of a list of file choices
    private static int getFileChoice(List<File> fileList, String choiceMessage) {

        // Printing file options
        System.out.println(choiceMessage);
        for (int i = 0; i < fileList.size(); i++) {
            File current = fileList.get(i);
            System.out.println(Integer.toString(i + 1) + ": " + current.getName());
        }
        System.out.println("0: Cancel action");

        // Getting input
        int fileChoice = promptIntegerInput();

        if (fileChoice > fileList.size() || fileChoice < 0) {
            System.out.println("\nYour choice of " + Integer.toString(fileChoice) + " was not a valid choice.");
            return getFileChoice(fileList, choiceMessage);
        } else {
            return fileChoice;
        }

    }

    // Loading a game from a JSON file
    private static void loadGameFromFile(File gameJson) {

        // Display
        System.out.println("\nLoading save file " + gameJson.getName() + " . . . ");

        // Converting to object
        try {

            // Loading game
            Game loadedGame = Game.createGameFromJSON(gameJson);
            currentGame = loadedGame;
            currentGame.initializeGame();

        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.err.println("Invalid JSON syntax: " + e.getMessage());
            e.printStackTrace();
        }

    }

    // Main load game method to load game
    public static void loadGame() {

        // Separator
        Main.printSeparator();

        // Getting input
        List<File> fileList = getGameFiles();

        if (fileList != null) {

            // Prompting file choice
            int fileChoice = getFileChoice(fileList, "\nPlease choose a file to load from the options below:");
            if (fileChoice == 0) {
                mainMenu();
                return;
            }
            File chosenFile = fileList.get(fileChoice - 1);

            // Loading file
            loadGameFromFile(chosenFile);

        } else {
            System.out.println("\nThere are no files available to load.");
            mainMenu();
        }

    }

    // Exit program
    public static void exitProgram() {
        System.out.println("Exiting program . . . ");
    }
}