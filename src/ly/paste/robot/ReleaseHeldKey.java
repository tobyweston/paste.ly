package ly.paste.robot;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class ReleaseHeldKey implements Observer {

	private final Set<Held> held = new ConcurrentSkipListSet<Held>();
	private final Robot robot;

	public ReleaseHeldKey(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void update(Observable observable, KeyHeldEvent event) {
		held.add(event.modifier);
	}

	@Override
	public void update(Observable observable, KeyPressedEvent event) {
		System.out.println("released " + held);
		for (Held key : held)
			key.release(robot);
	}
}
