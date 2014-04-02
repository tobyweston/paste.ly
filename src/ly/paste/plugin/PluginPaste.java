package ly.paste.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.DocumentUtil;
import ly.paste.intellij.NonEventDispatchingThread;
import ly.paste.robot.Clipboard;
import ly.paste.robot.Typist;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT;
import static com.intellij.openapi.ui.MessageType.ERROR;
import static com.intellij.openapi.ui.popup.Balloon.Position.atRight;
import static ly.paste.intellij.NonEventDispatchingThread.undoable;
import static ly.paste.intellij.NonEventDispatchingThread.writeOnEventDispatchingThread;

public class PluginPaste extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent event) {
        try {
            Thread.sleep(500);
            Clipboard clipboard = new Clipboard(new IntelliJTypist(event));
            clipboard.paste();
        } catch (Exception e) {
            popup(e, event);
        }


//        ApplicationManager.getApplication().runWriteAction(new Runnable() {
//            @Override
//            public void run() {
//                final Editor editor = FileEditorManager.getInstance(event.getProject()).getSelectedTextEditor();
//                editor.getDocument().insertString(0, "Hello Toby");
//            }
//        });

    }

    private void popup(final Exception e, final AnActionEvent event) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(PROJECT.getData(event.getDataContext()));
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(e.getMessage(), ERROR, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), atRight);
    }

    private static class IntelliJTypist implements Typist {
        private final AnActionEvent event;

        public IntelliJTypist(AnActionEvent event) {
            this.event = event;
        }

        @Override
        public void type(final String text) {
            writeOnEventDispatchingThread(undoable(new TypeText(text, event)));
        }
    }

    private static class TypeText implements NonEventDispatchingThread.WriteOperation {
        private final String text;
        private final Editor editor;

        public TypeText(String text, AnActionEvent event) {
            this.text = text;
            this.editor = FileEditorManager.getInstance(event.getProject()).getSelectedTextEditor();
        }

        @Override
        public void run() {
            final VisualPosition position = editor.getCaretModel().getVisualPosition();
            for (int i = 0; i < text.length(); i++) {
                final int offset = i;
                Document document = editor.getDocument();
                document.insertString(position.column + offset, Character.toString(text.charAt(offset)));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    editor.getComponent().revalidate();
                    editor.getComponent().repaint();
                }
            }
        }
    }
}
