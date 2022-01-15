package at.technikum.Server.QueryHandler;

import at.technikum.Cards.Card;
import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import at.technikum.User.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.*;

public class QueryHandler {
    private final String httpMethodWithPath;
    private final String buffer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();
    @Getter
    private ResponseObject responseObject;
    private String token;
    private String request;
    private String path;

    public QueryHandler(String httpMethodWithPath, String buffer, String token) {
        this.httpMethodWithPath = httpMethodWithPath;
        this.buffer = buffer;
        this.token = token;

        final String[] split = httpMethodWithPath.split(" ", 3);
        this.request = split[0];
        this.path = split[1];

        for(int i=0; i<3; i++) {
            System.out.println(i + ": " + split[i]);
        }
    }

    public void processQuery() {
        if(path.contains("/users")) {
            if(path.contains("/users/")){
                final String[] split2 = path.split("/", 3);
                String username = split2[2];
                UsersHandler usersHandler = new UsersHandler(buffer, token, request, username);
                responseObject = usersHandler.doHandle();
            }
            else {
                UsersHandler usersHandler = new UsersHandler(buffer, token, request, "");
                responseObject = usersHandler.doHandle();
            }
        }
        else if(path.equals("/sessions")) {
            SessionsHandler sessionsHandler = new SessionsHandler(buffer, request);
            responseObject = sessionsHandler.doHandle();
        }
        else if(path.contains("/packages")) {
            PackagesHandler packagesHandler = new PackagesHandler(path, buffer, token, request);
            responseObject = packagesHandler.doHandle();
        }
        else if(path.equals("/cards")) {
            CardsHandler cardsHandler = new CardsHandler(token, request);
            responseObject = cardsHandler.doHandle();
        }
        else if(path.contains("/deck")) {
            DeckHandler deckHandler = new DeckHandler(path, buffer, token, request);
            responseObject = deckHandler.doHandle();
        }
        else if(path.equals("/battles")) {
            BattlesHandler battlesHandler = new BattlesHandler(path, buffer, token, request);
            responseObject = battlesHandler.doHandle();
        }
        else if(path.contains("/tradings")) {
            TradingsHandler tradingsHandler = new TradingsHandler(path, buffer, token, request);
            responseObject = tradingsHandler.doHandle();
        }
        else if(path.equals("/stats")) {
            StatsHandler statsHandler = new StatsHandler(token, request);
            responseObject = statsHandler.doHandle();
        }
        else if(path.equals("/score")) {
            ScoreHandler scoreHandler = new ScoreHandler(token, request);
            responseObject = scoreHandler.doHandle();
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

}
