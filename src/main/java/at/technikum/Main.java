package at.technikum;

import at.technikum.Cards.CardElementType;
import at.technikum.Cards.CardMonsterType;
import at.technikum.Databank.DBmanager;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        UUID cardID1 = UUID.fromString("845f0dc7-37d0-426e-994e-43fc3ac83c08");
        UUID cardID2 = UUID.fromString("845f0dc7-37d0-426e-994e-43fc3ac83c56");
        UUID cardID3 = UUID.fromString("845f0dc7-37d0-426e-994e-43fc3ac83c34");
        UUID cardID4 = UUID.fromString("845f0dc7-37d0-426e-994e-43fc3ac83c00");
        UUID cardID5 = UUID.fromString("845f0dc7-37d0-426e-994e-43fc3ac83c99");
        UUID cardID6 = UUID.fromString("845f0dc7-37d0-426e-994e-43fc3ac83c09");
        UUID cardID7 = UUID.fromString("845f0dc7-37d0-426e-994e-43fc3ac83c14");
        UUID storeID = UUID.fromString("845f0dc7-37d0-347e-994e-43fc3ac83c14");

        DBmanager dbManager = new DBmanager();
        //dbManager.insertUser("Matilda", "test123");
        //dbManager.insertUser("Christina", "test123");
        //dbManager.insertUser("Sarah", "test123");
        //dbManager.insertUser("Elena", "test123");
        //dbManager.insertUser("Helene", "test123");

        dbManager.selectUser("Matilda");

        /*
        System.out.println("insertCard ");
        dbManager.insertCard(cardID1,"Water Wizzard", 15, "CWATER", "WIZZARD");
        dbManager.insertCard(cardID2, "Water Wizzard", 15, "CWATER", "WIZZARD");
        dbManager.insertCard(cardID3, "Water Wizzard", 15, "CWATER", "WIZZARD");
        dbManager.insertCard(cardID4, "Water Wizzard", 15, "CWATER", "WIZZARD");
        dbManager.insertCard(cardID5, "Water Wizzard", 15, "CWATER", "WIZZARD");
        dbManager.insertCard(cardID6, "Water Wizzard", 15, "CWATER", "WIZZARD");
        dbManager.insertCard(cardID7, "Water Wizzard", 15, "CWATER", "WIZZARD");
         */

        System.out.println("selectCard ");
        dbManager.selectCard(cardID7);
        System.out.println("updateCardOwner 2x ");
        dbManager.updateCardOwner(cardID6, "Matilda");
        dbManager.updateCardOwner(cardID7, "Matilda");

        //System.out.println("insertCardIntoStore");
        //dbManager.insertCardIntoStore(storeID, cardID1, "Spell", null, 60);
        System.out.println("selectCardFromStore");
        dbManager.selectCardFromStore(storeID);

        System.out.println("selectPackage");
        //dbManager.insertPackage(cardID1, cardID2, cardID3, cardID4, cardID5);
        dbManager.selectPackage(0);

        System.out.println("selectFullStack ");
        dbManager.selectFullStack("Matilda");

        System.out.println("insertIntoDeck and select ");
        dbManager.insertIntoDeck(cardID6);
        dbManager.selectDeck("Matilda");

        System.out.println("removeFromDeck and select ");
        //dbManager.removeFromDeck(cardID6);
        //dbManager.selectDeck("Matilda");

        System.out.println("selectScoreboard ");
        dbManager.selectScoreboard();
    }
}