package cards;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.JLabel;

import effects.Effect;
import frames.Duel;
import frames.DuelControler;
import frames.duelbuttons.ButtonPlaceCard;
import frames.duelbuttons.CardPanel;

public abstract class Card {
	String type;
	String appearance;
	Integer hpBase;
	Integer hp;
	Integer attack;
	Integer attackBase;
	List<Effect> effects;
	boolean mainDeck;
	protected Integer level;
	Integer poisoned;
	Integer rarity;
	boolean sacrificiable;
	
	public Card(String type, String appearance, Integer level, int hpBase, int attackBase, List<Effect> effects, int rarity, boolean mainDeck) {
		this.type = type;
		this.appearance = appearance;
		this.level = level;
		this.hpBase = hpBase;
		this.hp = hpBase;
		this.attackBase = attackBase;
		this.attack = attackBase;
		this.effects = effects;
		this.mainDeck = mainDeck;
		this.rarity = rarity;
		poisoned = 0;
		sacrificiable = true;
	}
	
	public Card(String type, String appearance, Integer level, int hpBase, int attackBase, List<Effect> effects, boolean mainDeck) {
		this.type = type;
		this.appearance = appearance;
		this.level = level;
		this.hpBase = hpBase;
		this.hp = hpBase;
		this.attackBase = attackBase;
		this.attack = attackBase;
		this.effects = effects;
		this.mainDeck = mainDeck;
		poisoned = 0;
		this.rarity = 0;
		sacrificiable = true;
	}
	//return new Card(card.type, card.appearance, card.level, card.hpBase, card.attackBase, card.effects, card.mainDeck);
	
	public abstract String toString();
	
	public abstract boolean playable(ButtonPlaceCard placesCards[], Integer bonePile);
	
