package frames.menubuttons;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

public class ButtonToDuel extends JButton {
	public String label;
	public ButtonToDuel() {
		super();
	    this.setPreferredSize(new Dimension(50,100));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
	public ButtonToDuel(String label) {
		super();
		this.label = label;
	    this.setPreferredSize(new Dimension(50,100));
	    this.add(new JLabel(label));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
}
