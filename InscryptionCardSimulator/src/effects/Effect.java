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
	
	public final static Map<String, Integer> mapEffectToCost = buildMapNamesEffectsToCost();
	
	public final static List<String> namesEffects = Arrays.asList("rabbit_hole","airborne","bee_within","sprinter_right","poison","fledgling","dambuilder","burrower","unkillable","sharp_quills",
			"hefty_right");
	public final static List<String> namesBeastEffects = Arrays.asList("rabbit_hole","airborne","bee_within","sprinter_right","poison","fledgling","dambuilder","burrower");
	public final static List<String> namesRobotEffects = Arrays.asList("sprinter_right","poison","burrower","unkillable","sharp_quills","hefty_right");
	public final static List<String> namesLevelEffects = Arrays.asList("rabbit_hole","bee_within","poison","fledgling","dambuilder","unkillable","sharp_quills");
	public final static List<String> namesAttackEffects = Arrays.asList("airborne","poison");
	
	String name;
	int costStats;
	Integer level;
	Icon icone;
	
	public Effect(String name, String typeCard, int level) throws IOException {
		super();
		this.name = name;
		this.costStats = mapEffectToCost.get(name);
		this.level = level;
		this.icone = new ImageIcon((ImageIO.read(new File("img/" + typeCard + "/effects/" + name + ".png")))
				.getScaledInstance(30,30, 
				Image.SCALE_DEFAULT));
	}
	
	public Effect(String name, String typeCard) throws IOException {
		super();
		this.name = name;
		this.costStats = mapEffectToCost.get(name);
		this.level = null;
		this.icone = new ImageIcon((ImageIO.read(new File("img/" + typeCard + "/effects/" + name + ".png")))
				.getScaledInstance(30,30, 
				Image.SCALE_DEFAULT));;
	}
	
	public void inverseDirection(JLabel label, Card card) throws IOException {
		if (name.equals("sprinter_right")) {
			name = "sprinter_left";
			icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
					.getScaledInstance(30,30, 
							Image.SCALE_DEFAULT));
			label.setIcon(icone);
		} else if (name.equals("sprinter_left")) {
			name = "sprinter_right";
			icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
					.getScaledInstance(30,30, 
							Image.SCALE_DEFAULT));
			label.setIcon(icone);
		} else if (name.equals("hefty_left")) {
			name = "hefty_right";
			icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
					.getScaledInstance(30,30, 
							Image.SCALE_DEFAULT));
			label.setIcon(icone);
		} else if (name.equals("hefty_right")) {
			name = "hefty_left";
			icone = new ImageIcon((ImageIO.read(new File("img/" + card.getType() + "/effects/" + name + ".png")))
					.getScaledInstance(30,30, 
							Image.SCALE_DEFAULT));
			label.setIcon(icone);
		}
	}
	
	
	
	private static Map<String, Integer> buildMapNamesEffectsToCost() {
		Map<String, Integer> effectsToCost = new HashMap<>();
		effectsToCost.put("rabbit_hole",3);
		effectsToCost.put("airborne",0);
		effectsToCost.put("bee_within",3);
		effectsToCost.put("sprinter_right",1);
		effectsToCost.put("poison",4);
		effectsToCost.put("fledgling",2);
		effectsToCost.put("dambuilder",3);
		effectsToCost.put("burrower",1);
		effectsToCost.put("unkillable",2);
		effectsToCost.put("sharp_quills",2);
		effectsToCost.put("hefty_right",1);
		return effectsToCost;
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
	
	
	
}