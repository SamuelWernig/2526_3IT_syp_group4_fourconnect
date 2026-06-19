package at.htlle.syp.fourconnect;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FourConnectAppTest {
    private FourConnectApp app;

    public void setup(String testData) {
        int[] columns = new int[testData.length()];
        for (int i = 0; i < testData.length(); i++) {
            columns[i] = Character.getNumericValue(testData.charAt(i));
        }

        app = new FourConnectApp();
        app.setColumnReader(new ColumnInputTestReader(columns));
    }

    @AfterEach
    public void teardown() {
    }

    private void printPreample(String preample) {
        System.out.println("___________________________________________________________________________");
        System.out.println("    " + preample);
        System.out.println();
    }


    @Test
    public void runJamesBond() {
        printPreample("James Bond");

        String testData = "007";

        setup(testData);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> app.runGame());

        assertTrue(true);
    }


    @Test
    public void redWinsInRow() {
        printPreample("Red wins in a Row");

        String testData = "00112233";

        setup(testData);

        Character winner = app.runGame();

        assertEquals('R', winner);

        char[][] board = app.getBoard();
        assertEquals('R', board[6][0]);
        assertEquals('R', board[6][1]);
        assertEquals('R', board[6][2]);
        assertEquals('R', board[6][3]);
    }

    @Test
    public void redWinsInColumn() {
        printPreample("Red wins in a Column");

        String testData = "0101010";

        setup(testData);

        Character winner = app.runGame();

        assertEquals('R', winner);

        char[][] board = app.getBoard();
        assertEquals('R', board[6][0]);
        assertEquals('R', board[5][0]);
        assertEquals('R', board[4][0]);
        assertEquals('R', board[3][0]);
    }

    @Test
    public void redWinsInLeftToRightDown() {
        printPreample("Red wins diagonal left to right down");

        String testData = "00010112203";

        setup(testData);

        Character winner = app.runGame();

        assertEquals('R', winner);

        char[][] board = app.getBoard();
        assertEquals('R', board[3][0]);
        assertEquals('R', board[4][1]);
        assertEquals('R', board[5][2]);
        assertEquals('R', board[6][3]);
    }

    @Test
    public void redWinsInLeftUpToRight() {
        printPreample("Red wins diagonal left up to right");

        String testData = "01122323303";

        setup(testData);

        Character winner = app.runGame();

        assertEquals('R', winner);

        char[][] board = app.getBoard();
        assertEquals('R', board[6][0]);
        assertEquals('R', board[5][1]);
        assertEquals('R', board[4][2]);
        assertEquals('R', board[3][3]);
    }

    @Test
    public void yellowWinsInRow() {
        printPreample("Yellow wins in a Row");

        String testData = "60011223";

        setup(testData);

        Character winner = app.runGame();

        assertEquals('Y', winner);

        char[][] board = app.getBoard();
        assertEquals('Y', board[6][0]);
        assertEquals('Y', board[6][1]);
        assertEquals('Y', board[6][2]);
        assertEquals('Y', board[6][3]);
    }

    @Test
    public void yellowWinsInColumn() {
        printPreample("Yellow wins in a Column");

        String testData = "01212121";

        setup(testData);

        Character winner = app.runGame();

        assertEquals('Y', winner);

        char[][] board = app.getBoard();
        assertEquals('Y', board[6][1]);
        assertEquals('Y', board[5][1]);
        assertEquals('Y', board[4][1]);
        assertEquals('Y', board[3][1]);
    }

    @Test
    public void fillWholeBoard() {
        printPreample("Fill whole board");

        String testData = "1624530143562034610250146532413560265024032156431";

        setup(testData);

        Character winner = app.runGame();

        char[][] board = app.getBoard();
        app.printBoard();
        System.out.println("Winner: " + winner);

        int count = 0;

        for (int r = 0; r < FourConnectApp.ROWS; r++) {
            for (int c = 0; c < FourConnectApp.COLUMNS; c++) {
                if (board[r][c] == 'R' || board[r][c] == 'Y') {
                    count++;
                }
            }
        }

        assertNull(winner);
        assertEquals(49, count, "Board should be full. Count: " + count);
    }
}