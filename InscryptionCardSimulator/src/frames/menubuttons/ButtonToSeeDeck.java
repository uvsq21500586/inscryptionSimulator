package frames.menubuttons;

import java.awt.Dimension;

import javax.swing.JButton;

public class ButtonToSeeDeck extends JButton {
	public ButtonToSeeDeck() {
		super();
	    this.setPreferredSize(new Dimension(50,100));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
}
