package at.htlle.syp.fourconnect;

import java.util.Arrays;

//(c) group4 Urschinger, Wernig, Schieder, Ranninger;

public class FourConnectApp {
    public static final int ROWS = 7;
    public static final int COLUMNS = 7;
    static final String BASELINE = "---------------";

    private boolean isRedTurn;
    private char[][] board = new char[ROWS][COLUMNS];

    private ColumnInputInterface columnInputInterface;

    public char[][] getBoard() {
        return board;
    }

    public void setColumnReader(ColumnInputInterface columnInputInterface) {
        this.columnInputInterface = columnInputInterface;
    }

    public void initBoard() {
        isRedTurn = true;
        for (int row = 0; row < ROWS; row++) {
            Arrays.fill(board[row], ' ');
        }
    }

    public void printBoard() {
        for (int row = 0; row < ROWS; row++) {
            printRow(board[row]);
        }
        System.out.println(BASELINE);
        System.out.println();
    }

    private void printRow(char[] row) {
        StringBuilder rowBuffer = new StringBuilder("|");
        for (int column = 0; column < COLUMNS; column++) {
            rowBuffer.append(row[column]);
            rowBuffer.append("|");
        }
        System.out.println(rowBuffer);
    }

    public void dropRedCoin() {
        System.out.println("Drop a red disk at column (0–6): ");
        int column = columnInputInterface.getColumn();

        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][column] == ' ') {
                board[row][column] = 'R';
                break;
            }
        }
    }

    public void dropYellowCoin() {
        System.out.println("Drop a yellow disk at column (0–6): ");
        int column = columnInputInterface.getColumn();

        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][column] == ' ') {
                board[row][column] = 'Y';
                break;
            }
        }
    }

    public Character checkWinner() {
        // horizontal
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column <= COLUMNS - 4; column++) {
                if (board[row][column] != ' '
                        && board[row][column + 1] == board[row][column]
                        && board[row][column + 2] == board[row][column]
                        && board[row][column + 3] == board[row][column]) {
                    return board[row][column];
                }
            }
        }

        // vertical
        for (int column = 0; column < COLUMNS; column++) {
            for (int row = 0; row <= ROWS - 4; row++) {
                if (board[row][column] != ' '
                        && board[row + 1][column] == board[row][column]
                        && board[row + 2][column] == board[row][column]
                        && board[row + 3][column] == board[row][column]) {
                    return board[row][column];
                }
            }
        }

        // diagonal links oben nach rechts unten
        for (int row = 0; row <= ROWS - 4; row++) {
            for (int column = 0; column <= COLUMNS - 4; column++) {
                if (board[row][column] != ' '
                        && board[row + 1][column + 1] == board[row][column]
                        && board[row + 2][column + 2] == board[row][column]
                        && board[row + 3][column + 3] == board[row][column]) {
                    return board[row][column];
                }
            }
        }

        // diagonal links unten nach rechts oben
        for (int row = ROWS - 1; row >= 3; row--) {
            for (int column = 0; column <= COLUMNS - 4; column++) {
                if (board[row][column] != ' '
                        && board[row - 1][column + 1] == board[row][column]
                        && board[row - 2][column + 2] == board[row][column]
                        && board[row - 3][column + 3] == board[row][column]) {
                    return board[row][column];
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        FourConnectApp app = new FourConnectApp();
        app.setColumnReader(new ColumnInputScanner());
        app.runGame();
    }

    private boolean isRedTurn() {
        return isRedTurn;
    }

    private void switchCoin() {
        isRedTurn = !isRedTurn;
    }

    public Character runGame() {
        initBoard();

        int count = 0;
        Character winner = null;

        printBoard();

        while (winner == null && count < ROWS * COLUMNS) {
            if (isRedTurn()) {
                dropRedCoin();
            } else {
                dropYellowCoin();
            }

            switchCoin();
            count++;

            printBoard();

            winner = checkWinner();

            if (winner == null && count == ROWS * COLUMNS) {
                System.out.println("It's a draw!");
            } else if (winner != null && winner == 'R') {
                System.out.println("The red player won.");
            } else if (winner != null && winner == 'Y') {
                System.out.println("The yellow player won.");
            }
        }

        return winner;
    }
}