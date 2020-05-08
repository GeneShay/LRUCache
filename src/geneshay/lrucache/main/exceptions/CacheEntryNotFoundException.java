package geneshay.lrucache.main.exceptions;

public class CacheEntryNotFoundException extends RuntimeException {

    public CacheEntryNotFoundException(String message){
        super(message);
    }
}
