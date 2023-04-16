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
	JLabel costGemsCard;
	JLabel costPrismCard;
	Font font;
	String type;
	Integer level;
	Integer gemCosts[];
	
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
	
	public CostPanel(int costsGems[], int level) throws FontFormatException, IOException {
		//for mages
		this.type = typeGemCost(costsGems);
		this.level = level;
		gemCosts = new Integer[3];
		gemCosts[0] = costsGems[0];
		gemCosts[1] = costsGems[1];
		gemCosts[2] = costsGems[2];
		int maxcostgem = Math.max(costsGems[0], Math.max(costsGems[1],costsGems[2]));
		font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		if (maxcostgem > 1) {
			levelCard = new JLabel(maxcostgem + "x", SwingConstants.RIGHT);
			levelCard.setFont(font.deriveFont(Font.BOLD,20f));
			
		    this.add(levelCard);
		    levelCard.setBounds(105,40,60,50);
		    levelCard.setForeground(new Color(255,0,0));	
		}
	    cost = new JLabel();
	    Image img = ImageIO.read(new File("img/costs/" + type + ".png"));
		cost.setIcon(new ImageIcon(img
				.getScaledInstance(50,50, 
				Image.SCALE_DEFAULT)));
		this.add(cost);
		cost.setBounds(145,40,50,50);
		int prismCost = level - costsGems[0] - costsGems[1] - costsGems[2];
		if (prismCost > 0) {
			JLabel prismCostLabel = new JLabel();
			img = ImageIO.read(new File("img/costs/prism_gem.png"));
			prismCostLabel.setIcon(new ImageIcon(img
					.getScaledInstance(50,50, 
							Image.SCALE_DEFAULT)));
			this.add(prismCostLabel);
			prismCostLabel.setBounds(145,90,50,50);
			if (prismCost > 0) {
				JLabel prismCardCostLabel = new JLabel(prismCost + "x", SwingConstants.RIGHT);
				prismCardCostLabel.setFont(font.deriveFont(Font.BOLD,20f));
				
			    this.add(prismCardCostLabel);
			    prismCardCostLabel.setBounds(105,90,60,50);
			    prismCardCostLabel.setForeground(new Color(255,0,0));	
			}
			
		}
	}
	
	private String typeGemCost(int costsGems[]) {
		if (costsGems[0]>0 && costsGems[1]==0 && costsGems[2]==0) {
			return "green_gem";
		}
		if (costsGems[0]==0 && costsGems[1]>0 && costsGems[2]==0) {
			return "orange_gem";
		}
		if (costsGems[0]==0 && costsGems[1]==0 && costsGems[2]>0) {
			return "blue_gem";
		}
		if (costsGems[0]>0 && costsGems[1]>0 && costsGems[2]==0) {
			return "green_orange_gem";
		}
		if (costsGems[0]>0 && costsGems[1]==0 && costsGems[2]>0) {
			return "green_blue_gem";
		}
		if (costsGems[0]==0 && costsGems[1]>0 && costsGems[2]>0) {
			return "orange_blue_gem";
		}
		if (costsGems[0]>0 && costsGems[1]>0 && costsGems[2]>0) {
			return "green_orange_blue_gem";
		}
		return null;
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

	public Integer[] getGemCosts() {
		return gemCosts;
	}

	public void setGemCosts(Integer[] gemCosts) {
		this.gemCosts = gemCosts;
	}
	
	

}
