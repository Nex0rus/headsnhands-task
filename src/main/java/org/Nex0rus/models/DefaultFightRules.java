package org.Nex0rus.models;

import org.Nex0rus.entities.Creature;
import org.Nex0rus.exceptions.CreatureAlreadyDeadException;

import java.util.Random;

public class DefaultFightRules implements FightRules {
    @Override
    public int calculateDamage(Creature attacker, Creature defender) {
        if (attacker.isDead() || defender.isDead()) {
            throw new CreatureAlreadyDeadException();
        }
        int attackModifier = Math.max(1, attacker.getAttack() - defender.getDefense() + 1);
        boolean isSuccess = false;
        Random random = new Random();
        for (int i = 0; i < attackModifier && !isSuccess; ++i) {
            int currentDiceRoll = random.nextInt(6) + 1;
            isSuccess = (currentDiceRoll == 5 || currentDiceRoll == 6);
        }

        if (isSuccess) {
            DamageRange attackerDamageRange = attacker.getDamageRange();
            return random.nextInt(attackerDamageRange.maxDamage() - attackerDamageRange.minDamage()) + attackerDamageRange.minDamage();
        }

        return 0;
    }
}
