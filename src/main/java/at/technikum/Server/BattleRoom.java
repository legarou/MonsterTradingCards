package at.technikum.Server;

import at.technikum.Battle.Battle;
import at.technikum.Cards.Card;
import at.technikum.Databank.DBwrapper;
import at.technikum.User.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

public class BattleRoom {
    private User playerOne;
    private User playerTwo;
    private boolean playerOneDone;
    private Boolean playerTwoDone;
    @Getter
    @Setter
    private boolean removed;

    @Getter
    private boolean freeToBattle;
    private boolean battleResult;
    @Getter
    private HashMap<String, String> battleLog;
    private DBwrapper dBwrapper = new DBwrapper();

    public BattleRoom(User playerOne) {
        this.playerOne = playerOne;
        this.freeToBattle = true;
        this.battleLog = null;

        playerOneDone = false;
        playerTwoDone = false;
        removed = false;
    }

    public boolean startBattle(User playerTwo_) {
        freeToBattle = false;
        playerTwo = playerTwo_;
        List<Card> deckOne = dBwrapper.getDeck(playerOne.getUsername());
        List<Card> deckTwo = dBwrapper.getDeck(playerTwo.getUsername());

        Battle battle = new Battle(playerOne.getUsername(), deckOne, playerTwo.getUsername(), deckTwo);
        battleResult = battle.start();

        battleLog = battle.getLogger();

        return battleResult;

    }

    public void endConnectionForUser(String username) {
        if(username.equals(playerOne.getUsername())) {
            playerOneDone = true;
        }
        if(username.equals(playerTwo.getUsername())) {
            playerTwoDone = true;
        }
    }

    public boolean checkIfBothDone() {
        if( playerOneDone && playerTwoDone) {
            return true;
        }
        else {
            return false;
        }
    }
}
