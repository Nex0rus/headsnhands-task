package org.Nex0rus.entities;

import org.Nex0rus.exceptions.CreatureAlreadyDeadException;
import org.Nex0rus.exceptions.MaxHealCountExceededException;
import org.Nex0rus.models.DamageRange;
import org.Nex0rus.models.FightRules;
import org.Nex0rus.models.ValidationRules;

public class Player extends Creature {
    private final float HEAL_COEFFICIENT = 0.3f;
    private final int MAX_HEAL_COUNT = 4;
    private final int HEAL_AMOUNT;
    private int healCount = 0;

    public Player(int health, int attack, int defense, DamageRange damageRange, ValidationRules validationRules, FightRules fightRules) {
        super(health, attack, defense, damageRange, validationRules, fightRules);
        HEAL_AMOUNT = (int)(health * HEAL_COEFFICIENT);
    }

    public void healSelf() {
        if (isDead) {
            throw new CreatureAlreadyDeadException();
        }
        if (healCount >= MAX_HEAL_COUNT) {
            throw new MaxHealCountExceededException(MAX_HEAL_COUNT);
        }

        health += HEAL_AMOUNT;
        ++healCount;
    }

    @Override
    public String toString() {
        return "player stats:" +
                "\nHealth: " + health +
                "\nRemaining heals count: " + (MAX_HEAL_COUNT - healCount) +
                "\nAttack: " + attack +
                "\nDefense: " + defense +
                "\nDamageRange: " + damageRange.minDamage() + "-" + damageRange.maxDamage();
    }
}
