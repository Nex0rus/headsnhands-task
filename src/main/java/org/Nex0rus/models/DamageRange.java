package org.Nex0rus.models;

import org.Nex0rus.exceptions.InvalidDamageException;

public record DamageRange(int minDamage, int maxDamage) {
    public DamageRange {
        if (minDamage < 0 || maxDamage < 0 || minDamage > maxDamage) {
            throw new InvalidDamageException();
        }
    }
}
