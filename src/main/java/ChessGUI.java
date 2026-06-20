import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChessGUI extends JFrame {
    private static final int BOARD_SIZE = 800;
    private static final int SQUARE_SIZE = BOARD_SIZE / 8;
    private static final Color LIGHT = new Color(240, 217, 181);
    private static final Color DARK = new Color(181, 136, 99);
    private static final Color HILITE = new Color(106, 158, 60, 180);
    private static final Color SEL = new Color(255, 255, 0, 80);

    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    private int selRow = -1, selCol = -1;
    private List<int[]> legalMoves;
    private final BoardPanel boardPanel = new BoardPanel();
    private final BufferedImage[][] images = new BufferedImage[2][6];

    private static final String[] PIECE_NAMES = {"pawn", "knight", "elephant", "rook", "minister", "king"};
    private static final String[] COLOR_NAMES = {"white", "black"};

    public ChessGUI(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;

        setTitle("MiniMaxed Java Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(boardPanel);
        pack();
        setLocationRelativeTo(null);

        loadImages();

        if (currentPlayer instanceof AiPlayer)
            SwingUtilities.invokeLater(this::triggerAiMove);
    }

    private void loadImages() {
        for (int c = 0; c < 2; c++) {
            for (int p = 0; p < 6; p++) {
                try {
                    BufferedImage img = ImageIO.read(new File("pics/" + COLOR_NAMES[c] + "_" + PIECE_NAMES[p] + ".png"));
                    if (img != null) {
                        Image tmp = img.getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH);
                        BufferedImage resized = new BufferedImage(SQUARE_SIZE - 10, SQUARE_SIZE - 10, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = resized.createGraphics();
                        g.drawImage(tmp, 0, 0, null);
                        g.dispose();
                        images[c][p] = resized;
                    }
                } catch (IOException e) { }
            }
        }
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        clearSelection();
    }

    private void clearSelection() {
        selRow = -1; selCol = -1; legalMoves = null;
    }

    private boolean isGameOver() {
        Player next = (currentPlayer == player1) ? player2 : player1;
        Board.GameState state = board.getGameState(next.isBlack);
        if (state != Board.GameState.Draw) {
            gameOver = true;
            String msg;
            switch (state) {
                case BlackWon: msg = "Black wins!"; break;
                case WhiteWon: msg = "White wins!"; break;
                case OverDraw: msg = "Draw!"; break;
                default: msg = "Game Over"; break;
            }
            JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    private void doMove(int fromR, int fromC, int toR, int toC) {
        piece p = board.board[fromR][fromC];
        board.makeMove(p, toR, toC, currentPlayer);
        boardPanel.repaint();
        if (isGameOver()) return;
        switchTurn();
        boardPanel.repaint();
        if (currentPlayer instanceof AiPlayer)
            triggerAiMove();
    }

    private void triggerAiMove() {
        new Thread(() -> {
            int[] move = currentPlayer.getPlayerMove(board);
            SwingUtilities.invokeLater(() -> {
                board.makeMove(board.board[move[0]][move[1]], move[2], move[3], currentPlayer);
                boardPanel.repaint();
                if (isGameOver()) return;
                switchTurn();
                boardPanel.repaint();
            });
        }).start();
    }

    private List<int[]> getLegalMovesFor(int r, int c) {
        piece p = board.board[r][c];
        if (p == null || p.IsBlack != currentPlayer.isBlack) return null;
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (p.isMoveLegal(board, i, j))
                    moves.add(new int[]{i, j});
        return moves.isEmpty() ? null : moves;
    }

    private static int pieceIndex(piece p) {
        if (p instanceof pawn) return 0;
        if (p instanceof knight) return 1;
        if (p instanceof elephant) return 2;
        if (p instanceof rook) return 3;
        if (p instanceof queen) return 4;
        if (p instanceof king) return 5;
        return -1;
    }

    private class BoardPanel extends JPanel {
        BoardPanel() {
            setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (gameOver || currentPlayer instanceof AiPlayer) return;
                    int col = e.getX() / SQUARE_SIZE;
                    int row = e.getY() / SQUARE_SIZE;
                    if (row < 0 || row > 7 || col < 0 || col > 7) return;

                    if (selRow == -1) {
                        List<int[]> moves = getLegalMovesFor(row, col);
                        if (moves != null) {
                            selRow = row; selCol = col; legalMoves = moves;
                            repaint();
                        }
                    } else {
                        if (row == selRow && col == selCol) {
                            clearSelection(); repaint(); return;
                        }
                        if (board.board[row][col] != null && board.board[row][col].IsBlack == currentPlayer.isBlack) {
                            List<int[]> moves = getLegalMovesFor(row, col);
                            if (moves != null) {
                                selRow = row; selCol = col; legalMoves = moves;
                                repaint(); return;
                            }
                        }
                        boolean valid = false;
                        if (legalMoves != null)
                            for (int[] m : legalMoves)
                                if (m[0] == row && m[1] == col) { valid = true; break; }
                        if (valid) {
                            doMove(selRow, selCol, row, col);
                        } else {
                            clearSelection(); repaint();
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int r = 0; r < 8; r++)
                for (int c = 0; c < 8; c++) {
                    g2.setColor((r + c) % 2 == 0 ? LIGHT : DARK);
                    g2.fillRect(c * SQUARE_SIZE, r * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }

            if (selRow != -1) {
                g2.setColor(SEL);
                g2.fillRect(selCol * SQUARE_SIZE, selRow * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }

            if (legalMoves != null) {
                g2.setColor(HILITE);
                for (int[] m : legalMoves)
                    g2.fillRect(m[1] * SQUARE_SIZE, m[0] * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }

            for (int r = 0; r < 8; r++)
                for (int c = 0; c < 8; c++) {
                    piece p = board.board[r][c];
                    if (p == null) continue;
                    int idx = pieceIndex(p);
                    if (idx < 0) continue;
                    BufferedImage img = images[p.IsBlack ? 1 : 0][idx];
                    int x = c * SQUARE_SIZE + 5;
                    int y = r * SQUARE_SIZE + 5;
                    if (img != null) {
                        g2.drawImage(img, x, y, null);
                    } else {
                        g2.setColor(p.IsBlack ? Color.DARK_GRAY : Color.WHITE);
                        g2.fillOval(x + 10, y + 10, SQUARE_SIZE - 40, SQUARE_SIZE - 40);
                        g2.setColor(Color.BLACK);
                        g2.drawOval(x + 10, y + 10, SQUARE_SIZE - 40, SQUARE_SIZE - 40);
                        String label = PIECE_NAMES[idx].substring(0, 1).toUpperCase();
                        g2.setFont(new Font("Arial", Font.BOLD, 24));
                        FontMetrics fm = g2.getFontMetrics();
                        g2.drawString(label, c * SQUARE_SIZE + (SQUARE_SIZE - fm.stringWidth(label)) / 2,
                            r * SQUARE_SIZE + (SQUARE_SIZE + fm.getAscent()) / 2 - 4);
                    }
                }

            g2.setColor(Color.BLACK);
            for (int i = 0; i <= 8; i++) {
                g2.drawLine(0, i * SQUARE_SIZE, BOARD_SIZE, i * SQUARE_SIZE);
                g2.drawLine(i * SQUARE_SIZE, 0, i * SQUARE_SIZE, BOARD_SIZE);
            }
        }
    }

    enum Mode { BlackVsAi, WhiteVsAi, TwoPlayers }

    private static Mode chooseMode() {
        String[] options = {"White vs AI", "Black vs AI", "Two Players"};
        int choice = JOptionPane.showOptionDialog(null,
            "Select game mode", "MiniMaxed Chess",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);
        if (choice < 0) System.exit(0);
        return Mode.values()[choice];
    }

    public static void main(String[] args) {
        Mode mode = chooseMode();
        Board board = new Board();
        Player Player1, Player2;

        if (mode == Mode.WhiteVsAi) {
            Player1 = new Player();
            Player2 = new AiPlayer();
            Player1.isBlack = false;
            Player2.isBlack = true;
            ((AiPlayer) Player2).level = ((AiPlayer) Player2).getLevel();
        } else if (mode == Mode.BlackVsAi) {
            Player2 = new Player();
            Player1 = new AiPlayer();
            Player1.isBlack = false;
            Player2.isBlack = true;
            ((AiPlayer) Player1).level = ((AiPlayer) Player1).getLevel();
        } else {
            Player1 = new Player();
            Player2 = new Player();
            Player1.isBlack = false;
            Player2.isBlack = true;
        }

        SwingUtilities.invokeLater(() -> {
            ChessGUI gui = new ChessGUI(board, Player1, Player2);
            gui.setVisible(true);
        });
    }
}
