package src.Enum;

import src.Main;
import src.GameObjects.Monsters.Monster;

public enum WorldRegions {
    ENCHANTED_FOREST("Enchanted Forest", "A magical forest filled with towering ancient trees, glowing flora, and mystical creatures. "
        + "The air is thick with magic, and the atmosphere changes with the time of day.",
        new Class<?>[] {Monster.class}, 1, 3, 5);

    private final String displayName;
    private final String description;

    private final Class<?>[] monsterTypes;
    private final int recommendedLevel;
    private final int minRooms;
    private final int maxRooms;

    // Constructor for the enum with a description property
    WorldRegions(String displayName, String description, Class<?>[] monsterTypes, int recommendedLevel, int minRooms, int maxRooms) {

        // Setting properties
        this.displayName = displayName;
        this.description = description;
        this.monsterTypes = monsterTypes;
        this.recommendedLevel = recommendedLevel;
        this.minRooms = minRooms;
        this.maxRooms = maxRooms;

    }
    
    // Getting random room count
    public int getRoomCount() {
        return Main.random.nextInt(this.maxRooms - this.minRooms + 1) + this.minRooms;
    }

    // Getters
    public String getDisplayName() {
        return this.displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public Class<?>[] getMonsterTypes() {
        return this.monsterTypes;
    }

    public int getRecommendedLevel() {
        return this.recommendedLevel;
    }

}

