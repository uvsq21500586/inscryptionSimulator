package effects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cards.Card;

public class Effect {
	
	public final static int dimensionIcon = 50;
	public final static int dimensionIconWithLevel = 40;
	public final static int dimensionIconRobot = 50;
	public final static int dimensionIconRobotWithLevel = 40;
	
	public final static Map<String, Integer> mapEffectToCost = buildMapNamesEffectsToCost();
	public final static Map<String, String> mapEffectToDescription = buildMapNamesEffectsToDescription();
	
	public final static List<String> namesEffects = Arrays.asList("rabbit_hole","airborne","bee_within","sprinter_right","touch_of_death","fledgling","dambuilder","burrower","unkillable","sharp_quills",
			"hefty_right","guardian","brittle","fecundity","loose_tail_right","corpse_eater","bone_king","mighty_leap","bifurcated_strike","scavenger",
			"green_gem","orange_gem","blue_gem","familiar","gem_animator","bone_digger","detonator","trifurcated_strike","ruby_heart","emerald_heart","amorpheous");
	public final static List<String> namesBeastEffects = Arrays.asList("rabbit_hole","airborne","bee_within","sprinter_right","touch_of_death","fledgling","dambuilder","burrower","fecundity","loose_tail_right",
			"corpse_eater","bone_king");
	public final static List<String> namesRobotEffects = Arrays.asList("sprinter_right","touch_of_death","burrower","unkillable","sharp_quills","hefty_right","guardian","airborne","mighty_leap","bifurcated_strike",
			"trifurcated_strike","brittle");
	public final static List<String> namesUndeadEffects = Arrays.asList("brittle","touch_of_death","corpse_eater","bone_king","unkillable","guardian","airborne","scavenger","bone_digger","detonator");
	public final static List<String> namesWizardMoxEffects = Arrays.asList("green_gem","orange_gem","blue_gem");
	public final static List<String> namesWizardGreenEffects = Arrays.asList("green_gem","familiar","emerald_heart");
	public final static List<String> namesWizardOrangeEffects = Arrays.asList("orange_gem","familiar","gem_animator","ruby_heart");
	public final static List<String> namesWizardBlueEffects = Arrays.asList("blue_gem","familiar","hoarder");
	public final static List<String> namesWizardGreenOrangeEffects = Arrays.asList("green_gem","orange_gem","familiar","gem_animator","ruby_heart","emerald_heart");
	public final static List<String> namesWizardGreenBlueEffects = Arrays.asList("green_gem","blue_gem","familiar","hoarder","emerald_heart");
	public final static List<String> namesWizardOrangeBlueEffects = Arrays.asList("orange_gem","blue_gem","familiar","hoarder","gem_animator","ruby_heart");
	public final static List<String> namesWizardGreenOrangeBlueEffects = Arrays.asList("green_gem","orange_gem","blue_gem","familiar","hoarder","gem_animator","ruby_heart","emerald_heart","amorpheous");
	public final static List<String> namesLevelEffects = Arrays.asList("rabbit_hole","bee_within","fledgling","dambuilder","unkillable","sharp_quills","loose_tail_right","corpse_eater","bone_king",
			"scavenger","green_gem","orange_gem","blue_gem","hoarder","gem_animator","bone_digger","ruby_heart","emerald_heart","amorpheous");
	public final static List<String> namesAttackEffects = Arrays.asList("airborne","touch_of_death","brittle","bifurcated_strike","scavenger","trifurcated_strike");
	public final static List<String> namesResourceEffects = Arrays.asList("bone_king","scavenger","green_gem","orange_gem","blue_gem","bone_digger");
	
	public final static List<String> namesEffectsTooStrongWithUnkillable = Arrays.asList("corpse_eater","bone_king","bone_digger");
	
	String name;
	String type;
	int costStats;
	Integer level;
	Icon icone;
	Image image;

	public Effect(String name, String typeCard, int level) throws IOException {
		super();
		this.name = name;
		this.type = typeCard;
		this.costStats = mapEffectToCost.get(name);
		this.level = level;
		this.image = ImageIO.read(new File("img/" + typeCard + "/effects/" + name + ".png"));
		this.icone = new ImageIcon(image
				.getScaledInstance(dimensionIconWithLevel,dimensionIconWithLevel, 
				Image.SCALE_DEFAULT));
		
	}
	
	public Effect(String name, String typeCard) throws IOException {
		super();
		this.name = name;
		this.type = typeCard;
		this.costStats = mapEffectToCost.get(name);
		this.level = null;
		this.image = ImageIO.read(new File("img/" + typeCard + "/effects/" + name + ".png"));
		this.icone = new ImageIcon(image
				.getScaledInstance(dimensionIcon,dimensionIcon, 
				Image.SCALE_DEFAULT));
	}
	
