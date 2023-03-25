package events;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CostPanel extends JPanel{
	JLabel cost;
	JLabel levelCard;
	Font font;
	String type;
	Integer level;
	
	public CostPanel(String type, int level) throws FontFormatException, IOException {
		this.type = type;
		this.level = level;
		font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		levelCard = new JLabel(level + "x", SwingConstants.RIGHT);
		levelCard.setFont(font.deriveFont(Font.BOLD,20f));
	    this.add(levelCard);
	    levelCard.setBounds(105,40,60,50);
	    cost = new JLabel();
	    Image img = ImageIO.read(new File("img/costs/" + type + ".png"));
		cost.setIcon(new ImageIcon(img
				.getScaledInstance(50,50, 
				Image.SCALE_DEFAULT)));
		this.add(cost);
		cost.setBounds(145,40,50,50);
		levelCard.setForeground(new Color(255,0,0));	
	}
	
	public CostPanel(String types[], int costs[]) {
		//for mages
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	

}
