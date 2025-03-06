package codes.wilma24.Skype.v1_0_R1.uicommon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TaskBar;
import org.eclipse.swt.widgets.TaskItem;

public class SWTWin32TaskBarTaskItemHelper {

	public static Display display;
	public static Shell shell;

	public static TaskItem getTaskBarItem() {
		TaskBar bar = display.getSystemTaskBar();
		if (bar == null)
			return null;
		TaskItem item = bar.getItem(shell);
		if (item == null)
			item = bar.getItem(null);
		return item;
	}

	private static long before = System.currentTimeMillis();

	/*
	 * this method should be called any time there is a state change
	 */
	public static void bringToFront() {
		display.asyncExec(() -> {
			tick();
			display.readAndDispatch();
			shell.setMinimized(false);
			display.readAndDispatch();
		});
	}

	private static void tick() {
		long after = System.currentTimeMillis() - before;
		if (after < 1000) {
			return;
		}
		// TODO: logic
		before = after;
	}

	static {
		Thread thread = new Thread(() -> {
			display = new Display();
			shell = new Shell(display);
			shell.setText("org.eclipse.swt.win32.win32.x86");
			shell.setLayout(new GridLayout());
			TaskItem item1 = getTaskBarItem();
			if (item1 != null) {
				// SWT.DEFAULT is nothing
				// SWT.NORMAL is green
				// SWT.ERROR is red
				// SWT.PAUSED is yellow
				item1.setProgressState(SWT.DEFAULT);
				item1.setProgress(0);
			}
			shell.pack();
			shell.open();
			while (!shell.isDisposed()) {
				tick();
				shell.setMinimized(true);
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			display.dispose();
		});
		thread.start();
	}

}
