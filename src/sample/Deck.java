package sample;

import javafx.scene.image.Image;

/**
 * This enum is for all the characters and spells inside the game
 */
public enum Deck {
    Barbarian,Archer,BabyDragon,Wizard,MiniPekka,Giant,Valkyrie,Rage,Fireball,Arrows,Cannon,
    InfernoTower;

    /**
     * This method gives you the image of a deck
     * @param deck deck
     * @return the related image
     */
    public static Image deckToImage(Deck deck){
        switch (deck) {
            case Barbarian -> {
                return new Image("\\images\\BarbariansCard.png");
            }
            case Archer -> {
                return new Image("\\images\\ArchersCard.png");
            }
            case BabyDragon -> {
                return new Image("\\images\\BabyDragonCard.png");
            }
            case Wizard -> {
                return new Image("\\images\\WizardCard.png");
            }
            case MiniPekka -> {
                return new Image("\\images\\MiniPEKKACard.png");
            }
            case Giant -> {
                return new Image("\\images\\GiantCard.png");
            }
            case Valkyrie -> {
                return new Image("\\images\\ValkyrieCard.png");
            }
            case Rage -> {
                return new Image("\\images\\RageCard.png");
            }
            case Fireball -> {
                return new Image("\\images\\FireballCard.png");
            }
            case Arrows -> {
                return new Image("\\images\\ArrowsCard.png");
            }
            case Cannon -> {
                return new Image("\\images\\CannonCard.png");
            }
            case InfernoTower -> {
                return new Image("\\images\\InfernoTowerCard.png");
            }
        }
        return null;
    }
}
