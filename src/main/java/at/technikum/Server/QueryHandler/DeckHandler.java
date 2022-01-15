package at.technikum.Server.QueryHandler;

import at.technikum.Cards.Card;
import at.technikum.Databank.DBwrapper;
import at.technikum.Server.ResponseObject;
import at.technikum.Server.TokenHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class DeckHandler {

    private final String buffer;
    private final String token;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DBwrapper dbWrapper = new DBwrapper();
    private final TokenHandler tokenHandler = new TokenHandler();

    private final String request;
    private final String path;

    public DeckHandler(String path, String buffer, String token, String request) {
        this.path = path;
        this.buffer = buffer;
        this.token = token;
        this.request = request;
    }

    public ResponseObject doHandle() {
        if(null == token || token.isEmpty()) {
            return new ResponseObject("failure", "No token", "", null, 400);
        }
        else if(! tokenHandler.verifyToken(token)) {
            return new ResponseObject("failure", "Token was faulty", "", null, 400);
        }

        if( ! (path.equals("/deck") || path.equals("/deck?format=plain")) ) {
            return new ResponseObject("failure", "Method not allowed", "", null, 405);
        }

        try {
            if(request.equals("GET")) {
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

    public ResponseObject handlePUT() throws JsonProcessingException {
        if(buffer == null || buffer.isEmpty()) {
            return new ResponseObject("failure", "No content to process", "", null, 400);
        }

        String username = tokenHandler.getUsername(token);

        List<Card> currentDeck = dbWrapper.getDeck(username);
        System.out.println(currentDeck);
        List<UUID> newDeckuuid = Arrays.asList(objectMapper.readValue(buffer, UUID[].class));
        List<Card> newDeck = new ArrayList<>();

        for(int i=0; i< newDeckuuid.size(); i++) {
            newDeck.add(dbWrapper.getCard(newDeckuuid.get(i)));
        }

        if(4 != newDeck.size()){
            return new ResponseObject("failure", "Deck must contain exactly 4 cards", "", null, 400);
        }
        else if((null != currentDeck) && (4 == currentDeck.size())) {

            for(int i=0; i<4; i++) {
                if(! dbWrapper.isCardOwner(newDeck.get(i).getCardID(), username)) {
                    return new ResponseObject("failure", "Cannot use cards that are not owned by user", "Current deck", currentDeck, 400);
                }
            }

            int count = 0;
            for(int i=0; i<4; i++) {
                Card card = currentDeck.get(i);
                for(int j=0; i<4; i++) {
                    if(card.getCardID().compareTo(newDeck.get(i).getCardID()) == 0) {
                        count++;
                        System.out.println("Card is same");
                        break;
                    }
                }
            }
            if(count == 4) {
                return new ResponseObject("failure", "Cannot update zero changes", "Current deck", currentDeck, 400);
            }

            System.out.println("will remove old deck");
            for(int i=0; i<4; i++) {
                if( ! dbWrapper.removeFromDeck(currentDeck.get(i).getCardID())) {
                    return new ResponseObject("failure", "Could not remove all cards from current deck", "", null, 400);
                }
            }
            System.out.println("removed old deck");
        }

        System.out.println("add new deck");
        for(int i=0; i<4; i++) {
            if( ! dbWrapper.insertIntoDeck(newDeck.get(i).getCardID())) {
                return new ResponseObject("failure", "Could not add all cards to deck", "", null, 400);
            }
        }

        return new ResponseObject("success", "Updated deck", "", null, 200);
    }

    public ResponseObject handleGET() {
        String username = tokenHandler.getUsername(token);
        List list = dbWrapper.getDeck(username);

        if(path.equals("/deck?format=plain")) {
            System.out.println("PLAIN DECK");
            list = plainDeck(list);
        }
        return new ResponseObject("success", "Deck was sent", "deck of 4 or 0 cards", list, 200);
    }

    public List<HashMap> plainDeck(List<Card> list) {
        List newList = new ArrayList<>();
        HashMap<String, Object> newHashMap = new HashMap<>();
        Card card;

        for(int i=0; i<list.size(); i++){
            card = list.get(i);
            newHashMap.put("name", card.getName());
            newHashMap.put("damage", card.getDamage());
            newHashMap.put("elementType", card.getElementType());
            newHashMap.put("monsterType", card.getCardMonsterType());
            newList.add(newHashMap);
        }
        return newList;
    }
}
