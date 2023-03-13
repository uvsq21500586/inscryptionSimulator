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
					for (int i=0;i<menu.getNbCards2();i++) {
						Random r = new Random();
						Card newCard = CardFactory.beastCard(
								menu.getModulo2(),
								menu.getMultiplier2(),
								menu.getGlobalStrenght2(),
								menu.getRarityStrenght2(),
								r.nextInt(menu.getModulo2()-1)+1);
						mainDeck2.add(newCard);
					}
					List<Card> sourceDeck2 = new ArrayList<>();
					for (int i=0;i<menu.getNbCards2();i++) {
						Random r = new Random();
						Card newCard = CardFactory.beastCardSource(
								menu.getModulo2(),
								menu.getMultiplier2(),
								menu.getGlobalStrenght2(),
								menu.getRarityStrenght2(),
								r.nextInt(menu.getModulo2()-1)+1);
						sourceDeck2.add(newCard);
					}
					duel.open(menu.getMainDeck1(), mainDeck2, menu.getSourceDeck1(), sourceDeck2, menu.getPV1(), menu.getPV2());
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
			for (int i=0;i<menu.getNbCards1();i++) {
				Random r = new Random();
				Card newCard;
				try {
					if (menu.getTypecards1().equals("beast")) {
					newCard = CardFactory.beastCard(
							menu.getModulo1(),
							menu.getMultiplier1(),
							menu.getGlobalStrenght1(),
							menu.getRarityStrenght1(),
							r.nextInt(menu.getModulo1()-1)+1);
					} else {
						newCard = CardFactory.robotCard(
								menu.getModulo1(),
								menu.getMultiplier1(),
								menu.getGlobalStrenght1(),
								menu.getRarityStrenght1(),
								r.nextInt(menu.getModulo1()-1)+1);
					}
					mainDeck1.add(newCard);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			List<Card> sourceDeck1 = new ArrayList<>();
			for (int i=0;i<menu.getNbCards1();i++) {
				Random r = new Random();
				Card newCard;
				try {
					if (menu.getTypecards1().equals("beast")) {
						newCard = CardFactory.beastCardSource(
								menu.getModulo1(),
								menu.getMultiplier1(),
								menu.getGlobalStrenght1(),
								menu.getRarityStrenght1(),
								r.nextInt(menu.getModulo1()-1)+1);
					} else {
						newCard = CardFactory.robotCardSource(
								menu.getModulo1(),
								menu.getMultiplier1(),
								menu.getGlobalStrenght1(),
								menu.getRarityStrenght1(),
								r.nextInt(menu.getModulo1()-1)+1);
					}
					
					sourceDeck1.add(newCard);
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
					boosterMain.add(CardFactory.beastCard(
							menu.getModulo1(),
							menu.getMultiplier1(),
							menu.getGlobalStrenght1(),
							menu.getRarityStrenght1(),
							r.nextInt(menu.getModulo1()-1)+1));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			try {
				boosterSource.add(CardFactory.beastCardSource(
						menu.getModulo1(),
						menu.getMultiplier1(),
						menu.getGlobalStrenght1(),
						menu.getRarityStrenght1(),
						r.nextInt(menu.getModulo1()-1)+1));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			menu.setMainDeck1(DeckManagement.optimizeDeck(menu.getMainDeck1(), boosterSource, boosterMain));
			saveDeck(menu.getMainDeck1(), menu.getSourceDeck1());
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
