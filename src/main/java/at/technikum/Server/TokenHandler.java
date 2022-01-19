package at.technikum.Server;

import at.technikum.Databank.DBwrapper;

public class TokenHandler {


    public TokenHandler() {
    }


    public String createToken(String username)    {
        return "Basic " + username +"-mtcgToken";
    }

    public boolean verifyUserToken(String token, String username) {
        final String[] splitBasic = token.split(" ", 2);

        if(!  splitBasic[0].equals("Basic")) {
            return false;
        }

        final String[] split2UsernameToken = splitBasic[1].split("-", 2);

        DBwrapper dbWrapper = new DBwrapper();

        if( splitBasic[0].equals("Basic") && split2UsernameToken[1].equals("mtcgToken") && split2UsernameToken[0].equals(username)) {
            if( null != dbWrapper.getUser(username)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token) {
        final String[] splitBasic = token.split(" ", 2);

        if(!  splitBasic[0].equals("Basic")) {
            return "";
        }
        final String[] split2UsernameToken = splitBasic[1].split("-", 2);
        return split2UsernameToken[0];
    }

    public boolean verifyToken(String token) {
        final String[] splitBasic = token.split(" ", 2);


        if(!  splitBasic[0].equals("Basic")) {
            return false;
        }

        final String[] split2UsernameToken = splitBasic[1].split("-", 2);

        if( split2UsernameToken[1].equals("mtcgToken")) {
            return true;
        }
        return false;
    }
}
