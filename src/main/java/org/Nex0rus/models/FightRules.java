package org.Nex0rus.models;

import org.Nex0rus.entities.Creature;

public interface FightRules {
    int calculateDamage(Creature attacker, Creature defender);
}
