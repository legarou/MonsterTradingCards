package at.technikum.Databank;

import at.technikum.Cards.Card;
import at.technikum.Store.Store;

import java.sql.*;
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

    public Card getCard(UUID cardID) {
        HashMap hashMap = manager.getCard(cardID);
        return new Card(hashMap);
    }

    public boolean updateCardOwner(UUID cardID, String newOwner) {

        return manager.updateCardOwner(cardID, newOwner);
    }

    public boolean isCardOwner(UUID cardID, String username) {
        HashMap hashMap = manager.getCardOwner(cardID);
        return username.equals(hashMap.get("ownerID"));
    }

    public boolean isTradeLocked(UUID cardID) {
        HashMap hashMap = manager.getTradeLock(cardID);
        return Boolean.parseBoolean(hashMap.get("tradeLock").toString());
    }

    public boolean isInDeck(UUID cardID) {
        HashMap hashMap = manager.getInDeck(cardID);
        return Boolean.parseBoolean(hashMap.get("inDeck").toString());
    }

    public boolean insertCardIntoStore(UUID storeID, UUID cardID, String requirementSpellMonster, String requierementElement, int requirementMinDamage) {
        return manager.insertCardIntoStore(storeID, cardID, requirementSpellMonster, requierementElement, requirementMinDamage);
    }

    public Store selectFromStore(UUID storeID) {
        HashMap<String, String> hashMap = manager.selectFromStore(storeID);
        if(null == hashMap || hashMap.isEmpty()){
            return null;
        }
        return new Store(hashMap);
    }

    public List<Store> getFullStore() {
        List<Store> listOfStore = new ArrayList();
        List<HashMap> listOfHash = manager.getFullStore();
        for(int i = 0; i < listOfHash.size(); i++) {
            listOfStore.add((new Store(listOfHash.get(i))));
        }
        return listOfStore;
    }

    public boolean deleteCardFromStore(UUID storeID) {
        return manager.deleteCardFromStore(storeID);
    }



    public boolean insertPackage(UUID cardID1, UUID cardID2, UUID cardID3, UUID cardID4, UUID cardID5) {
        return manager.insertPackage(cardID1, cardID2, cardID3, cardID4, cardID5);
    }

    public HashMap getPackage(int packageID) {

        return manager.getPackage(packageID);
    }

    public List getAllCards(String username) {
        List<Card> listOfCard = new ArrayList();
        List<HashMap> listOfHash = manager.getAllCards(username);
        System.out.println(listOfHash);
        for(int i = 0; i < listOfHash.size(); i++) {
            listOfCard.add((new Card(listOfHash.get(i))));
        }
        return listOfCard;
    }

    public List getDeck(String username) {
        List<Card> listOfCard = new ArrayList();
        List<HashMap> listOfHash = manager.getDeck(username);
        for(int i = 0; i < listOfHash.size(); i++) {
            listOfCard.add((new Card(listOfHash.get(i))));
        }
        return listOfCard;
    }

    public boolean insertIntoDeck(UUID cardID) {

        return  manager.insertIntoDeck(cardID);
    }

    public boolean removeFromDeck(UUID cardID) {

        return manager.removeFromDeck(cardID);
    }

    public HashMap getOldestPackage() {
        return manager.getOldestPackage();
    }

    public boolean deletePackage(int packageID) {

        return manager.deletePackage(packageID);
    }

    public boolean putInTradeLock(UUID cardID) {

        return manager.putInTradeLock(cardID);
    }

    public boolean removeFromTradeLock(UUID cardID) {

        return  manager.removeFromTradeLock(cardID);
    }

    public List getScoreboard() {

        return manager.getScoreboard();
    }

    public int getStats(String username) {
        HashMap elo = manager.getStats(username);
        if(null == elo || elo.isEmpty()){
            return -1;
        }
        return Integer.valueOf(elo.get("elo").toString());
    }

    public boolean updateElo(String username, int elo) {
        return manager.updateElo(username, elo);
    }
}
