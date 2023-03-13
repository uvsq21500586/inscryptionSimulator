package cards;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import effects.Effect;
import frames.duelbuttons.ButtonPlaceCard;

public class BeastCard extends Card {
	private String costType;

	public BeastCard(String appearance, String costType, Integer level, int hpBase, int attackBase, List<Effect> effects,
			boolean mainDeck) {
		super("beast", appearance, level, hpBase, attackBase, effects, mainDeck);
		this.costType = costType;
		// TODO Auto-generated constructor stub
	}
	
	public BeastCard(String appearance, String costType, Integer level, int hpBase, int attackBase, List<Effect> effects, int rarity,
			boolean mainDeck) {
		super("beast", appearance, level, hpBase, attackBase, effects, rarity, mainDeck);
		this.costType = costType;
		// TODO Auto-generated constructor stub
	}
	
	public static BeastCard sourceCard(int hpBase, List<Effect> effects) {
		return new BeastCard("squirrel", "blood", 0, hpBase, 0, effects, false);
		// TODO Auto-generated constructor stub
	}
	
	public static BeastCard mainCard(String appearance, String costType, Integer level, int hpBase, int attackBase, List<Effect> effects) {
		return new BeastCard(appearance, costType, level, hpBase, attackBase, effects, true);
		// TODO Auto-generated constructor stub
	}
	
	public boolean playable(ButtonPlaceCard placesCards[], Integer bonePile) {
		boolean emptyplace = placesCards[0].getCardPanel() == null
				|| placesCards[1].getCardPanel() == null
						|| placesCards[2].getCardPanel() == null
								|| placesCards[3].getCardPanel() == null;
		if (level == 0 && emptyplace) return true;
		if (getCostType().equals("blood")) {
			int count = 0;
			if (placesCards[0].getCardPanel() != null && placesCards[0].getCardPanel().getCard().isSacrificiable()) {
				count++;
			}
			if (placesCards[1].getCardPanel() != null && placesCards[1].getCardPanel().getCard().isSacrificiable()) {
				count++;
			}
			if (placesCards[2].getCardPanel() != null && placesCards[2].getCardPanel().getCard().isSacrificiable()) {
				count++;
			}
			if (placesCards[3].getCardPanel() != null && placesCards[3].getCardPanel().getCard().isSacrificiable()) {
				count++;
			}
			
			if (count >= getLevel()) return true;
		} else {
			if (bonePile >= getLevel() && emptyplace) return true;
		}
		
		return false;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}
	
	public BeastCard cloneCard(BeastCard card) {
		// TODO Auto-generated method stub
		List<Effect> effects = new LinkedList<>(Arrays.asList());
		for (int i=0;i<card.effects.size(); i++) {
			effects.add(card.effects.get(i));
		}
		return new BeastCard(card.appearance, card.costType, card.level, card.hpBase, card.attackBase, effects, card.mainDeck);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String stringForm = type + ";" + appearance + ";" + costType + ";" + level + ";" + hpBase + ";" + attackBase;
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
		return stringForm;
	}
	
	

}
