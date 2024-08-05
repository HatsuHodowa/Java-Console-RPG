package src.World;

import src.Main;
import src.Enum.WorldRegions;
import src.Utilities.StringUtil;

public class Explore {

    // Main properties
    public WorldRegions region;
    public int roomCount;

    // Constructors
    public Explore(WorldRegions region) {

        // Setting properties
        this.region = region;
        this.roomCount = region.getRoomCount();

        // Starting prompt
        System.out.println("\nYou choose to explore the " + region.getDisplayName() + ".");

        // Exploring rooms
        this.exploreRooms();

    }

    // Prompting region choice
    public static WorldRegions promptRegionChoice() {

        // Prompt message
        Main.printSeparator();
        System.out.println("\nWhat region would you like to explore?");

        WorldRegions[] allRegions = WorldRegions.values();
        for (int i = 0; i < allRegions.length; i++) {

            // Printing region
            WorldRegions region = allRegions[i];
            System.out.println(Integer.toString(i + 1) + ": " + region.getDisplayName());

            // Region information
            System.out.println("\n    Description: \n");
            StringUtil.printIndentedWrappedString(region.getDescription(), 4);
            System.out.println("\n    Recommended Level: " + region.getRecommendedLevel() + "\n");

        }
        System.out.println("0: Cancel exploration");

        // Getting choice
        int choice = Main.promptIntegerInput();
        
        if (choice == 0) {
            return null;
        } else {
            if (choice > allRegions.length || choice < 0) {
                return promptRegionChoice();
            } else {
                return allRegions[choice - 1];
            }
        }

    }

    // Exploring rooms
    public void exploreRooms() {

        // Looping through rooms
        for (int currentRoom = 1; currentRoom <= this.roomCount; currentRoom++) {
            ExploreRoom room = new ExploreRoom(region);
        }

    }

}
