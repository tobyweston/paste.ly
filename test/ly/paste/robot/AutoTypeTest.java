package ly.paste.robot;

import ly.paste.robot.AutoType;
import org.junit.Test;

import java.awt.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AutoTypeTest {

	public void typesAllKnownCharacters() throws AWTException {
		final AutoType type = new AutoType(new Robot());
		Stream<String> letters = Stream.of("!", "\"", "£", "$", "%", "^", "&", "*", "(", ")", ".", " ", "?", ",", "`", "'", "-", "=", "~", "@", "#", "_", "+", "\t", "\n", "[", "]", "\\", "{", "}", "|", ";", ",", "<", ">", "/");
		letters.forEach(new Consumer<String>() {
			@Override
			public void accept(String letter) {
				type.text(letter);
			}
		});
	}

	public static void main(String... args) throws AWTException {
		new AutoTypeTest().typesAllKnownCharacters();
	}

}
