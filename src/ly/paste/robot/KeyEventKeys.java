package ly.paste.robot;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static java.lang.String.format;

public class KeyEventKeys implements KeyFactory {

	private final Robot robot;

	public KeyEventKeys(Robot robot) {
		this.robot = robot;
	}

	@Override
	public Key createFromCharacter(String text) {
		if (text.length() != 1)
			throw new IllegalArgumentException("please use a single character");

		if (Character.isLetter(text.charAt(0))) {
			boolean upperCase = Character.isUpperCase(text.charAt(0));
			int keyCode = getKeyCodeFromKeyEvent("VK_" + text.toUpperCase());
			if (upperCase)
				return new Key(keyCode, Key.Modifier.Shift, robot);
			return new Key(keyCode, robot);
		}

		if (Character.isDigit(text.charAt(0)))
			return new Key(getKeyCodeFromKeyEvent("VK_" + text), robot);

		switch (text) {
			case "!": return new Key(VK_1, Key.Modifier.Shift, robot);
			case "@": return new Key(VK_2, Key.Modifier.Shift, robot);
			case "£": return new Key(VK_3, Key.Modifier.Shift, robot);
			case "$": return new Key(VK_4, Key.Modifier.Shift, robot);
			case "%": return new Key(VK_5, Key.Modifier.Shift, robot);
			case "^": return new Key(VK_6, Key.Modifier.Shift, robot);
			case "&": return new Key(VK_7, Key.Modifier.Shift, robot);
			case "*": return new Key(VK_8, Key.Modifier.Shift, robot);
			case "(": return new Key(VK_9, Key.Modifier.Shift, robot);
			case ")": return new Key(VK_0, Key.Modifier.Shift, robot);
			case ".": return new Key(VK_PERIOD, robot);
			case " ": return new Key(VK_SPACE, robot);
			case "?": return new Key(VK_SLASH, Key.Modifier.Shift, robot);
			case ",": return new Key(VK_COMMA, robot);
			case "`": return new Key(VK_BACK_QUOTE, robot);
			case "'": return new Key(VK_QUOTE, robot);
			case "-": return new Key(VK_MINUS, robot);
			case "=": return new Key(VK_EQUALS, robot);
			case "~": return new Key(VK_BACK_QUOTE, Key.Modifier.Shift, robot);
			case "\"": return new Key(VK_QUOTE, Key.Modifier.Shift, robot);
			case "#": return new Key(VK_NUMBER_SIGN, robot);
			case "_": return new Key(VK_MINUS, Key.Modifier.Shift, robot);
			case "+": return new Key(VK_EQUALS, Key.Modifier.Shift, robot);
			case "\t": return new Nothing();// return new Key(VK_TAB, robot); // ignore tabs if the IDE is going to insert them for us
			case "\n": return new Key(VK_ENTER, robot);
			case "[": return new Key(VK_OPEN_BRACKET, robot);
			case "]": return new Key(VK_CLOSE_BRACKET, robot);
			case "\\": return new Key(VK_BACK_SLASH, robot);
			case "{": return new Key(VK_OPEN_BRACKET, Key.Modifier.Shift, robot);
			case "}": return new Key(VK_CLOSE_BRACKET, Key.Modifier.Shift, robot);
			case "|": return new Key(VK_BACK_SLASH, Key.Modifier.Shift, robot);
			case ";": return new Key(VK_SEMICOLON, robot);
			case ":": return new Key(VK_SEMICOLON, Key.Modifier.Shift, robot);
			case "<": return new Key(VK_COMMA, Key.Modifier.Shift, robot);
			case ">": return new Key(VK_PERIOD, Key.Modifier.Shift, robot);
			case "/": return new Key(VK_SLASH, robot);

			default: throw new IllegalArgumentException(format("character '%s' not recognised", text));
		}
	}

	private static int getKeyCodeFromKeyEvent(String variable) {
		try {
			return KeyEvent.class.getField(variable).getInt(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalArgumentException(format("unable to find %s on the 'KeyEvent' class", variable), e);
		}
	}

}
