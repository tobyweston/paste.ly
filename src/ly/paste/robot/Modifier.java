package ly.paste.robot;

import com.intellij.util.containers.ConcurrentHashSet;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.awt.event.KeyEvent.*;

public enum Modifier implements Button {

    None(VK_UNDEFINED) {
        @Override
        public void press(Robot robot) { /* No-op*/ }

        @Override
        public void release(Robot robot) { /* No-op*/ }
    },
    Shift(VK_SHIFT),
    Control(VK_CONTROL),
    WindowsKey(VK_WINDOWS),
    Command(VK_META),
    Alt(VK_ALT);

    private static final Set<Button> held = new ConcurrentSkipListSet<Button>();

    private final int keyCode;

    Modifier(int keyCode) {
        this.keyCode = keyCode;
    }

    public void press(Robot robot) {
        robot.keyPress(keyCode);
        held.add(this);
    }

    public void release(Robot robot) {
        robot.keyRelease(keyCode);
        held.remove(this);
    }

    public static void releaseAll(Robot robot) {
        for (Button button : held)
            button.release(robot);
    }
}
