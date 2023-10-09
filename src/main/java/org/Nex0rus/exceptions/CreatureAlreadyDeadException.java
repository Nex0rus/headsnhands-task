package org.Nex0rus.exceptions;

public class CreatureAlreadyDeadException extends DomainException {
    public CreatureAlreadyDeadException() {
        super("Creature must be alive starting a fight");
    }
}
