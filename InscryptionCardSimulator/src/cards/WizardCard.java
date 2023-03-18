package cards;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import effects.Effect;
import frames.duelbuttons.ButtonPlaceCard;

public class WizardCard extends Card {
	private Integer costAnyMox = 0;
	private Integer costGreenMox = 0;
	private Integer costOrangeMox = 0;
	private Integer costBlueMox = 0;
	
	
	
	public WizardCard(String appearance, Integer costAnyMox, Integer costGreenMox, Integer costOrangeMox, Integer costBlueMox,
			Integer level, int hpBase, int attackBase, List<Effect> effects, boolean mainDeck) {
		super("wizard", appearance, level, hpBase, attackBase, effects, mainDeck);
		this.costAnyMox = costAnyMox;
		this.costGreenMox = costGreenMox;
		this.costOrangeMox = costOrangeMox;
		this.costBlueMox = costBlueMox;
		sacrificiable = mainDeck;
		// TODO Auto-generated constructor stub
	}
	
	public WizardCard(String appearance, Integer costAnyMox, Integer costGreenMox, Integer costOrangeMox, Integer costBlueMox,
			Integer level, int hpBase, int attackBase, List<Effect> effects, int rarity, boolean mainDeck) {
		super("wizard", appearance, level, hpBase, attackBase, effects, rarity, mainDeck);
		this.costAnyMox = costAnyMox;
		this.costGreenMox = costGreenMox;
		this.costOrangeMox = costOrangeMox;
		this.costBlueMox = costBlueMox;
		sacrificiable = mainDeck;
		// TODO Auto-generated constructor stub
	}
	
	public static WizardCard sourceCard(int hpBase, List<Effect> effects) {
		Boolean green_gem = effects.stream().anyMatch(effect -> effect.getName().equals("green_gem"));
		Boolean orange_gem = effects.stream().anyMatch(effect -> effect.getName().equals("orange_gem"));
		Boolean blue_gem = effects.stream().anyMatch(effect -> effect.getName().equals("blue_gem"));
		Boolean effectsMox[] = {green_gem,orange_gem,blue_gem};
		return new WizardCard(gemsToMox(effectsMox), 0, 0, 0, 0, 0, hpBase, 0, effects, false);
		// TODO Auto-generated constructor stub
	}
	
	public static WizardCard sourceCard(int hpBase, List<Effect> effects, int rarity) {
		Boolean green_gem = effects.stream().anyMatch(effect -> effect.getName().equals("green_gem"));
		Boolean orange_gem = effects.stream().anyMatch(effect -> effect.getName().equals("orange_gem"));
		Boolean blue_gem = effects.stream().anyMatch(effect -> effect.getName().equals("blue_gem"));
		Boolean effectsMox[] = {green_gem,orange_gem,blue_gem};
		return new WizardCard(gemsToMox(effectsMox), 0, 0, 0, 0, 0, hpBase, 0, effects, rarity, false);
		// TODO Auto-generated constructor stub
	}
	
	public static WizardCard mainCard(String appearance, Integer costAnyMox, Integer costGreenMox, Integer costOrangeMox, Integer costBlueMox, Integer level, int hpBase, int attackBase, List<Effect> effects) {
		return new WizardCard(appearance, costAnyMox, costGreenMox, costOrangeMox, costBlueMox, level, hpBase, attackBase, effects, true);
		// TODO Auto-generated constructor stub
	}
	
