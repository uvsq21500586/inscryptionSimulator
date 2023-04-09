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
import javax.swing.SwingConstants;

import cards.BeastCard;
import cards.Card;
import cards.RobotCard;
import cards.UndeadCard;
import cards.WizardCard;

public class CardPanel extends JPanel {
	Font font;
	Font font2;
	Card card;
	JLabel title;
	JLabel attack;
	JLabel hp;
	JLabel cost;
	JLabel level;
	JLabel selected;
	JLabel beingSacrified;
	JLabel poison;
	JLabel rarity;
	JLabel rarityText;
	String position;
	int fieldPosition;
	JLabel effects[];
	JLabel levelsEffects[];
	
	public CardPanel() {
		super();
	}
	
	
	public CardPanel(Card card) throws IOException, FontFormatException {
		super();
		font = Font.createFont(Font.TRUETYPE_FONT, new File("HEAVYWEI.TTF"));
		font2 = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		position = "onHand";
		setLayout(null);
		this.card = card;
	    this.setPreferredSize(new Dimension(200,300));
	    JLabel labelBackground = new JLabel("");
	    putBackGround(card, labelBackground);
	    JLabel labelAppeareance = new JLabel("");
	    level = new JLabel(card.getLevel().toString() + "x", SwingConstants.RIGHT);
    	level.setFont(font.deriveFont(28f));
    	this.add(level);
    	level.setBounds(105,40,60,50);
    	
	    appearanceCard(card, labelAppeareance);
	    poison = new JLabel("");
	    poison.setIcon(new ImageIcon(ImageIO.read(new File("img/poison.png"))
						.getScaledInstance(40,40, 
						Image.SCALE_DEFAULT)));
	    this.add(poison);
	    poison.setBounds(80,130,40,40);
	    poison.setVisible(false);
	    
	    rarity = new JLabel();
	    rarity.setIcon(new ImageIcon(ImageIO.read(new File("img/rarity.png"))
				.getScaledInstance(40,40, 
				Image.SCALE_DEFAULT)));
	    rarityText =  new JLabel(card.getRarity().toString(), SwingConstants.CENTER);
	    this.add(rarityText);
	    this.add(rarity);
	    rarity.setBounds(10,50,40,40);
	    rarityText.setBounds(10,50,50,40);
	    rarityText.setFont(font.deriveFont(Font.BOLD,16f));
	    if (card instanceof RobotCard) {
	    	rarity.setIcon(new ImageIcon(ImageIO.read(new File("img/rarity_robot.png"))
					.getScaledInstance(40,40, 
							Image.SCALE_DEFAULT)));
	    	rarityText.setForeground(Color.GREEN);
	    	rarityText.setFont(font2.deriveFont(Font.BOLD,16f));
	    	level.setFont(font2.deriveFont(Font.BOLD,20f));
	    }
	    
	    
	    if (card.getRarity() == 0) {
	    	rarity.setVisible(false);
	    	rarityText.setVisible(false);
	    }
	    
	    labelBackground.setBounds(0,0,200,300);
	    labelAppeareance.setBounds(0,0,200,200);
	    this.add(selected);
	    this.add(beingSacrified);
	    this.add(labelAppeareance);
	    hp = new JLabel(card.getHp().toString());
	    hp.setBounds(160,220,50,50);
	    hp.setFont(font.deriveFont(36f));
	    this.add(hp);
	    attack = new JLabel(card.getAttack().toString());
	    attack.setBounds(20,220,50,50);
	    attack.setFont(font.deriveFont(36f));
	    this.add(attack);
	    hp.setForeground(new Color(0,0,0));
    	attack.setForeground(new Color(0,0,0));
	    if (card instanceof RobotCard) {
	    	hp.setBounds(160,255,50,50);
	    	attack.setBounds(20,255,50,50);
	    	hp.setForeground(new Color(0,240,255));
	    	attack.setForeground(new Color(0,240,255));
	    	hp.setFont(font2.deriveFont(Font.BOLD,24f));
	    	attack.setFont(font2.deriveFont(Font.BOLD,24f));
	    }
	    effects = new JLabel[4];
	    levelsEffects = new JLabel[4];
	    for (int i=0;i<card.getEffects().size();i++) {
	    	effects[i] = new JLabel();
	    	effects[i].setIcon(card.getEffects().get(i).getIcone());
	    	this.add(effects[i]);
	    	effects[i].setBounds(50 + 50*(i%2),210 + 40*(i/2) ,30,30);
	    	if (card instanceof RobotCard) {
    			effects[i].setBounds(20 + 45*i,210,30,30);
    	    }
	    	if (card.getEffects().get(i).getLevel() != null) {
	    		levelsEffects[i] = new JLabel(card.getEffects().get(i).getLevel().toString());
	    		levelsEffects[i].setFont(font.deriveFont(Font.BOLD,12f));
	    		levelsEffects[i].setBounds(80 + 50*(i%2),210 + 40*(i/2) ,30,30);
	    		if (card instanceof RobotCard) {
	    			levelsEffects[i].setBounds(20 + 45*i,235 ,30,30);
	    	    	levelsEffects[i].setForeground(new Color(0,240,255));
	    	    	levelsEffects[i].setFont(font2.deriveFont(Font.BOLD,12f));
	    	    }
	    		this.add(levelsEffects[i]);
	    	}
	    }
	    this.add(labelBackground);
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}


