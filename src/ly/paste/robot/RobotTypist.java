package ly.paste.robot;

import java.awt.*;

/**
 * Ensure you set <code>-Dapple.awt.UIElement=true</code> on MacOSX
 * <p>
 * Use tabs instead of spaces to make the replay better
 * <p>
 * In IDEA, you can disable the auto complete right `}` under Editor -> Smart Keys -> Insert pair `}`
 */
public class RobotTypist implements Typist {

	private final KeyFactory keyFactory;

	public RobotTypist(Robot robot) {
		keyFactory = new KeyFactory(robot);
	}

	@Override
	public void type(String text) {
		for (int i = 0; i < text.length(); i++) {
			keyFactory.createFromCharacter(Character.toString(text.charAt(i))).type();
		}
	}

}
