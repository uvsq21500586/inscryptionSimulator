package cards;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import effects.Effect;
import frames.duelbuttons.ButtonPlaceCard;

public class RobotCard extends Card {
	public RobotCard(String appearance, Integer level, int hpBase, int attackBase, List<Effect> effects,
			boolean mainDeck) {
		super("robot", appearance, level, hpBase, attackBase, effects, mainDeck);
		// TODO Auto-generated constructor stub
	}
	
	public RobotCard(String appearance, Integer level, int hpBase, int attackBase, List<Effect> effects, int rarity,
			boolean mainDeck) {
		super("robot", appearance, level, hpBase, attackBase, effects, rarity, mainDeck);
		// TODO Auto-generated constructor stub
	}
	
	public static RobotCard sourceCard(int hpBase, List<Effect> effects) {
		return new RobotCard("empty_vessel", 1, hpBase, 0, effects, false);
		// TODO Auto-generated constructor stub
	}
	
	public static RobotCard mainCard(String appearance, Integer level, int hpBase, int attackBase, List<Effect> effects) {
		return new RobotCard(appearance, level, hpBase, attackBase, effects, true);
		// TODO Auto-generated constructor stub
	}
	
	public boolean playable(ButtonPlaceCard placesCards[], Integer energy) {
		boolean emptyplace = placesCards[0].getCardPanel() == null
				|| placesCards[1].getCardPanel() == null
						|| placesCards[2].getCardPanel() == null
								|| placesCards[3].getCardPanel() == null;
		if (energy >= getLevel() && emptyplace) {
			return true;
		}
		
		return false;
	}

	
	public RobotCard cloneCard(RobotCard card) {
		// TODO Auto-generated method stub
		List<Effect> effects = new LinkedList<>(Arrays.asList());
		for (int i=0;i<card.effects.size(); i++) {
			effects.add(card.effects.get(i));
		}
		return new RobotCard(card.appearance, card.level, card.hpBase, card.attackBase, effects, card.mainDeck);
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
		return stringForm;
	}
	
	

}
