package at.technikum.Databank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DBwrapper {

    DBmanager manager = new DBmanager();

    public boolean insertUser(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
            return false;
        }
        return manager.insertUser(username, password);
    }

    public HashMap getUser(String username) {
        return manager.getUser(username);
    }

    public boolean updateUser(String username, String name, String bio, String image) {
        return manager.updateUser(username, name, bio, image);
    }

    public boolean updateCoinsAfterPurchase(String username) {
        return manager.updateCoinsAfterPurchase(username);
    }

    public int getCoins(String username) {
        HashMap coins = manager.getCoins(username);
        return Integer.valueOf(coins.get("coins").toString());
    }

    public boolean insertCard(UUID cardID, String name, int damage, String elementType, String monsterType) {
        return manager.insertCard(cardID, name, damage, elementType, monsterType);
    }

    public HashMap getCard(UUID cardID) {
        return manager.getCard(cardID);
    }

    public boolean updateCardOwner(UUID cardID, String newOwner) {
        return manager.updateCardOwner(cardID, newOwner);
    }

    public boolean insertCardIntoStore(UUID storeID, UUID cardID, String requirementSpellMonster, String requierementElement, int requirementMinDamage) {
        return manager.insertCardIntoStore(storeID, cardID, requirementSpellMonster, requierementElement, requirementMinDamage);
    }

    public HashMap selectCardFromStore(UUID storeID) {
        return manager.selectCardFromStore(storeID);
    }

    public boolean insertPackage(UUID cardID1, UUID cardID2, UUID cardID3, UUID cardID4, UUID cardID5) {
        // could check if cards exist?
        return manager.insertPackage(cardID1, cardID2, cardID3, cardID4, cardID5);
    }

    public HashMap getPackage(int packageID) {
        return manager.getPackage(packageID);
    }

    public List getAllCards(String username) {
        return manager.getAllCards(username);
    }

    public List getDeck(String username) {
        return manager.getDeck(username);
    }

    public List getDeckIDs(String username) {
        return manager.getDeck(username);
    }

    public boolean insertIntoDeck(UUID cardID) {
        return  manager.insertIntoDeck(cardID);
    }

    public HashMap getOldestPackage() {
        return manager.getOldestPackage();
    }

    public boolean deletePackage(int packageID) {
        return manager.deletePackage(packageID);
    }

    public boolean removeFromDeck(UUID cardID) {

        return manager.removeFromDeck(cardID);
    }


    public List getScoreboard() {
        return manager.getScoreboard();
    }

    public int getStats(String username) {
        HashMap elo = manager.getStats(username);
        return Integer.valueOf(elo.get("elo").toString());
    }
}
