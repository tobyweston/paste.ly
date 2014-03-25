package ly.paste.plugin;

import java.awt.event.KeyEvent;

public class Nothing extends Key {

  public Nothing() {
    super(KeyEvent.VK_UNDEFINED, null);
  }

  @Override
  public void type() {
    // no-op
  }
}
