package ly.paste.robot;

public interface Observer {
	void update(Observable observable, KeyHeldEvent event);
	void update(Observable observable, KeyPressedEvent event);
}
