package cards;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import effects.Effect;
import frames.duelbuttons.ButtonPlaceCard;

public class UndeadCard extends Card {
	public UndeadCard(String appearance, Integer level, int hpBase, int attackBase, List<Effect> effects,
			boolean mainDeck) {
		super("undead", appearance, level, hpBase, attackBase, effects, mainDeck);
		// TODO Auto-generated constructor stub
	}
	
	public UndeadCard(String appearance, Integer level, int hpBase, int attackBase, List<Effect> effects, int rarity,
			boolean mainDeck) {
		super("undead", appearance, level, hpBase, attackBase, effects, rarity, mainDeck);
		// TODO Auto-generated constructor stub
	}
	
	public static UndeadCard sourceCard(int hpBase, int attackBase, List<Effect> effects) {
		return new UndeadCard("skeleton", 0, hpBase, attackBase, effects, false);
		// TODO Auto-generated constructor stub
	}
	
	public static UndeadCard mainCard(String appearance, Integer level, int hpBase, int attackBase, List<Effect> effects) {
		return new UndeadCard(appearance, level, hpBase, attackBase, effects, true);
		// TODO Auto-generated constructor stub
	}
	
	public boolean playable(ButtonPlaceCard placesCards[], Integer bones) {
		boolean emptyplace = placesCards[0].getCardPanel() == null
				|| placesCards[1].getCardPanel() == null
						|| placesCards[2].getCardPanel() == null
								|| placesCards[3].getCardPanel() == null;
		if (bones >= getLevel() && emptyplace) {
			return true;
		}
		
		return false;
	}

	
	public UndeadCard cloneCard(UndeadCard card) {
		// TODO Auto-generated method stub
		List<Effect> effects = new LinkedList<>(Arrays.asList());
		for (int i=0;i<card.effects.size(); i++) {
			effects.add(card.effects.get(i));
		}
		return new UndeadCard(card.appearance, card.level, card.hpBase, card.attackBase, effects, card.mainDeck);
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
		stringForm = stringForm + ";" + rarity;
		return stringForm;
	}
	
	

}