	public Effect(Effect effect) throws IOException {
		super();
		this.name = effect.name;
		this.type = effect.getType();
		this.costStats = mapEffectToCost.get(name);
		this.level = effect.level;
		this.image = ImageIO.read(new File("img/" + type + "/effects/" + name + ".png"));
		this.icone = effect.icone;
	}
	
	/*public void inverseDirection(JLabel label, Card card) throws IOException {
		if (name.equals("sprinter_right")) {
			name = "sprinter_left";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIcon,dimensionIcon, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobot,dimensionIconRobot, 
								Image.SCALE_DEFAULT));
			}
			
			label.setIcon(icone);
		} else if (name.equals("sprinter_left")) {
			name = "sprinter_right";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIcon,dimensionIcon, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobot,dimensionIconRobot, 
								Image.SCALE_DEFAULT));
			}
			label.setIcon(icone);
		} else if (name.equals("hefty_left")) {
			name = "hefty_right";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIcon,dimensionIcon, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobot,dimensionIconRobot, 
								Image.SCALE_DEFAULT));
			}
			label.setIcon(icone);
		} else if (name.equals("hefty_right")) {
			name = "hefty_left";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIcon,dimensionIcon, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobot,dimensionIconRobot, 
								Image.SCALE_DEFAULT));
			}
			label.setIcon(icone);
		} else if (name.equals("loose_tail_left")) {
			name = "loose_tail_right";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconWithLevel,dimensionIconWithLevel, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobotWithLevel,dimensionIconRobotWithLevel, 
								Image.SCALE_DEFAULT));
			}
			label.setIcon(icone);
		} else if (name.equals("loose_tail_right")) {
			name = "loose_tail_left";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconWithLevel,dimensionIconWithLevel, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobotWithLevel,dimensionIconRobotWithLevel, 
								Image.SCALE_DEFAULT));
			}
			label.setIcon(icone);
		}
	}*/
	
