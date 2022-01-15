package at.technikum.Server.QueryHandler;

import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StatsHandler {

    private final String token;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String request;

    public StatsHandler(String token, String request) {
        this.token = token;
        this.request = request;
    }

    public ResponseObject doHandle() {
        if(request.equals("GET")) {
            return handleGET();
        }
        else {
            return new ResponseObject("failure", "Method not allowed", "", null, 405);
        }
    }

    public ResponseObject handleGET() {
        if(null == token || token.isEmpty()) {
            return new ResponseObject("failure", "No token", "", null, 400);
        }
        String username = tokenHandler.getUsername(token);

        int elo = dbWrapper.getStats(username);
        if(elo == -1) {
            return new ResponseObject("failure", "Could not read elo", "", null, 400);
        }
        else {
            return new ResponseObject("success", "Stats were sent", "ELO", elo, 200);
        }

    }
}
