package org.Nex0rus.models;

import org.Nex0rus.exceptions.DomainException;
import org.Nex0rus.exceptions.InvalidDamageException;

public class DefaultValidationRules implements ValidationRules {
    private final int MIN_ATTACK = 1;
    private final int MAX_ATTACK = 30;
    private final int MIN_DEFENSE = 1;
    private final int MAX_DEFENSE = 30;

    @Override
    public void validateHealth(int health) {
        if (health < 1) {
            throw new DomainException("Health must be a positive integer");
        }
    }

    @Override
    public void validateDefense(int defense) {
        if (defense < MIN_DEFENSE || defense > MAX_DEFENSE) {
            throw new DomainException("defense must be a positive integer in range: " + MIN_DEFENSE + "-" + MAX_DEFENSE);
        }
    }

    @Override
    public void validateAttack(int attack) {
        if (attack < MIN_ATTACK || attack > MAX_ATTACK) {
            throw new DomainException("attack must be a positive integer in range: " + MIN_ATTACK + "-" + MAX_ATTACK);
        }
    }
}
