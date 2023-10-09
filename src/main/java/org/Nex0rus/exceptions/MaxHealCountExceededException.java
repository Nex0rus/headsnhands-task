package org.Nex0rus.exceptions;

public class MaxHealCountExceededException extends DomainException {
    public MaxHealCountExceededException(int maxHealCount) {
        super("Current heal count already exceeded maximum heal count: " + maxHealCount);
    }
}