	private void appearanceCard(Card card, JLabel labelAppeareance) throws IOException, FontFormatException {
		selected = new JLabel();
		selected.setIcon(new ImageIcon(ImageIO.read(new File("img/activedCard.png"))
				.getScaledInstance(200,300, 
				Image.SCALE_DEFAULT)));
		selected.setVisible(false);
		selected.setBounds(0,0,200,300);
		beingSacrified = new JLabel();
		beingSacrified.setIcon(new ImageIcon(ImageIO.read(new File("img/sacrifice.png"))
				.getScaledInstance(40,80, 
				Image.SCALE_DEFAULT)));
		beingSacrified.setVisible(false);
		beingSacrified.setBounds(80,110,40,80);
		
		if (card instanceof BeastCard) {
	    	Image img = ImageIO.read(new File("img/beast/" + card.getAppearance() + ".png"));
	    	labelAppeareance.setIcon(new ImageIcon(img
					.getScaledInstance(200,200, 
					Image.SCALE_DEFAULT)));
	    	if (card.getLevel() > 0) {
	    		String typeCost = ((BeastCard) card).getCostType();
	    		cost = new JLabel();
	    		img = ImageIO.read(new File("img/costs/" + typeCost + ".png"));
	    		cost.setIcon(new ImageIcon(img
						.getScaledInstance(50,50, 
						Image.SCALE_DEFAULT)));
	    		this.add(cost);
	    		cost.setBounds(145,40,50,50);
	    		level.setForeground(new Color(255,240,240));
	    	}
	    }
	    
	    if (card instanceof RobotCard) {
	    	Image img = ImageIO.read(new File("img/robot/" + card.getAppearance() + ".png"));
	    	labelAppeareance.setIcon(new ImageIcon(img
					.getScaledInstance(200,200, 
					Image.SCALE_DEFAULT)));
	    	level.setForeground(new Color(0,255,0));
	    }
	    
	    if (card instanceof UndeadCard) {
	    	Image img = ImageIO.read(new File("img/undead/" + card.getAppearance() + ".png"));
	    	labelAppeareance.setIcon(new ImageIcon(img
					.getScaledInstance(200,200, 
					Image.SCALE_DEFAULT)));
	    	if (card.getLevel() > 0) {
	    		cost = new JLabel();
    			img = ImageIO.read(new File("img/costs/bone.png"));
    			cost.setIcon(new ImageIcon(img
    					.getScaledInstance(50,50, 
    							Image.SCALE_DEFAULT)));
    			this.add(cost);
    			cost.setBounds(145,40,50,50);
    			level.setForeground(new Color(255,255,0));
	    	}
	    }
	    
	    if (card instanceof WizardCard) {
	    	Image img = ImageIO.read(new File("img/wizard/" + card.getAppearance() + ".png"));
	    	labelAppeareance.setIcon(new ImageIcon(img
					.getScaledInstance(200,200, 
					Image.SCALE_DEFAULT)));
	    	if (card.getLevel() > 0) {
	    		cost = new JLabel();
    			img = ImageIO.read(new File("img/costs/bone.png"));
    			
    			WizardCard wizard = (WizardCard) card;
    			int nbcolors = 1;
    			if (wizard.getCostGreenMox()>0 && wizard.getCostOrangeMox() == 0 && wizard.getCostBlueMox() == 0) {
    				img = ImageIO.read(new File("img/costs/green_gem.png"));
    			}
    			if (wizard.getCostGreenMox() == 0 && wizard.getCostOrangeMox()>0 && wizard.getCostBlueMox() == 0) {
    				img = ImageIO.read(new File("img/costs/orange_gem.png"));
    			}
    			if (wizard.getCostGreenMox() == 0 && wizard.getCostOrangeMox() == 0 && wizard.getCostBlueMox()>0) {
    				img = ImageIO.read(new File("img/costs/blue_gem.png"));
    			}
    			if (wizard.getCostGreenMox()>0 && wizard.getCostOrangeMox()>0 && wizard.getCostBlueMox() == 0) {
    				img = ImageIO.read(new File("img/costs/green_orange_gem.png"));
    				nbcolors = 2;
    			}
    			if (wizard.getCostGreenMox()>0 && wizard.getCostOrangeMox() == 0 && wizard.getCostBlueMox()>0) {
    				img = ImageIO.read(new File("img/costs/green_blue_gem.png"));
    				nbcolors = 2;
    			}
    			if (wizard.getCostGreenMox() == 0 && wizard.getCostOrangeMox()>0 && wizard.getCostBlueMox()>0) {
    				img = ImageIO.read(new File("img/costs/orange_blue_gem.png"));
    				nbcolors = 2;
    			}
    			if (wizard.getCostGreenMox()>0 && wizard.getCostOrangeMox()>0 && wizard.getCostBlueMox()>0) {
    				img = ImageIO.read(new File("img/costs/green_orange_blue_gem.png"));
    				nbcolors = 3;
    			}
    			
    			cost.setIcon(new ImageIcon(img
    					.getScaledInstance(50,30, 
    							Image.SCALE_DEFAULT)));
    			this.add(cost);
    			cost.setBounds(145,50,50,30);
    			int costMaxMox = Math.max(wizard.getCostGreenMox(), Math.max(wizard.getCostOrangeMox(),wizard.getCostBlueMox()));
    			if (wizard.getCostAnyMox() == 0 && costMaxMox == 1) {
    				level.setVisible(false);
    			}
    			if (wizard.getCostAnyMox() == 0 && costMaxMox>1) {
    				level.setText(((wizard.getLevel()-wizard.getCostAnyMox())/nbcolors) + "x");
    			}
    			if (wizard.getCostAnyMox()>0 && costMaxMox == 1) {
    				level.setText(wizard.getCostAnyMox() + "&");
    			}
    			if (wizard.getCostAnyMox()>0 && costMaxMox>1) {
    				level.setText(wizard.getCostAnyMox() + "&" + ((wizard.getLevel()-wizard.getCostAnyMox())/nbcolors) + "x");
    			}
    			level.setBounds(95,40,60,50);
    			level.setFont(font2.deriveFont(Font.BOLD,16f));
    			level.setForeground(new Color(255,0,0));
	    	}
	    }
	    
	    if (card.getLevel() < 2) {
	    	level.setVisible(false);
	    }
	    
	}


