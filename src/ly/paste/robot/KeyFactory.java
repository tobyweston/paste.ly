package ly.paste.robot;

@FunctionalInterface
public interface KeyFactory {

	public Key createFromCharacter(String text);

}
