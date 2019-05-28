package com.gafner.jwb.api.model;

public interface UserAPI {
    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setEmail(String emailSignup);

    void setPassword(String passwordSignup);

    String getFirstName();

    String getLastName();
}
