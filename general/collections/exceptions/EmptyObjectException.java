package com.techupstudio.otc_chingy.mychurch.utils.general.collections.exceptions;

import static com.techupstudio.otc_chingy.mychurch.utils.general.Funcs.println;

public class EmptyObjectException extends Exception {
    public EmptyObjectException(String object) {
        println("\n" + object + " is Empty.");
    }
}
