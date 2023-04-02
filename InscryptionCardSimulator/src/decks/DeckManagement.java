package decks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cards.BeastCard;
import cards.Card;
import cards.RobotCard;
import cards.UndeadCard;
import cards.WizardCard;
import effects.Effect;

public class DeckManagement {
	
	public static List<Card> optimizeMainDeck(List<Card> mainDeck, List<Card> sourceDeck, List<Card> mainBooster,
			boolean greenWiz, boolean orangeWiz, boolean blueWiz) {
		List<Card> mainCopyDeck = new ArrayList<>();
		List<Card> mainCopyBestDeck = new ArrayList<>();
		for (int i=0;i<mainDeck.size();i++) {
			mainCopyBestDeck.add(mainDeck.get(i));
		}
		int bestScoreDeck = scoreDeck(mainDeck, sourceDeck);
		for (int i=0;i<mainBooster.size();i++) {
			int bestPos = 0;
			int bestScore = bestScoreDeck;
			boolean maybeRemplace = true;
			if (mainBooster.get(i) instanceof WizardCard) {
				WizardCard wizCard = (WizardCard)mainBooster.get(i);
				if (wizCard.getCostGreenMox()>0 && !greenWiz) {
					maybeRemplace = false;
				}
				if (wizCard.getCostOrangeMox()>0 && !orangeWiz) {
					maybeRemplace = false;
				}
				if (wizCard.getCostBlueMox()>0 && !blueWiz) {
					maybeRemplace = false;
				}
			}
			if (maybeRemplace) {
				boolean swithPreferedMageCard = false;
			for (int j=0;j<mainDeck.size();j++) {
				Card cardToRemplace = mainDeck.get(j);
				if (cardToRemplace instanceof WizardCard) {
					boolean shouldbeRemplace = false;
					if (((WizardCard) cardToRemplace).getCostGreenMox()>0 && !greenWiz) {
						shouldbeRemplace = true;
					}
					if (((WizardCard) cardToRemplace).getCostOrangeMox()>0 && !orangeWiz) {
						shouldbeRemplace = true;
					}
					if (((WizardCard) cardToRemplace).getCostBlueMox()>0 && !blueWiz) {
						shouldbeRemplace = true;
					}
					if (shouldbeRemplace && mainBooster.get(i) instanceof WizardCard) {
						swithPreferedMageCard = true;
						mainDeck.set(j,mainBooster.get(i));
						break;
					}
				}
				
				if (similarTypeAndCostCards(mainBooster.get(i), cardToRemplace)) {
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
				
				
			}
			if (!swithPreferedMageCard && bestScore>bestScoreDeck) {
				bestScoreDeck = bestScore;
				mainDeck.set(bestPos,mainBooster.get(i));
			}
			}
		}
		
		return mainDeck;
	}
	
	public static List<Card> optimizeSourceDeck(List<Card> mainDeck, List<Card> sourceDeck, List<Card> sourceBooster) {
		List<Card> sourceCopyDeck = new ArrayList<>();
		List<Card> sourceCopyBestDeck = new ArrayList<>();
		for (int i=0;i<sourceDeck.size();i++) {
			sourceCopyBestDeck.add(sourceDeck.get(i));
		}
		int bestScoreDeck = scoreDeck(mainDeck, sourceDeck);
		for (int i=0;i<sourceBooster.size();i++) {
			int bestPos = 0;
			int bestScore = bestScoreDeck;
			for (int j=0;j<sourceDeck.size();j++) {
				Card cardToRemplace = sourceDeck.get(j);
				if (similarTypeAndCostCards(sourceBooster.get(i), cardToRemplace)) {
					
				}
				sourceCopyDeck = new ArrayList<>();
				for (int k=0;k<sourceDeck.size();k++) {
					if (k!=j) {
						sourceCopyDeck.add(sourceDeck.get(k));
					} else {
						sourceCopyDeck.add(sourceBooster.get(i));
					}
				}
				//evaluate
				int newScore = scoreDeck(mainDeck, sourceCopyDeck);
				if (newScore > bestScore) {
					bestScore = newScore;
					bestPos = j;
				}
			}
			if (bestScore>bestScoreDeck) {
				bestScoreDeck = bestScore;
				sourceDeck.set(bestPos,sourceBooster.get(i));
			}
		}
		
		return sourceDeck;
	}

	private static boolean similarTypeAndCostCards(Card cardToAdd, Card cardToRemplace) {
		if  (cardToRemplace instanceof BeastCard && cardToAdd instanceof BeastCard
				&& ((BeastCard)cardToRemplace).getCostType().equals(((BeastCard)cardToAdd).getCostType())) {
			return true;
		}
		if  (cardToRemplace instanceof RobotCard && cardToAdd instanceof RobotCard) {
			return true;
		}
		if  (cardToRemplace instanceof UndeadCard && cardToAdd instanceof UndeadCard) {
			return true;
		}
		if  (cardToRemplace instanceof WizardCard && cardToAdd instanceof WizardCard) {
			boolean greencardToRemplace = ((WizardCard) cardToRemplace).getCostGreenMox()>0;
			boolean orangecardToRemplace = ((WizardCard) cardToRemplace).getCostOrangeMox()>0;
			boolean bluecardToRemplace = ((WizardCard) cardToRemplace).getCostBlueMox()>0;
			boolean greencardToToAdd = ((WizardCard) cardToAdd).getCostGreenMox()>0;
			boolean orangecardToToAdd = ((WizardCard) cardToAdd).getCostOrangeMox()>0;
			boolean bluecardToToAdd = ((WizardCard) cardToAdd).getCostBlueMox()>0;
			
			return (greencardToRemplace == greencardToToAdd && orangecardToRemplace == orangecardToToAdd
					&& bluecardToRemplace == bluecardToToAdd);
		}
		
		return false;
	}
	
	public static int scoreDeck(List<Card> mainDeck, List<Card> sourceDeck) {
		int score = 0;
		int nbPotentialBones = sourceDeck.size();
		int bloodCost = 0;
		int boneCost = 0;
		int energyCost = 0;
		int moxCost = 0;
		int greenmox = 0;
		int orangemox = 0;
		int bluemox = 0;
		int lvlMaxgreenmox = 0;
		int lvlMaxorangemox = 0;
		int lvlMaxbluemox = 0;
		int maxsumlevelmox = 0;
		
		for (int i=0; i<sourceDeck.size(); i++) {
			Card card = sourceDeck.get(i);
			Optional<Effect> green_gem = card.getEffects().stream().filter(effect -> effect.getName().equals("green_gem")).findFirst();
			Optional<Effect> orange_gem = card.getEffects().stream().filter(effect -> effect.getName().equals("orange_gem")).findFirst();
			Optional<Effect> blue_gem = card.getEffects().stream().filter(effect -> effect.getName().equals("blue_gem")).findFirst();
			int sumlevelmox = 0;
			if (green_gem.isPresent()) {
				greenmox += green_gem.get().getLevel();
				sumlevelmox += green_gem.get().getLevel();
				lvlMaxgreenmox = Math.max(lvlMaxgreenmox, green_gem.get().getLevel());
			}
			if (orange_gem.isPresent()) {
				orangemox += orange_gem.get().getLevel();
				sumlevelmox += orange_gem.get().getLevel();
				lvlMaxorangemox = Math.max(lvlMaxorangemox, orange_gem.get().getLevel());
			}
			if (blue_gem.isPresent()) {
				bluemox += blue_gem.get().getLevel();
				sumlevelmox += blue_gem.get().getLevel();
				lvlMaxbluemox = Math.max(lvlMaxbluemox, blue_gem.get().getLevel());
			}
			maxsumlevelmox = Math.max(maxsumlevelmox, sumlevelmox);
		}
		
		//playable?
		List<Card> playableMainDeck = new ArrayList<>();
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card.getLevel() > 0) {
				if (card instanceof BeastCard && !((BeastCard) card).getCostType().equals("bone")) {
					bloodCost ++;
				} else if (card instanceof BeastCard || card instanceof UndeadCard) {
					boneCost ++;
				} else if (card instanceof RobotCard) {
					energyCost ++;
				} else if (card instanceof WizardCard) {
					moxCost ++;
				}
			}
			
			if (invocable(card, playableMainDeck, sourceDeck, nbPotentialBones,lvlMaxgreenmox,lvlMaxorangemox,lvlMaxbluemox,maxsumlevelmox)) {
				
				int bonus_bones = 1;
				if (card.getEffects().stream().anyMatch(effect -> effect.getName().equals("rabbit_hole"))) {
					bonus_bones++;
				}
				if (card.getEffects().stream().anyMatch(effect -> effect.getName().equals("bee_within"))) {
					bonus_bones+= (card.getHpBase()+1)/2;
				}
				Optional<Effect> bone_kingEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
				Optional<Effect> scavenger = card.getEffects().stream().filter(effect -> effect.getName().equals("scavenger")).findFirst();
				Optional<Effect> bone_digger = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_digger")).findFirst();
				
				
				if (bone_kingEffect.isPresent()) {
					bonus_bones += 3*bone_kingEffect.get().getLevel();
				}
				if (scavenger.isPresent()) {
					bonus_bones += scavenger.get().getLevel() + card.getHp()/2;
				}
				if (bone_digger.isPresent()) {
					if (card.getEffects().stream().anyMatch(effect -> effect.getName().equals("brittle"))) {
						bonus_bones += bone_digger.get().getLevel();
					} else {
						bonus_bones += bone_digger.get().getLevel() + card.getHp()/2;
					}
				}
				
				if ((card instanceof BeastCard && !((BeastCard) card).getCostType().equals("bone")) || card instanceof UndeadCard) {
					nbPotentialBones+= Math.max(0, bonus_bones - card.getLevel());
				} else {
					nbPotentialBones+= bonus_bones;
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
					if (card.getEffects().get(j).getName().equals("fledgling") || card.getEffects().get(j).getName().equals("hoarder")) {
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
					cost = cost * effect.getLevel();
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
		
		int nbTypesCosts = 0;
		if (bloodCost > 0) {
			nbTypesCosts ++;
		}
		if (boneCost > 0) {
			nbTypesCosts ++;
		}
		if (energyCost > 0) {
			nbTypesCosts ++;
		}
		if (moxCost > 0) {
			nbTypesCosts ++;
		}
		
		//other synergies
		score += synergieRabbitOrBee(playableMainDeck);
		
		//malus couts
		int malusBlood = malusBloodCost(playableMainDeck);
		
		int malusBone = malusBoneCost(playableMainDeck,nbPotentialBones);
		
		int malusEnergyCost = malusEnergyCost(playableMainDeck);
		
		int malusGemCost = malusGemsCost(playableMainDeck, greenmox, orangemox, bluemox);
		
		malusNoPlayable = malusNoPlayable * (1 + malusBlood + malusBone + malusEnergyCost + malusGemCost);
		if (!doesDeckContainsAttack(playableMainDeck)) {
			malusNoPlayable += Math.max(0, score) + malusNoPlayable;
		}
		if (malusBlood>0) {
			malusBlood += Math.max(0, score/nbTypesCosts);
		}
		if (malusBone>0) {
			malusBone += Math.max(0, score/nbTypesCosts);
		}
		if (malusEnergyCost>0) {
			malusEnergyCost += Math.max(0, score/nbTypesCosts);
		}
		if (malusGemCost>0) {
			malusGemCost += Math.max(0, score/nbTypesCosts);
		}
		
		score -= malusBlood;
		score -= malusBone;
		score -= malusEnergyCost;
		score -= malusGemCost;
		score -= malusNoPlayable;
		
		
		
		return score;
	}
	
	private static boolean invocable(Card card, List<Card> mainDeck, List<Card> sourceDeck, int nbPotentialBones,
			int lvlMaxgreenmox, int lvlMaxorangemox, int lvlMaxbluemox, int maxSumLevelsMox) {
		if (card.getLevel() == 0) return true;
		if (card instanceof BeastCard) {
			if (((BeastCard) card).getCostType().equals("bone")) {
				if (card.getLevel() > nbPotentialBones)
				return true;
			} else {
				if (card.getLevel() > 4) return false;
			}
		}
		if (card instanceof RobotCard) {
			if (card.getLevel() > 6) return false;
		}
		if (card instanceof UndeadCard) {
			if (card.getLevel() > nbPotentialBones) return false;
		}
		if (card instanceof WizardCard) {
			if (card.getLevel() > 2+maxSumLevelsMox) return false;
			if (((WizardCard) card).getCostGreenMox() > 2+lvlMaxgreenmox) return false;
			if (((WizardCard) card).getCostOrangeMox() > 2+lvlMaxorangemox) return false;
			if (((WizardCard) card).getCostBlueMox() > 2+lvlMaxbluemox) return false;
			return true;
		}
		/*int pos = mainDeck.indexOf(card);
		for (int i=0;i<mainDeck.size();i++) {
			if (i!= pos) {
				if (card instanceof BeastCard) {
					if (((BeastCard) card).getCostType().equals("bone")) {
						if (card.getLevel() > nbPotentialBones)
						return true;
					} else {
						if (card.getLevel() > 4) return false;
					}
				}
				if (card instanceof RobotCard) {
					if (card.getLevel() > 6) return false;
				}
				if (card instanceof UndeadCard) {
					if (card.getLevel() > 6) return false;
				}
			}
		}*/
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
		if (levelMax == 1) return 0;
		Integer tapLevelToNumberCards[] = new Integer[levelMax];
		Integer tapLevelToNumberCardsBonus[] = new Integer[levelMax];
		for (int i=0;i<levelMax;i++) {
			tapLevelToNumberCards[i] = 0;
			tapLevelToNumberCardsBonus[i] = 0;
		}
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card.getLevel()>0 && card instanceof BeastCard && ((BeastCard) card).getCostType().equals("blood")) {
				Optional<Effect> bee = card.getEffects().stream().filter(effect -> effect.getName().equals("bee_within")).findFirst();
				Optional<Effect> rabbit_hole = card.getEffects().stream().filter(effect -> effect.getName().equals("rabbit_hole")).findFirst();
				for (int j=card.getLevel()-1;j<levelMax;j++) {
					tapLevelToNumberCards[j]++;
				}
				if (bee.isPresent()) {
					int ratio = card.getHpBase()/bee.get().getLevel();
					for (int j=0;j<levelMax;j++) {
						tapLevelToNumberCardsBonus[j] += 1 + ratio;
					}
				}
				if (rabbit_hole.isPresent()) {
					for (int j=card.getLevel()-1;j<levelMax;j++) {
						tapLevelToNumberCardsBonus[j] += (rabbit_hole.get().getLevel()+1)/2;
					}
				}
				
			}
		}
		int malus = 0;
		for (int i=0;i<levelMax;i++) {
			malus += Math.max(0, tapLevelToNumberCards[levelMax-1]*(1+i)/(2+i) - tapLevelToNumberCards[i] - tapLevelToNumberCardsBonus[i]);
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
				Optional<Effect> bone_king = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
				if (bone_king.isPresent()) {
					marge -= Math.max(0,card.getLevel()-1-3*bone_king.get().getLevel());
				} else {
					marge -= (card.getLevel()-1);
				}
			}
		}
		if (marge<0) return marge*marge;
		return 0;
	}
	
