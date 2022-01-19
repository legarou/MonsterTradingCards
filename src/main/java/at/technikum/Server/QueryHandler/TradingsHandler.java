package at.technikum.Server.QueryHandler;

import at.technikum.Cards.Card;
import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import at.technikum.Store.Store;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

public class TradingsHandler {

    private final String buffer;
    private final String token;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String request;
    private final String path;
    private UUID storeID;

    public TradingsHandler(String path, String buffer, String token, String request) {
        this.buffer = buffer;
        this.token = token;
        this.request = request;
        this.path = path;
        storeID = null;
        if(! path.equals("/tradings")){
            final String[] split2 = path.split("/", 3);
            storeID = UUID.fromString(split2[2]);
        }
    }

    public ResponseObject doHandle() {
        if(null == token || token.isEmpty()) {
            return new ResponseObject("failure", "No token", "", null, 400);
        }
        else if(! tokenHandler.verifyToken(token)) {
            return new ResponseObject("failure", "Token was faulty", "", null, 400);
        }

        try {
            if(request.equals("POST")) {
                if(path.equals("/tradings")) {
                    return handleCreatePOST();
                }
                else {
                    return handleTradePOST();
                }

            }
            else if(request.equals("GET")) {
                if( ! (path.equals("/tradings"))) {
                    new ResponseObject("failure", "Method not allowed", "", null, 405);
                }
                return handleGET();
            }
            else if(request.equals("DELETE")) {
                return handleDELETE();
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

    public ResponseObject handleCreatePOST() throws JsonProcessingException {
        if(buffer == null || buffer.isEmpty()) {
            return new ResponseObject("failure", "No content to process", "", null, 400);
        }
        String username = tokenHandler.getUsername(token);

        Store storeEntry = objectMapper.readValue(buffer, Store.class);

        if(dbWrapper.isInDeck(storeEntry.getCardToTrade())) {
            return new ResponseObject("failure", "Cards that are in the current deck cannot be traded", "", null, 400);
        }
        if(! dbWrapper.isCardOwner(storeEntry.getCardToTrade(), username)) {
            return new ResponseObject("failure", "Cannot use cards that are not owned by user", "", null, 400);
        }

        if(dbWrapper.insertCardIntoStore(storeEntry.getStoreID(), storeEntry.getCardToTrade(), storeEntry.getMonsterType().name(), storeEntry.getElementType().name(), storeEntry.getDamage())) {
            if( ! dbWrapper.putInTradeLock(storeEntry.getCardToTrade())) {
                return new ResponseObject("failure", "Card was not put into tradeLock", "", null, 400);
            }

            return new ResponseObject("success", "Card was put into store for trade", "Store entry", storeEntry, 200);
        }
        else {
            return new ResponseObject("failure", "Could not put card into store for trade", "", null, 400);
        }
    }


    public ResponseObject handleTradePOST() throws JsonProcessingException {
        if(buffer == null || buffer.isEmpty()) {
            return new ResponseObject("failure", "No content to process", "", null, 400);
        }
        String buyerUsername = tokenHandler.getUsername(token);

        Store storeEnty = dbWrapper.selectFromStore(storeID);
        if(null == storeEnty) {
            return new ResponseObject("failure", "Could not reach store", "", null, 400);
        }

        Card cardToTake = dbWrapper.getCard(storeEnty.getCardToTrade());
        if(null == cardToTake) {
            return new ResponseObject("failure", "Could not get card out of store", "", null, 400);
        }

        String newBuffer = buffer.substring(1, buffer.length() - 1);
        Card cardToGive = dbWrapper.getCard(UUID.fromString(newBuffer));

        if(null == cardToGive) {
            return new ResponseObject("failure", "Could not get card from user", "", null, 400);
        }
        if(dbWrapper.isInDeck(cardToGive.getCardID())) {
            return new ResponseObject("failure", "Cards that are in the current deck cannot be traded", "", null, 400);
        }
        if(! dbWrapper.isCardOwner(cardToGive.getCardID(), buyerUsername)) {
            return new ResponseObject("failure", "Cannot use cards that are not owned by user", "", null, 400);
        }
        if(! storeEnty.checkRequirements(cardToGive)) {
            return new ResponseObject("failure", "Card does not meet requirements", "", null, 400);
        }
        String sellerUsername = cardToTake.getOwnerID();
        if(buyerUsername.equals(sellerUsername)) {
            return new ResponseObject("failure", "Cannot trade with yourself", "", null, 400);
        }


        if( ! dbWrapper.updateCardOwner(cardToTake.getCardID(), buyerUsername)) {
            return new ResponseObject("failure", "Could not give ownership of card to user (buyer)", "", null, 400);
        }
        if( ! dbWrapper.removeFromTradeLock(cardToTake.getCardID())) {
            return new ResponseObject("failure", "Could not remove tradelock from acquired card", "", null, 400);
        }
        if( ! dbWrapper.updateCardOwner(cardToGive.getCardID(), sellerUsername)) {
            return new ResponseObject("failure", "Could not give ownership of card to user (seller)", "", null, 400);
        }
        if( ! (dbWrapper.deleteCardFromStore(storeID))) {
            return new ResponseObject("failure", "Cards were traded, but could not remove store entry", "", null, 400);
        }

        return new ResponseObject("success", "Cards were traded", "", null, 200);
    }


    public ResponseObject handleDELETE()  {
        String username = tokenHandler.getUsername(token);

        System.out.println("before get store entry");
        Store storeEnty = dbWrapper.selectFromStore(storeID);
        if(null == storeEnty) {
            return new ResponseObject("failure", "Could not reach store", "", null, 400);
        }

        System.out.println("before get card entry");
        Card cardToTrade = dbWrapper.getCard(storeEnty.getCardToTrade());
        String sellerUsername = cardToTrade.getOwnerID();
        if(! username.equals(sellerUsername)) {
            return new ResponseObject("failure", "Cannot delete somebody else's entry", "", null, 400);
        }

        if( ! (dbWrapper.deleteCardFromStore(storeID))) {
            return new ResponseObject("failure", "Could not remove store entry", "", null, 400);
        }
        if( ! dbWrapper.removeFromTradeLock(cardToTrade.getCardID())) {
            return new ResponseObject("failure", "COuld not remove tradelock from card", "", null, 400);
        }

        return new ResponseObject("success", "Card was removed from store", "", null, 200);

    }

    public ResponseObject handleGET() {
        List<Store> list = dbWrapper.getFullStore();
        return new ResponseObject("success", "Store entries were sent", "Store list", list, 200);
    }
}
