package at.technikum.Server;

import at.technikum.Databank.DBmanager;
import at.technikum.Databank.DBwrapper;
import at.technikum.User.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class QueryHandler {
    private final String httpMethodWithPath;
    private final String buffer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    @Getter
    private ResponseObject responseObject;

    public QueryHandler(String httpMethodWithPath, String buffer) {
        this.httpMethodWithPath = httpMethodWithPath;
        this.buffer = buffer;
    }

    public void findQuery() {
        try {
            final String[] split = httpMethodWithPath.split(" ", 3);
            System.out.println(split);
            for(int i=0; i<3; i++) {
                System.out.println(i + ": " + split[i]);
            }
            if(split[1].equals("/users")) {
                System.out.println("in /users findQuery");
                userRequest(split[0]);
            }
            else if("/packages" == split[1]) {

            }
            else {}


            if("POST" == split[0]) {

            }
            else if("GET" == split[0]) {

            }
            else if("PUT" == split[0]) {

            }
            else if("DELETE" == split[0]) {

            }
        }
        catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }


    }

    public void userRequest(String request) throws JsonProcessingException {
        if(request.equals("POST")) {

            System.out.println("in /users POST");
            System.out.println(buffer);
            if(buffer == null || buffer.isEmpty()) {
                return;
            }
            final User user = objectMapper.readValue(buffer, User.class);
            if(dbWrapper.insertUser(user.getUsername(), user.getPassword())) {
                responseObject = new ResponseObject("success", "User was inserted", "", null, 200);
                return;
            }
            else {
                responseObject = new ResponseObject("failure", "Could not insert user", "?", null, 400);
                return;
            }

        }
        else if("GET" == request) {

        }
        else if("PUT" == request) {

        }
        else if("DELETE" == request) {

        }
        else {

        }
    }


}
