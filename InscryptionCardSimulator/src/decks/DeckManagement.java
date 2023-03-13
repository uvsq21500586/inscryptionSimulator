package decks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cards.BeastCard;
import cards.Card;
import cards.RobotCard;
import effects.Effect;

public class DeckManagement {
	
	public static List<Card> optimizeDeck(List<Card> mainDeck, List<Card> sourceDeck, List<Card> mainBooster) {
		List<Card> mainCopyDeck = new ArrayList<>();
		List<Card> mainCopyBestDeck = new ArrayList<>();
		for (int i=0;i<mainDeck.size();i++) {
			mainCopyBestDeck.add(mainDeck.get(i));
		}
		int bestScoreDeck = scoreDeck(mainDeck, sourceDeck);
		for (int i=0;i<mainBooster.size();i++) {
			int bestPos = 0;
			int bestScore = bestScoreDeck;
			for (int j=0;j<mainDeck.size();j++) {
				mainCopyDeck = new ArrayList<>();
				for (int k=0;k<mainDeck.size();k++) {
					if (k!=j) {
						mainCopyDeck.add(mainDeck.get(k));
					} else {
						mainCopyDeck.add(mainBooster.get(i));
					}
				}
				//evaluate
				int newScore = scoreDeck(mainCopyDeck, sourceDeck);
				if (newScore > bestScore) {
					bestScore = newScore;
					bestPos = j;
				}
			}
			if (bestScore>bestScoreDeck) {
				bestScoreDeck = bestScore;
				mainDeck.set(bestPos,mainBooster.get(i));
			}
		}
		
		return mainDeck;
	}
	
	public static int scoreDeck(List<Card> mainDeck, List<Card> sourceDeck) {
		int score = 0;
		int nbPotentialBones = sourceDeck.size();
		//playable?
		List<Card> playableMainDeck = new ArrayList<>();
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (invocable(card, playableMainDeck, sourceDeck, nbPotentialBones)) {
				if (card.getLevel() == 0) { 
					nbPotentialBones++;
				} else if (card instanceof BeastCard && !((BeastCard) card).getCostType().equals("bone")) {
					nbPotentialBones++;
				}
				if (card.getEffects().stream().anyMatch(effect -> effect.getName().equals("rabbit_hole"))) {
					nbPotentialBones++;
				}
				if (card.getEffects().stream().anyMatch(effect -> effect.getName().equals("bee_within"))) {
					nbPotentialBones+= (card.getHpBase()+1)/2;
				}
				playableMainDeck.add(card);
			}
		}
		int malusNoPlayable = mainDeck.size() - playableMainDeck.size();
		
		//stats and effects bonus
		for (int i=0;i<playableMainDeck.size();i++) {
			Card card = playableMainDeck.get(i);
			score += 3*card.getAttackBase() + card.getHpBase();
			if (card.getAttackBase() == 0 && card.getEffects().stream().noneMatch(effect -> effect.getName().equals("fledgling"))) score -= 2;
			
			for (int j=0;j<card.getEffects().size();j++) {
				Effect effect = card.getEffects().get(j);
				int cost = effect.getCostStats();
				if (Effect.namesLevelEffects.contains(effect.getName())) {
					cost = cost * effect.getCostStats();
					if (card.getEffects().get(j).getName().equals("fledgling")) {
						cost = cost * effect.getCostStats();
					}
				}
				score += cost;
			}
		}
		for (int i=0;i<sourceDeck.size();i++) {
			Card card = sourceDeck.get(i);
			score += 3*card.getAttackBase() + card.getHpBase();
			
			for (int j=0;j<card.getEffects().size();j++) {
				Effect effect = card.getEffects().get(j);
				int cost = effect.getCostStats();
				if (Effect.namesLevelEffects.contains(effect.getName())) {
					cost = cost * effect.getCostStats();
				}
				score += cost;
			}
		}
		
