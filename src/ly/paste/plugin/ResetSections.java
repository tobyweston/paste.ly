package ly.paste.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import ly.paste.robot.Clipboard;

import java.awt.*;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT;
import static com.intellij.openapi.ui.MessageType.ERROR;
import static com.intellij.openapi.ui.popup.Balloon.Position.atRight;

public class ResetSections extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent event) {
		Clipboard.resetSections();
    }

}
