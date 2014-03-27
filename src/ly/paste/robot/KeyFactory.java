package ly.paste.robot;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static java.lang.String.format;
import static ly.paste.robot.Modifier.*;
import static ly.paste.robot.ModifierSequence.sequence;

public class KeyFactory {

	private final Robot robot;

	public KeyFactory(Robot robot) {
		this.robot = robot;
	}

	public Typable createFromCharacter(String text) {
		if (text.length() != 1)
			throw new IllegalArgumentException("please use a single character");

		if (Character.isLetter(text.charAt(0))) {
			boolean upperCase = Character.isUpperCase(text.charAt(0));
			int keyCode = getKeyCodeFromKeyEvent("VK_" + text.toUpperCase());
			if (upperCase)
				return new Key(keyCode, Shift, robot);
			return new Key(keyCode, robot);
		}

		if (Character.isDigit(text.charAt(0)))
			return new Key(getKeyCodeFromKeyEvent("VK_" + text), robot);

		switch (text.charAt(0)) {
			// modifiers
			case '⇧': return new Shift(robot);
            case '⌥': return new Option(robot);
            case '⌘': return new Command(robot);
			case '␑': return new Ctrl(robot);
            case '⎇': return new Alt(robot);

            // navigation
            case '←': return new Key(VK_LEFT, robot);
            case '↑': return new Key(VK_UP, robot);
            case '→': return new Key(VK_RIGHT, robot);
            case '↓': return new Key(VK_DOWN, robot);
            case '⌫': return new Key(VK_BACK_SPACE, robot);
            case '␛': return new Key(VK_ESCAPE, robot);
            case '␡': return new Key(VK_DELETE, robot);
            case '⏎': return new Key(VK_ENTER, robot);
            case '\n': return new Key(VK_ENTER, robot);
			case '⇥': return new Key(VK_TAB, robot);
			case '\t': return new Nothing(); // ignore tabs if the IDE is going to insert them for us

            // function keys
            case '①': return new Key(VK_F1, robot);
            case '②': return new Key(VK_F2, robot);
            case '③': return new Key(VK_F3, robot);
            case '④': return new Key(VK_F4, robot);
            case '⑤': return new Key(VK_F5, robot);
            case '⑥': return new Key(VK_F6, robot);
            case '⑦': return new Key(VK_F7, robot);
            case '⑧': return new Key(VK_F8, robot);
            case '⑨': return new Key(VK_F9, robot);
            case '⑩': return new Key(VK_F10, robot);
            case '⑪': return new Key(VK_F11, robot);
            case '⑫': return new Key(VK_F12, robot);

			// IntelliJ key combinations
			case '☺': return new MakeProject();
			case '␘': return new ClearToolWindows(robot);

			// special keys
			case '!': return new Key(VK_1, Shift, robot);
			case '@': return new Key(VK_2, Shift, robot);
			case '£': return new Key(VK_3, Shift, robot);
			case '$': return new Key(VK_4, Shift, robot);
			case '%': return new Key(VK_5, Shift, robot);
			case '^': return new Key(VK_6, Shift, robot);
			case '&': return new Key(VK_7, Shift, robot);
			case '*': return new Key(VK_8, Shift, robot);
			case '(': return new Key(VK_9, Shift, robot);
			case ')': return new Key(VK_0, Shift, robot);
			case '.': return new Key(VK_PERIOD, robot);
			case ' ': return new Key(VK_SPACE, robot);
			case '?': return new Key(VK_SLASH, Shift, robot);
			case ',': return new Key(VK_COMMA, robot);
			case '`': return new Key(VK_BACK_QUOTE, robot);
			case '"': return new Key(VK_QUOTE, robot);
			case '-': return new Key(VK_MINUS, robot);
			case '=': return new Key(VK_EQUALS, robot);
			case '~': return new Key(VK_BACK_QUOTE, Shift, robot);
			case '\'': return new Key(VK_QUOTE, Shift, robot);
			case '#': return new Key(VK_NUMBER_SIGN, robot);
			case '_': return new Key(VK_MINUS, Shift, robot);
			case '+': return new Key(VK_EQUALS, Shift, robot);
			case '[': return new Key(VK_OPEN_BRACKET, robot);
			case ']': return new Key(VK_CLOSE_BRACKET, robot);
			case '\\': return new Key(VK_BACK_SLASH, robot);
			case '{': return new Key(VK_OPEN_BRACKET, Shift, robot);
			case '}': return new Key(VK_CLOSE_BRACKET, Shift, robot);
			case '|': return new Key(VK_BACK_SLASH, Shift, robot);
			case ';': return new Key(VK_SEMICOLON, robot);
			case ':': return new Key(VK_SEMICOLON, Shift, robot);
			case '<': return new Key(VK_COMMA, Shift, robot);
			case '>': return new Key(VK_PERIOD, Shift, robot);
			case '/': return new Key(VK_SLASH, robot);

			default: throw new IllegalArgumentException(format("character '%s' not recognised", text));
		}
	}

	private static int getKeyCodeFromKeyEvent(String variable) {
		try {
			return KeyEvent.class.getField(variable).getInt(null);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException(format("unable to find %s on the 'KeyEvent' class", variable), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(format("unable to find %s on the 'KeyEvent' class", variable), e);
		}
	}

    public static class HeldKey implements Typable {

        private final Modifier modifier;
        private final Robot robot;

        HeldKey(Modifier modifier, Robot robot) {
            this.modifier = modifier;
            this.robot = robot;
        }

        @Override
        public void type() {
            modifier.press(robot);  // never release!
        }
    }

    private static class Shift extends HeldKey {
        public Shift(Robot robot) {
            super(Modifier.Shift, robot);
        }
    }

    private static class Command extends HeldKey {
        public Command(Robot robot) {
            super(Modifier.Command, robot);
        }
    }

    private static class Option extends HeldKey {
        Option(Robot robot) {
            super(Modifier.None, robot);
        }

        @Override
        public void type() {
            throw new UnsupportedOperationException("Don't know the keyCode");
        }
    }

    private static class Ctrl extends HeldKey {
        public Ctrl(Robot robot) {
            super(Control, robot);
        }
    }

    private static class Alt extends HeldKey {
        Alt(Robot robot) {
            super(Modifier.Alt, robot);
        }
    }

	private class ClearToolWindows extends Key {
		public ClearToolWindows(Robot robot) {
			super(VK_F12, sequence(Command, Modifier.Shift), robot);
		}
	}

	private class MakeProject extends Key {
		public MakeProject() {
			super(VK_F9, sequence(Command, Modifier.Shift), KeyFactory.this.robot);
		}
	}
}
