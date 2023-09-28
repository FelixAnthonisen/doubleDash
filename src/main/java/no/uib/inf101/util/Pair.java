package no.uib.inf101.util;

/**
 * Can hold two objects of any type. Note that the two objects does not have to
 * be of the same type
 * 
 * @param first  the first object
 * @param second the second object
 */
public record Pair<T, U>(T first, U second) {
}
