package src.GameObjects;

import src.*;
import src.Enum.WorldRegions;
import src.World.Explore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.google.gson.JsonSyntaxException;

public class Game {

    // Constants
    public static final int STARTING_ID = 1;

    // Class properties
    Player currentPlayer;
    int gameId;

    // Constructor
    public Game() {
        gameId = getNextGameId();
    }

    // Constructing game object from JSON file
    public static Game createGameFromJSON(File jsonFile) throws JsonSyntaxException, IOException {

        // Creating and returning game object
        Game gameObj = Main.gson.fromJson(Files.readString(jsonFile.toPath()), Game.class);
        gameObj.currentPlayer.game = gameObj;
        return gameObj;

    }

    // Getting next game id
    public static int getNextGameId() {

        // Looping files
        List<File> gameFiles = Main.getGameFiles();
        int highestId = STARTING_ID - 1;

        if (gameFiles != null) {
            for (File file : gameFiles) {
                int fileId = Main.extractIdFromFileName(file.getName());
                if (fileId > highestId) {
                    highestId = fileId;
                }
            }
        }

        // Returning next ID to use
        return highestId + 1;

    }

    // Game initialization
    public void initializeGame() {

        // Creating player
        if (this.currentPlayer == null) {
            this.currentPlayer = new Player(this);
        }

        // Opening game menu
        gameMenu();

    }

    // Game menu
    public void gameMenu() {

        // Prompting options
        Main.printSeparator();
        System.out.println("\nIt's a bright, sunny day. What would you like to do?");
        System.out.println("1: View profile\n2: Explore\n3: Save current game\n0: Return to main menu");
        
        int choice = Main.promptIntegerInput();

        // Processing choice
        switch (choice) {
            case 1: this.currentPlayer.viewProfile(); break;
            case 2: explore(); gameMenu(); break;
            case 3: saveGame(); gameMenu(); break;
            case 0: promptSaveGame(); Main.mainMenu(); break;
            default: gameMenu(); break;
        }

    }

    // Prompting explore
    public void explore() {
        WorldRegions region = Explore.promptRegionChoice();
        if (region != null) {
            new Explore(region);
        }
    }

    // Prompting to save
    public void promptSaveGame() {
        System.out.println("\nWould you like to save the current game?");
        System.out.println("1: Yes\n2: No\n");
        int choice = Main.promptIntegerInput();

        switch (choice) {
            case 1: saveGame(); break;
            case 2: default: break;
        }
    }
    
    // Saving game
    public void saveGame() {

        // Creating file name
        String namePart = this.currentPlayer.firstName + "_" + this.currentPlayer.lastName;
        namePart = namePart.replaceAll("[^a-zA-Z0-9_]", "");
        String fileName = "file_" + this.gameId + "_" + namePart + ".json";
        System.out.println("\nSaving current game as file " + this.gameId + " . . . ");

        // Converting to JSON and writing to file
        String jsonString = Main.gson.toJson(this);
        try (FileWriter writer = new FileWriter("data/" + fileName)) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}