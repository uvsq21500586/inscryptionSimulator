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
		sacrificiable = true;
	}
	//return new Card(card.type, card.appearance, card.level, card.hpBase, card.attackBase, card.effects, card.mainDeck);
	
	public abstract String toString();
	
	public abstract boolean playable(ButtonPlaceCard placesCards[], Integer bonePile);
	
	public void attackPlayer2(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position) throws IOException, FontFormatException {
		CardPanel cardBurrowerPanel = cardBurrowerAdv(buttonPlaceCard);
		if (buttonPlaceCard[position+4].getCardPanel() == null && cardBurrowerPanel != null) {
			//attaque directe contr�e
			int pos = cardBurrowerPanel.getFieldPosition();
			buttonPlaceCard[pos].setCardPanel(null);
			buttonPlaceCard[position+4].setCardPanel(cardBurrowerPanel);
			cardBurrowerPanel.setFieldPosition(position+4);
			cardBurrowerPanel.setBounds(buttonPlaceCard[position+4].getX(),buttonPlaceCard[position+4].getY(),
					buttonPlaceCard[position+4].getWidth(), buttonPlaceCard[position+4].getHeight());
		}
		if (buttonPlaceCard[position+4].getCardPanel() == null || effects.stream().anyMatch(effect -> effect.getName().equals("airborne"))) {
			//attaque directe
			duel.setBalance(duel.getBalance() + attack);
			duel.getBalanceLabel().setText("Balance: " + duel.getBalance());
		} else {
			//attaque une autre carte
			Card advCard = buttonPlaceCard[position+4].getCardPanel().getCard();
			advCard.isattackedByPlayer1(duel, buttonPlaceCard, position, this);
			advCard.setHp(advCard.getHp() - attack);
			buttonPlaceCard[position+4].getCardPanel().getHp().setText(advCard.getHp().toString());
			
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
					} else {
						//carte morte
						deadCard(duel, buttonPlaceCard, position);
						duel.setBoneP1(duel.getBoneP1()+1);
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
					buttonPlaceCard[position+4].getCardPanel().setPosition("onHand");
					buttonPlaceCard[position+4].getCardPanel().getAttack().setText(advCard.getAttackBase().toString());
					buttonPlaceCard[position+4].getCardPanel().getHp().setText(advCard.getHpBase().toString());
					duel.getHandCard2().add(buttonPlaceCard[position+4].getCardPanel());
					duel.getPanel().remove(buttonPlaceCard[position+4].getCardPanel());
					buttonPlaceCard[position+4].setCardPanel(null);
					
				} else {
					//carte morte
					deadCard(duel, buttonPlaceCard, position+4);
					duel.setBoneP2(duel.getBoneP2()+1);
				}
			} else {
				if (effects.stream().anyMatch(effect -> effect.getName().equals("poison"))) {
					Effect effectpoison = effects.stream().filter(effect -> effect.getName().equals("poison")).findFirst().get();
					advCard.setPoisoned(effectpoison.getLevel());
					buttonPlaceCard[position+4].getCardPanel().getPoison().setVisible(true);
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

	public void deadCard(Duel duel, ButtonPlaceCard[] buttonPlaceCard, int position) {
		duel.getPanel().remove(buttonPlaceCard[position].getCardPanel());
		buttonPlaceCard[position].setCardPanel(null);
	}
	
	public void attackPlayer1(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position) throws IOException, FontFormatException {
		CardPanel cardBurrowerPanel = cardBurrowerAdv(buttonPlaceCard);
		if (buttonPlaceCard[position+4].getCardPanel() == null && cardBurrowerPanel != null && !effects.stream().anyMatch(effect -> effect.getName().equals("airborne"))) {
			//attaque directe contr�e
			int pos = cardBurrowerPanel.getFieldPosition();
			buttonPlaceCard[pos].setCardPanel(null);
			buttonPlaceCard[position+4].setCardPanel(cardBurrowerPanel);
			cardBurrowerPanel.setFieldPosition(position+4);
			cardBurrowerPanel.setBounds(buttonPlaceCard[position+4].getX(),buttonPlaceCard[position+4].getY(),
					buttonPlaceCard[position+4].getWidth(), buttonPlaceCard[position+4].getHeight());
		}
		if (buttonPlaceCard[position+4].getCardPanel() == null || effects.stream().anyMatch(effect -> effect.getName().equals("airborne"))) {
			duel.setBalance(duel.getBalance() - attack);
			duel.getBalanceLabel().setText("Balance: " + duel.getBalance());
		} else {
			Card advCard = buttonPlaceCard[position+4].getCardPanel().getCard();
			advCard.isattackedByPlayer2(duel, buttonPlaceCard, position, this);
			advCard.setHp(advCard.getHp() - attack);
			buttonPlaceCard[position+4].getCardPanel().getHp().setText(advCard.getHp().toString());
			
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
					} else {
						//carte morte
						deadCard(duel, buttonPlaceCard, position);
						duel.setBoneP2(duel.getBoneP1()+1);
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
					buttonPlaceCard[position+4].getCardPanel().setPosition("onHand");
					buttonPlaceCard[position+4].getCardPanel().getAttack().setText(advCard.getAttackBase().toString());
					buttonPlaceCard[position+4].getCardPanel().getHp().setText(advCard.getHpBase().toString());
					duel.getHandCard1().add(buttonPlaceCard[position+4].getCardPanel());
					duel.getPanel().remove(buttonPlaceCard[position+4].getCardPanel());
					buttonPlaceCard[position+4].setCardPanel(null);
					
				} else {
					//carte morte
					deadCard(duel, buttonPlaceCard, position+4);
					duel.setBoneP1(duel.getBoneP1()+1);
				}
			}
		}
	}
	
	public void placeCard(Duel duel, ButtonPlaceCard buttonPlaceCard[], int position) throws IOException, FontFormatException {
		Optional<Effect> rabbitEffect = effects.stream().filter(effect -> effect.getName().equals("rabbit_hole")).findAny();
		if (rabbitEffect.isPresent()) {
			BeastCard rabbitCard = new BeastCard("rabbit", "blood", 0, rabbitEffect.get().getLevel(), 0, new ArrayList<>(), mainDeck);
			if (duel.isTurnJ2()) {
				duel.getHandCard2().add(new CardPanel(rabbitCard));
			} else {
				duel.getHandCard1().add(new CardPanel(rabbitCard));
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
					int idEffect = effects.indexOf(sprinterrighteffect.get());
					JLabel effectLabel = buttonPlaceCard[position].getCardPanel().getEffects()[idEffect];
					
					sprinterrighteffect.get().inverseDirection(effectLabel, this);
					
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
					//trou � droite
					for (int i=nearestRightGap-1;i>=position;i--) {
						//d�placer
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
					//trou � gauche
					int idEffect = effects.indexOf(heftyrighteffect.get());
					JLabel effectLabel = buttonPlaceCard[position].getCardPanel().getEffects()[idEffect];
					heftyrighteffect.get().inverseDirection(effectLabel, this);
					for (int i=nearestLeftGap+1;i<=position;i++) {
						//d�placer
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
					//trou � gauche
					for (int i=nearestLeftGap+1;i<=position;i++) {
						//d�placer
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
					//trou � droite
					int idEffect = effects.indexOf(heftylefteffect.get());
					JLabel effectLabel = buttonPlaceCard[position].getCardPanel().getEffects()[idEffect];
					heftylefteffect.get().inverseDirection(effectLabel, this);
					for (int i=nearestRightGap-1;i>=position;i--) {
						//d�placer
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

	

	
	
	
	
	
	
}