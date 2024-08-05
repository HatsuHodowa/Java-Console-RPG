package src.GameObjects;

import src.*;
import java.io.FileWriter;
import java.io.IOException;

public class Game {

    // Static properties
    static int nextGameId = 0;

    // Class properties
    Player currentPlayer;
    int gameId;

    // Constructor
    public Game() {

        // Creating player
        this.currentPlayer = new Player(this);
        this.gameId = nextGameId; nextGameId++;

        // Opening game menu
        gameMenu();

    }

    // Game menu
    public void gameMenu() {

        // Prompting options
        Main.printSeparator();
        System.out.println("\nIt's a bright, sunny day. What would you like to do?");
        System.out.println("1: View profile\n2: Save current game\n0: Return to main menu");
        
        int choice = Main.scanner.nextInt();
        Main.scanner.nextLine();

        // Processing choice
        switch (choice) {
            case 1: this.currentPlayer.viewProfile(); break;
            case 2: saveGame(); break;
            case 0: promptSaveGame(); Main.mainMenu(); break;
            default: gameMenu(); break;
        }

    }

    // Prompting to save
    public void promptSaveGame() {
        System.out.println("\nWould you like to save the current game?");
        System.out.println("1: Yes\n2: No\n");
        int choice = Main.scanner.nextInt(); Main.scanner.nextLine();

        switch (choice) {
            case 1: saveGame(); break;
            case 2: default: break;
        }
    }
    
    // Saving game
    public void saveGame() {
        System.out.println("\nSaving current game as file " + this.gameId + " . . . ");

        // Converting to JSON and writing to file
        String jsonString = Main.gson.toJson(this);
        try (FileWriter writer = new FileWriter("data/file_" + this.gameId + ".json")) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}