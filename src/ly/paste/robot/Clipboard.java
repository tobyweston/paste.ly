package ly.paste.robot;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Clipboard {

	private static final Sections sections = new Sections();

    private final Robot robot;

    public Clipboard(Robot robot) {
        this.robot = robot;
    }

    public void paste() {
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(new Object());
        try {
            String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
			if (sections.isEmpty())
				sections.initialise(text);
            new AutoType(robot).text(sections.next());
        } catch (UnsupportedFlavorException | IOException e) {
            throw new IllegalStateException("unable to paste non-string clipboard contents, please make sure only text is in your clipboard");
        }
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

}