	public void inverseDirection(EffectPanel effectpanel, Card card) throws IOException {
		if (name.equals("sprinter_right")) {
			name = "sprinter_left";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIcon,dimensionIcon, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobot,dimensionIconRobot, 
								Image.SCALE_DEFAULT));
			}
			
			effectpanel.setIconToEffectIcon(icone);
		} else if (name.equals("sprinter_left")) {
			name = "sprinter_right";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIcon,dimensionIcon, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobot,dimensionIconRobot, 
								Image.SCALE_DEFAULT));
			}
			effectpanel.setIconToEffectIcon(icone);
		} else if (name.equals("hefty_left")) {
			name = "hefty_right";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIcon,dimensionIcon, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobot,dimensionIconRobot, 
								Image.SCALE_DEFAULT));
			}
			effectpanel.setIconToEffectIcon(icone);
		} else if (name.equals("hefty_right")) {
			name = "hefty_left";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIcon,dimensionIcon, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobot,dimensionIconRobot, 
								Image.SCALE_DEFAULT));
			}
			effectpanel.setIconToEffectIcon(icone);
		} else if (name.equals("loose_tail_left")) {
			name = "loose_tail_right";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconWithLevel,dimensionIconWithLevel, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobotWithLevel,dimensionIconRobotWithLevel, 
								Image.SCALE_DEFAULT));
			}
			effectpanel.setIconToEffectIcon(icone);
		} else if (name.equals("loose_tail_right")) {
			name = "loose_tail_left";
			if (card.getType().equals("robot")) {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconWithLevel,dimensionIconWithLevel, 
								Image.SCALE_DEFAULT));
			} else {
				icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
						.getScaledInstance(dimensionIconRobotWithLevel,dimensionIconRobotWithLevel, 
								Image.SCALE_DEFAULT));
			}
			effectpanel.setIconToEffectIcon(icone);
		}
	}
	
	
	public void ajustSizeRobot4Effects() {
		this.icone = new ImageIcon(image
				.getScaledInstance(dimensionIconRobotWithLevel,dimensionIconRobotWithLevel, 
				Image.SCALE_DEFAULT));
	}
	
	private static Map<String, Integer> buildMapNamesEffectsToCost() {
		Map<String, Integer> effectsToCost = new HashMap<>();
		effectsToCost.put("rabbit_hole",3);
		effectsToCost.put("airborne",0);
		effectsToCost.put("bee_within",3);
		effectsToCost.put("sprinter_right",1);
		effectsToCost.put("touch_of_death",4);
		effectsToCost.put("fledgling",2);
		effectsToCost.put("dambuilder",3);
		effectsToCost.put("burrower",1);
		effectsToCost.put("unkillable",2);
		effectsToCost.put("sharp_quills",2);
		effectsToCost.put("hefty_right",1);
		effectsToCost.put("guardian",1);
		effectsToCost.put("brittle",-2);
		effectsToCost.put("fecundity",3);
		effectsToCost.put("loose_tail_right",3);
		effectsToCost.put("corpse_eater",3);
		effectsToCost.put("bone_king",2);
		effectsToCost.put("mighty_leap",1);
		effectsToCost.put("bifurcated_strike",4);
		effectsToCost.put("scavenger",2);
		effectsToCost.put("green_gem",2);
		effectsToCost.put("orange_gem",2);
		effectsToCost.put("blue_gem",2);
		effectsToCost.put("familiar",-2);
		effectsToCost.put("hoarder",3);
		effectsToCost.put("gem_animator",3);
		effectsToCost.put("bone_digger",2);
		effectsToCost.put("detonator",0);
		effectsToCost.put("trifurcated_strike",5);
		effectsToCost.put("ruby_heart",3);
		effectsToCost.put("emerald_heart",3);
		effectsToCost.put("amorpheous",3);
		return effectsToCost;
	}
	
	private static Map<String, String> buildMapNamesEffectsToDescription() {
		Map<String, String> effectsToDescription = new HashMap<>();
		effectsToDescription.put("rabbit_hole","When this card is played, a Rabbit is created in your hand.\n More rabbits can be created with level of this effect.");
		effectsToDescription.put("airborne","This card will ignore opposing cards and strike an opponent directly.");
		effectsToDescription.put("bee_within","When this card is struck, a Bee is created in your hand. With the level of this effect, the Bee is stronger.");
		effectsToDescription.put("sprinter_right","At the end of the owner's turn, this card moves in the sigil's direction.");
		effectsToDescription.put("touch_of_death","This card kills any card it damages.");
		effectsToDescription.put("fledgling","After surviving for 1 turn, this card grows into a stronger form (+2 hp and +1 attack per level).");
		effectsToDescription.put("dambuilder","When this card is played, Dams are created on adjacent empty spaces. Dams are not sacrificiable.");
		effectsToDescription.put("burrower","This card will move to any empty space that is attacked by an enemy to block it.");
		effectsToDescription.put("unkillable","When this card perishes (except by sacrifice), a copy of it enters your hand.\n If the level of this effect is greater, this card will gain hp or attack each time it is killed.");
		effectsToDescription.put("sharp_quills","Once this card is struck, the striker is dealt x damage, x equals to level of this effect.");
		effectsToDescription.put("hefty_right","At the end of the owner's turn, this and adjacent cards move in the sigil's direction.");
		effectsToDescription.put("guardian","When an opposing card is played opposite an empty space, this card moves to that space.");
		effectsToDescription.put("brittle","After attacking, this card perishes.");
		effectsToDescription.put("fecundity","When this card is played, a copy of it enters your hand.");
		effectsToDescription.put("loose_tail_right","When this card would be struck, a tail is created in its place and this card moves to the right.");
		effectsToDescription.put("corpse_eater","If a card that you own dies by combat, this card is played from your hand on its space (but with weaker stats depending on cost of the card and level of this effect).");
		effectsToDescription.put("bone_king","When this card dies, 3 more bones per level of this effect.");
		effectsToDescription.put("mighty_leap","This card blocks opposing Airborne creatures.");
		effectsToDescription.put("bifurcated_strike","This card will strike each opposing space to the left and right of the spaces across it.");
		effectsToDescription.put("scavenger","When a card bearing this sigil kills opposing card, its owner receives x bones, x equal to level of this effect.");
		effectsToDescription.put("green_gem","While this card is on the board, it provides x Green Gems, x equal to level of this effect.");
		effectsToDescription.put("orange_gem","While this card is on the board, it provides x Orange Gems, x equal to level of this effect.");
		effectsToDescription.put("blue_gem","While this card is on the board, it provides x Blue Gems, x equal to level of this effect.");
		effectsToDescription.put("familiar","When a card bearing this sigil is alone (or with only dams, chime, pelts, vessels or mox cards), it dies.");
		effectsToDescription.put("hoarder","When this card is played, draw x card from your deck, x equal to level of this effect.");
		effectsToDescription.put("gem_animator","Mox cards on the owner's side of the board gain x attack, x equal to level of this effect.");
		effectsToDescription.put("bone_digger","At the end of the owner's turn, this card generates x bones, x equal to level of this effect.");
		effectsToDescription.put("detonator","When this card dies, adjacent and opposing cards are dealt 10 damage multiplied by the card's base hp.");
		effectsToDescription.put("trifurcated_strike","This card will deal damage to the opposing spaces left, right, and opposite of it.");
		effectsToDescription.put("ruby_heart","When a card bearing this sigil perishes, a Ruby Mox is created in its place.");
		effectsToDescription.put("emerald_heart","When a card bearing this sigil perishes, a Emerald Mox is created in its place.");
		effectsToDescription.put("amorpheous","When a card bearing this sigil is drawn, this sigil becomes another random sigil.");
		return effectsToDescription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCostStats() {
		return costStats;
	}

	public void setCostStats(int costStats) {
		this.costStats = costStats;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Icon getIcone() {
		return icone;
	}

	public void setIcone(Icon icone) {
		this.icone = icone;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
