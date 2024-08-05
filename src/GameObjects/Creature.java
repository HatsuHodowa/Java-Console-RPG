package src.GameObjects;

public abstract class Creature {

    // Properties
    protected int health;

    // Primary stats
    protected int agility = 0;
    protected int dexterity = 0;
    protected int intelligence = 0;
    protected int strength = 0;
    protected int vitality = 0;

    // Constructor
    public Creature() {
        this.health = this.getMaxHealth();
    }

    // Getting the sum of all stats
    public int getStatSum() {
        return this.agility + this.dexterity + this.intelligence + this.strength + this.vitality;
    }

    // Getting the average of all stats
    public float getStatAverage() {
        return this.getStatSum() / 5;
    }

    // Health functions
    public int getMaxHealth() {
        return vitality * 10 + 100;
    }

    public float getHealthProportion() {
        return this.health / this.getMaxHealth();
    }

    public void heal(int healthGain) {
        this.health = Math.min(this.health + healthGain, this.getMaxHealth());
    }

    public void takeDamage(Creature attacker, int damage) {
        this.health = Math.max(this.health - damage, 0);
        if (this.health == 0) {
            this.die(attacker);
        }
    }

    protected void die(Creature killer) {

    }
    
}
