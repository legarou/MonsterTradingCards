package at.technikum.Server.QueryHandler;

import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import at.technikum.User.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class UsersHandler {
    private final String buffer;
    private final String token;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String request;
    private final String username;

    public UsersHandler(String buffer, String token, String request, String username) {
        this.buffer = buffer;
        this.token = token;
        this.request = request;
        this.username = username;
    }

    public ResponseObject doHandle() {
        try {
            return switch (request) {
                case "POST" -> handlePOST();
                case "GET" -> handleGET();
                case "PUT" -> handlePUT();
                default -> new ResponseObject("failure", "Method not allowed", "", null, 405);
            };
        }
        catch(JsonProcessingException ex) {
            ex.printStackTrace();
            return new ResponseObject("failure", "An error occured", "", null, 400);
        }
    }

    public ResponseObject handlePOST() throws JsonProcessingException {
        if(buffer == null || buffer.isEmpty()) {
            return new ResponseObject("failure", "No content to process", "", null, 400);
        }

        final User user = objectMapper.readValue(buffer, User.class);
        if(dbWrapper.insertUser(user.getUsername(), user.getPassword())) {
            return new ResponseObject("success", "User was inserted", "", null, 200);
        }
        else {
            return new ResponseObject("failure", "Could not insert user", "", null, 400);
        }
    }

    public ResponseObject handlePUT() throws JsonProcessingException {
        if(buffer == null || buffer.isEmpty()) {
            return new ResponseObject("failure", "No content to process", "", null, 400);
        }

        if(tokenHandler.verifyUserToken(token, username)) {
            final User user = objectMapper.readValue(buffer, User.class);
            if(dbWrapper.updateUser(username, user.getName(), user.getBio(), user.getImage())){
                return new ResponseObject("success", "User data updated", "", null, 200);
            }
            else {
                return new ResponseObject("failure", "Could not update user data", "", null, 400);
            }
        }
        else {
            return new ResponseObject("failure", "Token was faulty", "", null, 400);
        }
    }

    public ResponseObject handleGET() {
        if(tokenHandler.verifyUserToken(token, username)) {

            HashMap<String, Object> hashMap = dbWrapper.getUser(username);

            if(null != hashMap){
                return new ResponseObject("success", "User data sent", "User_data", hashMap, 200);
            }
            else {
                return new ResponseObject("failure", "Could not retrieve user data", "", null, 400);
            }
        }
        else {
            return new ResponseObject("failure", "Token was faulty", "", null, 400);
        }
    }

}
