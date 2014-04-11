package ly.paste.robot;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static java.awt.datatransfer.DataFlavor.stringFlavor;

public class Clipboard {

	private static final Sections sections = new Sections();

    private final Robot robot;
	private final Typist typist;

	public Clipboard(Robot robot) {
        this.robot = robot;
		this.typist = new RobotTypist(this.robot);
	}

    public void paste() {
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(new Object());
        try {
			if (sections.isEmpty() || sections.reset())
				sections.initialise((String) contents.getTransferData(stringFlavor));
			typist.type(sections.next());
        } catch (UnsupportedFlavorException e) {
            throw new NonTextPasteAttempt();
        } catch (IOException e) {
            throw new NonTextPasteAttempt();
        }
	}

	public static void resetSections() {
		sections.shouldReset();
	}

    public static void main(String... args) throws Exception {
		countdown();
		new Clipboard(new Robot()).paste();
    }

	private static void countdown() throws InterruptedException {
		System.out.print("Pasting in ");
		for (int i = 3; i > 0; i--) {
			System.out.printf("%d...", i);
			Thread.sleep(1000);
		}
	}

	private static class NonTextPasteAttempt extends IllegalStateException {
		public NonTextPasteAttempt() {
			super("unable to paste non-string clipboard contents, please make sure only text is in your clipboard");
		}
	}
}
