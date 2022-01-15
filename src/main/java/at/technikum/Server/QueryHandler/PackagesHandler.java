package at.technikum.Server.QueryHandler;

import at.technikum.Cards.Card;
import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PackagesHandler {

    private final String buffer;
    private final String token;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();
    private final String request;
    private final String path;

    public PackagesHandler(String path, String buffer, String token, String request) {
        this.path = path;
        this.buffer = buffer;
        this.token = token;
        this.request = request;
    }

    public ResponseObject doHandle() {
        try {
            if(request.equals("POST")) {
                if(path.equals("/packages")) {
                    return handlePackagesPOST();
                }
                else if(path.equals("/transactions/packages")) {
                    return handleTransactionsPOST();
                }

            }
            return new ResponseObject("failure", "Method not allowed", "", null, 405);
        }
        catch(JsonProcessingException ex) {
            ex.printStackTrace();
            return new ResponseObject("failure", "An error occured", "", null, 400);
        }
    }

    public ResponseObject handlePackagesPOST() throws JsonProcessingException {
        if(buffer == null || buffer.isEmpty()) {
            return new ResponseObject("failure", "No content to process", "", null, 400);
        }

        if(tokenHandler.verifyUserToken(token, "admin")) {
            final List<Card> cards = Arrays.asList(objectMapper.readValue(buffer, Card[].class));
            // insert cards
            for(int i=0; i<cards.size(); i++){
                if(! dbWrapper.insertCard(cards.get(i).getCardID(), cards.get(i).getName(), cards.get(i).getDamage(), cards.get(i).getElementType().name(), cards.get(i).getCardMonsterType().name())) {
                    return new ResponseObject("failure", "Something Went wrong while inserting the cards, query was aborted", "", null, 400);
                }
            }
            if(dbWrapper.insertPackage(cards.get(0).getCardID(), cards.get(1).getCardID(), cards.get(2).getCardID(), cards.get(3).getCardID(), cards.get(4).getCardID())){
                return new ResponseObject("success", "Package was inserted", "", null, 200);
            }
            else {
                return new ResponseObject("failure", "Could not insert package", "", null, 400);
            }
        }
        else {
            return new ResponseObject("failure", "Token was faulty", "", null, 400);
        }
    }

    public ResponseObject handleTransactionsPOST() throws JsonProcessingException {
        String username;

        if(tokenHandler.verifyToken(token)) {
            username = tokenHandler.getUsername(token);

            if(dbWrapper.getCoins(username) < 5) {
                return new ResponseObject("failure", "Not enough coins for purchase", "", null, 400);
            }

            HashMap oldestPackage = dbWrapper.getOldestPackage();

            if(null != oldestPackage){
                UUID card;
                for(int i=1; i<6; i++){
                    card = UUID.fromString(oldestPackage.get("cardID_" + i).toString());
                    if(!dbWrapper.updateCardOwner(card, username)) {
                        return new ResponseObject("failure", "Something Went wrong while taking ownership of cards", "", null, 400);
                    }
                }

                if(dbWrapper.deletePackage(Integer.valueOf(oldestPackage.get("packageID").toString()))) {
                    if(dbWrapper.updateCoinsAfterPurchase(username)){
                        return new ResponseObject("success", "User acquired package", "", null, 200);
                    }
                    else {
                        return new ResponseObject("failure", "Cards were acquired and package deleted, but coin status was not updated for user", "", null, 400);
                    }
                }
                else {
                    return new ResponseObject("failure", "Cards were acquired but could not delete package", "", null, 400);
                }
            }
            else {
                return new ResponseObject("failure", "Could not retrieve package", "", null, 400);
            }
        }
        else {
            return new ResponseObject("failure", "Token was faulty", "", null, 400);
        }
    }


}
