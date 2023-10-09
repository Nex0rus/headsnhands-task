package org.Nex0rus;

import org.Nex0rus.entities.Creature;
import org.Nex0rus.entities.Monster;
import org.Nex0rus.entities.Player;
import org.Nex0rus.models.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Game extends JPanel implements KeyListener {
    private static final int MAP_WIDTH = 15;
    private static final int MAP_HEIGHT = 15;
    private char[][] map;
    private Player player;
    private int playerX;
    private int playerY;
    private int playerCursorX;
    private int playerCursorY;
    private final int[][] rotDxDy = new int[][]{{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    private int currentRotation = 0;
    private JFrame frame;
    private final ValidationRules validationRules = new DefaultValidationRules();
    private final FightRules fightRules = new DefaultFightRules();
    private final Map<Coordinates, Creature> creatures = new HashMap<>();

    public Game(int maxMonsterCount) {
        player = new Player(50, 25, 25, new DamageRange(10, 20), validationRules, fightRules);
        initializeMap();
        initializeMonsters(maxMonsterCount);
        this.repaint();
    }

    private void initializeMap() {
        frame = new JFrame("Game");
        JPanel panel = this;
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.addKeyListener(this);

        map = new char[MAP_HEIGHT][MAP_WIDTH];
        playerX = MAP_WIDTH / 2;
        playerY = MAP_HEIGHT / 2;
        playerCursorX = playerX;
        playerCursorY = playerY - 1;

        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                map[i][j] = MapSymbols.EMPTY.label;
            }
        }
    }

    public void initializeMonsters(int maxMonsters) {
        int monsterCount = 0;
        Random random = new Random();

        int mapHeight = map.length;
        int mapWidth = map[0].length;

        while (monsterCount < maxMonsters) {
            int x = random.nextInt(mapWidth);
            int y = random.nextInt(mapHeight);

            if (map[y][x] == MapSymbols.EMPTY.label && isSurroundedByEmptyCells(x, y)) {
                map[y][x] = MapSymbols.MONSTER.label;
                Creature newMonster = generateRandomMonster();
                creatures.put(new Coordinates(x, y), newMonster);
            }
            ++monsterCount;
        }
    }

    private Creature generateRandomMonster() {
        Random random = new Random();
        int attack = random.nextInt(30) + 1;
        int defense = random.nextInt(30) + 1;
        int health = random.nextInt(50) + 1;
        int maxDamage = random.nextInt(30) + 1;
        int minDamage = random.nextInt(maxDamage) + 1;
        return new Monster(health, attack, defense, new DamageRange(minDamage, maxDamage), validationRules, fightRules);
    }

    private boolean isSurroundedByEmptyCells(int x, int y) {
        int mapHeight = map.length;
        int mapWidth = map[0].length;

        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i >= 0 && i < mapHeight && j >= 0 && j < mapWidth && map[i][j] != MapSymbols.EMPTY.label) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = 40;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                g.setColor(Color.BLACK);
                g.fillRect(x, y, cellSize, cellSize);
                if (map[i][j] == MapSymbols.MONSTER.label) {
                    g.setColor(Color.RED);
                    g.drawString(String.valueOf(MapSymbols.MONSTER.label), x + cellSize / 2, y + cellSize / 2);
                }
                if (i == playerY && j == playerX) {
                    g.setColor(Color.GREEN);
                    g.drawString("P", x + cellSize / 2, y + cellSize / 2);
                }
                if (i == playerCursorY && j == playerCursorX) {
                    g.setColor(Color.YELLOW);
                    g.drawString("*", x + cellSize / 2,  y + cellSize / 2);
                }
            }
        }
    }

    public void movePlayer(int dx, int dy) {
        int newPlayerX = playerX + dx;
        int newPlayerY = playerY + dy;

        if (newPlayerX >= 0 && newPlayerX < MAP_WIDTH && newPlayerY >= 0 && newPlayerY < MAP_HEIGHT) {
            playerX = newPlayerX;
            playerY = newPlayerY;
            playerCursorY += dy;
            playerCursorX += dx;
        }


    }

    public void rotatePlayer(boolean isClockwise) {
        if (isClockwise) {
            ++currentRotation;
        } else {
            --currentRotation;
        }
        if (currentRotation < 0) {
            currentRotation = 3;
        } else if (currentRotation > 3) {
            currentRotation = 0;
        }
        int[] rotationDxDY = rotDxDy[currentRotation];
        playerCursorX = playerX + rotationDxDY[0];
        playerCursorY = playerY + rotationDxDY[1];
    }

    private String getInfoAboutCell() {
        if (playerCursorX >= 0 && playerCursorX < MAP_WIDTH && playerCursorY >= 0 && playerCursorY < MAP_HEIGHT
                && map[playerCursorY][playerCursorX] != MapSymbols.EMPTY.label) {
            return creatures.get(new Coordinates(playerCursorX, playerCursorY)).toString();
        } else {
            return "There is nothing out there";
        }
    }

    private String attack() {
        StringBuilder builder = new StringBuilder("There is nothing to attack here");
        if (playerCursorX >= 0 && playerCursorX < MAP_WIDTH && playerCursorY >= 0 && playerCursorY < MAP_HEIGHT
                && map[playerCursorY][playerCursorX] != MapSymbols.EMPTY.label) {
            Coordinates position = new Coordinates(playerCursorX, playerCursorY);
            Creature creature = creatures.get(position);
            builder.delete(0, builder.length());
            int prevCreatureHealth = creature.getHealth();
            builder.append("Fighting a monster:\n").append(creature.toString()).append("\n");
            player.attack(creature);
            builder.append("Creature got ")
                    .append(prevCreatureHealth - creature.getHealth())
                    .append(" points of damage and its current health is : ")
                    .append(creature.getHealth())
                    .append("\n");
            if (creature.isDead()) {
                builder.append("Creature died");
                creatures.remove(position);
                map[playerCursorY][playerCursorX] = MapSymbols.EMPTY.label;
            }
        }

        return builder.toString();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W -> movePlayer(0, -1);
            case KeyEvent.VK_A -> movePlayer(-1, 0);
            case KeyEvent.VK_S -> movePlayer(0, 1);
            case KeyEvent.VK_D -> movePlayer(1, 0);
            case KeyEvent.VK_Q -> rotatePlayer(true);
            case KeyEvent.VK_E -> rotatePlayer(false);
            case KeyEvent.VK_I -> System.out.println(getInfoAboutCell());
            case KeyEvent.VK_P -> System.out.println(player.toString());
            case KeyEvent.VK_F -> System.out.println(attack());
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
