package io.github.allaudin.annotations;

/**
 * Type of factories to be generated
 *
 * @author M.Allaudin
 */

public enum FactoryType {

    /**
     * The layout belongs to activity and generated class will accept activity
     * as factory method argument.
     */
    ACTIVITY,

    /**
     * The layout belongs to fragment or any other dialog etc and generated class
     * will accept view as factory method argument.
     */
    VIEW
}
