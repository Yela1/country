package com.example.country.exception;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(Long id) {
        super("Could not find room with id " + id + ".");
    }
}
