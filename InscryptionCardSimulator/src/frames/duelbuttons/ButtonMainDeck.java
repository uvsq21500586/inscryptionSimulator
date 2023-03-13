package frames.duelbuttons;

import java.awt.Dimension;

import javax.swing.JButton;

public class ButtonMainDeck extends JButton {
	public ButtonMainDeck() {
		super();
	    this.setPreferredSize(new Dimension(100,200));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
}
