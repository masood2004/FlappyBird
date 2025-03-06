# Flappy Bird Java Clone

A Java-based clone of the popular Flappy Bird game, built using Swing for GUI. Jump through pipes and score points while avoiding collisions!

[![GitHub](https://img.shields.io/badge/GitHub-Repository-blue?style=flat-square&logo=github)](https://github.com/masood2004/FlappyBird)

![Gameplay Demo](https://github.com/masood2004/FlappyBird/raw/main/demo.gif) *(Replace with actual gameplay GIF)*

## Features
- **Bird Mechanics**: Jump with the spacebar to navigate through pipes.
- **Procedural Pipes**: Randomly generated pipes with consistent gaps.
- **Collision Detection**: Precise hitbox checks for bird-pipe collisions.
- **Score Tracking**: Earn points for every pair of pipes cleared.
- **Game Over & Restart**: Automatically restart the game by pressing space after a collision.
- **Swing GUI**: Smooth animations and responsive controls.

## Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/masood2004/FlappyBird.git
   cd FlappyBird
   ```
2. **Ensure Image Assets**:
   - Create an `images` folder in the project directory.
   - Add the following images to `images/`:
     - `flappybirdbg.png` (background)
     - `flappybird.png` (bird)
     - `toppipe.png` (top pipe)
     - `bottompipe.png` (bottom pipe)
3. **Compile and Run**:
   ```bash
   javac Main.java FlappyBird.java
   java Main
   ```

## How to Play
- Press **SPACE** to make the bird jump.
- Avoid hitting pipes or the ground.
- Each successful pipe pair passed increases your score by 1.
- Game over triggers automatically on collision. Press **SPACE** to restart.

## Controls
| Action          | Key     |
|-----------------|---------|
| Jump/Restart    | SPACE   |

## Code Structure
- `FlappyBird.java`: Handles game logic, rendering, and user input.
  - Manages bird movement, pipe generation, and collisions.
  - Uses Swing timers for game loops.
- `Main.java`: Sets up the JFrame and launches the game window.

## Credits
- Inspired by the original **Flappy Bird** by Dong Nguyen.
- Developed by [masood2004](https://github.com/masood2004).

---

⭐ Feel free to contribute by submitting issues or pull requests!
