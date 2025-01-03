package com.cgvsu.objreader;

public class ObjReaderException extends Exception {
    public ObjReaderException(String errorMessage, int lineInd) {
        super("Error parsing OBJ file on line: " + lineInd + ". " + errorMessage);
    }
}