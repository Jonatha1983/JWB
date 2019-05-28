package com.gafner.jwb.client.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class Pair {

    public final Double first;
    public final Double second;

    @JsonCreator
    public Pair(@JsonProperty("first")Double first, @JsonProperty("second")Double second) {
        this.first = first;
        this.second = second;
    }

    public static Pair create(Double t1, Double t2) {
        return new Pair(t1, t2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return first.equals(pair.first) &&
                second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public Double getFirst() {
        return first;
    }

    public Double getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "[" +
                first +
                ", " + second +
                "]";
    }
}
