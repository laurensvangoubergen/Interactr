package util;

import java.io.Serializable;

/**
 * a generic class that stores 2 objects,  a pair.
 */
public class Pair<A,B> implements Serializable {

    private final A first;
    private final B second;

    /**
     *
     * @param first
     *      first object to store
     * @param second
     *      second object to store
     */
    public Pair(A first, B second){
        this.first = first;
        this.second = second;
    }

    /**
     * returns the first object
     * @return
     *      the first object
     */
    public A getA(){
        return first;
    }

    /**
     * returns the second object
     * @return
     *      the second object
     */
    public B getB(){
        return second;
    }
}
