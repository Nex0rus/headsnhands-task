package org.Nex0rus.entities;

import org.Nex0rus.models.DamageRange;
import org.Nex0rus.models.FightRules;
import org.Nex0rus.models.ValidationRules;

public class Monster extends Creature {
    public Monster(int health, int attack, int defense, DamageRange damageRange, ValidationRules validationRules, FightRules fightRules) {
        super(health, attack, defense, damageRange, validationRules, fightRules);
    }

    @Override
    public String toString() {
        return "monster stats:" +
                "\nHealth: " + health +
                "\nAttack: " + attack +
                "\nDefense: " + defense +
                "\nDamageRange: " + damageRange.minDamage() + "-" + damageRange.maxDamage();
    }
}
