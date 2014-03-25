package ly.paste.plugin;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.awt.event.KeyEvent.*;

public class Key {

	private final static Delay delay = Delay.MediumDelay;
	
    private final Robot robot;
    private final int keyCode;
    private final Modifier modifier;

    public static enum Modifier {

        None(VK_UNDEFINED) {
            @Override
            public void press(Robot robot) { /* No-op*/ }

            @Override
            public void release(Robot robot) { /* No-op*/ }
        },
        Shift(VK_SHIFT),
        Control(VK_CONTROL),
        WindowsKey(VK_WINDOWS),
        Alt(VK_ALT);

        private final int keyCode;

        Modifier(int keyCode) {
            this.keyCode = keyCode;
        }

        public void press(Robot robot) {
            robot.keyPress(keyCode);
        }

        public void release(Robot robot) {
            robot.keyRelease(keyCode);
        }
    }

    public Key(int keyCode, Robot robot) {
        this(keyCode, Modifier.None, robot);
    }

    public Key(int keyCode, Modifier modifier, Robot robot) {
        this.robot = robot;
        this.keyCode = keyCode;
        this.modifier = modifier;
    }

    public void type() {
        try {
            modifier.press(robot);
            wrapWithException(keyCode, robot::keyPress);
        } finally {
            robot.keyRelease(keyCode);
            modifier.release(robot);
            delay.applyTo(robot);
        }
    }

    public static void wrapWithException(Integer keyCode, Consumer<Integer> consumer) {
        try {
            consumer.accept(keyCode);
        } catch (Throwable e) {
            throw new IllegalArgumentException(String.format("Invalid key code '%s' which is %s in 'KeyEvent'", keyCode, findConstantFor(keyCode)));
        }
    }

    private static String findConstantFor(int keyCode) {
        return Arrays.toString(Stream.of(KeyEvent.class.getFields()).filter(fieldMatching(keyCode)).map(Field::getName).toArray());
    }

    private static Predicate<Field> fieldMatching(int keyCode) {
        return field -> {
            try {
                return field.getName().startsWith("VK_") && field.getInt(null) == keyCode;
            } catch (IllegalAccessException e) {
                return false;
            }
        };
    }

}
