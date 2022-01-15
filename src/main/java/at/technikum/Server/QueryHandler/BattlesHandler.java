package at.technikum.Server.QueryHandler;

import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BattlesHandler {

    private final String buffer;
    private final String token;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String request;
    private final String username;

    public BattlesHandler(String buffer, String token, String request, String username) {
        this.buffer = buffer;
        this.token = token;
        this.request = request;
        this.username = username;
    }

    public ResponseObject doHandle() {
        try {
            if(request.equals("POST")) {
                return handlePOST();
            }
            else if(request.equals("GET")) {
                return handleGET();
            }
            else if(request.equals("PUT")) {
                return handlePUT();
            }
            else {
                return new ResponseObject("failure", "Method not allowed", "", null, 405);
            }
        }
        catch(JsonProcessingException ex) {
            ex.printStackTrace();
            return new ResponseObject("failure", "An error occured", "", null, 400);
        }
    }

    public ResponseObject handlePOST() throws JsonProcessingException {
        return new ResponseObject("failure", "CREATE ME !!!!!! ", "", null, 400);
    }

    public ResponseObject handlePUT()  {
        return new ResponseObject("failure", "CREATE ME !!!!!! ", "", null, 400);
    }

    public ResponseObject handleGET() {
        return new ResponseObject("failure", "CREATE ME !!!!!! ", "", null, 400);
    }
}
