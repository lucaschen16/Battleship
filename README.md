# Battleship

Classic Battleship against the computer on a 10×10 grid. This repo contains a **console** game with menus, difficulty settings, and save/load (`src/`), plus a **Swing GUI** in a separate source tree (`gui/`) so the two entry points stay easy to tell apart.

## Features

- **Fleet**: Destroyer (2), Cruiser (3), Submarine (3), Battleship (4), Aircraft carrier (5).
- **AI**: **Easy** fires at random squares; **Normal** hunts adjacent cells after a hit.
- **Console** (`BattleshipConsole`): main menu, new game, load/save, gameplay loop.
- **GUI** (`BattleshipGUI`): menu, instructions dialog, ship placement and play screens (Java Swing).

## Requirements

- [JDK](https://adoptium.net/) 8 or newer (code uses standard library only).

## Build and run

From the repository root:

```bash
mkdir -p out
javac -d out src/*.java gui/*.java
```

To build **only** the console game (no Swing on the classpath):

```bash
javac -d out src/*.java
```

**Graphical UI**

```bash
java -cp out BattleshipGUI
```

**Console**

```bash
java -cp out BattleshipConsole
```

### IntelliJ

Open the project folder (or `Battleship.iml` / module), then run `BattleshipGUI` or `BattleshipConsole` using the gutter ▶ on the class’s `main` method.

## Project layout

| Path | Role |
|------|------|
| `src/BattleshipConsole.java` | Console `main`, menus, game loop |
| `src/GameRules.java`, `GameConstants.java` | Board rules, ship logic, constants |
| `src/ComputerPlayer.java`, `ConsoleDisplay.java`, `GamePersistence.java` | AI, terminal UI, save/load |
| `src/Main.java` | Placeholder entry (not the launcher) |
| `gui/BattleshipGUI.java` | Swing shell: `CardLayout` (menu, game, placement) |
| `gui/MenuPanel.java`, `GamePanel.java`, `ShipPlacementPanel.java` | GUI panels |

## License

No `LICENSE` file is included; add one if you want to clarify terms for reuse.
