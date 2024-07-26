package main.GameObjects;

import main.*;

public class Game {

    // Properties
    Player currentPlayer;

    // Constructor
    public Game() {

        // Creating player
        this.currentPlayer = new Player(this);

        // Opening game menu
        gameMenu();

    }

    // Game menu
    public void gameMenu() {

        // Prompting options
        Main.printSeparator();
        System.out.println("\nIt's a bright, sunny day. What would you like to do?");
        System.out.println("1: View profile\n2: Return to main menu");
        
        int choice = Main.scanner.nextInt();
        Main.scanner.nextLine();

        // Processing choice
        switch (choice) {
            case 1: this.currentPlayer.viewProfile(); break;
            case 2: Main.mainMenu(); break;
            default: gameMenu(); break;
        }

    }

}