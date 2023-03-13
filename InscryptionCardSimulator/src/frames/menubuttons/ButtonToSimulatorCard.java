package frames.menubuttons;

import java.awt.Dimension;

import javax.swing.JButton;

public class ButtonToSimulatorCard extends JButton {
	public ButtonToSimulatorCard() {
		super();
	    this.setPreferredSize(new Dimension(50,100));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
}
