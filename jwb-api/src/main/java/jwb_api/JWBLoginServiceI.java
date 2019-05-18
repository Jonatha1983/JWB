package jwb_api;

import exceptions.JWBLoginException;

public interface JWBLoginServiceI {
    JWBUserData login(String id) throws JWBLoginException;
}
