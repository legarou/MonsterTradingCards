package at.technikum.Server.QueryHandler;

import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CardsHandler {

    private final String token;
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String request;

    public CardsHandler(String token, String request) {
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

        if(tokenHandler.verifyToken(token)) {
            List list = dbWrapper.getAllCards(username);

            if(null != list){
                return new ResponseObject("success", "Package was inserted", "Stack of cards", list, 200);
            }
            else {
                return new ResponseObject("failure", "Could not get any cards for user", "", null, 400);
            }
        }
        else {
            return new ResponseObject("failure", "Token was faulty", "", null, 400);
        }
    }


}
