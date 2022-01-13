package at.technikum.Server;

import at.technikum.Databank.DBwrapper;
import at.technikum.User.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.HashMap;

public class QueryHandler {
    private final String httpMethodWithPath;
    private final String buffer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();
    @Getter
    private ResponseObject responseObject;
    private String token;

    public QueryHandler(String httpMethodWithPath, String buffer, String token) {
        this.httpMethodWithPath = httpMethodWithPath;
        this.buffer = buffer;
        this.token = token;
    }

    public void processQuery() {
        try {
            final String[] split = httpMethodWithPath.split(" ", 3);

            for(int i=0; i<3; i++) {
                System.out.println(i + ": " + split[i]);
            }

            System.out.println("at " + split[1]);

            if(split[1].contains("/users")) {
                userRequest(split[0]);
            }
            else if(split[1].equals("/sessions")) {
                sessionsRequest(split[0]);
            }
            else if(split[1].equals("/packages")) {
                packagesRequest(split[0]);
            }
            else if(split[1].equals("/transactions/packages")) {
                transactionsRequest(split[0]);
            }
            else if(split[1].equals("/cards")) {
                cardsRequest(split[0]);
            }
            else if(split[1].contains("/deck")) {
                deckRequest(split[0]);
            }
            else if(split[1].equals("/battles")) {
                battlesRequest(split[0]);
            }
            else if(split[1].contains("/tradings")) {
                tradingsRequest(split[0]);
            }
            else if(split[1].equals("/stats")) {
                statsRequest(split[0]);
            }
            else if(split[1].equals("/score")) {
                scoreRequest(split[0]);
            }
            else {
                responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
                return;
            }

        }
        catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }

        responseObject = new ResponseObject("failure", "Something went wrong", "", null, 400);
        return;


    }

    public void userRequest(String request) throws JsonProcessingException {

        final String[] split1 = httpMethodWithPath.split(" ", 3);
        final String[] split2 = split1[1].split("/", 3);

        if(request.equals("POST")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            final User user = objectMapper.readValue(buffer, User.class);
            if(dbWrapper.insertUser(user.getUsername(), user.getPassword())) {
                responseObject = new ResponseObject("success", "User was inserted", "", null, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "Could not insert user", "", null, 400);
                return;
            }

        }
        else if(request.equals("GET")) {

            if(tokenHandler.verifyUserToken(token, split2[2])) {
                HashMap<String, Object> hashMap = dbWrapper.selectUser(split2[2]);
                if(null != hashMap){
                    responseObject = new ResponseObject("success", "User data sent", "User data", hashMap, 200);
                }
                else {
                    responseObject = new ResponseObject("failure", "Could not retrieve user data", "", null, 400);
                }
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "Token was faulty", "", null, 400);
                return;
            }

        }
        else if(request.equals("PUT")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            System.out.println("username: " +  split2[2]);
            if(tokenHandler.verifyUserToken(token, split2[2])) {
                final User user = objectMapper.readValue(buffer, User.class);
                if(dbWrapper.updateUser(split2[2], user.getName(), user.getBio(), user.getImage())){
                    responseObject = new ResponseObject("success", "User data updated", "", null, 200);
                }
                else {
                    responseObject = new ResponseObject("failure", "Could not update user data", "", null, 400);
                }
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "Token was faulty", "", null, 400);
                return;
            }

        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void sessionsRequest(String request) throws JsonProcessingException {
        if(request.equals("POST")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            final User user = objectMapper.readValue(buffer, User.class);
            HashMap<String, Object> hashMap = dbWrapper.selectUser(user.getUsername());

            if(user.getPassword().equals(hashMap.get("password"))) {
                String token = tokenHandler.createToken(user.getUsername());
                responseObject = new ResponseObject("success", "User was logged in", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "Could not login user", "", null, 400);
                return;
            }

        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void packagesRequest(String request) throws JsonProcessingException {
        if(request.equals("POST")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // admin
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void transactionsRequest(String request) throws JsonProcessingException {
        if(request.equals("POST")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // user name
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void cardsRequest(String request) throws JsonProcessingException {
        if(request.equals("GET")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void deckRequest(String request) throws JsonProcessingException {
        // could have id ?? or name or sth but SPECIAL CASE

        if(request.equals("GET")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else if(request.equals("PUT")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void statsRequest(String request) throws JsonProcessingException {
        if(request.equals("GET")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void scoreRequest(String request) throws JsonProcessingException {
        if(request.equals("GET")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void tradingsRequest(String request) throws JsonProcessingException {
        if(request.equals("GET")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void battlesRequest(String request) throws JsonProcessingException {
        if(request.equals("POST")) {
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            // TODO
            if(false) {
                responseObject = new ResponseObject("success", "XXX", "Authorization", token, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "XXX", "", null, 400);
                return;
            }
        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

}
