package ly.paste.robot;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static java.lang.String.format;
import static ly.paste.robot.Modifier.*;
import static ly.paste.robot.ModifierSequence.sequence;

public class KeyFactory {

	private final Robot robot;
	private final Observer[] observers;

	public KeyFactory(Robot robot) {
		this.robot = robot;
		this.observers = new Observer[]{new ReleaseHeldKey(robot), new EventLogger()};
	}

	public Typable createFromCharacter(String text) {
		if (text.length() != 1)
			throw new IllegalArgumentException("please use a single character");

		if (Character.isLetter(text.charAt(0))) {
			boolean upperCase = Character.isUpperCase(text.charAt(0));
			int keyCode = getKeyCodeFromKeyEvent("VK_" + text.toUpperCase());
			if (upperCase)
				return new Key(keyCode, Shift, robot, observers);
			return new Key(keyCode, robot, observers);
		}

		if (Character.isDigit(text.charAt(0)))
			return new Key(getKeyCodeFromKeyEvent("VK_" + text), robot, observers);

		switch (text.charAt(0)) {
			// modifiers
			case '⇧': return new Shift(robot, observers);
            case '⌘': return new Command(robot, observers);
			case '␑': return new Ctrl(robot, observers);
            case '⌥': return new Option(robot, observers);
            case '⎇': return new Alt(robot, observers);

            // navigation
            case '←': return new Key(VK_LEFT, robot, observers);
            case '↑': return new Key(VK_UP, robot, observers);
            case '→': return new Key(VK_RIGHT, robot, observers);
            case '↓': return new Key(VK_DOWN, robot, observers);
            case '⌫': return new Key(VK_BACK_SPACE, robot, observers);
            case '␛': return new Key(VK_ESCAPE, robot, observers);
            case '␡': return new Key(VK_DELETE, robot, observers);
            case '⏎': return new Key(VK_ENTER, robot, observers);
            case '\n': return new Key(VK_ENTER, robot, observers);
			case '⇥': return new Key(VK_TAB, robot, observers);
			case '❙': return new Pause();
			case '\t': return new Nothing(); // ignore tabs if the IDE is going to insert them for us

            // function keys
            case '①': return new Key(VK_F1, robot, observers);
            case '②': return new Key(VK_F2, robot, observers);
            case '③': return new Key(VK_F3, robot, observers);
            case '④': return new Key(VK_F4, robot, observers);
            case '⑤': return new Key(VK_F5, robot, observers);
            case '⑥': return new Key(VK_F6, robot, observers);
            case '⑦': return new Key(VK_F7, robot, observers);
            case '⑧': return new Key(VK_F8, robot, observers);
            case '⑨': return new Key(VK_F9, robot, observers);
            case '⑩': return new Key(VK_F10, robot, observers);
            case '⑪': return new Key(VK_F11, robot, observers);
            case '⑫': return new Key(VK_F12, robot, observers);
            case '⑬': return new Key(VK_F13, robot, observers);
            case '⑭': return new Key(VK_F14, robot, observers);
            case '⑮': return new Key(VK_F15, robot, observers);
            case '⑯': return new Key(VK_F16, robot, observers);
            case '⑰': return new Key(VK_F17, robot, observers);
            case '⑱': return new Key(VK_F18, robot, observers);

			// IntelliJ key combinations
			case '☺': return new MakeProject(robot);
			case '☻': return new Compile(robot);
			case '␘': return new ClearToolWindows(robot);

			// special keys
			case '!': return new Key(VK_1, Shift, robot, observers);
			case '@': return new Key(VK_2, Shift, robot, observers);
			case '£': return new Key(VK_3, Shift, robot, observers);
			case '$': return new Key(VK_4, Shift, robot, observers);
			case '%': return new Key(VK_5, Shift, robot, observers);
			case '^': return new Key(VK_6, Shift, robot, observers);
			case '&': return new Key(VK_7, Shift, robot, observers);
			case '*': return new Key(VK_8, Shift, robot, observers);
			case '(': return new Key(VK_9, Shift, robot, observers);
			case ')': return new Key(VK_0, Shift, robot, observers);
			case '.': return new Key(VK_PERIOD, robot, observers);
			case ' ': return new Key(VK_SPACE, robot, observers);
			case '?': return new Key(VK_SLASH, Shift, robot, observers);
			case ',': return new Key(VK_COMMA, robot, observers);
			case '`': return new Key(VK_BACK_QUOTE, robot, observers);
			case '"': return new Key(VK_QUOTE, Shift, robot, observers);
			case '-': return new Key(VK_MINUS, robot, observers);
			case '=': return new Key(VK_EQUALS, robot, observers);
			case '~': return new Key(VK_BACK_QUOTE, Shift, robot, observers);
			case '\'': return new Key(VK_QUOTE, Shift, robot, observers);
			case '#': return new Key(VK_NUMBER_SIGN, robot, observers);
			case '_': return new Key(VK_MINUS, Shift, robot, observers);
			case '+': return new Key(VK_EQUALS, Shift, robot, observers);
			case '[': return new Key(VK_OPEN_BRACKET, robot, observers);
			case ']': return new Key(VK_CLOSE_BRACKET, robot, observers);
			case '\\': return new Key(VK_BACK_SLASH, robot, observers);
			case '{': return new Key(VK_OPEN_BRACKET, Shift, robot, observers);
			case '}': return new Key(VK_CLOSE_BRACKET, Shift, robot, observers);
			case '|': return new Key(VK_BACK_SLASH, Shift, robot, observers);
			case ';': return new Key(VK_SEMICOLON, robot, observers);
			case ':': return new Key(VK_SEMICOLON, Shift, robot, observers);
			case '<': return new Key(VK_COMMA, Shift, robot, observers);
			case '>': return new Key(VK_PERIOD, Shift, robot, observers);
			case '/': return new Key(VK_SLASH, robot, observers);

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

    public static class HeldKey extends Observable implements Typable {

        private final Modifier modifier;
        private final Robot robot;

        HeldKey(Modifier modifier, Robot robot, Observer... observers) {
            this.modifier = modifier;
            this.robot = robot;
			for (Observer observer : observers)
				addObserver(observer);			
		}

        @Override
        public void type() {
            modifier.press(robot);  // never release!
			notifyObservers(new KeyHeldEvent(modifier));
        }
    }

    private static class Shift extends HeldKey {
        public Shift(Robot robot, Observer... observers) {
            super(Modifier.Shift, robot, observers);
		}
    }

    private static class Command extends HeldKey {
        public Command(Robot robot, Observer... observers) {
            super(Modifier.Command, robot, observers);
        }
    }

    private static class Ctrl extends HeldKey {

		public Ctrl(Robot robot, Observer... observers) {
            super(Control, robot, observers);
        }
	}

    private static class Alt extends HeldKey {

		Alt(Robot robot, Observer... observers) {
            super(Modifier.Alt, robot, observers);
        }
	}

	private static class Option extends Alt {
		Option(Robot robot, Observer... observers) {
			super(robot, observers);
		}
	}

	private static class EventLogger implements Observer {
		@Override
		public void update(Observable observable, KeyHeldEvent event) {
			System.out.printf("[%s] ", event.modifier);
		}

		@Override
		public void update(Observable observable, KeyPressedEvent event) {
			System.out.printf("%d ", event.keyCode);
		}
	}

	private class ClearToolWindows extends Key {
		public ClearToolWindows(Robot robot) {
			super(VK_F12, sequence(Command, Modifier.Shift), robot, observers);
		}
	}

	private class MakeProject extends Key {
		public MakeProject(Robot robot) {
			super(VK_F9, Command, robot, observers);
		}
	}

	private class Compile extends Key {
		public Compile(Robot robot) {
			super(VK_F9, sequence(Command, Shift), robot, observers);
		}
	}

	private class Pause extends Key {
		public Pause() {
			super(VK_UNDEFINED, null, observers);
		}

		@Override
		public void type() {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
