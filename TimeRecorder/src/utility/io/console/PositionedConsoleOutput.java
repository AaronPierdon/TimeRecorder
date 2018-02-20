/**
 *Developer:     Aaron Pierdon
 *
 *Description:   This class uses ASCII escape codes and sends them to the console
 *               via System.out.print(); This gives programmatic control over the
 *               cursor and output of the console. Note that prior to Windows
 *               10, Windows cmd did not accept ASCII escape sequences.
 *
 *Date:          9/16/2017
 *
 */
package utility.io.console;

public class PositionedConsoleOutput {

    protected final char escCode = 0x1B;

    public void moveCursor(int row, int column) {

        String cmd = String.format("%c[%d;%df", escCode, row, column);
        System.out.print(cmd);
    }

    public void cursorUp() {
        String cmd = String.format("%c[%s", escCode, "A");
        System.out.print(cmd);
    }

    public void cursorDown() {
        String cmd = String.format("%c[%s", escCode, "B");
        System.out.print(cmd);
    }

    public void cursorLeft() {
        String cmd = String.format("%c[%s", escCode, "D");
        System.out.print(cmd);
    }

    public void cursorRight() {
        String cmd = String.format("%c[%s", escCode, "C");
        System.out.print(cmd);
    }

    public void cursorPosSave() {
        String cmd = String.format("%c[%s", escCode, "s");
        System.out.print(cmd);
    }

    public void cursorPosLoad() {
        String cmd = String.format("%c[%s", escCode, "u");
        System.out.print(cmd);
    }

    public void clearLine() {
        String cmd = String.format("%c[s", escCode, "K");
        System.out.print(cmd);
    }

    public void clearDisplay() {
        String cmd = String.format("%c[s", escCode, "2J");
        System.out.print(cmd);
    }

}
