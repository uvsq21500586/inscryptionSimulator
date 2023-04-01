package frames.menubuttons;

import java.awt.Dimension;

import javax.swing.JButton;

public class ButtonToHelp extends JButton {
	public ButtonToHelp() {
		super();
	    this.setPreferredSize(new Dimension(50,100));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
}
