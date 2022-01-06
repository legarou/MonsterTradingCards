package at.technikum.Databank;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DBwrapper {

    DBmanager manager = new DBmanager();

    public boolean insertUser(String username, String password) {

        return manager.insertUser(username, password);
    }

    public HashMap selectUser(String username) {
        return manager.selectUser(username);
    }

    public boolean insertCard(UUID cardID, String name, int damage, String elementType, String monsterType) {
        return manager.insertCard(cardID, name, damage, elementType, monsterType);
    }

    public HashMap selectCard(UUID cardID) {
        return manager.selectCard(cardID);
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

    public boolean insertPackage(UUID cardID1, String name1, int damage1, String elementType1, String monsterType1, UUID cardID2, String ownerID2, String name2, int damage2, String elementType2, String monsterType2, UUID cardID3, String ownerID3, String name3, int damage3, String elementType3, String monsterType3, UUID cardID4, String ownerID4, String name4, int damage4, String elementType4, String monsterType4, UUID cardID5, String ownerID5, String name5, int damage5, String elementType5, String monsterType5) {
        if(!insertCard(cardID1, name1, damage1, elementType1, monsterType1)) {
            return false;
        }
        if(!insertCard(cardID1, name1, damage1, elementType1, monsterType1)) {
            return false;
        }
        if(!insertCard(cardID1, name1, damage1, elementType1, monsterType1)) {
            return false;
        }
        if(!insertCard(cardID1, name1, damage1, elementType1, monsterType1)) {
            return false;
        }
        if(!insertCard(cardID1, name1, damage1, elementType1, monsterType1)) {
            return false;
        }
        return manager.insertPackage(cardID1, cardID2, cardID3, cardID4, cardID5);
    }

    public HashMap selectPackage(int packageID) {
        return manager.selectPackage(packageID);
    }

    public boolean selectFullStack(String username) {
        // TODO
        return true;
    }

    public boolean selectDeck(String username) {
        // TODO
        return true;
    }

    public boolean insertIntoDeck(UUID cardID) {
        manager.insertIntoDeck(cardID);
        return true;
    }

    public boolean removeFromDeck(UUID cardID) {
        manager.removeFromDeck(cardID);
        return true;
    }


    public boolean selectScoreboard() {
        // TODO
        return true;
    }
}
