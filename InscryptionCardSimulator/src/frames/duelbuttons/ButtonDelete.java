package frames.duelbuttons;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import cards.BeastCard;
import cards.Card;

public class ButtonDelete extends JButton {
	
	public ButtonDelete() throws IOException, FontFormatException {
		super();
		Image img = ImageIO.read(new File("img/hammer.png"));
		this.setPreferredSize(new Dimension(50,50));
		this.setOpaque(false);
		this.setIcon(new ImageIcon(img
				.getScaledInstance(50,50, 
				Image.SCALE_DEFAULT)));
	    this.repaint();
		this.revalidate();
	}
	
	
	
}