	public void attackPlayer2(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position, DuelControler controler) throws IOException, FontFormatException {
		if (effects.stream().anyMatch(effect -> effect.getName().equals("bifurcated_strike"))) {
			if (position>0) {
				attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+3, controler);
			}
			if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position<3) {
				attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+5, controler);
			}
			if (effects.stream().anyMatch(effect -> effect.getName().equals("bifurcated_strike"))){
				if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position>0) {
					attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+3, controler);
				}
				if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this)) {
					attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+4, controler);
				}
				if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position<3) {
					attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+5, controler);
				}
			}
		} else if (effects.stream().anyMatch(effect -> effect.getName().equals("bifurcated_strike"))){
			if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position>0) {
				attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+3, controler);
			}
			if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this)) {
				attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+4, controler);
			}
			if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position<3) {
				attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+5, controler);
			}
		} else {
			attackPlayer2WithTarget(duel, buttonPlaceCard, position, position+4, controler);
		}
	}
	
	public void attackPlayer2WithTarget(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position, int positionAdv, DuelControler controler) throws IOException, FontFormatException {
		CardPanel cardBurrowerPanel = cardBurrowerAdv(buttonPlaceCard);
		CardPanel cardBurrowerMightyLeapPanel = cardBurrowerMightyLeapAdv(buttonPlaceCard);
		if (buttonPlaceCard[positionAdv].getCardPanel() == null && cardBurrowerPanel != null && !effects.stream().anyMatch(effect -> effect.getName().equals("airborne"))) {
			//attaque directe contrée
			int pos = cardBurrowerPanel.getFieldPosition();
			buttonPlaceCard[pos].setCardPanel(null);
			buttonPlaceCard[positionAdv].setCardPanel(cardBurrowerPanel);
			cardBurrowerPanel.setFieldPosition(positionAdv);
			cardBurrowerPanel.setBounds(buttonPlaceCard[positionAdv].getX(),buttonPlaceCard[positionAdv].getY(),
					buttonPlaceCard[positionAdv].getWidth(), buttonPlaceCard[positionAdv].getHeight());
		} else if (buttonPlaceCard[positionAdv].getCardPanel() == null && effects.stream().anyMatch(effect -> effect.getName().equals("airborne"))
				&& cardBurrowerMightyLeapPanel != null) {
			int pos = cardBurrowerMightyLeapPanel.getFieldPosition();
			buttonPlaceCard[pos].setCardPanel(null);
			buttonPlaceCard[positionAdv].setCardPanel(cardBurrowerMightyLeapPanel);
			cardBurrowerMightyLeapPanel.setFieldPosition(positionAdv);
			cardBurrowerMightyLeapPanel.setBounds(buttonPlaceCard[positionAdv].getX(),buttonPlaceCard[positionAdv].getY(),
					buttonPlaceCard[positionAdv].getWidth(), buttonPlaceCard[positionAdv].getHeight());
		}
		if (buttonPlaceCard[positionAdv].getCardPanel() == null || (
				effects.stream().anyMatch(effect -> effect.getName().equals("airborne")) && buttonPlaceCard[positionAdv].getCardPanel().getCard().getEffects().stream().noneMatch(effect -> effect.getName().equals("mighty_leap"))
				)) {
			//attaque directe
			duel.setBalance(duel.getBalance() + attack);
			duel.getBalanceLabel().setText("Balance: " + duel.getBalance());
		} else {
			//attaque une autre carte
			Card advCard = buttonPlaceCard[positionAdv].getCardPanel().getCard();
			//loose_tail
			Optional<Effect> loose_tail_rightEffect = advCard.getEffects().stream().filter(effect -> effect.getName().equals("loose_tail_right")).findFirst();
			Optional<Effect> loose_tail_leftEffect = advCard.getEffects().stream().filter(effect -> effect.getName().equals("loose_tail_left")).findFirst();
			if (loose_tail_rightEffect.isPresent()) {
				advCard.loosing_tail(duel, buttonPlaceCard, positionAdv, true, loose_tail_rightEffect.get(), controler);
			} else if (loose_tail_leftEffect.isPresent()) {
				advCard.loosing_tail(duel, buttonPlaceCard, positionAdv, false, loose_tail_leftEffect.get(), controler);
			}
			advCard = buttonPlaceCard[positionAdv].getCardPanel().getCard();
			advCard.isattackedByPlayer1(duel, buttonPlaceCard, position, this);
			advCard.setHp(advCard.getHp() - attack);
			buttonPlaceCard[positionAdv].getCardPanel().getHp().setText(advCard.getHp().toString());
			
			//sharp_quills
			Optional<Effect> sharp_quillsEffect = advCard.getEffects().stream().filter(effect -> effect.getName().equals("sharp_quills")).findFirst();
			Optional<Effect> unkillableEffectAttacker = effects.stream().filter(effect -> effect.getName().equals("unkillable")).findFirst();
			if (sharp_quillsEffect.isPresent()) {
				hp = hp - sharp_quillsEffect.get().getLevel();
				if (hp <= 0) {
					if (unkillableEffectAttacker.isPresent()) {
						attackBase = attackBase + (unkillableEffectAttacker.get().getLevel()-1)/3;
						hpBase = hpBase + (unkillableEffectAttacker.get().getLevel()-1)/3;
						int lvlSup = (unkillableEffectAttacker.get().getLevel()-1)%3;
						if (lvlSup == 1) {
							hpBase++;
						}
						if (lvlSup == 2) {
							attackBase++;
						}
						buttonPlaceCard[position].getCardPanel().setPosition("onHand");
						buttonPlaceCard[position].getCardPanel().getAttack().setText(attackBase.toString());
						buttonPlaceCard[position].getCardPanel().getHp().setText(hpBase.toString());
						duel.getHandCard1().add(buttonPlaceCard[position].getCardPanel());
						duel.getPanel().remove(buttonPlaceCard[position].getCardPanel());
						buttonPlaceCard[position].setCardPanel(null);
						Optional<Effect> bone_kingEffect = effects.stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
						if (bone_kingEffect.isPresent()) {
							duel.setBoneP1(duel.getBoneP1()+ 1 + 3*bone_kingEffect.get().getLevel());
						} else {
							duel.setBoneP1(duel.getBoneP1()+1);
						}
					} else {
						//carte morte
						deadCard(duel, buttonPlaceCard, position);
						
					}
					Optional<Effect> ruby_heart = effects.stream().filter(effect -> effect.getName().equals("ruby_heart")).findFirst();
					Optional<Effect> emerald_heart = effects.stream().filter(effect -> effect.getName().equals("emerald_heart")).findFirst();
					if (ruby_heart.isPresent() || emerald_heart.isPresent()) {
						CardPanel moxPanel = null;
						List<Effect> newmoxEffect = new ArrayList<>();
						if (ruby_heart.isPresent() && emerald_heart.isPresent()) {
							newmoxEffect.add(new Effect("green_gem","wizard",1));
							newmoxEffect.add(new Effect("orange_gem","wizard",1));
							moxPanel = new CardPanel(new WizardCard("goranjs_mox", 0, 0, 0, 0, 0, emerald_heart.get().getLevel() + ruby_heart.get().getLevel(), 0, newmoxEffect, false));
						} else if (ruby_heart.isPresent()) {
							newmoxEffect.add(new Effect("orange_gem","wizard",1));
							moxPanel = new CardPanel(new WizardCard("orange_mox", 0, 0, 0, 0, 0, ruby_heart.get().getLevel(), 0, newmoxEffect, false));
						} else {
							newmoxEffect.add(new Effect("green_gem","wizard",1));
							moxPanel = new CardPanel(new WizardCard("green_gem", 0, 0, 0, 0, 0, emerald_heart.get().getLevel(), 0, newmoxEffect, false));
						}
						duel.getPanel().add(moxPanel,0);
						moxPanel.setBounds(100 + 200 * (position%4), 310, 200, 300);
						moxPanel.addMouseListener(controler);
						buttonPlaceCard[position].setCardPanel(moxPanel);
						moxPanel.setPosition("onField");
						moxPanel.setFieldPosition(position);
						moxPanel.addMouseListener(controler);
						duel.recalculateAttk(moxPanel.getCard(), position);
					} else {
						corpse_eaterEffectP1(duel, buttonPlaceCard, controler, position);
					}
					
				} else {
					buttonPlaceCard[position].getCardPanel().getHp().setText(hp.toString());
				}
			}
			
			
			if (advCard.getHp()<=0) {
				Optional<Effect> unkillableEffect = advCard.getEffects().stream().filter(effect -> effect.getName().equals("unkillable")).findFirst();
				if (unkillableEffect.isPresent()) {
					advCard.setAttackBase(advCard.getAttackBase() + (unkillableEffect.get().getLevel()-1)/3);
					advCard.setHpBase(advCard.getHpBase() + (unkillableEffect.get().getLevel()-1)/3);
					int lvlSup = (unkillableEffect.get().getLevel()-1)%3;
					if (lvlSup == 1) {
						advCard.setHpBase(advCard.getHpBase() + 1);
					}
					if (lvlSup == 2) {
						advCard.setAttackBase(advCard.getAttackBase() + 1);
					}
					advCard.setAttack(advCard.getAttackBase());
					advCard.setHp(advCard.getHpBase());
					buttonPlaceCard[positionAdv].getCardPanel().setPosition("onHand");
					buttonPlaceCard[positionAdv].getCardPanel().getAttack().setText(advCard.getAttackBase().toString());
					buttonPlaceCard[positionAdv].getCardPanel().getHp().setText(advCard.getHpBase().toString());
					duel.getHandCard2().add(buttonPlaceCard[positionAdv].getCardPanel());
					duel.getPanel().remove(buttonPlaceCard[positionAdv].getCardPanel());
					buttonPlaceCard[positionAdv].setCardPanel(null);
					Optional<Effect> bone_kingEffect =advCard.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
					if (bone_kingEffect.isPresent()) {
						duel.setBoneP2(duel.getBoneP2()+ 1 + 3*bone_kingEffect.get().getLevel());
					} else {
						duel.setBoneP2(duel.getBoneP2()+1);
					}
					
				} else {
					//carte morte
					advCard.deadCard(duel, buttonPlaceCard, positionAdv);
				}
				
				int scavengerlevelMax = duel.scavengerLevel();
				duel.setBoneP1(duel.getBoneP1()+scavengerlevelMax);
				
				Optional<Effect> ruby_heart_adv = advCard.getEffects().stream().filter(effect -> effect.getName().equals("ruby_heart")).findFirst();
				Optional<Effect> emerald_heart_adv = advCard.getEffects().stream().filter(effect -> effect.getName().equals("emerald_heart")).findFirst();
				if (ruby_heart_adv.isPresent() || emerald_heart_adv.isPresent()) {
					CardPanel moxPanel = null;
					List<Effect> newmoxEffect = new ArrayList<>();
					if (ruby_heart_adv.isPresent() && emerald_heart_adv.isPresent()) {
						newmoxEffect.add(new Effect("green_gem","wizard",1));
						newmoxEffect.add(new Effect("orange_gem","wizard",1));
						moxPanel = new CardPanel(new WizardCard("goranjs_mox", 0, 0, 0, 0, 0, emerald_heart_adv.get().getLevel() + ruby_heart_adv.get().getLevel(), 0, newmoxEffect, false));
					} else if (ruby_heart_adv.isPresent()) {
						newmoxEffect.add(new Effect("orange_gem","wizard",1));
						moxPanel = new CardPanel(new WizardCard("orange_mox", 0, 0, 0, 0, 0, ruby_heart_adv.get().getLevel(), 0, newmoxEffect, false));
					} else {
						newmoxEffect.add(new Effect("green_gem","wizard",1));
						moxPanel = new CardPanel(new WizardCard("green_gem", 0, 0, 0, 0, 0, emerald_heart_adv.get().getLevel(), 0, newmoxEffect, false));
					}
					duel.getPanel().add(moxPanel,0);
					moxPanel.setBounds(100 + 200 * (positionAdv%4), 10, 200, 300);
					moxPanel.addMouseListener(controler);
					buttonPlaceCard[positionAdv].setCardPanel(moxPanel);
					moxPanel.setPosition("onField");
					moxPanel.setFieldPosition(positionAdv);
					moxPanel.addMouseListener(controler);
					duel.recalculateAttk(moxPanel.getCard(), positionAdv);
				} else {
					advCard.corpse_eaterEffectP2(duel, buttonPlaceCard, controler, positionAdv);
				}
			} else {
				if (effects.stream().anyMatch(effect -> effect.getName().equals("poison"))) {
					Effect effectpoison = effects.stream().filter(effect -> effect.getName().equals("poison")).findFirst().get();
					advCard.setPoisoned(effectpoison.getLevel());
					buttonPlaceCard[positionAdv].getCardPanel().getPoison().setVisible(true);
				}
			}
		}
	}

	private CardPanel cardBurrowerAdv(ButtonPlaceCard[] buttonPlaceCard) {
		// TODO Auto-generated method stub
		if (buttonPlaceCard[4].getCardPanel() != null && buttonPlaceCard[4].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("burrower"))) {
			return buttonPlaceCard[4].getCardPanel();
		}
		if (buttonPlaceCard[5].getCardPanel() != null && buttonPlaceCard[5].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("burrower"))) {
			return buttonPlaceCard[5].getCardPanel();
		}
		if (buttonPlaceCard[6].getCardPanel() != null && buttonPlaceCard[6].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("burrower"))) {
			return buttonPlaceCard[6].getCardPanel();
		}
		if (buttonPlaceCard[7].getCardPanel() != null && buttonPlaceCard[7].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("burrower"))) {
			return buttonPlaceCard[7].getCardPanel();
		}
		return null;
	}
	
	private CardPanel cardBurrowerMightyLeapAdv(ButtonPlaceCard[] buttonPlaceCard) {
		// TODO Auto-generated method stub
		if (buttonPlaceCard[4].getCardPanel() != null && buttonPlaceCard[4].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("burrower"))
				&& buttonPlaceCard[4].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("mighty_leap"))) {
			return buttonPlaceCard[4].getCardPanel();
		}
		if (buttonPlaceCard[5].getCardPanel() != null && buttonPlaceCard[5].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("burrower"))
				&& buttonPlaceCard[5].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("mighty_leap"))) {
			return buttonPlaceCard[5].getCardPanel();
		}
		if (buttonPlaceCard[6].getCardPanel() != null && buttonPlaceCard[6].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("burrower"))
				&& buttonPlaceCard[6].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("mighty_leap"))) {
			return buttonPlaceCard[6].getCardPanel();
		}
		if (buttonPlaceCard[7].getCardPanel() != null && buttonPlaceCard[7].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("burrower"))
				&& buttonPlaceCard[7].getCardPanel().getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("mighty_leap"))) {
			return buttonPlaceCard[7].getCardPanel();
		}
		return null;
	}


	public void deadCard(Duel duel, ButtonPlaceCard[] buttonPlaceCard, int position) {
		boolean detonator = effects.stream().anyMatch(effect -> effect.getName().equals("detonator"));
		
		
		Optional<Effect> gem_animator = effects.stream().filter(effect -> effect.getName().equals("gem_animator")).findFirst();
		if (gem_animator.isPresent()) {
			if (position<4) {
				for (int i=0;i<4;i++) {
					if (i !=position && buttonPlaceCard[i].getCardPanel() != null) {
						Card card = buttonPlaceCard[i].getCardPanel().getCard();
						if (card instanceof WizardCard && !card.isSacrificiable()) {
							card.setAttack(card.getAttack()-gem_animator.get().getLevel());
							buttonPlaceCard[i].getCardPanel().getAttack().setText(card.getAttack().toString());
						}
					}
				}
				
			} else {
				for (int i=4;i<8;i++) {
					if (i !=position && buttonPlaceCard[i].getCardPanel() != null) {
						Card card = buttonPlaceCard[i].getCardPanel().getCard();
						if (card instanceof WizardCard && !card.isSacrificiable()) {
							card.setAttack(card.getAttack()-gem_animator.get().getLevel());
							buttonPlaceCard[i].getCardPanel().getAttack().setText(card.getAttack().toString());
						}
					}
				}
			}
		}
		duel.getPanel().remove(buttonPlaceCard[position].getCardPanel());
		buttonPlaceCard[position].setCardPanel(null);
		if (detonator) {
			if (position<4) {
				if (buttonPlaceCard[position+4].getCardPanel() != null) {
					Card advCard = buttonPlaceCard[position+4].getCardPanel().getCard();
					advCard.setHp(advCard.getHp() - 10*hpBase);
					if (advCard.getHp()<=0) {
						advCard.deadCard(duel, buttonPlaceCard, position+4);
					}
				}
				if (position>0 && buttonPlaceCard[position-1].getCardPanel() != null) {
					Card allieCard = buttonPlaceCard[position-1].getCardPanel().getCard();
					allieCard.setHp(allieCard.getHp() - 10*hpBase);
					if (allieCard.getHp()<=0) {
						allieCard.deadCard(duel, buttonPlaceCard, position-1);
					}
				}
				if (position<3 && buttonPlaceCard[position+1].getCardPanel() != null) {
					Card allieCard = buttonPlaceCard[position+1].getCardPanel().getCard();
					allieCard.setHp(allieCard.getHp() - 10*hpBase);
					if (allieCard.getHp()<=0) {
						allieCard.deadCard(duel, buttonPlaceCard, position+1);
					}
				}
			} else {
				if (buttonPlaceCard[position-4].getCardPanel() != null) {
					Card advCard = buttonPlaceCard[position-4].getCardPanel().getCard();
					advCard.setHp(advCard.getHp() - 10*hpBase);
					if (advCard.getHp()<=0) {
						advCard.deadCard(duel, buttonPlaceCard, position-4);
					};
				}
				if (position>4 && buttonPlaceCard[position-1].getCardPanel() != null) {
					Card allieCard = buttonPlaceCard[position-1].getCardPanel().getCard();
					allieCard.setHp(allieCard.getHp() - 10*hpBase);
					if (allieCard.getHp()<=0) {
						allieCard.deadCard(duel, buttonPlaceCard, position-1);
					}
				}
				if (position<7 && buttonPlaceCard[position+1].getCardPanel() != null) {
					Card allieCard = buttonPlaceCard[position+1].getCardPanel().getCard();
					allieCard.setHp(allieCard.getHp() - 10*hpBase);
					if (allieCard.getHp()<=0) {
						allieCard.deadCard(duel, buttonPlaceCard, position+1);
					}
				}
			}
		}
		Optional<Effect> bone_kingEffect = effects.stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
		if ((!duel.isTurnJ2() && position<4) || (duel.isTurnJ2() && position>=4)) {
			if (bone_kingEffect.isPresent()) {
				duel.setBoneP1(duel.getBoneP1()+ 1 + 3*bone_kingEffect.get().getLevel());
			} else {
				duel.setBoneP1(duel.getBoneP1()+1);
			}
		} else {
			if (bone_kingEffect.isPresent()) {
				duel.setBoneP2(duel.getBoneP2()+ 1 + 3*bone_kingEffect.get().getLevel());
			} else {
				duel.setBoneP2(duel.getBoneP2()+1);
			}
		}
		
	}
	
	public void attackPlayer1(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position, DuelControler controler) throws IOException, FontFormatException {
		if (effects.stream().anyMatch(effect -> effect.getName().equals("bifurcated_strike"))) {
			if (position>0) {
				attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+3, controler);
			}
			if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position<3) {
				attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+5, controler);
			}
			if (effects.stream().anyMatch(effect -> effect.getName().equals("trifurcated_strike"))){
				if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position>0) {
					attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+3, controler);
				}
				if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this)) {
					attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+4, controler);
				}
				if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position<3) {
					attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+5, controler);
				}
			}
		} else if (effects.stream().anyMatch(effect -> effect.getName().equals("trifurcated_strike"))){
			if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position>0) {
				attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+3, controler);
			}
			if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this)) {
				attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+4, controler);
			}
			if (buttonPlaceCard[position] != null && buttonPlaceCard[position].getCardPanel().getCard().equals(this) && position<3) {
				attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+5, controler);
			}
		} else {
			attackPlayer1WithTarget(duel, buttonPlaceCard, position, position+4, controler);
		}
	}
	
	public void attackPlayer1WithTarget(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position, int positionAdv, DuelControler controler) throws IOException, FontFormatException {
		CardPanel cardBurrowerPanel = cardBurrowerAdv(buttonPlaceCard);
		CardPanel cardBurrowerMightyLeapPanel = cardBurrowerMightyLeapAdv(buttonPlaceCard);
		if (buttonPlaceCard[positionAdv].getCardPanel() == null && cardBurrowerPanel != null && !effects.stream().anyMatch(effect -> effect.getName().equals("airborne"))) {
			//attaque directe contrée
			int pos = cardBurrowerPanel.getFieldPosition();
			buttonPlaceCard[pos].setCardPanel(null);
			buttonPlaceCard[positionAdv].setCardPanel(cardBurrowerPanel);
			cardBurrowerPanel.setFieldPosition(positionAdv);
			cardBurrowerPanel.setBounds(buttonPlaceCard[positionAdv].getX(),buttonPlaceCard[positionAdv].getY(),
					buttonPlaceCard[positionAdv].getWidth(), buttonPlaceCard[positionAdv].getHeight());
		} else if (buttonPlaceCard[positionAdv].getCardPanel() == null && effects.stream().anyMatch(effect -> effect.getName().equals("airborne"))
				&& cardBurrowerMightyLeapPanel != null) {
			int pos = cardBurrowerMightyLeapPanel.getFieldPosition();
			buttonPlaceCard[pos].setCardPanel(null);
			buttonPlaceCard[positionAdv].setCardPanel(cardBurrowerMightyLeapPanel);
			cardBurrowerMightyLeapPanel.setFieldPosition(positionAdv);
			cardBurrowerMightyLeapPanel.setBounds(buttonPlaceCard[positionAdv].getX(),buttonPlaceCard[positionAdv].getY(),
					buttonPlaceCard[positionAdv].getWidth(), buttonPlaceCard[positionAdv].getHeight());
		}
		if (buttonPlaceCard[positionAdv].getCardPanel() == null || (
				effects.stream().anyMatch(effect -> effect.getName().equals("airborne")) && buttonPlaceCard[positionAdv].getCardPanel().getCard().getEffects().stream().noneMatch(effect -> effect.getName().equals("mighty_leap"))
				)) {
			duel.setBalance(duel.getBalance() - attack);
			duel.getBalanceLabel().setText("Balance: " + duel.getBalance());
		} else {
			Card advCard = buttonPlaceCard[positionAdv].getCardPanel().getCard();
			//loose_tail
			Optional<Effect> loose_tail_rightEffect = advCard.getEffects().stream().filter(effect -> effect.getName().equals("loose_tail_right")).findFirst();
			Optional<Effect> loose_tail_leftEffect = advCard.getEffects().stream().filter(effect -> effect.getName().equals("loose_tail_left")).findFirst();
			if (loose_tail_rightEffect.isPresent()) {
				advCard.loosing_tail(duel, buttonPlaceCard, positionAdv, true, loose_tail_rightEffect.get(), controler);
			} else if (loose_tail_leftEffect.isPresent()) {
				advCard.loosing_tail(duel, buttonPlaceCard, positionAdv, false, loose_tail_leftEffect.get(), controler);
			}
			advCard = buttonPlaceCard[positionAdv].getCardPanel().getCard();
			advCard.isattackedByPlayer2(duel, buttonPlaceCard, position, this);
			advCard.setHp(advCard.getHp() - attack);
			buttonPlaceCard[positionAdv].getCardPanel().getHp().setText(advCard.getHp().toString());
			
			//sharp_quills
			Optional<Effect> sharp_quillsEffect = advCard.getEffects().stream().filter(effect -> effect.getName().equals("sharp_quills")).findFirst();
			Optional<Effect> unkillableEffectAttacker = effects.stream().filter(effect -> effect.getName().equals("unkillable")).findFirst();
			if (sharp_quillsEffect.isPresent()) {
				hp = hp - sharp_quillsEffect.get().getLevel();
				if (hp <= 0) {
					if (unkillableEffectAttacker.isPresent()) {
						attackBase = attackBase + (unkillableEffectAttacker.get().getLevel()-1)/3;
						hpBase = hpBase + (unkillableEffectAttacker.get().getLevel()-1)/3;
						int lvlSup = (unkillableEffectAttacker.get().getLevel()-1)%3;
						if (lvlSup == 1) {
							hpBase++;
						}
						if (lvlSup == 2) {
							attackBase++;
						}
						buttonPlaceCard[position].getCardPanel().setPosition("onHand");
						buttonPlaceCard[position].getCardPanel().getAttack().setText(attackBase.toString());
						buttonPlaceCard[position].getCardPanel().getHp().setText(hpBase.toString());
						duel.getHandCard2().add(buttonPlaceCard[position].getCardPanel());
						duel.getPanel().remove(buttonPlaceCard[position].getCardPanel());
						buttonPlaceCard[position].setCardPanel(null);
						Optional<Effect> bone_kingEffect = effects.stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
						if (bone_kingEffect.isPresent()) {
							duel.setBoneP2(duel.getBoneP2()+ 1 + 3*bone_kingEffect.get().getLevel());
						} else {
							duel.setBoneP2(duel.getBoneP2()+1);
						}
						
					} else {
						//carte morte
						deadCard(duel, buttonPlaceCard, position);
					}
					Optional<Effect> ruby_heart = effects.stream().filter(effect -> effect.getName().equals("ruby_heart")).findFirst();
					Optional<Effect> emerald_heart = effects.stream().filter(effect -> effect.getName().equals("emerald_heart")).findFirst();
					if (ruby_heart.isPresent() || emerald_heart.isPresent()) {
						CardPanel moxPanel = null;
						List<Effect> newmoxEffect = new ArrayList<>();
						if (ruby_heart.isPresent() && emerald_heart.isPresent()) {
							newmoxEffect.add(new Effect("green_gem","wizard",1));
							newmoxEffect.add(new Effect("orange_gem","wizard",1));
							moxPanel = new CardPanel(new WizardCard("goranjs_mox", 0, 0, 0, 0, 0, emerald_heart.get().getLevel() + ruby_heart.get().getLevel(), 0, newmoxEffect, false));
						} else if (ruby_heart.isPresent()) {
							newmoxEffect.add(new Effect("orange_gem","wizard",1));
							moxPanel = new CardPanel(new WizardCard("orange_mox", 0, 0, 0, 0, 0, ruby_heart.get().getLevel(), 0, newmoxEffect, false));
						} else {
							newmoxEffect.add(new Effect("green_gem","wizard",1));
							moxPanel = new CardPanel(new WizardCard("green_gem", 0, 0, 0, 0, 0, emerald_heart.get().getLevel(), 0, newmoxEffect, false));
						}
						duel.getPanel().add(moxPanel,0);
						moxPanel.setBounds(100 + 200 * (position%4), 310, 200, 300);
						moxPanel.addMouseListener(controler);
						buttonPlaceCard[position].setCardPanel(moxPanel);
						moxPanel.setPosition("onField");
						moxPanel.setFieldPosition(position);
						moxPanel.addMouseListener(controler);
						duel.recalculateAttk(moxPanel.getCard(), position);
					} else {
						corpse_eaterEffectP2(duel, buttonPlaceCard, controler, position);
					}
					
				} else {
					buttonPlaceCard[position].getCardPanel().getHp().setText(hp.toString());
				}
			}
			
			if (advCard.getHp()<=0) {
				Optional<Effect> unkillableEffect = advCard.getEffects().stream().filter(effect -> effect.getName().equals("unkillable")).findFirst();
				if (unkillableEffect.isPresent()) {
					advCard.setAttackBase(advCard.getAttackBase() + (unkillableEffect.get().getLevel()-1)/3);
					advCard.setHpBase(advCard.getHpBase() + (unkillableEffect.get().getLevel()-1)/3);
					int lvlSup = (unkillableEffect.get().getLevel()-1)%3;
					if (lvlSup == 1) {
						advCard.setHpBase(advCard.getHpBase() + 1);
					}
					if (lvlSup == 2) {
						advCard.setAttackBase(advCard.getAttackBase() + 1);
					}
					advCard.setAttack(advCard.getAttackBase());
					advCard.setHp(advCard.getHpBase());
					buttonPlaceCard[positionAdv].getCardPanel().setPosition("onHand");
					buttonPlaceCard[positionAdv].getCardPanel().getAttack().setText(advCard.getAttackBase().toString());
					buttonPlaceCard[positionAdv].getCardPanel().getHp().setText(advCard.getHpBase().toString());
					duel.getHandCard1().add(buttonPlaceCard[positionAdv].getCardPanel());
					duel.getPanel().remove(buttonPlaceCard[positionAdv].getCardPanel());
					buttonPlaceCard[positionAdv].setCardPanel(null);
					Optional<Effect> bone_kingEffect =advCard.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
					if (bone_kingEffect.isPresent()) {
						duel.setBoneP1(duel.getBoneP1()+ 1 + 3*bone_kingEffect.get().getLevel());
					} else {
						duel.setBoneP1(duel.getBoneP1()+1);
					}
					
				} else {
					//carte morte
					advCard.deadCard(duel, buttonPlaceCard, positionAdv);
				}
				int scavengerlevelMax = duel.scavengerLevel();
				duel.setBoneP2(duel.getBoneP2()+scavengerlevelMax);
				
				Optional<Effect> ruby_heart_adv = advCard.getEffects().stream().filter(effect -> effect.getName().equals("ruby_heart")).findFirst();
				Optional<Effect> emerald_heart_adv = advCard.getEffects().stream().filter(effect -> effect.getName().equals("emerald_heart")).findFirst();
				if (ruby_heart_adv.isPresent() || emerald_heart_adv.isPresent()) {
					CardPanel moxPanel = null;
					List<Effect> newmoxEffect = new ArrayList<>();
					if (ruby_heart_adv.isPresent() && emerald_heart_adv.isPresent()) {
						newmoxEffect.add(new Effect("green_gem","wizard",1));
						newmoxEffect.add(new Effect("orange_gem","wizard",1));
						moxPanel = new CardPanel(new WizardCard("goranjs_mox", 0, 0, 0, 0, 0, emerald_heart_adv.get().getLevel() + ruby_heart_adv.get().getLevel(), 0, newmoxEffect, false));
					} else if (ruby_heart_adv.isPresent()) {
						newmoxEffect.add(new Effect("orange_gem","wizard",1));
						moxPanel = new CardPanel(new WizardCard("orange_mox", 0, 0, 0, 0, 0, ruby_heart_adv.get().getLevel(), 0, newmoxEffect, false));
					} else {
						newmoxEffect.add(new Effect("green_gem","wizard",1));
						moxPanel = new CardPanel(new WizardCard("green_gem", 0, 0, 0, 0, 0, emerald_heart_adv.get().getLevel(), 0, newmoxEffect, false));
					}
					duel.getPanel().add(moxPanel,0);
					moxPanel.setBounds(100 + 200 * (positionAdv%4), 10, 200, 300);
					moxPanel.addMouseListener(controler);
					buttonPlaceCard[positionAdv].setCardPanel(moxPanel);
					moxPanel.setPosition("onField");
					moxPanel.setFieldPosition(positionAdv);
					moxPanel.addMouseListener(controler);
					duel.recalculateAttk(moxPanel.getCard(), positionAdv);
				} else {
					advCard.corpse_eaterEffectP1(duel, buttonPlaceCard, controler, positionAdv);
				}
			} else {
				if (effects.stream().anyMatch(effect -> effect.getName().equals("poison"))) {
					Effect effectpoison = effects.stream().filter(effect -> effect.getName().equals("poison")).findFirst().get();
					advCard.setPoisoned(effectpoison.getLevel());
					buttonPlaceCard[positionAdv].getCardPanel().getPoison().setVisible(true);
				}
			}
		}
	}
	
	public void placeCard(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position) throws IOException, FontFormatException {
		Optional<Effect> rabbitEffect = effects.stream().filter(effect -> effect.getName().equals("rabbit_hole")).findAny();
		if (rabbitEffect.isPresent()) {
			for (int i=0;i<(1+rabbitEffect.get().getLevel())/2;i++) {
				BeastCard rabbitCard = new BeastCard("rabbit", "blood", 0, 1+rabbitEffect.get().getLevel()/2, 0, new ArrayList<>(), mainDeck);
				if (duel.isTurnJ2()) {
					duel.getHandCard2().add(new CardPanel(rabbitCard));
				} else {
					duel.getHandCard1().add(new CardPanel(rabbitCard));
				}
			}
			
		}
		Optional<Effect> damEffect = effects.stream().filter(effect -> effect.getName().equals("dambuilder")).findAny();
		if (damEffect.isPresent()) {
			if (position>0 && buttonPlaceCard[position-1].getCardPanel() == null) {
				BeastCard damCard = new BeastCard("dam", "blood", 0, damEffect.get().getLevel()*2, 0, new ArrayList<>(), mainDeck);
				damCard.setSacrificiable(false);
				CardPanel damPanel = new CardPanel(damCard);
				damPanel.setPosition("on Field");
				damPanel.setFieldPosition(position-1);
				buttonPlaceCard[position-1].setCardPanel(damPanel);
				duel.getPanel().add(damPanel,0);
				damPanel.setBounds(100 + 200 * ((position-1)%4), 310, 200, 300);
			}
			if (position<3 && buttonPlaceCard[position+1].getCardPanel() == null) {
				BeastCard damCard = new BeastCard("dam", "blood", 0, damEffect.get().getLevel()*2, 0, new ArrayList<>(), mainDeck);
				damCard.setSacrificiable(false);
				CardPanel damPanel = new CardPanel(damCard);
				damPanel.setPosition("on Field");
				damPanel.setFieldPosition(position+1);
				buttonPlaceCard[position+1].setCardPanel(damPanel);
				duel.getPanel().add(damPanel,1);
				damPanel.setBounds(100 + 200 * ((position+1)%4), 310, 200, 300);
			}
		}
		if (buttonPlaceCard[position + 4].getCardPanel() == null) {
			//guardian?
			for (int i=4;i<8;i++) {
				if (i-4 != position) {
					CardPanel cardPanelAdv = buttonPlaceCard[i].getCardPanel();
					if (cardPanelAdv != null && cardPanelAdv.getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("guardian"))) {
						cardPanelAdv.setBounds(100 + 200 * position, 10, 200, 300);
						cardPanelAdv.setFieldPosition(position+4);
						buttonPlaceCard[i].setCardPanel(null);
						buttonPlaceCard[position+4].setCardPanel(cardPanelAdv);
						break;
					}
				}
			}
		}
	}
	
	public void isattackedByPlayer2(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position, Card advcard) throws IOException, FontFormatException {
		Optional<Effect> beeEffect = effects.stream().filter(effect -> effect.getName().equals("bee_within")).findAny();
		if (beeEffect.isPresent()) {
			int bonus = (beeEffect.get().getLevel()-1)%3;
			BeastCard beeCard = new BeastCard("bee", "blood", 0, 1 + (bonus-1)/3, 1 + (bonus-1)/3, Arrays.asList(new Effect("airborne", "beast")), mainDeck);
			if (bonus == 1) {
				beeCard.setHpBase(beeCard.getHpBase() + 1);
				beeCard.setHp(beeCard.getHpBase());
			}
			if (bonus == 2) {
				beeCard.setAttackBase(beeCard.getAttackBase() + 1);
				beeCard.setAttack(beeCard.getAttackBase());
			}
			duel.getHandCard1().add(new CardPanel(beeCard));
		}
	}
	
	public void isattackedByPlayer1(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position, Card advcard) throws IOException, FontFormatException {
		Optional<Effect> beeEffect = effects.stream().filter(effect -> effect.getName().equals("bee_within")).findAny();
		if (beeEffect.isPresent()) {
			int bonus = (beeEffect.get().getLevel()-1)%3;
			BeastCard beeCard = new BeastCard("bee", "blood", 0, 1 + (bonus-1)/3, 1 + (bonus-1)/3, Arrays.asList(new Effect("airborne", "beast")), mainDeck);
			if (bonus == 1) {
				beeCard.setHpBase(beeCard.getHpBase() + 1);
				beeCard.setHp(beeCard.getHpBase());
			}
			if (bonus == 2) {
				beeCard.setAttackBase(beeCard.getAttackBase() + 1);
				beeCard.setAttack(beeCard.getAttackBase());
			}
			duel.getHandCard2().add(new CardPanel(beeCard));
		}
	}
	
	public void deplacement(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position) throws IOException, FontFormatException {
		Optional<Effect> sprinterrighteffect = effects.stream().filter(effect -> effect.getName().equals("sprinter_right")).findAny();
		Optional<Effect> sprinterlefteffect = effects.stream().filter(effect -> effect.getName().equals("sprinter_left")).findAny();
		Optional<Effect> heftylefteffect = effects.stream().filter(effect -> effect.getName().equals("hefty_left")).findAny();
		Optional<Effect> heftyrighteffect = effects.stream().filter(effect -> effect.getName().equals("hefty_right")).findAny();
		if (sprinterrighteffect.isPresent()) {
			if (position<3 && buttonPlaceCard[position+1].getCardPanel() == null) {
				CardPanel cardpanel = buttonPlaceCard[position].getCardPanel();
				cardpanel.setFieldPosition(position+1);
				buttonPlaceCard[position].setCardPanel(null);
				buttonPlaceCard[position+1].setCardPanel(cardpanel);
				return;
			} else {
				if (position>0 && buttonPlaceCard[position-1].getCardPanel() == null) {
					int idEffect = effects.indexOf(sprinterrighteffect.get());
					JLabel effectLabel = buttonPlaceCard[position].getCardPanel().getEffects()[idEffect];
					
					sprinterrighteffect.get().inverseDirection(effectLabel, this);
					
					CardPanel cardpanel = buttonPlaceCard[position].getCardPanel();
					cardpanel.setFieldPosition(position-1);
					buttonPlaceCard[position].setCardPanel(null);
					buttonPlaceCard[position-1].setCardPanel(cardpanel);
					return;
				}
			}
		}
		
		if (sprinterlefteffect.isPresent()) {
			if (position>0 && buttonPlaceCard[position-1].getCardPanel() == null) {
				CardPanel cardpanel = buttonPlaceCard[position].getCardPanel();
				cardpanel.setFieldPosition(position-1);
				buttonPlaceCard[position].setCardPanel(null);
				buttonPlaceCard[position-1].setCardPanel(cardpanel);
				return;
			} else {
				if (position<3 && buttonPlaceCard[position+1].getCardPanel() == null) {
					int idEffect = effects.indexOf(sprinterlefteffect.get());
					JLabel effectLabel = buttonPlaceCard[position].getCardPanel().getEffects()[idEffect];
					
					sprinterlefteffect.get().inverseDirection(effectLabel, this);
					
					CardPanel cardpanel = buttonPlaceCard[position].getCardPanel();
					cardpanel.setFieldPosition(position+1);
					buttonPlaceCard[position].setCardPanel(null);
					buttonPlaceCard[position+1].setCardPanel(cardpanel);
					return;
				}
			}
		}
		
		if (heftyrighteffect.isPresent()) {
			if (position<3) {
				Integer nearestRightGap = null;
				for (int i=3;i>position;i--) {
					if (buttonPlaceCard[i].getCardPanel() == null) {
						nearestRightGap = i;
					}
				}
				if (nearestRightGap != null) {
					//trou à droite
					for (int i=nearestRightGap-1;i>=position;i--) {
						//déplacer
						CardPanel cardPanToMove = buttonPlaceCard[i].getCardPanel();
						cardPanToMove.setBounds(100 + 200 * ((i+1)%4), 310, 200, 300);
						cardPanToMove.setFieldPosition(cardPanToMove.getFieldPosition()+1);
						buttonPlaceCard[i+1].setCardPanel(cardPanToMove);
					}
					buttonPlaceCard[position].setCardPanel(null);
					return;
				}	
			}
			if (position>0) {
				Integer nearestLeftGap = null;
				for (int i=0;i<position;i++) {
					if (buttonPlaceCard[i].getCardPanel() == null) {
						nearestLeftGap = i;
					}
				}
				if (nearestLeftGap != null) {
					//trou à gauche
					int idEffect = effects.indexOf(heftyrighteffect.get());
					JLabel effectLabel = buttonPlaceCard[position].getCardPanel().getEffects()[idEffect];
					heftyrighteffect.get().inverseDirection(effectLabel, this);
					for (int i=nearestLeftGap+1;i<=position;i++) {
						//déplacer
						CardPanel cardPanToMove = buttonPlaceCard[i].getCardPanel();
						cardPanToMove.setBounds(100 + 200 * ((i-1)%4), 310, 200, 300);
						cardPanToMove.setFieldPosition(cardPanToMove.getFieldPosition()-1);
						buttonPlaceCard[i-1].setCardPanel(cardPanToMove);
					}
					buttonPlaceCard[position].setCardPanel(null);
					return;
				}
			}
		}
		if (heftylefteffect.isPresent()) {
			if (position>0) {
				Integer nearestLeftGap = null;
				for (int i=0;i<position;i++) {
					if (buttonPlaceCard[i].getCardPanel() == null) {
						nearestLeftGap = i;
					}
				}
				if (nearestLeftGap != null) {
					//trou à gauche
					for (int i=nearestLeftGap+1;i<=position;i++) {
						//déplacer
						CardPanel cardPanToMove = buttonPlaceCard[i].getCardPanel();
						cardPanToMove.setBounds(100 + 200 * ((i-1)%4), 310, 200, 300);
						cardPanToMove.setFieldPosition(cardPanToMove.getFieldPosition()-1);
						buttonPlaceCard[i-1].setCardPanel(cardPanToMove);
					}
					buttonPlaceCard[position].setCardPanel(null);
					return;
				}
			}
			if (position<3) {
				Integer nearestRightGap = null;
				for (int i=3;i>position;i--) {
					if (buttonPlaceCard[i].getCardPanel() == null) {
						nearestRightGap = i;
					}
				}
				if (nearestRightGap != null) {
					//trou à droite
					int idEffect = effects.indexOf(heftylefteffect.get());
					JLabel effectLabel = buttonPlaceCard[position].getCardPanel().getEffects()[idEffect];
					heftylefteffect.get().inverseDirection(effectLabel, this);
					for (int i=nearestRightGap-1;i>=position;i--) {
						//déplacer
						CardPanel cardPanToMove = buttonPlaceCard[i].getCardPanel();
						cardPanToMove.setBounds(100 + 200 * ((i+1)%4), 310, 200, 300);
						cardPanToMove.setFieldPosition(cardPanToMove.getFieldPosition()+1);
						buttonPlaceCard[i+1].setCardPanel(cardPanToMove);
					}
					buttonPlaceCard[position].setCardPanel(null);
					return;
				}	
			}
			
		}
	}
	
	public void loosing_tail(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position, boolean isRight, Effect effect, DuelControler controler) throws IOException, FontFormatException {
		CardPanel cardPanel = buttonPlaceCard[position].getCardPanel();
		if (isRight) {
			if (position<7 && buttonPlaceCard[position+1].getCardPanel() == null) {
				cardPanel.setFieldPosition(position+1);
				cardPanel.setBounds(100 + 200 * ((position+1)%4), 10, 200, 300);
				CardPanel tailPanel = new CardPanel(new BeastCard("tail", "blood", 0, 2, 0, new ArrayList<>(), true));
				duel.add(tailPanel,0);
				tailPanel.setPosition("onField");
				tailPanel.setFieldPosition(position);		
				tailPanel.addMouseListener(controler);
				tailPanel.setBounds(100 + 200 * ((position)%4), 10, 200, 300);
				buttonPlaceCard[position].setCardPanel(tailPanel);
				buttonPlaceCard[position+1].setCardPanel(cardPanel);
				if (effect.getLevel()>1) {
					effect.setLevel(effect.getLevel()-1);
				} else {
					effects.remove(effect);
				}
				
			} else if (position>4 && buttonPlaceCard[position-1].getCardPanel() == null) {
				//turn effect
				int id = cardPanel.getCard().getEffects().indexOf(effect);
				effect.inverseDirection(cardPanel.getEffects()[id], cardPanel.getCard());
				cardPanel.setFieldPosition(position-1);
				cardPanel.setBounds(100 + 200 * ((position-1)%4), 10, 200, 300);
				CardPanel tailPanel = new CardPanel(new BeastCard("tail", "blood", 0, 2, 0, new ArrayList<>(), true));
				duel.add(tailPanel,0);
				tailPanel.setPosition("onField");
				tailPanel.setFieldPosition(position);		
				tailPanel.addMouseListener(controler);
				tailPanel.setBounds(100 + 200 * ((position)%4), 10, 200, 300);
				buttonPlaceCard[position].setCardPanel(tailPanel);
				buttonPlaceCard[position-1].setCardPanel(cardPanel);
				if (effect.getLevel()>1) {
					effect.setLevel(effect.getLevel()-1);
				} else {
					effects.remove(effect);
				}
			}
		} else {
			if (position>4 && buttonPlaceCard[position-1].getCardPanel() == null) {
				cardPanel.setFieldPosition(position-1);
				cardPanel.setBounds(100 + 200 * ((position-1)%4), 10, 200, 300);
				CardPanel tailPanel = new CardPanel(new BeastCard("tail", "blood", 0, 2, 0, new ArrayList<>(), true));
				duel.add(tailPanel,0);
				tailPanel.setPosition("onField");
				tailPanel.setFieldPosition(position);		
				tailPanel.addMouseListener(controler);
				tailPanel.setBounds(100 + 200 * ((position)%4), 10, 200, 300);
				buttonPlaceCard[position].setCardPanel(tailPanel);
				buttonPlaceCard[position-1].setCardPanel(cardPanel);
				if (effect.getLevel()>1) {
					effect.setLevel(effect.getLevel()-1);
				} else {
					effects.remove(effect);
				}
			} else if (position<7 && buttonPlaceCard[position+1].getCardPanel() == null) {
				//turn effect
				int id = cardPanel.getCard().getEffects().indexOf(effect);
				effect.inverseDirection(cardPanel.getEffects()[id], cardPanel.getCard());
				cardPanel.setFieldPosition(position+1);
				cardPanel.setBounds(100 + 200 * ((position+1)%4), 10, 200, 300);
				CardPanel tailPanel = new CardPanel(new BeastCard("tail", "blood", 0, 2, 0, new ArrayList<>(), true));
				duel.add(tailPanel,0);
				tailPanel.setPosition("onField");
				tailPanel.setFieldPosition(position);		
				tailPanel.addMouseListener(controler);
				tailPanel.setBounds(100 + 200 * ((position)%4), 10, 200, 300);
				buttonPlaceCard[position].setCardPanel(tailPanel);
				buttonPlaceCard[position+1].setCardPanel(cardPanel);
				if (effect.getLevel()>1) {
					effect.setLevel(effect.getLevel()-1);
				} else {
					effects.remove(effect);
				}
			}
		}
		cardPanel.redrawEffects();
	}
	
	public void corpse_eaterEffectP1(Duel duel, ButtonPlaceCard buttonPlaceCard[], DuelControler controler, int position) {
		if (getEffects().stream().anyMatch(effect -> effect.getName().equals("corpse_eater"))) {
			return;
		}
		for (int i=0;i<duel.getHandCard1().size();i++) {
			Card card = duel.getHandCard1().get(i).getCard();
			Optional<Effect> corpse_eaterEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("corpse_eater")).findFirst();
			if (corpse_eaterEffect.isPresent()) {
				CardPanel cardpanel =  duel.getHandCard1().get(i);
				cardpanel.setPosition("onField");
				cardpanel.setFieldPosition(position);
				cardpanel.addMouseListener(controler);
				buttonPlaceCard[position].setCardPanel(cardpanel);
				cardpanel.setBounds(100+200*position%4,310-300*position/4,200,300);
				duel.getPanel().add(cardpanel,0);
				if (card instanceof BeastCard && ((BeastCard)card).getCostType().equals("blood")) {
					int numerator = card.getAttackBase()*corpse_eaterEffect.get().getLevel();
					int denominator = corpse_eaterEffect.get().getLevel() * card.getLevel();
					int rest = numerator%denominator;
					if (rest >1) rest = 1;
					card.setAttack(numerator/denominator + rest);
					numerator = card.getHpBase()*corpse_eaterEffect.get().getLevel();
					rest = numerator%denominator;
					if (rest >1) rest = 1;
					card.setHp(numerator/denominator + rest);
				} else {
					int numerator = card.getAttackBase()*corpse_eaterEffect.get().getLevel();
					int denominator = corpse_eaterEffect.get().getLevel() * (1+card.getLevel()/2);
					int rest = numerator%denominator;
					if (rest >1) rest = 1;
					card.setAttack(numerator/denominator + rest);
					numerator = card.getHpBase()*corpse_eaterEffect.get().getLevel();
					rest = numerator%denominator;
					if (rest >1) rest = 1;
					card.setHp(numerator/denominator + rest);
				}
				cardpanel.getAttack().setText(card.getAttack().toString());
				cardpanel.getHp().setText(card.getHp().toString());
				duel.getHandCard1().remove(i);
				break;
			}
		}
	}
	
	public void corpse_eaterEffectP2(Duel duel, ButtonPlaceCard buttonPlaceCard[], DuelControler controler, int position) {
		if (getEffects().stream().anyMatch(effect -> effect.getName().equals("corpse_eater"))) {
			return;
		}
		for (int i=0;i<duel.getHandCard2().size();i++) {
			Card card = duel.getHandCard2().get(i).getCard();
			Optional<Effect> corpse_eaterEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("corpse_eater")).findFirst();
			if (corpse_eaterEffect.isPresent()) {
				CardPanel cardpanel =  duel.getHandCard2().get(i);
				cardpanel.setPosition("onField");
				cardpanel.setFieldPosition(position);
				cardpanel.addMouseListener(controler);
				buttonPlaceCard[position].setCardPanel(cardpanel);
				cardpanel.setBounds(100+200*position%4,310-300*position/4,200,300);
				duel.getPanel().add(cardpanel);
				if (card instanceof BeastCard && ((BeastCard)card).getCostType().equals("blood")) {
					int numerator = card.getAttackBase()*corpse_eaterEffect.get().getLevel();
					int denominator = corpse_eaterEffect.get().getLevel() * card.getLevel();
					int rest = numerator%denominator;
					if (rest >1) rest = 1;
					card.setAttack(numerator/denominator + rest);
					numerator = card.getHpBase()*corpse_eaterEffect.get().getLevel();
					rest = numerator%denominator;
					if (rest >1) rest = 1;
					card.setHp(numerator/denominator + rest);
				} else {
					int numerator = card.getAttackBase()*corpse_eaterEffect.get().getLevel();
					int denominator = corpse_eaterEffect.get().getLevel() * (1+card.getLevel()/2);
					int rest = numerator%denominator;
					if (rest >1) rest = 1;
					card.setAttack(numerator/denominator + rest);
					numerator = card.getHpBase()*corpse_eaterEffect.get().getLevel();
					rest = numerator%denominator;
					if (rest >1) rest = 1;
					card.setHp(numerator/denominator + rest);
				}
				cardpanel.getAttack().setText(card.getAttack().toString());
				cardpanel.getHp().setText(card.getHp().toString());
				duel.getHandCard2().remove(i);
				break;
			}
		}
	}
	
	public void familiarP1(Duel duel, ButtonPlaceCard buttonPlaceCard[], DuelControler controler, int position) {
		boolean isAlone = true;
		if (position<4) {
			for (int i=0;i<4;i++) {
				if (i != position && buttonPlaceCard[i].getCardPanel() != null && buttonPlaceCard[i].getCardPanel().getCard().isSacrificiable()) {
					isAlone = false;
				}
			}
			
		} else {
			for (int i=4;i<8;i++) {
				if (i != position && buttonPlaceCard[i].getCardPanel() != null && buttonPlaceCard[i].getCardPanel().getCard().isSacrificiable()) {
					isAlone = false;
				}
			}
		}
		if (isAlone) {
			//death
			deadCard(duel, buttonPlaceCard, position);
			duel.setBoneP1(duel.getBoneP1()+1);
			corpse_eaterEffectP1(duel, buttonPlaceCard, controler, position);
		}
	}
	
	public void familiarP2(Duel duel, ButtonPlaceCard buttonPlaceCard[], DuelControler controler, int position) {
		boolean isAlone = true;
		if (position<4) {
			for (int i=0;i<4;i++) {
				if (i != position && buttonPlaceCard[i].getCardPanel() != null && buttonPlaceCard[i].getCardPanel().getCard().isSacrificiable()) {
					isAlone = false;
				}
			}
			
		} else {
			for (int i=4;i<8;i++) {
				if (i != position && buttonPlaceCard[i].getCardPanel() != null && buttonPlaceCard[i].getCardPanel().getCard().isSacrificiable()) {
					isAlone = false;
				}
			}
		}
		if (isAlone) {
			//death
			deadCard(duel, buttonPlaceCard, position);
			duel.setBoneP2(duel.getBoneP2()+1);
			corpse_eaterEffectP2(duel, buttonPlaceCard, controler, position);
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppearance() {
		return appearance;
	}

	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getAttack() {
		return attack;
	}

	public void setAttack(Integer attack) {
		this.attack = attack;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getHpBase() {
		return hpBase;
	}

	public void setHpBase(Integer hpBase) {
		this.hpBase = hpBase;
	}

	public Integer getAttackBase() {
		return attackBase;
	}

	public void setAttackBase(Integer attackBase) {
		this.attackBase = attackBase;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}

	public Integer getPoisoned() {
		return poisoned;
	}

	public void setPoisoned(Integer poisoned) {
		this.poisoned = poisoned;
	}

	public boolean isSacrificiable() {
		return sacrificiable;
	}

	public void setSacrificiable(boolean sacrificiable) {
		this.sacrificiable = sacrificiable;
	}

	public Integer getRarity() {
		return rarity;
	}

	public void setRarity(Integer rarity) {
		this.rarity = rarity;
	}

	public boolean isMainDeck() {
		return mainDeck;
	}

	public void setMainDeck(boolean mainDeck) {
		this.mainDeck = mainDeck;
	}

	

	
	
	
	
	
	
}
