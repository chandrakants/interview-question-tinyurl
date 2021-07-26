package com.tinyurl.app.utils;


/**
 * This class is responsible to validate string manipulations/operations.
 * @author Chandrakant
 * @version 1.0
 */
public class StringUtilities {

    private StringUtilities(){}

    /**
     * Check to see if the passed value to the method is null or the
     * length of the string is 0
     *
     * @param value input value to the method
     * @return true if is empty false if its not
     */
    public static boolean isEmpty(String value){
        return (value ==null || (value.length() == 0));
    }

    /**
     * This method checks if the input value is not null or length is not 0  then trim the
     * value and return it.
     * @param value input value to the function to be checked
     * @return true after trimming the value and false if empty after trim
     */
    public static boolean isEmptyOnTrim(String value){

        boolean isEmpty = isEmpty(value);
        if(!isEmpty){
            isEmpty = isEmpty(value.trim());
        }
        return isEmpty;
    }

}
