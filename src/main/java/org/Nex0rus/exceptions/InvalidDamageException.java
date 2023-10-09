package org.Nex0rus.exceptions;

public class InvalidDamageException extends DomainException {
    public InvalidDamageException() {
        super("Min and max damage must be positive integers and min damage must be less than or equal to max damage");
    }
}
