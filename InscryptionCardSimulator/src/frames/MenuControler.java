package frames;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cards.Card;
import cards.CardFactory;
import decks.DeckManagement;
import frames.menubuttons.ButtonToBoosterCard;
import frames.menubuttons.ButtonToBuildDeck;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToOptions;
import frames.menubuttons.ButtonToSeeDeck;
import frames.menubuttons.ButtonToSimulatorCard;

public class MenuControler implements ActionListener,MouseListener {
	private Menu menu;
	
	public MenuControler(Menu menu) {
		this.menu = menu;
		//menu.getButtonduel().addActionListener(this);
		menu.getButtonduel().addMouseListener(this);
		menu.getButtonSimulatorCard().addMouseListener(this);
		menu.getButtonToBuildDeck().addMouseListener(this);
		menu.getButtonTrueDuel().addMouseListener(this);
		menu.getButtonBoosterCard().addMouseListener(this);
		menu.getButtonToSeeDeck().addMouseListener(this);
		menu.getButtonOptions().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof ButtonToDuel) {
			Duel duel = new Duel();
			try {
				if (((ButtonToDuel) e.getSource()).label.contains("True") && menu.getMainDeck1() != null) {
					// construire les decks du joueur2
					List<Card> mainDeck2 = new ArrayList<>();
					List<Card> sourceDeck2 = new ArrayList<>();
					for (int i=0;i<menu.getNbMainCards2();i++) {
						Random r = new Random();
						Card newCard = CardFactory.mainCard(
								menu.getModulo2(),
								menu.getMultiplier2(),
								menu.getGlobalStrenght2(),
								menu.getRarityStrenght2(),
								r.nextInt(menu.getModulo2()-1)+1,
								menu.getTypecards2());
						mainDeck2.add(newCard);
					}
					
					for (int i=0;i<menu.getNbSourceCards2();i++) {
						Random r = new Random();
						Card newCard = CardFactory.sourceCard(
								menu.getModulo2(),
								menu.getMultiplier2(),
								menu.getGlobalStrenght2(),
								menu.getRarityStrenght2(),
								r.nextInt(menu.getModulo2()-1)+1,
								menu.getTypecards2());
						sourceDeck2.add(newCard);
					}
					
					List<Card> supMainCardsP2 = new ArrayList<>();
					List<Card> supSourceCardsP2 = new ArrayList<>();
					for (int i=0;i<menu.getNbSupCards2();i++) {
						Random r = new Random();
						Card newCard = CardFactory.mainCard(
								menu.getModulo2(),
								menu.getMultiplier2(),
								menu.getGlobalStrenght2(),
								menu.getRarityStrenght2(),
								r.nextInt(menu.getModulo2()-1)+1,
								menu.getTypecards2());
						supMainCardsP2.add(newCard);
					}
					
					for (int i=0;i<menu.getNbSupCards2();i++) {
						Random r = new Random();
						Card newCard = CardFactory.sourceCard(
								menu.getModulo2(),
								menu.getMultiplier2(),
								menu.getGlobalStrenght2(),
								menu.getRarityStrenght2(),
								r.nextInt(menu.getModulo2()-1)+1,
								menu.getTypecards2());
						supSourceCardsP2.add(newCard);
					}
					
					mainDeck2 = DeckManagement.optimizeMainDeck(mainDeck2, sourceDeck2, supMainCardsP2,
							menu.getCheckGreenMageP2().isSelected(),menu.getCheckOrangeMageP2().isSelected(),menu.getCheckBlueMageP2().isSelected());
					sourceDeck2 = DeckManagement.optimizeSourceDeck(mainDeck2, sourceDeck2, supSourceCardsP2);
					
					duel.open(menu.getMainDeck1(), mainDeck2, menu.getSourceDeck1(), sourceDeck2, menu.getLifePointsP1(), menu.getLifePointsP2());
				} else if (!((ButtonToDuel) e.getSource()).label.contains("True")) {
					duel.openTest();
				}
			} catch (IOException | FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if (e.getSource() instanceof ButtonToSimulatorCard) {
			SimulatorCard simulatorCard = new SimulatorCard();
			try {
				simulatorCard.open();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() instanceof ButtonToBuildDeck) {
			// construire les decks du joueur2
			List<Card> mainDeck1 = new ArrayList<>();
			for (int i=0;i<menu.getNbMainCards1();i++) {
				Random r = new Random();
				try {
					Card card1 = CardFactory.mainCard(
							menu.getModulo1(),
							menu.getMultiplier1(),
							menu.getGlobalStrenght1(),
							menu.getRarityStrenght1(),
							r.nextInt(menu.getModulo1()-1)+1,
							menu.getTypecards1());
					Card card2 = CardFactory.mainCard(
							menu.getModulo1(),
							menu.getMultiplier1(),
							menu.getGlobalStrenght1(),
							menu.getRarityStrenght1(),
							r.nextInt(menu.getModulo1()-1)+1,
							menu.getTypecards1());
					if (card2.getRarity() < card1.getRarity()) {
						mainDeck1.add(card2);
					} else {
						mainDeck1.add(card1);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			List<Card> sourceDeck1 = new ArrayList<>();
			for (int i=0;i<menu.getNbSourceCards1();i++) {
				Random r = new Random();
				try {
					Card card1 = CardFactory.sourceCard(
							menu.getModulo1(),
							menu.getMultiplier1(),
							menu.getGlobalStrenght1(),
							menu.getRarityStrenght1(),
							r.nextInt(menu.getModulo1()-1)+1,
							menu.getTypecards1());
					Card card2 = CardFactory.sourceCard(
							menu.getModulo1(),
							menu.getMultiplier1(),
							menu.getGlobalStrenght1(),
							menu.getRarityStrenght1(),
							r.nextInt(menu.getModulo1()-1)+1,
							menu.getTypecards1());
					if (card2.getRarity() < card1.getRarity()) {
						sourceDeck1.add(card2);
					} else {
						sourceDeck1.add(card1);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			menu.setMainDeck1(mainDeck1);
			menu.setSourceDeck1(sourceDeck1);
			try {
				Player1Deck deckView = new Player1Deck(menu);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			saveDeck(mainDeck1, sourceDeck1);
		}
		
		if (e.getSource() instanceof ButtonToSeeDeck) {
			try {
				Player1Deck deckView = new Player1Deck(menu);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() instanceof ButtonToBoosterCard) {
			List<Card> boosterMain = new ArrayList<>();
			List<Card> boosterSource = new ArrayList<>();
			
			Random r = new Random();
				for (int i=0;i<4;i++) {
					try {
						Card card1 = CardFactory.mainCard(
								menu.getModulo1(),
								menu.getMultiplier1(),
								menu.getGlobalStrenght1(),
								menu.getRarityStrenght1(),
								r.nextInt(menu.getModulo1()-1)+1,
								menu.getTypecards1());
						Card card2 = CardFactory.mainCard(
								menu.getModulo1(),
								menu.getMultiplier1(),
								menu.getGlobalStrenght1(),
								menu.getRarityStrenght1(),
								r.nextInt(menu.getModulo1()-1)+1,
								menu.getTypecards1());
						if (card2.getRarity() < card1.getRarity()) {
							boosterMain.add(card2);
						} else {
							boosterMain.add(card1);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				for (int i=0;i<1;i++) {
					try {
						Card card1 = CardFactory.sourceCard(
								menu.getModulo1(),
								menu.getMultiplier1(),
								menu.getGlobalStrenght1(),
								menu.getRarityStrenght1(),
								r.nextInt(menu.getModulo1()-1)+1,
								menu.getTypecards1());
						Card card2 = CardFactory.sourceCard(
								menu.getModulo1(),
								menu.getMultiplier1(),
								menu.getGlobalStrenght1(),
								menu.getRarityStrenght1(),
								r.nextInt(menu.getModulo1()-1)+1,
								menu.getTypecards1());
						if (card2.getRarity() < card1.getRarity()) {
							boosterSource.add(card2);
						} else {
							boosterSource.add(card1);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			
			try {
				Player1Deck boosterView = new Player1Deck(boosterMain, boosterSource);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			menu.setMainDeck1(DeckManagement.optimizeMainDeck(menu.getMainDeck1(),menu.getSourceDeck1(), boosterMain,
					menu.getCheckGreenMageP1().isSelected(),menu.getCheckOrangeMageP1().isSelected(),menu.getCheckBlueMageP1().isSelected()));
			menu.setSourceDeck1(DeckManagement.optimizeSourceDeck(menu.getMainDeck1(),menu.getSourceDeck1(), boosterSource));
			saveDeck(menu.getMainDeck1(), menu.getSourceDeck1());
		}
		if (e.getSource() instanceof ButtonToOptions) {
			Options options = new Options();
			try {
				options.open(menu);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void saveDeck(List<Card> mainDeck1, List<Card> sourceDeck1) {
		File file = new File("save/deck.txt");

		// créer le fichier s'il n'existe pas
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		String content = "main deck:\n";
		for (int i=0;i<mainDeck1.size();i++) {
			Card card = mainDeck1.get(i);
			content = content + card.toString() + "\n";
		}
		content = content + "source deck:\n";
		for (int i=0;i<sourceDeck1.size();i++) {
			Card card = sourceDeck1.get(i);
			content = content + card.toString() + "\n";
		}
		content = content + "fin\n";
		
		FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
