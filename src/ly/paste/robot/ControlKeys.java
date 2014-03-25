package ly.paste.robot;

import java.awt.*;

import static java.awt.event.KeyEvent.*;
import static java.lang.String.format;

public class ControlKeys implements KeyFactory {

	private final Robot robot;
	private final KeyFactory delegate;

	public ControlKeys(Robot robot, KeyFactory delegate) {
		this.robot = robot;
		this.delegate = delegate;
	}

	@Override
	public Key createFromCharacter(String text) {
		KeyFactory.validate(text);
		switch (text) {
			case "←": return new Key(VK_LEFT, robot);
			case "↑": return new Key(VK_UP, robot);
			case "→": return new Key(VK_RIGHT, robot);
			case "↓": return new Key(VK_DOWN, robot);
			case "↚": return new Key(VK_BACK_SPACE, robot);

			default: return delegate.createFromCharacter(text);
		}
	}

}
