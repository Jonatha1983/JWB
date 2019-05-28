package com.gafner.jwb.api.service.users;

import com.gafner.jwb.api.model.UserAPI;

public interface UserConnectionService<T extends UserAPI> {

    boolean authenticate(String email, String password);

    UserAPI findByEmail(String email);

    boolean saveUserParameters(String firstName, String lastName, String emailSignup, String passwordSignup);

    String getUserByEmail(String usedEmail);
}
