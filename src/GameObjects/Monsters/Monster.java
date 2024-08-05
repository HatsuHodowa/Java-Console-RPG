package src.GameObjects.Monsters;

import src.GameObjects.Creature;
import src.GameObjects.Player;

public abstract class Monster extends Creature {
    
    // Calculating experience reward
    public int getExperienceReward() {
        return Math.round(this.getStatAverage()) * 2 + 25;
    }

    // On death
    @Override
    protected void die(Creature killer) {
        if (killer instanceof Player) {
            Player player = (Player) killer;
            player.addExperience(this.getExperienceReward());
        }
    }
    
}