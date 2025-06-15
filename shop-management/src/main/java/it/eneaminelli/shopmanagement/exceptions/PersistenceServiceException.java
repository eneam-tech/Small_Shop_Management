package it.eneaminelli.shopmanagement.exceptions;
//Manages errors during saving and loading
//for recovering from errors like not finding the file
public class PersistenceServiceException extends Exception{
    /**
        * Constructs a new persistence exceptyion
        * 
        * @param message describes the problem to the user
        * @param cause preserves the stack trace for developer
    */
    public PersistenceServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
