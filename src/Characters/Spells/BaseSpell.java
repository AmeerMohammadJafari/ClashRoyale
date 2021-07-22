package Characters.Spells;

import Characters.BaseCharacter;
import DataModel.Point;

import java.util.List;

public abstract class BaseSpell {
    Point position;
    double radius;
    public List<BaseCharacter> targets;
    public BaseSpell(Point position, double radius, List<BaseCharacter> targets) {
        this.position = position;
        this.radius = radius;
        this.targets = targets;
    }
    public abstract boolean update();
    public boolean inrange(BaseCharacter target){
        if(Point.distance(new Point(target.getCenterX(), target.getCenterY(), target.r), position)<=radius){

            return true;
        }
        return false;
    }


}
