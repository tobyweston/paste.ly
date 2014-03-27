package ly.paste.robot;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Arrays;

import static java.lang.String.*;

public class Key extends Observable implements Typable {

	private final static Delay delay = Delay.MediumDelay;

	private final Robot robot;
	private final int keyCode;
	private final Held modifier;

    public Key(int keyCode, Robot robot, Observer... observers) {
		this(keyCode, Modifier.None, robot, observers);
	}

	public Key(int keyCode, Held modifier, Robot robot, Observer... observers) {
		this.robot = robot;
		this.keyCode = keyCode;
		this.modifier = modifier;
		addObserver(observers);
	}

	@Override
    public void type() {
		try {
			modifier.press(robot);
			robot.keyPress(keyCode);
			notifyObservers(new KeyPressedEvent(keyCode));
		} catch (Throwable e) {
			throw new IllegalArgumentException(format("Invalid key code '%s' which is %s in 'KeyEvent'", keyCode, findConstantFor(keyCode)));
		} finally {
			robot.keyRelease(keyCode);
			modifier.release(robot);
			delay.applyTo(robot);
		}
	}

	private static String findConstantFor(int keyCode) {
		for (Field field : Arrays.asList(KeyEvent.class.getFields())) {
			if (fieldMatching(field, keyCode)) {
				return field.getName();
			}
		}
		throw new RuntimeException();
	}

	private static Boolean fieldMatching(Field field, int keyCode) {
		try {
			return field.getName().startsWith("VK_") && field.getInt(null) == keyCode;
		} catch (IllegalAccessException e) {
			return false;
		}
	}

}
