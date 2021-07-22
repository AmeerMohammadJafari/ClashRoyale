package Characters.Spells;

import Characters.BaseCharacter;
import DataModel.Point;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Rage extends BaseSpell{
    private final double impact = 0.4;
    private LocalDateTime startedTime;
    private LocalDateTime endTime;
    private double duration;
    private HashMap<BaseCharacter, Boolean> isChanged = new HashMap<>();
    public Rage(Point position, double radius, double duration, List<BaseCharacter> targets) {
        super(position, radius, targets);
        this.duration = duration;
        startedTime = LocalDateTime.now();
        endTime = startedTime.plusNanos((long)(Math.pow(10,9)*duration));
        for(BaseCharacter character: targets){
            isChanged.put(character, false);
        }
    }

    @Override
    public boolean update() {
        if(LocalDateTime.now().compareTo(endTime)>=0){
            return false;
        }
        for(BaseCharacter character : targets){
            if(inrange(character)){
                if(!isChanged.get(character)){
                    changeCharacter(character);
                }
            }else if(!inrange(character)){
                if(isChanged.get(character)){
                    reverseChange(character);
                }
            }
        }
        return true;
    }

    private void changeCharacter(BaseCharacter character){
        character.damage*=1.4;
        character.dx *=1.4;
        character.dy *=1.4;
        character.delay/=1.4;
    }
    private void reverseChange(BaseCharacter character){
        character.damage *= (1/1.4);
        character.dx *= (1/1.4);
        character.dy *= (1/1.4);
        character.delay*=1.4;

    }

}
