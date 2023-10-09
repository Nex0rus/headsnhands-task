package org.Nex0rus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
    public static void main(String[] args) {
        int maxMonsterCount = 5;
        SwingUtilities.invokeLater(() -> new Game(maxMonsterCount));
    }
}