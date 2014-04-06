package ly.paste.intellij;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;

import static com.intellij.openapi.application.ApplicationManager.*;

public class NonEventDispatchingThread {

    public interface ReadOperation extends Runnable {};
    public interface WriteOperation extends Runnable {};

    public static void read(ReadOperation operation) {
        if (!isEventDispatchThread())
            getApplication().runReadAction(operation);
        else
            operation.run();
    }

    public static void writeOnEventDispatchingThread(final WriteOperation operation) {
        if (!isEventDispatchThread()) {
            getApplication().invokeLater(new Runnable() {
                @Override
                public void run() {
                    getApplication().runWriteAction(operation);
                }
            });
        } else {
            getApplication().runWriteAction(operation);
        }
    }

    public static WriteOperation undoable(final WriteOperation operation) {
        return new WriteOperation() {
            @Override
            public void run() {
				CommandProcessor.getInstance().runUndoTransparentAction(operation);
			}
        };
	}

	private static boolean isEventDispatchThread() {
        return getApplication().isDispatchThread();
    }

}
