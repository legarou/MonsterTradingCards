package at.technikum.Server.QueryHandler;

import at.technikum.Databank.DBwrapper;
import at.technikum.Server.BattleRoom;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import at.technikum.User.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class BattlesHandler {

    private final String token;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();
    ConcurrentHashMap<String, BattleRoom> concurrentMap;
    private HashMap<String, String> battleLog;
    BattleRoom subRoom;
    String username;
    User user;
    String foundKey;


    private final String request;

    public BattlesHandler(String token, String request, ConcurrentHashMap<String, BattleRoom> concurrentMap) {
        this.token = token;
        this.request = request;
        this.concurrentMap = concurrentMap;

    }

    public ResponseObject doHandle() {

        if(request.equals("POST")) {

            if(null == token || token.isEmpty()) {
                return new ResponseObject("failure", "No token", "", null, 400);
            }
            else if(! tokenHandler.verifyToken(token)) {
                return new ResponseObject("failure", "Token was faulty", "", null, 400);
            }

            this.username = tokenHandler.getUsername(token);
            this.user = new User(dbWrapper.getUser(username));

            String username = tokenHandler.getUsername(token);
            boolean foundARoom = false;

            User user = new User(dbWrapper.getUser(username));



            foundKey = searchThroughMap();
            if(null == foundKey) {
                waitSome();
            }
            foundKey = searchThroughMap();
            if(null == foundKey) {
                return waitingForBattle();
            }
            else {
                return wantingToJoinBattle();
            }
        }
        else {
            return new ResponseObject("failure", "Method not allowed", "", null, 405);
        }
    }

    public ResponseObject waitingForBattle() {
        UUID uuid = UUID.randomUUID();
        foundKey = uuid.toString();

        BattleRoom battleRoom = new BattleRoom(user);
        concurrentMap.put(uuid.toString(), battleRoom);

        // wait for removed or no longer free to battle
        while(!battleRoom.isRemoved() && battleRoom.isFreeToBattle()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return postProcessing();

    }

    public ResponseObject wantingToJoinBattle() {
        System.out.println("wanting to join batl");
        subRoom = concurrentMap.get(foundKey);
        System.out.println(subRoom);

        if((subRoom.startBattle(user))) {
            return postProcessing();
        }
        else {
            concurrentMap.remove(foundKey);
            subRoom.setRemoved(true);
            return new ResponseObject("failure", "At least one of the decks has the wrong size! Battle cannot commence.", "", null, 400);
        }

    }

    public ResponseObject postProcessing() {
        System.out.println("foundkey: " + foundKey);
        System.out.println("postProcessing");
        subRoom = concurrentMap.get(foundKey);
        System.out.println(subRoom);
        battleLog = subRoom.getBattleLog();
        System.out.println("battle log is null ? " + (battleLog==null));

        if(null == battleLog || battleLog.isEmpty()) {
            // BOTH SHOULD NOTICE ITSS NULL
            concurrentMap.remove(foundKey);
            subRoom.setRemoved(true);
            return new ResponseObject("failure", "Battle was not logged correctly.", "", null, 400);
        }

        subRoom.endConnectionForUser(username);

        if(battleLog.get("winner").equals(username)) {
            dbWrapper.updateElo(username, user.getElo()+3);
        }
        else if(battleLog.get("loser").equals(username)) {
            dbWrapper.updateElo(username, user.getElo()-5);
        }

        // wait to remove entry
        while(!subRoom.checkIfBothDone()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        concurrentMap.remove(foundKey);
        subRoom.setRemoved(true);

        return new ResponseObject("success", "Battle was successful", "Battle log", battleLog, 400);
    }


    public String searchThroughMap() {
        for (String key : concurrentMap.keySet()) {
            subRoom = concurrentMap.get(key);
            if(subRoom.isFreeToBattle()) {
                return key;
            }
        }
        return null;
    }

    public void waitSome() {
        for(int i=0; i< ThreadLocalRandom.current().nextInt(2, 11); i++) {
            // wait in case started at the same time for  2-10 seconds
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
