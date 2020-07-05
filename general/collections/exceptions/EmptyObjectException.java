package com.techupstudio.utils.general.collections.exceptions;

import static com.techupstudio.utils.general.Funcs.println;

public class EmptyObjectException extends Exception {
    public EmptyObjectException(String object) {
        println("\n" + object + " is Empty.");
    }
}