	public boolean playable(ButtonPlaceCard placesCards[], Integer energy) {
		boolean emptyplace = placesCards[0].getCardPanel() == null
				|| placesCards[1].getCardPanel() == null
						|| placesCards[2].getCardPanel() == null
								|| placesCards[3].getCardPanel() == null;
		if (emptyplace) {
			if (level == 0) {
				return true;
			}
			
			int nbGreenGems = 0;
			int nbOrangeGems = 0;
			int nbBlueGems = 0;
			
			for (int i=0;i<4;i++) {
				if (placesCards[i].getCardPanel() != null) {
					Card card = placesCards[i].getCardPanel().getCard();
					Optional<Effect> green_gem = card.getEffects().stream().filter(effect -> effect.getName().equals("green_gem")).findFirst();
					Optional<Effect> orange_gem = card.getEffects().stream().filter(effect -> effect.getName().equals("orange_gem")).findFirst();
					Optional<Effect> blue_gem = card.getEffects().stream().filter(effect -> effect.getName().equals("blue_gem")).findFirst();
					if (green_gem.isPresent()) {
						nbGreenGems += green_gem.get().getLevel();
					}
					if (orange_gem.isPresent()) {
						nbOrangeGems += orange_gem.get().getLevel();
					}
					if (blue_gem.isPresent()) {
						nbBlueGems += blue_gem.get().getLevel();
					}
				}
			}
			
			if (costGreenMox <= nbGreenGems && costOrangeMox <= nbOrangeGems && costBlueMox <= nbBlueGems && level>=(nbGreenGems + nbOrangeGems + nbBlueGems)) {
				return true;
			}
		}
		
		
		
		return false;
	}

	
	public WizardCard cloneCard(WizardCard card) throws IOException {
		// TODO Auto-generated method stub
		List<Effect> effects = new LinkedList<>(Arrays.asList());
		for (int i=0;i<card.effects.size(); i++) {
			effects.add(new Effect(card.effects.get(i)));
		}
		return new WizardCard(card.appearance, card.getCostAnyMox(), card.getCostGreenMox(), card.getCostOrangeMox(), card.getCostBlueMox(), card.level, card.hpBase, card.attackBase, effects, card.rarity, card.mainDeck);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String stringForm = type + ";" + appearance + ";" + level + ";" + hpBase + ";" + attackBase;
		for(int i=0;i<4;i++) {
			if (effects.size()>i) {
				stringForm = stringForm + ";" + effects.get(i).getName();
				if (effects.get(i).getLevel() != null) {
					stringForm = stringForm + ";" + effects.get(i).getLevel();
				} else {
					stringForm = stringForm + ";_";
				}
			} else {
				stringForm = stringForm + ";_;_";
			}
		}
		stringForm = stringForm + ";" + costAnyMox + ";" + costGreenMox + ";" + costOrangeMox + ";" + costBlueMox + ";" + rarity;
		return stringForm;
	}
	
	public static String gemsToMox(Boolean[] sourceGemEffects) {
		if (sourceGemEffects[0] && !sourceGemEffects[1] && !sourceGemEffects[2]) return "green_mox";
		if (!sourceGemEffects[0] && sourceGemEffects[1] && !sourceGemEffects[2]) return "orange_mox";
		if (!sourceGemEffects[0] && !sourceGemEffects[1] && sourceGemEffects[2]) return "blue_mox";
		if (sourceGemEffects[0] && sourceGemEffects[1] && !sourceGemEffects[2]) return "goranjs_mox";
		if (sourceGemEffects[0] && !sourceGemEffects[1] && sourceGemEffects[2]) return "bleens_mox";
		if (!sourceGemEffects[0] && sourceGemEffects[1] && sourceGemEffects[2]) return "bleens_mox";
		if (sourceGemEffects[0] && sourceGemEffects[1] && sourceGemEffects[2]) return "magnus_mox";
		return null;
	}

	public Integer getCostAnyMox() {
		return costAnyMox;
	}

	public void setCostAnyMox(Integer costAnyMox) {
		this.costAnyMox = costAnyMox;
	}

	public Integer getCostGreenMox() {
		return costGreenMox;
	}

	public void setCostGreenMox(Integer costGreenMox) {
		this.costGreenMox = costGreenMox;
	}

	public Integer getCostOrangeMox() {
		return costOrangeMox;
	}

	public void setCostOrangeMox(Integer costOrangeMox) {
		this.costOrangeMox = costOrangeMox;
	}

	public Integer getCostBlueMox() {
		return costBlueMox;
	}

	public void setCostBlueMox(Integer costBlueMox) {
		this.costBlueMox = costBlueMox;
	}
	
	

}