	private static int malusGemsCost(List<Card> mainDeck, int nbGreenGems, int nbOrangeGems, int nbBlueGems) {
		int greenmarge = nbGreenGems;
		int orangemarge = nbOrangeGems;
		int bluemarge = nbBlueGems;
		for (int i=0;i<mainDeck.size();i++) {
			Card card = mainDeck.get(i);
			if (card.getLevel()>0 && card instanceof WizardCard ) {
				//Optional<Effect> bone_king = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
				greenmarge -= ((WizardCard)card).getCostGreenMox();
				orangemarge -= ((WizardCard)card).getCostOrangeMox();
				bluemarge -= ((WizardCard)card).getCostBlueMox();
			}
		}
		greenmarge = Math.min(greenmarge, 0);
		orangemarge = Math.min(orangemarge, 0);
		bluemarge = Math.min(bluemarge, 0);
		int someMalusMarge = greenmarge + orangemarge + bluemarge;
		return someMalusMarge*someMalusMarge;
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
			Optional<Effect> trifurcated_strike = card.getEffects().stream().filter(effect -> effect.getName().equals("trifurcated_strike")).findFirst();
			if (bee.isPresent()) {
				bonus += (card.getHpBase()-1)/2;
			}
			if (burrower.isPresent()) {
				bonus += Math.max(0,(card.getHpBase()-card.getAttackBase())/2);
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
			if (trifurcated_strike.isPresent()) {
				bonus += 2*card.getAttackBase();
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
			Optional<Effect> corpse_eater = card.getEffects().stream().filter(effect -> effect.getName().equals("corpse_eater")).findFirst();
			Optional<Effect> fledgling = card.getEffects().stream().filter(effect -> effect.getName().equals("fledgling")).findFirst();
			if (fledgling.isPresent()) {
				malus += Math.max(0, fledgling.get().getLevel()-2*card.getHpBase());
			}
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
			if (corpse_eater.isPresent()) {
				if (card instanceof BeastCard && ((BeastCard)card).getCostType().equals("blood")) {
					int numerator = card.getAttackBase()*corpse_eater.get().getLevel();
					int denominator = corpse_eater.get().getLevel() * card.getLevel();
					int rest = numerator%denominator;
					if (rest >1) rest = 1;
					int deltaAttk = card.getAttackBase() - numerator/denominator - rest;
					numerator = card.getHpBase()*corpse_eater.get().getLevel();
					rest = numerator%denominator;
					if (rest >1) rest = 1;
					int deltaHp = card.getHpBase() - numerator/denominator - rest;
					malus += deltaHp + 3*deltaAttk;
				} else {
					int numerator = card.getAttackBase()*corpse_eater.get().getLevel();
					int denominator = corpse_eater.get().getLevel() * (1+card.getLevel()/2);
					int rest = numerator%denominator;
					if (rest >1) rest = 1;
					int deltaAttk = card.getAttackBase() - numerator/denominator - rest;
					numerator = card.getHpBase()*corpse_eater.get().getLevel();
					rest = numerator%denominator;
					if (rest >1) rest = 1;
					int deltaHp = card.getHpBase() - numerator/denominator - rest;
					malus += deltaHp + 3*deltaAttk;
				}
			}
		}
		return malus;
	}
	
	private static boolean doesDeckContainsAttack(List<Card> mainDeck) {
		for (int i=0;i<mainDeck.size();i++) {
			if (mainDeck.get(i).getAttack()>0 && mainDeck.get(i).getEffects().stream().noneMatch(effect -> effect.getName().equals("brittle"))) {
				return true;
			}
			if (mainDeck.get(i).getEffects().stream().anyMatch(effect -> effect.getName().equals("fledgling"))
					|| mainDeck.get(i).getEffects().stream().anyMatch(effect -> effect.getName().equals("gem_animator"))
					) {
				return true;
			}
		}
		return false;
	}
			
			
}
