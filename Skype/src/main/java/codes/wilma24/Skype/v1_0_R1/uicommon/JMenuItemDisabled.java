package codes.wilma24.Skype.v1_0_R1.uicommon;

import javax.swing.JMenuItem;

public class JMenuItemDisabled extends JMenuItem {
	
	public JMenuItemDisabled(String arg0) {
		super(arg0);
		setEnabled(false);
	}

}
