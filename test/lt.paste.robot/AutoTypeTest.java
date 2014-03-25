package lt.paste.robot;

import ly.paste.robot.AutoType;
import org.junit.Test;

import java.awt.*;
import java.util.stream.Stream;

public class AutoTypeTest {

	@Test
	public void typesAllKnownCharacters() throws AWTException {
		AutoType type = new AutoType(new Robot());
		Stream<String> letters = Stream.of("!", "\"", "£", "$", "%", "^", "&", "*", "(", ")", ".", " ", "?", ",", "`", "'", "-", "=", "~", "@", "#", "_", "+", "\t", "\n", "[", "]", "\\", "{", "}", "|", ";", ",", "<", ">", "/");
		letters.forEach((letter) -> type.text(letter));
	}

}
