# MiniMaxed-Java-Chess
a basic Java chess game . the ai was made using MinMax algo with pruning variation


# Chess Game with Minimax AI

A fully-functional chess engine built in Java using Object-Oriented Programming principles, featuring an AI opponent powered by the Minimax algorithm with alpha-beta pruning.

## Features

### Core Gameplay
- **Full Chess Rules Implementation**: All standard piece movements (pawns, rooks, knights, bishops, queens, kings)
- **Move Validation**: Complete legal move checking including:
  - Pin detection (pieces pinned to the king cannot move)
  - En passant captures
  - Pawn promotion
  - Capture detection
- **Game State Management**: Tracks wins, draws, stalemate, and insufficient material conditions
- **50-Move Rule**: Resets on captures and pawn moves

### AI System
- **Minimax Algorithm with Alpha-Beta Pruning**: Efficient game tree search for optimal move selection
- **Configurable Difficulty**: Adjustable search depth for varied AI strength
- **Evaluation Function**: Board assessment based on piece values (K=999999999, Q=8, R=5, E/N=3, P=1)

### Game Modes
- **Two Players**: Human vs Human
- **Single Player (Black)**: Play as black against AI
- **Single Player (White)**: Play as white against AI

## Project Structure

```
├── piece.java           # Abstract base class for all pieces
├── pawn.java           # Pawn implementation with promotion logic
├── rook.java           # Rook implementation
├── knight.java         # Knight implementation
├── elephant.java       # Bishop implementation
├── queen.java          # Queen implementation (delegates to rook + bishop)
├── king.java           # King implementation with check detection
├── Board.java          # Game board state and management
├── Player.java         # Base player class (human)
├── AiPlayer.java       # AI player with minimax implementation
├── Main.java           # Game loop and mode selection
└── README.md           # This file
```

## How It Works

### Architecture
- **OOP Design**: Each piece type extends the `piece` base class with specific movement rules
- **Polymorphism**: `Player` base class with `AiPlayer` override allows seamless mode switching
- **Board Representation**: 8x8 2D array of piece objects (null for empty squares)

### Move Validation Flow
1. `CanMakeThemove()` - Validates basic piece movement rules
2. `isPinnedMove()` - Checks if move leaves/puts king in check
3. `isMoveLegal()` - Combines both checks for final validation

### AI Decision Making
1. Generate all legal moves via `getAllPossibleMoves()`
2. For each move, create a simulated board state
3. Recursively evaluate positions using minimax to specified depth
4. Alpha-beta pruning eliminates unnecessary branches
5. Return move with highest evaluation score

### Game End Conditions
- **Win**: Opponent's king is captured
- **Draw**: Stalemate, 50-move rule, or insufficient material
- **Ongoing**: Any other position

## Usage

### Compilation
```bash
javac *.java
```

### Running
```bash
java Main
```

### Current State
The game core is fully functional. The main missing component is the **GUI**:
- Move input currently hardcoded in `Player.getPlayerMove()`
- Board display not implemented in `showBoard()`
- Promotion piece selection not interactive

## Key Design Decisions

### Why `CanMakeThemove()` in Check Detection
Even pinned pieces can deliver check or checkmate. Using `CanMakeThemove()` instead of `isMoveLegal()` in `king.isChecked()` avoids circular dependency while correctly identifying threats. however if it was a variation where pinned pieces can't checkmate or even check instead of using is move legal you can use the combination of canMakeTheMove && !isPinnedMove instead .

### Deep Copy for Minimax
Board states are deep cloned when simulating moves, ensuring parent nodes remain unchanged during recursive evaluation.

### Static AI Objects
The `AiPlayer` uses static temporary board references for efficiency, resetting them for each simulated move.

## Future Enhancements

1. **GUI Implementation**: JavaFX or Swing for visual board and piece interaction
2. **Move History**: Store and display all played moves
3. **Undo/Redo**: Allow players to take back moves
4. **Opening Book**: Use established chess openings for better early-game AI
5. **Transposition Tables**: Cache evaluated positions to improve AI efficiency
7. **Move Time Limit**: Enforce thinking time for balanced gameplay
8. **Game Export**: Save/load games in PGN format

## Technical Notes

- **Stack Overflow Prevention**: Minimax depth limited to avoid stack exhaustion
- **Null Checks**: Board cloning handles all piece types to prevent null pointer exceptions
- **Short-Circuit Evaluation**: Used throughout for safe null checks (e.g., `piece != null && piece.method()`)
- **Integer Arithmetic**: King value set to 999999999 to represent "infinity" in minimax evaluation

## Testing Recommendations

Add print statements to trace:
- Move generation: `System.out.println("Legal moves: " + moves.length)`
- AI decision making: `System.out.println("Best move evaluation: " + evaluation)`
- Game state: `System.out.println("Board status: " + board.status)`

## Variation
 This game is a mix between modern(you can't take the king and you can't move to a check) and old chess(where you can do both) . where here you must take the king with your own hands but you can't move to a check . since it is incoherant(there would be no winner like this in my variation ) you should modify that to either old or modern chess (remove the checkmate (as i prefer) or end the game if any piece can take the king (by a simple condition) before giving the hand to the other player to play (in the main class)) . so it is about you to choose and feel free to do so .

## Credits

Built from scratch with focus on OOP principles, game tree algorithms, and clean architecture.
