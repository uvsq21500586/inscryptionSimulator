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

public class TrialPanel extends JPanel{
	JLabel symbol;
	JLabel levelTrial;
	Font font;
	String type;
	Integer level;
	
	public TrialPanel(String type, Integer level) throws FontFormatException, IOException {
		super();
		this.setLayout(null);
		this.type = type;
		this.level = level;
		font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		levelTrial = new JLabel(level.toString(), SwingConstants.CENTER);
		levelTrial.setFont(font.deriveFont(Font.BOLD,28f));
	    this.add(levelTrial);
	    levelTrial.setBounds(50,160,100,50);
	    symbol = new JLabel();
	    Image img = ImageIO.read(new File("img/trial/" + type + ".png"));
		symbol.setIcon(new ImageIcon(img
				.getScaledInstance(100,100, 
				Image.SCALE_DEFAULT)));
		this.add(symbol);
		symbol.setBounds(50,50,100,100);
		levelTrial.setForeground(new Color(255,0,0));	
	}
	
	public TrialPanel(String types[], int costs[]) {
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
