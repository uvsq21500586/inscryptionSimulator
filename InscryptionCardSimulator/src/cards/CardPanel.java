package cards;

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

import effects.Effect;
import effects.EffectPanel;

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
	EffectPanel effects2[];
	JLabel levelsEffects[];
	JPanel effectPanel;
	JLabel labelAppeareance;
	
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
	    labelAppeareance = new JLabel("");
	    if (card instanceof RobotCard) {
	    	level = new JLabel(card.getLevel().toString() + "x",SwingConstants.RIGHT);
	    } else {
	    	level = new JLabel(card.getLevel().toString(),SwingConstants.RIGHT);
	    }
    	level.setFont(font.deriveFont(28f));
    	this.add(level);
    	level.setBounds(105,50,60,40);
    	
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
	    rarityText.setBounds(10,50,40,40);
	    rarityText.setFont(font.deriveFont(Font.BOLD,16f));
	    if (card instanceof RobotCard) {
	    	rarity.setIcon(new ImageIcon(ImageIO.read(new File("img/rarity_robot.png"))
					.getScaledInstance(40,40, 
							Image.SCALE_DEFAULT)));
	    	rarityText.setForeground(Color.GREEN);
	    	rarityText.setFont(font2.deriveFont(Font.BOLD,16f));
	    	level.setBounds(105,40,60,50);
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
	    hp = new JLabel(card.getHp().toString(), SwingConstants.CENTER);
	    hp.setBounds(150,235,40,60);
	    if (card instanceof UndeadCard) {
	    	hp.setBounds(150,230,40,60);
	    }
	    hp.setFont(font.deriveFont(40f));
	    if (card.getHp().toString().length()<2) {
	    	if (card instanceof UndeadCard) {
	    		hp.setFont(font.deriveFont(40f));
		    } else if (!(card instanceof RobotCard)) {
		    	hp.setFont(font.deriveFont(56f));
		    }
	    	 
	    }
	    //hp.setOpaque(true);
	    this.add(hp);
	    attack = new JLabel(card.getAttack().toString(), SwingConstants.CENTER);
	    attack.setBounds(10,210,40,60);
	    attack.setFont(font.deriveFont(40f));
	    if (card.getAttack().toString().length()<2) {
	    	if (card instanceof UndeadCard) {
	    		attack.setBounds(10,230,40,60);
		    } else if (!(card instanceof RobotCard)) {
		    	attack.setFont(font.deriveFont(56f));
		    }
	    }
	    //attack.setOpaque(true);
	    this.add(attack);
	    hp.setForeground(new Color(0,0,0));
    	attack.setForeground(new Color(0,0,0));
	    if (card instanceof RobotCard) {
	    	hp.setBounds(130,255,50,50);
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
	    redrawEffects();
	    this.add(labelAppeareance);
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
	    		if (typeCost.equals("blood")) {
	    			if (card.getLevel() == 2) {
		    			img = ImageIO.read(new File("img/costs/2blood.png"));
			    		cost.setIcon(new ImageIcon(img
								.getScaledInstance(50,50, 
								Image.SCALE_DEFAULT)));
			    		level.setVisible(false);
		    		}
		    		if (card.getLevel() == 3) {	
		    			img = ImageIO.read(new File("img/costs/3blood.png"));
			    		cost.setIcon(new ImageIcon(img
								.getScaledInstance(60,50, 
								Image.SCALE_DEFAULT)));
			    		cost.setBounds(135,40,60,50);
			    		level.setVisible(false);
		    		}
					if (card.getLevel() == 4) {
						img = ImageIO.read(new File("img/costs/4blood.png"));
			    		cost.setIcon(new ImageIcon(img
								.getScaledInstance(70,50, 
								Image.SCALE_DEFAULT)));
			    		cost.setBounds(125,40,70,50);
			    		level.setVisible(false);
						
					}
					if (card.getLevel() > 4) {	
			    		JPanel levelPanel = new JPanel();
			    		levelPanel.setBounds(105,50,60,40);
			    		levelPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,0,0));
			    		
			    		String levelStr = card.getLevel().toString();
			    		for (int i=0;i<levelStr.length();i++) {
			    			JLabel labelNumber  = new JLabel();
			    			img = ImageIO.read(new File("img/costs/blood/" + levelStr.charAt(i) + ".png"));
			    			labelNumber.setIcon(new ImageIcon(img
									.getScaledInstance(20,35, 
											Image.SCALE_DEFAULT)));
			    			levelPanel.add(labelNumber);
			    		}
			    		img = ImageIO.read(new File("img/costs/blood/x.png"));
			    		JLabel labelX = new JLabel();
			    		labelX.setIcon(new ImageIcon(img
								.getScaledInstance(20,35, 
								Image.SCALE_DEFAULT)));
			    		level.setVisible(false);
			    		levelPanel.add(labelX);
			    		levelPanel.setOpaque(false);
			    		this.add(levelPanel,0);
			    		//cost.setBounds(155,50,35,30);
			    		//level.setBounds(105,50,60,40);
		    		}
	    		} else {
	    			if (card.getLevel() == 2) {
		    			img = ImageIO.read(new File("img/costs/2bone.png"));
			    		cost.setIcon(new ImageIcon(img
								.getScaledInstance(60,50, 
								Image.SCALE_DEFAULT)));
			    		level.setVisible(false);
			    		cost.setBounds(135,40,60,50);
		    		}
		    		if (card.getLevel() == 3) {	
		    			img = ImageIO.read(new File("img/costs/3bone.png"));
			    		cost.setIcon(new ImageIcon(img
								.getScaledInstance(70,50, 
								Image.SCALE_DEFAULT)));
			    		level.setVisible(false);
			    		cost.setBounds(125,40,70,50);
		    		}
		    		if (card.getLevel() > 3) {	
			    		JPanel levelPanel = new JPanel();
			    		levelPanel.setBounds(105,50,60,40);
			    		levelPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,0,0));
			    		
			    		String levelStr = card.getLevel().toString();
			    		for (int i=0;i<levelStr.length();i++) {
			    			JLabel labelNumber  = new JLabel();
			    			img = ImageIO.read(new File("img/costs/bone/" + levelStr.charAt(i) + ".png"));
			    			labelNumber.setIcon(new ImageIcon(img
									.getScaledInstance(20,35, 
											Image.SCALE_DEFAULT)));
			    			levelPanel.add(labelNumber);
			    		}
			    		img = ImageIO.read(new File("img/costs/bone/x.png"));
			    		JLabel labelX = new JLabel();
			    		labelX.setIcon(new ImageIcon(img
								.getScaledInstance(20,35, 
								Image.SCALE_DEFAULT)));
			    		level.setVisible(false);
			    		levelPanel.add(labelX);
			    		levelPanel.setOpaque(false);
			    		this.add(levelPanel,0);
			    		//cost.setBounds(155,50,35,30);
			    		//level.setBounds(105,50,60,40);
		    		}
	    		}
	    		
	    		level.setForeground(new Color(255,240,240));
	    		level.setOpaque(true);
	    		level.setBackground(new Color(0,0,0,96));
	    	}
	    }
	    
	    if (card instanceof RobotCard) {
	    	Image img = ImageIO.read(new File("img/robot/" + card.getAppearance() + ".png"));
	    	labelAppeareance.setIcon(new ImageIcon(img
					.getScaledInstance(200,200, 
					Image.SCALE_DEFAULT)));
	    	level.setForeground(new Color(0,255,0));
	    	if (card.getLevel() > 1 && card.getLevel() < 7) {
	    		img = ImageIO.read(new File("img/costs/" + card.getLevel() + "energy.png"));
	    		cost = new JLabel();
	    		cost.setIcon(new ImageIcon(img
						.getScaledInstance(72,25, 
						Image.SCALE_DEFAULT)));
	    		level.setVisible(false);
	    		this.add(cost);
	    		cost.setBounds(123,55,72,25);
	    		
	    	}
	    	
	    }
	    
	    if (card instanceof UndeadCard) {
	    	Image img = ImageIO.read(new File("img/undead/" + card.getAppearance() + ".png"));
	    	labelAppeareance.setIcon(new ImageIcon(img
					.getScaledInstance(200,200, 
					Image.SCALE_DEFAULT)));
	    	if (card.getLevel() > 0) {
	    		this.remove(level);
	    		level = new JLabel(card.getLevel().toString(), SwingConstants.CENTER);
	    		level.setBounds(75,40,50,50);
	    		level.setForeground(new Color(0,0,0));
	    		this.add(level);
	    		level.setVisible(true);
	    		level.setFont(font.deriveFont(28f));
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
    			Integer costMaxMox = Math.max(wizard.getCostGreenMox(), Math.max(wizard.getCostOrangeMox(),wizard.getCostBlueMox()));
    			level.setVisible(false);
    			if (costMaxMox>1) {
    				JPanel levelPanel = new JPanel();
		    		levelPanel.setBounds(105,50,60,40);
		    		levelPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,0,0));
		    		
		    		String levelStr = costMaxMox.toString();
		    		for (int i=0;i<levelStr.length();i++) {
		    			JLabel labelNumber  = new JLabel();
		    			img = ImageIO.read(new File("img/costs/gem/" + levelStr.charAt(i) + ".png"));
		    			labelNumber.setIcon(new ImageIcon(img
								.getScaledInstance(20,35, 
										Image.SCALE_DEFAULT)));
		    			levelPanel.add(labelNumber);
		    		}
		    		img = ImageIO.read(new File("img/costs/gem/x.png"));
		    		JLabel labelX = new JLabel();
		    		labelX.setIcon(new ImageIcon(img
							.getScaledInstance(20,35, 
							Image.SCALE_DEFAULT)));
		    		level.setVisible(false);
		    		levelPanel.add(labelX);
		    		levelPanel.setOpaque(false);
		    		this.add(levelPanel,0);
    			}
    			if (wizard.getCostAnyMox()>0) {
    				JLabel prismGem = new JLabel();
    				img = ImageIO.read(new File("img/costs/prism_gem.png"));
    				prismGem.setIcon(new ImageIcon(img
        					.getScaledInstance(50,30, 
        							Image.SCALE_DEFAULT)));
    				this.add(prismGem,0);
    				prismGem.setBounds(145,95,50,30);
    				if (wizard.getCostAnyMox()>1) {
    					JPanel levelPanel = new JPanel();
    		    		levelPanel.setBounds(105,100,60,40);
    		    		levelPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,0,0));
    		    		
    		    		String levelStr = wizard.getCostAnyMox().toString();
    		    		for (int i=0;i<levelStr.length();i++) {
    		    			JLabel labelNumber  = new JLabel();
    		    			img = ImageIO.read(new File("img/costs/gem/" + levelStr.charAt(i) + ".png"));
    		    			labelNumber.setIcon(new ImageIcon(img
    								.getScaledInstance(20,35, 
    										Image.SCALE_DEFAULT)));
    		    			levelPanel.add(labelNumber);
    		    		}
    		    		img = ImageIO.read(new File("img/costs/gem/x.png"));
    		    		JLabel labelX = new JLabel();
    		    		labelX.setIcon(new ImageIcon(img
    							.getScaledInstance(20,35, 
    							Image.SCALE_DEFAULT)));
    		    		level.setVisible(false);
    		    		levelPanel.add(labelX);
    		    		levelPanel.setOpaque(false);
    		    		this.add(levelPanel,0);
    				}
    				
    			}
    			/*if (wizard.getCostAnyMox() == 0 && costMaxMox == 1) {
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
    			level.setForeground(new Color(255,0,0));*/
	    	}
	    }
	    if (card instanceof UndeadCard) {
	    	if (card.getLevel() < 1) {
		    	level.setVisible(false);
		    }
	    } else if (card.getLevel() < 2) {
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
	    	WizardCard wizardcard = (WizardCard) card;
	    	if (wizardcard.isMainDeck()) {
	    		
	    		if (wizardcard.getCostGreenMox()>0 && wizardcard.getCostOrangeMox()==0 && wizardcard.getCostBlueMox()==0) {
	    			Image img = ImageIO.read(new File("img/wizard/green.png"));
			    	labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(200,300, 
							Image.SCALE_DEFAULT)));
	    		}
	    		if (wizardcard.getCostGreenMox()==0 && wizardcard.getCostOrangeMox()>0 && wizardcard.getCostBlueMox()==0) {
	    			Image img = ImageIO.read(new File("img/wizard/orange.png"));
			    	labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(200,300, 
							Image.SCALE_DEFAULT)));
	    		}
	    		if (wizardcard.getCostGreenMox()==0 && wizardcard.getCostOrangeMox()==0 && wizardcard.getCostBlueMox()>0) {
	    			Image img = ImageIO.read(new File("img/wizard/blue.png"));
			    	labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(200,300, 
							Image.SCALE_DEFAULT)));
	    		}
	    		if (wizardcard.getCostGreenMox()>0 && wizardcard.getCostOrangeMox()>0 && wizardcard.getCostBlueMox()==0) {
	    			Image img = ImageIO.read(new File("img/wizard/green_orange.png"));
			    	labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(200,300, 
							Image.SCALE_DEFAULT)));
	    		}
	    		if (wizardcard.getCostGreenMox()>0 && wizardcard.getCostOrangeMox()==0 && wizardcard.getCostBlueMox()>0) {
	    			Image img = ImageIO.read(new File("img/wizard/green_blue.png"));
			    	labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(200,300, 
							Image.SCALE_DEFAULT)));
	    		}
	    		if (wizardcard.getCostGreenMox()==0 && wizardcard.getCostOrangeMox()>0 && wizardcard.getCostBlueMox()>0) {
	    			Image img = ImageIO.read(new File("img/wizard/orange_blue.png"));
			    	labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(200,300, 
							Image.SCALE_DEFAULT)));
	    		}
	    		if (wizardcard.getCostGreenMox()>0 && wizardcard.getCostOrangeMox()>0 && wizardcard.getCostBlueMox()>0) {
	    			Image img = ImageIO.read(new File("img/wizard/green_orange_blue.png"));
			    	labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(200,300, 
							Image.SCALE_DEFAULT)));
	    		}
	    	} else {
	    		Image img = ImageIO.read(new File("img/wizard/empty.png"));
		    	labelBackground.setIcon(new ImageIcon(img
						.getScaledInstance(200,300, 
						Image.SCALE_DEFAULT)));
	    	}
	    	
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
	    if (card instanceof RobotCard) {
	    	level = new JLabel(card.getLevel().toString() + "x",SwingConstants.RIGHT);
	    } else {
	    	level = new JLabel(card.getLevel().toString(),SwingConstants.RIGHT);
	    }
	    
    	level.setFont(font.deriveFont(28f));
    	this.add(level);
    	level.setBounds(105,50,60,40);
    	
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
	    rarityText.setBounds(10,50,40,40);
	    //rarityText.setOpaque(true);
	    rarityText.setFont(font.deriveFont(Font.BOLD,16f));
	    
	    if (card instanceof RobotCard) {
	    	rarity.setIcon(new ImageIcon(ImageIO.read(new File("img/rarity_robot.png"))
					.getScaledInstance(40,40, 
							Image.SCALE_DEFAULT)));
	    	rarityText.setForeground(Color.GREEN);
	    	level.setBounds(105,40,60,50);
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
	    hp = new JLabel(card.getHp().toString(), SwingConstants.CENTER);
	    hp.setBounds(150,235,40,60);
	    if (card instanceof UndeadCard) {
	    	hp.setBounds(150,230,40,60);
	    }
	    hp.setFont(font.deriveFont(40f));
	    if (card.getHp().toString().length()<2) {
	    	if (card instanceof UndeadCard) {
	    		hp.setFont(font.deriveFont(40f));
		    } else if (!(card instanceof RobotCard)) {
		    	hp.setFont(font.deriveFont(56f));
		    }
	    	 
	    }
	    //hp.setOpaque(true);
	    this.add(hp);
	    attack = new JLabel(card.getAttack().toString(), SwingConstants.CENTER);
	    attack.setBounds(10,210,40,60);
	    attack.setFont(font.deriveFont(40f));
	    if (card.getAttack().toString().length()<2) {
	    	if (card instanceof UndeadCard) {
	    		attack.setBounds(10,230,40,60);
		    } else if (!(card instanceof RobotCard)) {
		    	attack.setFont(font.deriveFont(56f));
		    }
	    }
	    //attack.setOpaque(true);
	    this.add(attack);
	    hp.setForeground(new Color(0,0,0));
	    attack.setForeground(new Color(0,0,0));
	    if (card instanceof RobotCard) {
	    	hp.setBounds(130,255,50,50);
	    	attack.setBounds(20,255,50,50);
	    	hp.setForeground(new Color(0,240,255));
	    	attack.setForeground(new Color(0,240,255));
	    	hp.setFont(font2.deriveFont(Font.BOLD,24f));
	    	attack.setFont(font2.deriveFont(Font.BOLD,24f));
	    }
	    /*if (card instanceof RobotCard) {
	    	redrawEffects();
	    } else {
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
	    }*/
	    redrawEffects();
	    this.add(labelAppeareance);
	    this.add(labelBackground);
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
	
	public void redrawEffects() throws FontFormatException, IOException {
		//effects2
		for (int i=0;i<4;i++) {
			if (effects != null && effects[i] != null) {
				this.remove(effects[i]);
			}
			if (levelsEffects != null && levelsEffects[i] != null) {
				this.remove(levelsEffects[i]);
			}
			if (effects2 != null && effects2[i] != null) {
				this.remove(effects2[i]);
			}
		}
		if (effectPanel != null) {
			this.remove(effectPanel);
		}
		
		effects2 = new EffectPanel[4];
		if (card instanceof RobotCard) {
			effectPanel = new JPanel();
			effectPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
			effectPanel.setBounds(5,200,190,60);
			if (card.getEffects().stream().allMatch(effect -> effect.getLevel() == null)) {
				effectPanel.setBounds(5,205,190,50);
			}
			effectPanel.setOpaque(false);
			for (int i=0;i<card.getEffects().size();i++) {
				//effects[i].setIcon(card.getEffects().get(i).getIcone());
				if (card.getEffects().size() == 4) {
					card.getEffects().get(i).ajustSizeRobot4Effects();
				}
				effects2[i] = new EffectPanel(card.getEffects().get(i), "robot");
				effectPanel.add(effects2[i]);
			}
			this.add(effectPanel);
		} else {
			for (int i=0;i<card.getEffects().size();i++) {
				if (i>1) {
					card.getEffects().get(i).setImage(ImageIO.read(new File("img/" + card.getType() + "/effects/" + card.getEffects().get(i).getName() + "_34.png")));
					if (card.getEffects().get(i).getLevel() != null) {
						card.getEffects().get(i).setIcone( new ImageIcon(card.getEffects().get(i).getImage()
								.getScaledInstance(Effect.dimensionIconWithLevel,Effect.dimensionIconWithLevel, 
								Image.SCALE_DEFAULT)));
					} else {
						card.getEffects().get(i).setIcone( new ImageIcon(card.getEffects().get(i).getImage()
								.getScaledInstance(Effect.dimensionIcon,Effect.dimensionIcon, 
								Image.SCALE_DEFAULT)));
					}
					
				}
				effects2[i] = new EffectPanel(card.getEffects().get(i), card.getType());
				if (i==0) {
					if (card.getEffects().size()>1) {
						effects2[i].setBounds(50,230,55,60);
						if (card.getEffects().get(i).getLevel() != null) {
							card.getEffects().get(i).setIcone( new ImageIcon(card.getEffects().get(i).getImage()
									.getScaledInstance(Effect.dimensionIconWithLevel,Effect.dimensionIconWithLevel, 
									Image.SCALE_DEFAULT)));
						} else {
							card.getEffects().get(i).setIcone( new ImageIcon(card.getEffects().get(i).getImage()
									.getScaledInstance(Effect.dimensionIcon,Effect.dimensionIcon, 
									Image.SCALE_DEFAULT)));
						}
					} else {
						if (card.getEffects().get(i).getLevel() != null) {
							card.getEffects().get(i).setIcone( new ImageIcon(card.getEffects().get(i).getImage()
									.getScaledInstance(Effect.dimensionIconWithLevel+25,Effect.dimensionIconWithLevel+25, 
									Image.SCALE_DEFAULT)));
						} else {
							card.getEffects().get(i).setIcone( new ImageIcon(card.getEffects().get(i).getImage()
									.getScaledInstance(Effect.dimensionIcon+25,Effect.dimensionIcon+25, 
									Image.SCALE_DEFAULT)));
						}
						effects2[i] = new EffectPanel(card.getEffects().get(i), card.getType());
						effects2[i].setBounds(62,210,80,80);
					}
					
				}
				if (i==1) {
					effects2[i].setBounds(100,210,55,60);
				}
				if (i==2) {
					effects2[i].setBounds(5,90,55,50);
					effects2[i].setOpaque(true);
					effects2[i].setBackground(new Color(0,0,0));
					if (effects2[i].getLevel() != null) {
						effects2[i].getLevel().setForeground(new Color(128,255,192));
					}
				}
				if (i==3) {
					effects2[i].setBounds(5,140,55,50);
					effects2[i].setOpaque(true);
					effects2[i].setBackground(new Color(0,0,0));
					if (effects2[i].getLevel() != null) {
						effects2[i].getLevel().setForeground(new Color(128,255,192));
					}
				}
				
				this.add(effects2[i]);
			}
		}
		
		
		/*
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
	    }*/
		
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


	public EffectPanel[] getEffects2() {
		return effects2;
	}


	public void setEffects2(EffectPanel[] effects2) {
		this.effects2 = effects2;
	}
	
	
}
