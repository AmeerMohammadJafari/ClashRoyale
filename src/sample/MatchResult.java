package sample;

import java.io.Serializable;

public class MatchResult implements Serializable {
    private String opponentName;
    private boolean win;

    public MatchResult(String opponentName, boolean win){
        this.opponentName = opponentName;
        this.win = win;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public boolean isWin() {
        return win;
    }
}
