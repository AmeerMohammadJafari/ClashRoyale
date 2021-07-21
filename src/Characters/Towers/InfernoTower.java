package Characters.Towers;

import Characters.BaseCharacter;
import Characters.Bullets.BaseBullet;
import Characters.Teams;
import Utils.Images;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class InfernoTower extends BaseTower {
    HashMap<BaseCharacter , Double> targets = new HashMap<>();
    private LocalDateTime createTime;

    @Override
    public boolean isRemovable() {
        if(LocalDateTime.now().compareTo(createTime.plusNanos((long)(Math.pow(10,9) * 40)))>=0){
            return true;
        }
        return health<=0;
    }

    @Override
    public void checkTarget() {
        for(BaseCharacter character: targets.keySet()){
            if(!inRange(character)){
                targets.remove(character);
            }else{
                targets.replace(character, targets.get(character)+0.1);
            }
        }
    }

    @Override
    public void findTarget(List<? extends BaseCharacter> targetList) {
        for(BaseCharacter character: targetList){
            if(targets.containsKey(character)){
                continue;
            }
            if(inRange(character)){
                targets.put(character, damage);
            }
        }
    }

    @Override
    public void updateUI() {

    }

    @Override
    public boolean hitTarget() {
        boolean hitted = false;
        for(BaseCharacter character: targets.keySet()){
            character.hitByDamage(targets.get(character));
            hitted = true;
        }
        return hitted;

    }

    public InfernoTower(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, double delay, List<BaseBullet> bulletList, List<BaseCharacter> opponents, List<BaseTower> towers) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamType, delay, bulletList, opponents, towers);
        this.createTime = LocalDateTime.now();
        imageView.setImage(image.infernoImage);
        imageView.setFitWidth(40);
        imageView.setFitHeight(100);
        addToLayer();
    }
}
