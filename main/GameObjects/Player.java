package main.GameObjects;

import main.*;

public class Player {

    // Constants
    public static final int SKILL_POINTS_PER_LEVEL = 3;

    // Other properties
    Game game;

    // Character details
    String firstName;
    String lastName;
    String gender;

    // Levels and experience
    int level = 1;
    int experience = 0;
    int skillPoints = 0;

    // Primary stats
    int agility = 0;
    int dexterity = 0;
    int strength = 0;
    int vitality = 0;
    
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
        int genderChoice = Main.scanner.nextInt(); Main.scanner.nextLine();

        switch (genderChoice) {
            case 1: this.gender = "Male"; break;
            case 2: this.gender = "Female"; break;
            case 3: this.gender = "Not Specified"; break;
            default: promptChooseGender();
        }

    }

    // Prompting to choose name
    public void promptChooseName() {

        System.out.println("\nInsert your character's first name:");
        String firstName = Main.scanner.nextLine();
        this.firstName = firstName;

        System.out.println("\nInsert your character's last name:");
        String lastName = Main.scanner.nextLine();
        this.lastName = lastName;

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
        System.out.println("Skill Points: " + Integer.toString(this.skillPoints));
        System.out.println("\nExperience: (" + this.experience + "/" + this.getMaxExperience() + ")");
        Main.printProgressBar((float) this.experience / (float) this.getMaxExperience());
        
        // Primary stats
        System.out.println("\nPrimary Stats:");
        System.out.println("Agility: " + Integer.toString(this.agility));
        System.out.println("Dexterity: " + Integer.toString(this.dexterity));
        System.out.println("Strength: " + Integer.toString(this.strength));
        System.out.println("Vitality: " + Integer.toString(this.vitality));

        // Displaying options
        this.profileOptions();

    }

    // Profile options
    public void profileOptions() {

        // Options
        System.out.println("\nWhat would you like to do now?");
        System.out.println("1: Apply skill points\n2: Get 9999 exp (testing)\n0: Return to menu");
        int choice = Main.scanner.nextInt(); Main.scanner.nextLine();

        switch (choice) {
            case 1: this.applySkillPoints(); break;
            case 2: this.addExperience(9999);; this.game.gameMenu(); break;
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
        this.skillPoints += SKILL_POINTS_PER_LEVEL;
    }

    // Applying skill points
    public void applySkillPoints() {
        System.out.println("\nThis feature has not been implemented yet.");
        this.game.gameMenu();
    }

}