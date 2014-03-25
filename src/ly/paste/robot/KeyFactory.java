package ly.paste.robot;

@FunctionalInterface
public interface KeyFactory {

	public Key createFromCharacter(String text);

	static void validate(String text) {
		if (text.length() != 1)
			throw new IllegalArgumentException("please use a single character");
	}
}
