package ly.paste.robot;

import java.awt.*;
import java.util.List;
import java.util.Arrays;

public class AutoTypeTest {

	public void typesAllKnownCharacters() throws AWTException {
		final RobotTypist type = new RobotTypist(new Robot());
		List<String> letters = Arrays.asList("!", "\"", "£", "$", "%", "^", "&", "*", "(", ")", ".", " ", "?", ",", "`", "'", "-", "=", "~", "@", "#", "_", "+", "\t", "\n", "[", "]", "\\", "{", "}", "|", ";", ",", "<", ">", "/");
		for (String letter : letters) {
			type.type(letter);
		}
	}

	public static void main(String... args) throws AWTException {
		new AutoTypeTest().typesAllKnownCharacters();
	}

}
