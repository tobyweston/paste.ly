package ly.paste.robot;

import java.awt.*;
import java.util.stream.Stream;

/**
 * Ensure you set <code>-Dapple.awt.UIElement=true</code> on MacOSX
 * <p>
 * Use tabs instead of spaces to make the replay better
 * <p>
 * In IDEA, you can disable the auto complete right `}` under Editor -> Smart Keys -> Insert pair `}`
 */
public class AutoType {
	private final KeyFactory keyFactory;

	public AutoType(Robot robot) {
		keyFactory = new KeyFactory(robot);
	}

	public void text(String text) {
		for (int i = 0; i < text.length(); i++) {
			keyFactory.createFromCharacter(Character.toString(text.charAt(i))).type();
		}
	}

}
