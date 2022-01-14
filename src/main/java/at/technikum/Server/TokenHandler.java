package at.technikum.Server;

import at.technikum.Databank.DBwrapper;

public class TokenHandler {


    public TokenHandler() {
    }


    public String createToken(String username)    {
        return "Basic " + username +"-mtcgToken";
    }

    public boolean verifyUserToken(String token, String username) {
        final String[] split1 = token.split(" ", 2);
        final String[] split2 = split1[1].split("-", 2);

        DBwrapper dbWrapper = new DBwrapper();

        if( split1[0].equals("Basic") && split2[1].equals("mtcgToken") && split2[0].equals(username)) {
            if( null != dbWrapper.getUser(username)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token) {
        final String[] split1 = token.split(" ", 2);
        final String[] split2 = split1[1].split("-", 2);
        return split2[0];
    }

    public boolean verifyToken(String token) {
        final String[] split1 = token.split(" ", 2);
        final String[] split2 = split1[1].split("-", 2);

        if( split1[0].equals("Basic") && split2[1].equals("mtcgToken")) {
            return true;
        }
        return false;
    }
}
