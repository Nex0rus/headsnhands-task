package org.Nex0rus;

public enum MapSymbols {
    EMPTY(' '),
    PLAYER('P'),
    MONSTER('M');

    public final Character label;

    private MapSymbols(Character label) {
        this.label = label;
    }
}