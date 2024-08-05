package src.GameObjects;

import src.*;

public class Player extends Creature {

    // Constants
    public static final int SKILL_POINTS_PER_LEVEL = 3;

    // Other properties
    transient Game game;

    // Character details
    String firstName;
    String lastName;
    String gender;

    // Levels and experience
    int level = 1;
    int experience = 0;
    int statPoints = 0;
    
    // Constructor
    public Player(Game game) { 

        // Setting game property
        this.game = game;

        // Prompting information
        System.out.println("\nCreating new player instance . . . ");
        this.promptChooseGender();
        this.promptChooseName();
        System.out.println("\nPlayer object successfully created. Proceeding transportation to fantasy world.");

    }

    // Prompting to choose gender
    public void promptChooseGender() {

        System.out.println("\nChoose your character's gender:");
        System.out.println("1: Male\n2: Female\n3: Not Specified");
        int genderChoice = Main.promptIntegerInput();

        switch (genderChoice) {
            case 1: this.gender = "Male"; break;
            case 2: this.gender = "Female"; break;
            case 3: this.gender = "Not Specified"; break;
            default: promptChooseGender();
        }

    }

    // Prompting to choose name
    public void promptChooseName() {
        this.promptChooseFirstName();
        this.promptChooseLastName();
    }

    private void promptChooseFirstName() {

        // Prompting
        System.out.println("\nInsert your character's first name:");
        String firstName = Main.scanner.nextLine();

        // Confirming
        if (Main.confirmationMessage("\nAre you okay with the first name \"" + firstName + "\"? You can't change this later.")) {
            this.firstName = firstName;
        } else {
            promptChooseFirstName();
        }

    }

    private void promptChooseLastName() {

        // Prompting
        System.out.println("\nInsert your character's last name:");
        String lastName = Main.scanner.nextLine();

        // Confirming
        if (Main.confirmationMessage("\nAre you okay with the last name \"" + lastName + "\"? You can't change this later.")) {
            this.lastName = lastName;
        } else {
            promptChooseLastName();
        }

    }

    // Viewing profile
    public void viewProfile() {
        Main.printSeparator();

        // Character details
        System.out.println("Character Information:");
        System.out.println("Name: " + this.firstName + " " + this.lastName);
        System.out.println("Gender: " + this.gender);

        // Level information
        System.out.println("\nLevel Stats:");
        System.out.println("Level: " + Integer.toString(this.level));
        System.out.println("Stat Points: " + Integer.toString(this.statPoints));
        System.out.println("\nExperience: (" + this.experience + "/" + this.getMaxExperience() + ")");
        Main.printProgressBar((float) this.experience / (float) this.getMaxExperience());
        
        // Primary stats
        System.out.println("\nPrimary Stats:");
        System.out.println("Agility: " + Integer.toString(this.agility));
        System.out.println("Dexterity: " + Integer.toString(this.dexterity));
        System.out.println("Intelligence: " + Integer.toString(this.intelligence));
        System.out.println("Strength: " + Integer.toString(this.strength));
        System.out.println("Vitality: " + Integer.toString(this.vitality));

        // Displaying options
        this.profileOptions();

    }

    // Profile options
    public void profileOptions() {

        // Options
        System.out.println("\nWhat would you like to do now?");
        System.out.println("1: Apply stat points\n2: Get 50 exp (testing)\n0: Return to options");
        int choice = Main.promptIntegerInput();

        switch (choice) {
            case 1: this.applyStatPoints(); break;
            case 2: this.addExperience(50); this.viewProfile(); break;
            case 0: this.game.gameMenu(); break;
            default: this.profileOptions(); break;
        }

    }

    // Calculating max experience
    public int getMaxExperience() {
        return this.level * 100;
    }

    // Adding experience
    public void addExperience(int toAdd) {
        this.experience += toAdd;

        while (this.experience >= this.getMaxExperience()) {
            this.levelUp();
        }
    }

    // Leveling up
    public void levelUp() {
        this.experience -= this.getMaxExperience();
        this.level++;
        this.statPoints += SKILL_POINTS_PER_LEVEL;
    }

    // Applying stat points
    public void applyStatPoints() {
        if (this.statPoints > 0) {

            // Prompting stat choice
            System.out.println("\nWhat stat would you like to apply stat points to?");
            System.out.println("1: Agility\n2: Dexterity\n3: Intelligence\n4: Strength\n5: Vitality\n0: Cancel");
            int statChoice = Main.promptIntegerInput();

            // Prompting points to spend
            int pointsToSpend = promptStatPointsToSpend();

            // Taking stat points
            switch (statChoice) {
                case 0: break;
                case 1: case 2: case 3: case 4: case 5:
                    this.statPoints -= pointsToSpend; break;
            }

            // Adding to stats
            switch (statChoice) {
                case 1: this.agility += pointsToSpend; break;
                case 2: this.dexterity += pointsToSpend; break;
                case 3: this.intelligence += pointsToSpend; break;
                case 4: this.strength += pointsToSpend; break;
                case 5: this.vitality += pointsToSpend; break;
                case 0: System.out.println("Canceled stat point application."); break;
                default: applyStatPoints(); break;
            }

            // Confirmation message
            System.out.println("Successfully applied " + pointsToSpend + " stat points.");

        } else {
            System.out.println("\nYou don't have any stat points to spend!");
        }
        this.viewProfile();
    }

    // Helper function for choosing amount of stat points to spend
    public int promptStatPointsToSpend() {

        // Prompting choice
        System.out.println("\nHow many stat points would you like to spend? You have " + this.statPoints + " stat points available.");
        int choice = Main.promptIntegerInput();

        // Clamping choice
        int clampedChoice = Math.max(Math.min(choice, this.statPoints), 0);
        if (choice != clampedChoice) {
            System.out.println("Specified number of stat points was out of range. Clamped choice to " + clampedChoice + ".");
        }

        // Returning
        return clampedChoice;

    }

}