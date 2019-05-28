package com.gafner.jwb.api.model;

import java.io.Serializable;

public interface UserAPI extends Serializable {
    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setEmail(String emailSignup);

    void setPassword(String passwordSignup);

    String getFirstName();

    String getLastName();
}
