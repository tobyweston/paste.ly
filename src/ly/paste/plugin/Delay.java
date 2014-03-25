package ly.paste.plugin;

import java.awt.*;
import java.util.Random;


public enum Delay {

    ShortDelay(5, 50),
    MediumDelay(10, 100),
    LongDelay(50, 250);

    private static final Random random = new Random();

    private final int upperBound;
	private final int lowerBound;

    Delay(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public void applyTo(Robot robot) {
		robot.delay(delay());
	}

	private int delay() {
		int delay = random.nextInt(upperBound);
		return delay < lowerBound ? lowerBound : delay;
	}
}
