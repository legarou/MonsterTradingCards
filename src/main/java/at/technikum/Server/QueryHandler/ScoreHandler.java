package at.technikum.Server.QueryHandler;

import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;

import java.util.List;

public class ScoreHandler {

    private final String token;
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String request;

    public ScoreHandler(String token, String request) {
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

        if(tokenHandler.verifyToken(token)) {
            List list = dbWrapper.getScoreboard();

            return new ResponseObject("success", "Scoreboard was sent", "List of users and ther ELO ordered descending", list, 200);
        }
        else {
            return new ResponseObject("failure", "Token was faulty", "", null, 400);
        }
    }
}