		//malus couts
		int malusBlood = malusBloodCost(playableMainDeck);
		int malusBone = malusBoneCost(playableMainDeck,nbPotentialBones);
		int malusEnergyCost = malusEnergyCost(playableMainDeck);
		malusNoPlayable = malusNoPlayable * (1 + malusBlood + malusBone + malusEnergyCost);
		score -= malusBlood;
		score -= malusBone;
		score -= malusEnergyCost;
		score -= malusNoPlayable;
		
		//synergies
		score += synergieRabbitOrBee(playableMainDeck);
		
		return score;
	}
	
	private static boolean invocable(Card card, List<Card> mainDeck, List<Card> sourceDeck, int nbPotentialBones) {
		if (card.getLevel() == 0) return true;
		int pos = mainDeck.indexOf(card);
		for (int i=0;i<mainDeck.size();i++) {
			if (i!= pos) {
				if (card instanceof BeastCard) {
					if (((BeastCard) card).getCostType().equals("bone")) {
						return true;
					} else {
						if (card.getLevel() > 4) return false;
					}
				}
			}
		}
		return true;
	}

	private static int malusBloodCost(List<Card> mainDeck) {
		int levelMax = 1;
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card instanceof BeastCard && ((BeastCard) card).getCostType().equals("blood")) {
				levelMax = Math.max(levelMax, card.getLevel());
			}
		}
		Integer tapLevelToNumberCards[] = new Integer[levelMax];
		for (int i=0;i<levelMax;i++) {
			tapLevelToNumberCards[i] = 0;
		}
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card.getLevel()>0 && card instanceof BeastCard && ((BeastCard) card).getCostType().equals("blood")) {
				for (int j=card.getLevel()-1;j<levelMax;j++) {
				tapLevelToNumberCards[j]++;
				}
			}
		}
		int malus = 0;
		for (int i=0;i<levelMax;i++) {
			malus += Math.max(0, tapLevelToNumberCards[i]*(1+i)/(2+i) - tapLevelToNumberCards[i]);
		}
		return malus*malus;
	}
	
	private static int malusEnergyCost(List<Card> mainDeck) {
		int levelMax = 1;
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card instanceof RobotCard) {
				levelMax = Math.max(levelMax, card.getLevel());
			}
		}
		Integer tapLevelToNumberCards[] = new Integer[levelMax];
		for (int i=0;i<levelMax;i++) {
			tapLevelToNumberCards[i] = 0;
		}
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card instanceof RobotCard) {
				for (int j=card.getLevel()-1;j<levelMax;j++) {
				tapLevelToNumberCards[j]++;
				}
			}
		}
		if (levelMax == 1) return 0;
		int malus = 0;
		for (int i=1;i<levelMax;i++) {
			malus += Math.max(0, tapLevelToNumberCards[i]*i/(3+i) - tapLevelToNumberCards[i]);
		}
		return malus*malus;
	}
	
	
	private static int malusBoneCost(List<Card> mainDeck, int nbPotentialBones) {
		int marge = nbPotentialBones;
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card.getLevel()>1 && card instanceof BeastCard && ((BeastCard) card).getCostType().equals("bone")) {
				nbPotentialBones -= (card.getLevel()-1);
			}
		}
		if (marge<0) return marge*marge;
		return 0;
	}
	
	private static int synergieRabbitOrBee(List<Card> mainDeck) {
		int nbBloodCardsLevelSup2 = 0;
		int nbCardsWithRabbitEffect = 0;
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card instanceof BeastCard && ((BeastCard) card).getCostType().equals("blood") && card.getLevel()>1) {
				nbBloodCardsLevelSup2++;
			} else if (card.getEffects().stream().anyMatch(effect -> effect.getName().equals("rabbit_hole") || effect.getName().equals("bee_within"))) {
				nbCardsWithRabbitEffect++;
			}
		}
		return Math.min(nbBloodCardsLevelSup2, nbCardsWithRabbitEffect);
	}
			
}