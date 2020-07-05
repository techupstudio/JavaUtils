package com.techupstudio.GeneralUtils.collections.exceptions;

import static com.techupstudio.GeneralUtils.Funcs.println;

public class EmptyObjectException extends Exception {
    public EmptyObjectException(String object){
            println( "\n"+object+" is Empty.");
    }
}
