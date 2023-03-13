package frames.duelbuttons;

import java.awt.Dimension;

import javax.swing.JButton;

public class ButtonSourceDeck extends JButton {
	public ButtonSourceDeck() {
		super();
	    this.setPreferredSize(new Dimension(100,200));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
}
