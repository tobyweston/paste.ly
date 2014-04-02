package ly.paste.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import ly.paste.robot.Clipboard;
import ly.paste.robot.RobotTypist;

import javax.swing.*;
import java.awt.*;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT;
import static com.intellij.openapi.ui.MessageType.ERROR;
import static com.intellij.openapi.ui.MessageType.INFO;
import static com.intellij.openapi.ui.popup.Balloon.Position.atRight;

public class RobotPaste extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent event) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    Clipboard clipboard = new Clipboard(new RobotTypist(new Robot()));
                    clipboard.paste();
                } catch (Exception e) {
                    popup(e, event);
                }
            }
        }.start();
    }

	private void popup(final Exception e, final AnActionEvent event) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StatusBar statusBar = WindowManager.getInstance().getStatusBar(PROJECT.getData(event.getDataContext()));
				JBPopupFactory.getInstance()
					.createHtmlTextBalloonBuilder(e.getMessage(), ERROR, null)
					.setFadeoutTime(7500)
					.createBalloon()
					.show(RelativePoint.getCenterOf(statusBar.getComponent()), atRight);
			}
		});
	}

}
