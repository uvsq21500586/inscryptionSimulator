package effects;

import java.awt.Color;
import java.awt.Dimension;
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
import uses.VerticalFlowLayout;

public class EffectPanel extends JPanel {
	
	Font font;
	Effect effect;
	JLabel effectIcon;
	JLabel level;
	
	public EffectPanel(Effect effect, String typeCard) throws FontFormatException, IOException {
		super();
		this.setLayout(new VerticalFlowLayout(0));
		//this.setLayout(null);
		
		font = Font.createFont(Font.TRUETYPE_FONT, new File("HEAVYWEI.TTF"));
		if (typeCard.equals("robot")) {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		}
		effectIcon = new JLabel();
		effectIcon.setIcon(effect.getIcone());
		this.add(effectIcon);
		effectIcon.setBounds(0,0,50,80);
		if (effect.getLevel() != null) {
			level = new JLabel(effect.getLevel().toString(), SwingConstants.CENTER);
			level.setFont(font.deriveFont(Font.BOLD,16f));
			if (typeCard.equals("robot")) {
				level.setForeground(new Color(0,240,255));
			}
			if (typeCard.equals("undead")) {
				level.setForeground(new Color(255,255,0));
			}
			this.add(level);
		}
		this.setOpaque(false);
	}

	public JLabel getLevel() {
		return level;
	}

	public void setLevel(JLabel level) {
		this.level = level;
	}
	
	

}