	private void putBackGround(Card card, JLabel labelBackground) throws IOException {
		if (card instanceof BeastCard) {
	    	Image img = ImageIO.read(new File("img/beast/empty.png"));
	    	labelBackground.setIcon(new ImageIcon(img
					.getScaledInstance(200,300, 
					Image.SCALE_DEFAULT)));
	    }
	    if (card instanceof RobotCard) {
	    	Image img = ImageIO.read(new File("img/robot/empty.png"));
	    	labelBackground.setIcon(new ImageIcon(img
					.getScaledInstance(200,300, 
					Image.SCALE_DEFAULT)));
	    }
	    if (card instanceof UndeadCard) {
	    	Image img = ImageIO.read(new File("img/undead/empty.png"));
	    	labelBackground.setIcon(new ImageIcon(img
					.getScaledInstance(200,300, 
					Image.SCALE_DEFAULT)));
	    }
	    if (card instanceof WizardCard) {
	    	Image img = ImageIO.read(new File("img/wizard/empty.png"));
	    	labelBackground.setIcon(new ImageIcon(img
					.getScaledInstance(200,300, 
					Image.SCALE_DEFAULT)));
	    }
	}
	
	public void repaint(Card card) throws IOException, FontFormatException {
		this.removeAll();
		position = "onHand";
		font = Font.createFont(Font.TRUETYPE_FONT, new File("HEAVYWEI.TTF"));
		font2 = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		setLayout(null);
		this.card = card;
	    this.setPreferredSize(new Dimension(200,300));
	    JLabel labelBackground = new JLabel("");
	    putBackGround(card, labelBackground);
	    JLabel labelAppeareance = new JLabel("");
	    level = new JLabel(card.getLevel().toString() + "x",SwingConstants.RIGHT);
    	level.setFont(font.deriveFont(28f));
    	this.add(level);
    	level.setBounds(105,40,60,50);
    	
    	poison = new JLabel("");
	    poison.setIcon(new ImageIcon(ImageIO.read(new File("img/poison.png"))
						.getScaledInstance(40,40, 
						Image.SCALE_DEFAULT)));
	    this.add(poison);
	    poison.setBounds(80,130,40,40);
	    if (card.getPoisoned() == 0) {
	    	poison.setVisible(false);
	    }
	    
    	
    	rarity = new JLabel(card.getRarity().toString());
	    rarity.setIcon(new ImageIcon(ImageIO.read(new File("img/rarity.png"))
				.getScaledInstance(40,40, 
				Image.SCALE_DEFAULT)));
	    
	    rarityText =  new JLabel(card.getRarity().toString(), SwingConstants.CENTER);
	    this.add(rarityText);
	    this.add(rarity);
	    rarity.setBounds(10,50,40,40);
	    rarityText.setBounds(10,50,50,40);
	    rarityText.setFont(font.deriveFont(Font.BOLD,16f));
	    
	    if (card instanceof RobotCard) {
	    	rarity.setIcon(new ImageIcon(ImageIO.read(new File("img/rarity_robot.png"))
					.getScaledInstance(40,40, 
							Image.SCALE_DEFAULT)));
	    	rarityText.setForeground(Color.GREEN);
	    	level.setFont(font2.deriveFont(Font.BOLD,20f));
	    	rarityText.setFont(font2.deriveFont(Font.BOLD,16f));
	    }
	    
	    if (card.getRarity() == 0) {
	    	rarity.setVisible(false);
	    	rarityText.setVisible(false);
	    }
    	
    	appearanceCard(card, labelAppeareance);
	    
	    labelBackground.setBounds(0,0,200,300);
	    labelAppeareance.setBounds(0,0,200,200);
	    this.add(selected);
	    this.add(beingSacrified);
	    this.add(labelAppeareance);
	    hp = new JLabel(card.getHp().toString());
	    hp.setBounds(160,220,50,50);
	    hp.setFont(font.deriveFont(36f));
	    this.add(hp);
	    attack = new JLabel(card.getAttack().toString());
	    attack.setBounds(20,220,50,50);
	    attack.setFont(font.deriveFont(36f));
	    this.add(attack);
	    hp.setForeground(new Color(0,0,0));
	    attack.setForeground(new Color(0,0,0));
	    if (card instanceof RobotCard) {
	    	hp.setBounds(160,255,50,50);
	    	attack.setBounds(20,255,50,50);
	    	hp.setForeground(new Color(0,240,255));
	    	attack.setForeground(new Color(0,240,255));
	    	hp.setFont(font2.deriveFont(Font.BOLD,24f));
	    	attack.setFont(font2.deriveFont(Font.BOLD,24f));
	    }
	    effects = new JLabel[4];
	    levelsEffects = new JLabel[4];
	    for (int i=0;i<card.getEffects().size();i++) {
	    	effects[i] = new JLabel();
	    	effects[i].setIcon(card.getEffects().get(i).getIcone());
	    	this.add(effects[i]);
	    	effects[i].setBounds(50 + 50*(i%2),210 + 40*(i/2) ,30,30);
	    	if (card instanceof RobotCard) {
    			effects[i].setBounds(20 + 40*i,210 ,30,30);
    	    }
	    	if (card.getEffects().get(i).getLevel() != null) {
	    		levelsEffects[i] = new JLabel(card.getEffects().get(i).getLevel().toString());
	    		levelsEffects[i].setFont(font.deriveFont(Font.BOLD,12f));
	    		levelsEffects[i].setBounds(80 + 50*(i%2),210 + 40*(i/2) ,30,30);
	    		if (card instanceof RobotCard) {
	    			levelsEffects[i].setBounds(20 + 40*i,235 ,30,30);
	    	    	levelsEffects[i].setForeground(new Color(0,240,255));
	    	    	levelsEffects[i].setFont(font2.deriveFont(Font.BOLD,12f));
	    	    }
	    		this.add(levelsEffects[i]);
	    	}
	    }
	    this.add(labelBackground);
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
	
	public void redrawEffects() {
		for (int i=0;i<4;i++) {
			if (effects[i] != null) {
				this.remove(effects[i]);
			}
			if (levelsEffects[i] != null) {
				this.remove(levelsEffects[i]);
			}
		}
		for (int i=0;i<card.getEffects().size();i++) {
	    	effects[i] = new JLabel();
	    	effects[i].setIcon(card.getEffects().get(i).getIcone());
	    	this.add(effects[i], 1);
	    	effects[i].setBounds(50 + 50*(i%2),210 + 40*(i/2) ,30,30);
	    	if (card instanceof RobotCard) {
    			effects[i].setBounds(20 + 45*i,210,30,30);
    	    }
	    	if (card.getEffects().get(i).getLevel() != null) {
	    		levelsEffects[i] = new JLabel(card.getEffects().get(i).getLevel().toString());
	    		levelsEffects[i].setFont(font.deriveFont(Font.BOLD,12f));
	    		levelsEffects[i].setBounds(80 + 50*(i%2),210 + 40*(i/2) ,30,30);
	    		if (card instanceof RobotCard) {
	    			levelsEffects[i].setBounds(20 + 45*i,235 ,30,30);
	    	    	levelsEffects[i].setForeground(new Color(0,240,255));
	    	    	levelsEffects[i].setFont(font2.deriveFont(Font.BOLD,12f));
	    	    }
	    		this.add(levelsEffects[i], 1);
	    	}
	    }
		
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getFieldPosition() {
		return fieldPosition;
	}

	public void setFieldPosition(int fieldPosition) {
		this.fieldPosition = fieldPosition;
	}

	public JLabel getHp() {
		return hp;
	}

	public void setHp(JLabel hp) {
		this.hp = hp;
	}


	public JLabel[] getEffects() {
		return effects;
	}


	public void setEffects(JLabel[] effects) {
		this.effects = effects;
	}


	public JLabel getPoison() {
		return poison;
	}


	public void setPoison(JLabel poison) {
		this.poison = poison;
	}


	public JLabel[] getLevelsEffects() {
		return levelsEffects;
	}


	public void setLevelsEffects(JLabel[] levelsEffects) {
		this.levelsEffects = levelsEffects;
	}


	public JLabel getAttack() {
		return attack;
	}


	public void setAttack(JLabel attack) {
		this.attack = attack;
	}


	public JLabel getSelected() {
		return selected;
	}


	public void setSelected(JLabel selected) {
		this.selected = selected;
	}


	public JLabel getBeingSacrified() {
		return beingSacrified;
	}


	public void setBeingSacrified(JLabel beingSacrified) {
		this.beingSacrified = beingSacrified;
	}
	
	
	
}
