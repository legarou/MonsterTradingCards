package at.technikum.Server.QueryHandler;

import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import at.technikum.User.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class SessionsHandler {

    private final String buffer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String request;

    public SessionsHandler(String buffer, String request) {
        this.buffer = buffer;
        this.request = request;
    }

    public ResponseObject doHandle() {
        try {
            if(request.equals("POST")) {
                return handlePOST();
            }
            else {
                return new ResponseObject("failure", "Method not allowed", "", null, 405);
            }
        }
        catch(JsonProcessingException ex) {
            ex.printStackTrace();
            return new ResponseObject("failure", "An error occurred", "", null, 400);
        }
    }

    public ResponseObject handlePOST() throws JsonProcessingException {
        if(buffer == null || buffer.isEmpty()) {
            return new ResponseObject("failure", "No content to process", "", null, 400);

        }
        final User user = objectMapper.readValue(buffer, User.class);
        HashMap<String, Object> hashMap = dbWrapper.getUser(user.getUsername());

        if(null == hashMap) {
            return new ResponseObject("failure", "Could not find user", "", null, 400);
        }

        if(user.getPassword().equals(hashMap.get("password"))) {
            String token = tokenHandler.createToken(user.getUsername());
            return new ResponseObject("success", "User was logged in", "Authorization", token, 200);
        }
        else {
            return new ResponseObject("failure", "Could not login user", "", null, 400);
        }
    }
}
