package at.technikum.Server.QueryHandler;

import at.technikum.Server.BattleRoom;
import at.technikum.Server.ResponseObject;
import lombok.Getter;


import java.util.concurrent.ConcurrentHashMap;

public class QueryHandler {
    private final String buffer;
    @Getter
    private final String token;
    private final String request;
    private final String path;
    ConcurrentHashMap<String, BattleRoom> concurrentMap;

    public QueryHandler(String httpMethodWithPath, String buffer, String token, ConcurrentHashMap<String, BattleRoom> concurrentMap) {
        this.buffer = buffer;
        this.token = token;
        this.concurrentMap = concurrentMap;

        final String[] split = httpMethodWithPath.split(" ", 3);
        this.request = split[0];
        this.path = split[1];

        for(int i=0; i<3; i++) {
            System.out.println(i + ": " + split[i]);
        }
    }

    public ResponseObject processQuery() {
        if(path.contains("/users")) {
            if(path.contains("/users/")){
                final String[] split2 = path.split("/", 3);
                String username = split2[2];
                UsersHandler usersHandler = new UsersHandler(buffer, token, request, username);
                return usersHandler.doHandle();
            }
            else {
                UsersHandler usersHandler = new UsersHandler(buffer, token, request, "");
                return usersHandler.doHandle();
            }
        }
        else if(path.equals("/sessions")) {
            SessionsHandler sessionsHandler = new SessionsHandler(buffer, request);
            return sessionsHandler.doHandle();
        }
        else if(path.contains("/packages")) {
            PackagesHandler packagesHandler = new PackagesHandler(path, buffer, token, request);
            return packagesHandler.doHandle();
        }
        else if(path.equals("/cards")) {
            CardsHandler cardsHandler = new CardsHandler(token, request);
            return cardsHandler.doHandle();
        }
        else if(path.contains("/deck")) {
            DeckHandler deckHandler = new DeckHandler(path, buffer, token, request);
            return deckHandler.doHandle();
        }
        else if(path.equals("/battles")) {
            BattlesHandler battlesHandler = new BattlesHandler(token, request, concurrentMap);
            return battlesHandler.doHandle();
        }
        else if(path.contains("/tradings")) {
            TradingsHandler tradingsHandler = new TradingsHandler(path, buffer, token, request);
            return tradingsHandler.doHandle();
        }
        else if(path.equals("/stats")) {
            StatsHandler statsHandler = new StatsHandler(token, request);
            return statsHandler.doHandle();
        }
        else if(path.equals("/score")) {
            ScoreHandler scoreHandler = new ScoreHandler(token, request);
            return scoreHandler.doHandle();
        }
        else {
            return new ResponseObject("failure", "Method not allowed", "", null, 405);
        }
    }

}
