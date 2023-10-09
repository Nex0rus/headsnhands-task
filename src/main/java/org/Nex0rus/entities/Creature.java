package org.Nex0rus.entities;

import lombok.Getter;
import org.Nex0rus.exceptions.CreatureAlreadyDeadException;
import org.Nex0rus.exceptions.DomainException;
import org.Nex0rus.models.DamageRange;
import org.Nex0rus.models.FightRules;
import org.Nex0rus.models.ValidationRules;

@Getter
public abstract class Creature {
    protected final FightRules fightRules;
    protected final ValidationRules validationRules;
    protected final DamageRange damageRange;
    protected final int attack;
    protected final int defense;
    protected int health;
    protected boolean isDead;

    public Creature(int health, int attack, int defense, DamageRange damageRange, ValidationRules validationRules, FightRules fightRules) {
        if (validationRules == null) {
            throw new DomainException("Validation rules for a creature must be not null");
        }
        if (fightRules == null) {
            throw new DomainException("Fight rules for a creature must be not null");
        }
        this.validationRules = validationRules;
        this.fightRules = fightRules;
        this.validationRules.validateHealth(health);
        this.validationRules.validateAttack(attack);
        this.validationRules.validateDefense(defense);
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.damageRange = damageRange;
        this.isDead = false;
    }

    public void takeDamage(int damage) {
        if (isDead) {
            throw new CreatureAlreadyDeadException();
        }
        if (damage < 0) {
            throw new DomainException("Damage must be a positive integer");
        }
        health = Math.max(0, health- damage);
        if (health == 0) {
            isDead = true;
        }
    }

    public void attack(Creature other) {
        int damage = fightRules.calculateDamage(this, other);
        other.takeDamage(damage);
    }
}
