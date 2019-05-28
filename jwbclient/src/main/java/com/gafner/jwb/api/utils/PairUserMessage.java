package com.gafner.jwb.api.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class PairUserMessage {

    private final String userName;
    private final String message;

    @JsonCreator
    public PairUserMessage(@JsonProperty("userName") String userName, @JsonProperty("message") String second) {
        this.userName = userName;
        this.message = second;
    }

    public static PairUserMessage create(String t1, String t2) {
        return new PairUserMessage(t1, t2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairUserMessage pair = (PairUserMessage) o;
        return userName.equals(pair.userName) &&
                message.equals(pair.message);
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, message);
    }

    @Override
    public String toString() {
        return userName + ": " + message;
    }
}

