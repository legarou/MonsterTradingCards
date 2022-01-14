package at.technikum.Server;

import at.technikum.Cards.Card;
import at.technikum.Databank.DBwrapper;
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
                deckRequest(split[0], split[1]);
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


    }

    public void userRequest(String request) throws JsonProcessingException {

        final String[] split1 = httpMethodWithPath.split(" ", 3);
        final String[] split2 = split1[1].split("/", 3);

        if(request.equals("POST")) {
            if(buffer == null || buffer.isEmpty()) {
                responseObject = new ResponseObject("failure", "No content to process", "", null, 400);
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
                HashMap<String, Object> hashMap = dbWrapper.getUser(split2[2]);
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
                responseObject = new ResponseObject("failure", "No content to process", "", null, 400);
                return;
            }
            // System.out.println("username: " +  split2[2]);
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
                responseObject = new ResponseObject("failure", "No content to process", "", null, 400);
                return;
            }
            final User user = objectMapper.readValue(buffer, User.class);
            HashMap<String, Object> hashMap = dbWrapper.getUser(user.getUsername());

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
                responseObject = new ResponseObject("failure", "No content to process", "", null, 400);
                return;
            }
            if(tokenHandler.verifyUserToken(token, "admin")) {
                final List<Card> cards = Arrays.asList(objectMapper.readValue(buffer, Card[].class));
                // insert cards
                for(int i=0; i<cards.size(); i++){
                    if(dbWrapper.insertCard(cards.get(i).getCardID(), cards.get(i).getName(), cards.get(i).getDamage(), cards.get(i).getElementType().name(), cards.get(i).getCardMonsterType().name())) {
                        responseObject = new ResponseObject("failure", "Something Went wrong while inserting the cards, query was aborted", "", null, 400);
                    }
                }
                if(dbWrapper.insertPackage(cards.get(0).getCardID(), cards.get(1).getCardID(), cards.get(2).getCardID(), cards.get(3).getCardID(), cards.get(4).getCardID())){
                    responseObject = new ResponseObject("success", "Package was inserted", "", null, 200);
                }
                else {
                    responseObject = new ResponseObject("failure", "Could not insert package", "", null, 400);
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

    public void transactionsRequest(String request) throws JsonProcessingException {
        if(token.isEmpty()) {
            responseObject = new ResponseObject("failure", "No token", "", null, 400);
            return;
        }

        String username = tokenHandler.getUsername(token);

        if(request.equals("POST")) {
            if(tokenHandler.verifyUserToken(token, username)) {
                if(dbWrapper.getCoins(username) < 5) {
                    responseObject = new ResponseObject("failure", "Not enough coins for purchase", "", null, 400);
                    return;
                }

                HashMap oldestPackage = dbWrapper.getOldestPackage();
                System.out.println(oldestPackage);

                if(null != oldestPackage){
                    UUID card;
                    for(int i=1; i<6; i++){
                        card = UUID.fromString(oldestPackage.get("cardID_" + i).toString());
                        if(!dbWrapper.updateCardOwner(card, username)) {
                            responseObject = new ResponseObject("failure", "Something Went wrong while taking ownership of cards", "", null, 400);
                            return;
                        }
                    }

                    if(dbWrapper.deletePackage(Integer.valueOf(oldestPackage.get("packageID").toString()))) {
                        if(dbWrapper.updateCoinsAfterPurchase(username)){
                            responseObject = new ResponseObject("success", "User acquired package", "", null, 200);
                            return;
                        }
                        else {
                            responseObject = new ResponseObject("failure", "Cards were acquired and package deleted, but coin status was not updated for user", "", null, 400);
                            return;
                        }
                    }
                    else {
                        responseObject = new ResponseObject("failure", "Cards were acquired but could not delete package", "", null, 400);
                        return;
                    }
                }
                else {
                    responseObject = new ResponseObject("failure", "Could not retrieve package", "", null, 400);
                    return;
                }
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

    public void cardsRequest(String request) throws JsonProcessingException {
        if(null == token || token.isEmpty()) {
            responseObject = new ResponseObject("failure", "No token", "", null, 400);
            return;
        }

        String username = tokenHandler.getUsername(token);

        if(request.equals("GET")) {
            if(tokenHandler.verifyUserToken(token, username)) {
                List list = dbWrapper.getAllCards(username);


                if(null != list){
                    responseObject = new ResponseObject("success", "Package was inserted", "Stack of cards", list, 200);
                }
                else {
                    responseObject = new ResponseObject("failure", "Could not get any cards for user", "", null, 400);
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

    public void deckRequest(String request, String how) throws JsonProcessingException {
        // deck may be empty

        if(null == token || token.isEmpty()) {
            responseObject = new ResponseObject("failure", "No token", "", null, 400);
            return;
        }

        String username = tokenHandler.getUsername(token);
        if(! tokenHandler.verifyUserToken(token, username)) {
            responseObject = new ResponseObject("failure", "Token was faulty", "", null, 400);
            return;
        }

        if(request.equals("GET")) {
            List list = dbWrapper.getDeck(username);

            if(how.equals("deck?format=plain")) {
                list = plainDeck(list);
            }
            responseObject = new ResponseObject("success", "Deck was sent", "deck of 4 or 0 cards", list, 200);
            return;
        }
        else if(request.equals("PUT")) {
            List currentDeck = idsOfDeck(dbWrapper.getDeckIDs(username));
            System.out.println("got current deck");
            System.out.println(currentDeck);
            final List<String> newDeck = Arrays.asList(objectMapper.readValue(buffer, String[].class));
            // TODO

            if(4 != newDeck.size()){
                System.out.println("newdeck wrong size");
                responseObject = new ResponseObject("failure", "Deck must contain exactly 4 cards", "", null, 400);
                return;
            }
            else if((null != currentDeck) && (4 == currentDeck.size())) {
                System.out.println("check current deck is same");
                int count = 0;
                for(int i=0; i<4; i++) {
                    if(currentDeck.get(i) == UUID.fromString(newDeck.get(i))) {
                        count++;
                    }
                }
                System.out.println("done checking");
                if(count == 4) {
                    responseObject = new ResponseObject("failure", "Cannot update zero changes", "", null, 400);
                    return;
                }

                System.out.println("will remove old deck");
                for(int i=0; i<4; i++) {
                    System.out.println("remove " + i);
                    if( ! dbWrapper.removeFromDeck(UUID.fromString(currentDeck.get(i).toString())) ) {
                        responseObject = new ResponseObject("failure", "Could not remove all cards from current deck", "", null, 400);
                        return;
                    }
                }
                System.out.println("removed old deck");
            }

            System.out.println("add new deck");
            for(int i=0; i<4; i++) {
                if( ! dbWrapper.insertIntoDeck(UUID.fromString(newDeck.get(i)))) {
                    responseObject = new ResponseObject("failure", "Could not add all cards to deck", "", null, 400);
                    return;
                }
            }

            responseObject = new ResponseObject("success", "Updated deck", "new deck", newDeck, 200);
            return;



        }
        else {
            responseObject = new ResponseObject("failure", "Method not allowed", "", null, 405);
            return;
        }
    }

    public void statsRequest(String request) throws JsonProcessingException {
        if(null == token || token.isEmpty()) {
            responseObject = new ResponseObject("failure", "No token", "", null, 400);
            return;
        }

        String username = tokenHandler.getUsername(token);

        if(request.equals("GET")) {
            if(tokenHandler.verifyUserToken(token, username)) {
                int elo = dbWrapper.getStats(username);

                responseObject = new ResponseObject("success", "Stats were sent", "ELO", elo, 200);
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

    public void scoreRequest(String request) throws JsonProcessingException {
        if(null == token || token.isEmpty()) {
            responseObject = new ResponseObject("failure", "No token", "", null, 400);
            return;
        }

        if(request.equals("GET")) {
            if(tokenHandler.verifyToken(token)) {
                List list = dbWrapper.getScoreboard();

                responseObject = new ResponseObject("success", "Scoreboard was sent", "List of users and ther ELO ordered descending", list, 200);
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

    public void tradingsRequest(String request) throws JsonProcessingException {
        if(request.equals("GET")) {
            /*
            if(buffer == null || buffer.isEmpty()) {
                return;
            }*/
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
            /*
            if(buffer == null || buffer.isEmpty()) {
                return;
            } */
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

    public List<HashMap> plainDeck(List<HashMap> list) {
        List newList = new ArrayList<>();
        HashMap<String, Object> newHashMap;
        HashMap<String, Object> hashMap;

        for(int i=0; i<list.size(); i++){
            newHashMap = new HashMap();
            hashMap = list.get(i);
            newHashMap.put("name", hashMap.get("name"));
            newHashMap.put("damage", hashMap.get("damage"));
            newHashMap.put("elementType", hashMap.get("elementType"));
            newHashMap.put("monsterType", hashMap.get("monsterType"));
            newList.add(newHashMap);
        }
        return newList;
    }

    public List idsOfDeck(List<HashMap> list) {
        List newList = new ArrayList<>();
        HashMap<String, Object> hashMap;

        for(int i=0; i<list.size(); i++){
            hashMap = list.get(i);
            newList.add(hashMap.get("cardID"));
        }
        return newList;
    }

}
