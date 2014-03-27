package ly.paste.robot;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ModifierSequence implements Button {

    private final Set<Modifier> modifiers = new HashSet<Modifier>();

    public static ModifierSequence sequence(Modifier... modifiers) {
        return new ModifierSequence(modifiers);
    }

    private ModifierSequence(Modifier... modifiers) {
        for (Modifier modifier : modifiers)
            this.modifiers.add(modifier);
    }

    @Override
    public void press(Robot robot) {
        for (Modifier modifier : modifiers)
            modifier.press(robot);
    }

    @Override
    public void release(Robot robot) {
        for (Modifier modifier : modifiers)
            modifier.release(robot);
    }
}
