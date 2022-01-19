package at.technikum.Databank;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DBmanager {
    private final String db_url;
    private final String db_user;
    private final String db_password;

    public DBmanager() {
        this.db_url = "jdbc:postgresql://localhost:5432/MonsterCardGame";
        this.db_user = "swe1user";
        this.db_password = "swe1pw";
    }

    public boolean insertUser(String username, String password) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO "MonsterCardGame".public."User"
                (username, password)
                VALUES (?,?);
            """)
            ) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.execute();
            } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap getUser(String username) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM "MonsterCardGame".public."User"
                WHERE username = ? ;
            """)
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateUser(String username, String name, String bio, String image) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE "MonsterCardGame".public."User"
                SET bio = ?, name = ?, image = ?
                WHERE username = ?;
            """)
        ) {
            statement.setString(1, bio);
            statement.setString(2, name);
            statement.setString(3, image);
            statement.setString(4, username);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCoinsAfterPurchase(String username) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE "MonsterCardGame".public."User"
                SET coins = coins - 5
                WHERE username = ?;
            """)
        ) {
            statement.setString(1, username);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HashMap getCoins(String username) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT coins FROM "MonsterCardGame".public."User"
                WHERE username = ? ;
            """)
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertCard(UUID cardID, String name, int damage, String elementType, String monsterType) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO "MonsterCardGame".public."Card"
                ("cardID", name, damage, "elementType", "monsterType")
                VALUES (?::uuid,?,?,?,?);
            """)
        ) {
            statement.setString(1, cardID.toString());
            statement.setString(2, name);
            statement.setInt(3, damage);
            statement.setString(4, elementType);
            statement.setString(5, monsterType);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap getCard(UUID cardID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM "MonsterCardGame".public."Card"
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, cardID.toString());
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean updateCardOwner(UUID cardID, String newOwner) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE "MonsterCardGame".public."Card"
                SET "ownerID" = ?, "tradeLock" = false
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, newOwner);
            statement.setString(2, cardID.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap getCardOwner(UUID cardID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT "ownerID" FROM "MonsterCardGame".public."Card"
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, cardID.toString());
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap getTradeLock(UUID cardID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT "tradeLock" FROM "MonsterCardGame".public."Card"
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, cardID.toString());
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap getInDeck(UUID cardID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT "inDeck" FROM "MonsterCardGame".public."Card"
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, cardID.toString());
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertCardIntoStore(UUID storeID, UUID cardID, String requirementSpellMonster, String requirementElement, int requirementMinDamage) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO "MonsterCardGame".public."Store"
                ("storeID", "storeCardID", "requirementSpellMonster", "requirementElement", "requirementMinDamage")
                VALUES (?::uuid,?::uuid,?,?,?);
            """)
        ) {
            statement.setString(1, storeID.toString());
            statement.setString(2, cardID.toString());
            statement.setString(3, requirementSpellMonster);
            statement.setString(4, requirementElement);
            statement.setInt(5, requirementMinDamage);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap selectFromStore(UUID storeID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM "MonsterCardGame".public."Store"
                WHERE "storeID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, storeID.toString());
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HashMap> getFullStore() {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM "MonsterCardGame".public."Store";
            """)
        ) {
            ResultSet resultSet = statement.executeQuery();
            return convertToList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteCardFromStore(UUID storeIDD) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM "MonsterCardGame".public."Store"
                WHERE "storeID" = ?::uuid ;
                
            """)
        ) {
            statement.setString(1, storeIDD.toString());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertPackage(UUID cardID1, UUID cardID2, UUID cardID3, UUID cardID4, UUID cardID5) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO "MonsterCardGame".public."Package"
                ("cardID_1", "cardID_2", "cardID_3", "cardID_4", "cardID_5")
                VALUES (?::uuid,?::uuid,?::uuid,?::uuid,?::uuid);
            """)
        ) {
            statement.setString(1, cardID1.toString());
            statement.setString(2, cardID2.toString());
            statement.setString(3, cardID3.toString());
            statement.setString(4, cardID4.toString());
            statement.setString(5, cardID5.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap getPackage(int packageID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM "MonsterCardGame".public."Package"
                WHERE "packageID" = ? ;
            """)
        ) {
            statement.setInt(1, packageID);
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap getOldestPackage() {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM "Package"
                ORDER BY "packageID" ASC
                LIMIT 1;
            """)
        ) {
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletePackage(int packageID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM "MonsterCardGame".public."Package"
                WHERE "packageID" = ?;
            """)
        ) {
            statement.setInt(1, packageID);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HashMap> getAllCards(String username) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM "MonsterCardGame".public."Card"
                WHERE "ownerID" = ? ;
            """)
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return convertToList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HashMap> getDeck(String username) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM "MonsterCardGame".public."Card"
                WHERE "ownerID" = ? AND "inDeck" = true ;
            """)
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return convertToList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertIntoDeck(UUID cardID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE "MonsterCardGame".public."Card"
                SET "inDeck" = true
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, cardID.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeFromDeck(UUID cardID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE "MonsterCardGame".public."Card"
                SET "inDeck" = false
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, cardID.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean putInTradeLock(UUID cardID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE "MonsterCardGame".public."Card"
                SET "tradeLock" = true
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, cardID.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeFromTradeLock(UUID cardID) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE "MonsterCardGame".public."Card"
                SET "tradeLock" = false
                WHERE "cardID" = ?::uuid ;
            """)
        ) {
            statement.setString(1, cardID.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public List<HashMap> getScoreboard() {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT username, elo
                FROM "MonsterCardGame".public."User"
                ORDER BY elo desc ;
            """)
        ) {
            ResultSet resultSet = statement.executeQuery();
            return convertToList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap getStats(String username) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                SELECT elo
                FROM "MonsterCardGame".public."User"
                WHERE username = ? ;
            """)
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return convertToHashtable(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateElo(String username, Integer elo) {
        try(Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement statement = connection.prepareStatement("""
                UPDATE "MonsterCardGame".public."User"
                SET elo = ?
                WHERE username = ? ;
            """)
        ) {
            statement.setInt(1, elo);
            statement.setString(2, username);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HashMap convertToHashtable(ResultSet resultSet) {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            HashMap<String, Object> hashMap = new HashMap<>();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    hashMap.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                }
            }
            if(hashMap.isEmpty()) {
                return null;
            }
            else {
                return hashMap;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HashMap> convertToList(ResultSet resultSet) {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            List<HashMap> list = new ArrayList();
            HashMap<String, String> hashMap = new HashMap<>();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    hashMap.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                }
                list.add(hashMap);
                hashMap = new HashMap<>();
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
