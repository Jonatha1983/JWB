package org.gafner.jwbc.utils;





import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class Pair<T1, T2> {

    public final T1 first;
    public final T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public static <T1, T2> Pair<T1, T2> create(T1 t1, T2 t2) {
        return new Pair<>(t1, t2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return first.equals(pair.first) &&
                second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "[" +
                first +
                ", " + second +
                "]";
    }


    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> Comparator<Pair<T1, T2>> comparator() {
        return Comparator.comparing((Function<Pair<T1, T2>, T1>) t1T2Pair ->
                t1T2Pair.first).thenComparing(p2 -> p2.second);

    }
}
