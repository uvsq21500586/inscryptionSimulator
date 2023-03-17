package decks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cards.BeastCard;
import cards.Card;
import cards.RobotCard;
import cards.UndeadCard;
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
				Optional<Effect> bone_kingEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
				Optional<Effect> scavenger = card.getEffects().stream().filter(effect -> effect.getName().equals("scavenger")).findFirst();
				if (bone_kingEffect.isPresent()) {
					if (card instanceof BeastCard && !((BeastCard) card).getCostType().equals("bone")) {
						nbPotentialBones+= 1 + 3*bone_kingEffect.get().getLevel();
						if (scavenger.isPresent()) {
							nbPotentialBones+= 1+card.getHpBase()/2;
						}
					} else {
						if (scavenger.isPresent()) {
							nbPotentialBones+= Math.max(0, 2 + 3*bone_kingEffect.get().getLevel() + card.getHpBase()/2 - card.getLevel());
						} else {
							nbPotentialBones+= Math.max(0, 1 + 3*bone_kingEffect.get().getLevel() - card.getLevel());
						}
						
					}
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
					cost = cost * effect.getLevel();
					if (card.getEffects().get(j).getName().equals("fledgling")) {
						cost = cost + effect.getLevel();
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
					if (card.getEffects().get(j).getName().equals("fledgling")) {
						cost = cost + effect.getLevel();
					}
				}
				score += cost;
			}
		}
		int synergiestatseffects = synergieStatsEffects(playableMainDeck);
		int badstatseffects = badStatsEffects(playableMainDeck);
		score += synergiestatseffects;
		score -= badstatseffects;
		
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
			malus += Math.max(0, tapLevelToNumberCards[levelMax-1]*i/(2+i) - tapLevelToNumberCards[i]);
		}
		return malus*malus;
	}
	
	
	private static int malusBoneCost(List<Card> mainDeck, int nbPotentialBones) {
		int marge = nbPotentialBones;
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card.getLevel()>1 && ((card instanceof BeastCard && ((BeastCard) card).getCostType().equals("bone")) || card instanceof UndeadCard) ) {
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
	
	private static int synergieStatsEffects(List<Card> mainDeck) {
		int bonus = 0;
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			Optional<Effect> bee = card.getEffects().stream().filter(effect -> effect.getName().equals("bee_within")).findFirst();
			Optional<Effect> burrower = card.getEffects().stream().filter(effect -> effect.getName().equals("burrower")).findFirst();
			Optional<Effect> mighty_leap = card.getEffects().stream().filter(effect -> effect.getName().equals("mighty_leap")).findFirst();
			Optional<Effect> air = card.getEffects().stream().filter(effect -> effect.getName().equals("airborne")).findFirst();
			Optional<Effect> sharp_quills = card.getEffects().stream().filter(effect -> effect.getName().equals("sharp_quills")).findFirst();
			Optional<Effect> scavenger = card.getEffects().stream().filter(effect -> effect.getName().equals("scavenger")).findFirst();
			Optional<Effect> bifurcated_strike = card.getEffects().stream().filter(effect -> effect.getName().equals("bifurcated_strike")).findFirst();
			if (bee.isPresent()) {
				bonus += (card.getHpBase()-1)/2;
			}
			if (burrower.isPresent()) {
				bonus += Math.max(0,card.getHpBase()-card.getAttackBase());
			}
			if (burrower.isPresent() && mighty_leap.isPresent()) {
				bonus += Math.max(0,card.getHpBase()-card.getAttackBase());
			}
			if (air.isPresent()) {
				bonus += (card.getAttackBase()-1)/2;
			}
			if (sharp_quills.isPresent()) {
				bonus += (card.getHpBase()-1+sharp_quills.get().getLevel())/2;
			}
			if (burrower.isPresent() && sharp_quills.isPresent()) {
				bonus += Math.max(0,card.getHpBase()-card.getAttackBase());
			}
			if (scavenger.isPresent() && bifurcated_strike.isPresent() && !air.isPresent()) {
				bonus += scavenger.get().getLevel() + card.getAttackBase();
			}
			if (bifurcated_strike.isPresent()) {
				bonus += card.getAttackBase();
			}
		}
		return bonus;
	}
	
	private static int badStatsEffects(List<Card> mainDeck) {
		int malus = 0;
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			//Optional<Effect> bee = card.getEffects().stream().filter(effect -> effect.getName().equals("bee_within")).findFirst();
			Optional<Effect> burrower = card.getEffects().stream().filter(effect -> effect.getName().equals("burrower")).findFirst();
			Optional<Effect> guardian = card.getEffects().stream().filter(effect -> effect.getName().equals("guardian")).findFirst();
			Optional<Effect> sprinter = card.getEffects().stream().filter(effect -> effect.getName().equals("sprinter_right")).findFirst();
			Optional<Effect> hefty = card.getEffects().stream().filter(effect -> effect.getName().equals("hefty_right")).findFirst();
			Optional<Effect> air = card.getEffects().stream().filter(effect -> effect.getName().equals("airborne")).findFirst();
			Optional<Effect> scavenger = card.getEffects().stream().filter(effect -> effect.getName().equals("scavenger")).findFirst();
			if (burrower.isPresent() && card.getHpBase() == 1) {
				malus += card.getAttack();
			}
			if (burrower.isPresent() && guardian.isPresent()) {
				malus ++;
			}
			if (sprinter.isPresent() && hefty.isPresent()) {
				malus ++;
			}
			if (scavenger.isPresent() && air.isPresent()) {
				malus += 2*scavenger.get().getLevel();
			}
		}
		return malus;
	}
			
}
