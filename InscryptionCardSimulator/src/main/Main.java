package main;
import frames.*;

import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cards.BeastCard;
import cards.Card;
import cards.RobotCard;
import cards.UndeadCard;
import cards.WizardCard;
import effects.Effect;

class Main {

	public static void main(String[] args) throws IOException, FontFormatException {
		// TODO Auto-generated method stub
		Menu menu = new Menu();
		menu.open();
		try
	    {
	      // Le fichier d'entrée
	      File file = new File("save/options.txt");    
	      // Créer l'objet File Reader
	      FileReader fr = new FileReader(file);  
	      // Créer l'objet BufferedReader        
	      BufferedReader br = new BufferedReader(fr);      
	      String line;
	      String lineContentOptions[];
	      line = br.readLine();
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setTypecards1(lineContentOptions[1]);
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setModulo1(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setMultiplier1(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setGlobalStrenght1(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setRarityStrenght1(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setLifePointsP1(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setNbMainCards1(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setNbSourceCards1(Integer.parseInt(lineContentOptions[1]));
	      
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setTypecards2(lineContentOptions[1]);
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setModulo2(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setMultiplier2(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setGlobalStrenght2(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setRarityStrenght2(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setLifePointsP2(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setNbMainCards2(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setNbSourceCards2(Integer.parseInt(lineContentOptions[1]));
	      line = br.readLine();
	      lineContentOptions = line.split(" ");
	      menu.setNbSupCards2(Integer.parseInt(lineContentOptions[1]));
	      
	      
	      
	      
	      
	      fr.close();
	      
	      file = new File("save/deck.txt");    
	      // Créer l'objet File Reader
	      fr = new FileReader(file);  
	      // Créer l'objet BufferedReader        
	      br = new BufferedReader(fr); 
	      line = br.readLine();
	      line = br.readLine();
	      List<Card> maindeck = new ArrayList<>();
	      while(!line.equals("source deck:"))
	      {
	    	  String lineContent[] = line.split(";");
	    	  List<Effect> effects = new ArrayList<>();
	    	  
	    	  if (lineContent[0].equals("beast")) {
	    		  if (!lineContent[6].equals("_")) {
		    		  if (lineContent[7].equals("_")) {
		    			  effects.add(new Effect(lineContent[6], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[6], lineContent[0], Integer.parseInt(lineContent[7])));
			    	  }
		    		  
		    		  if (!lineContent[8].equals("_")) {
			    		  if (lineContent[9].equals("_")) {
			    			  effects.add(new Effect(lineContent[8], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[8], lineContent[0], Integer.parseInt(lineContent[9])));
				    	  }
			    		  if (!lineContent[10].equals("_")) {
				    		  if (lineContent[11].equals("_")) {
				    			  effects.add(new Effect(lineContent[10], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[10], lineContent[0], Integer.parseInt(lineContent[11])));
					    	  }
				    		  if (!lineContent[12].equals("_")) {
					    		  if (lineContent[13].equals("_")) {
					    			  effects.add(new Effect(lineContent[12], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[12], lineContent[0], Integer.parseInt(lineContent[13])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  BeastCard newCard = new BeastCard(
	    				  lineContent[1],
	    				  lineContent[2],
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  Integer.parseInt(lineContent[5]),
	    				  effects,
	    				  Integer.parseInt(lineContent[14]),
	    				  true);
	    		  maindeck.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("robot")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  RobotCard newCard = new RobotCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[13]),
	    				  true);
	    		  maindeck.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("undead")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  UndeadCard newCard = new UndeadCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[11]),
	    				  true);
	    		  maindeck.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("wizard")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  WizardCard newCard = new WizardCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[13]),
	    				  Integer.parseInt(lineContent[14]),
	    				  Integer.parseInt(lineContent[15]),
	    				  Integer.parseInt(lineContent[16]),
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[17]),
	    				  true);
	    		  maindeck.add(newCard);
	    	  }
	    	  line = br.readLine();
	      }
	      List<Card> sourcedeck = new ArrayList<>();
	      line = br.readLine();
	      while(!line.equals("fin"))
	      {
	    	  String lineContent[] = line.split(";");
	    	  List<Effect> effects = new ArrayList<>();
	    	  
	    	  if (lineContent[0].equals("beast")) {
	    		  if (!lineContent[6].equals("_")) {
		    		  if (lineContent[7].equals("_")) {
		    			  effects.add(new Effect(lineContent[6], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[6], lineContent[0], Integer.parseInt(lineContent[7])));
			    	  }
		    		  
		    		  if (!lineContent[8].equals("_")) {
			    		  if (lineContent[9].equals("_")) {
			    			  effects.add(new Effect(lineContent[8], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[8], lineContent[0], Integer.parseInt(lineContent[9])));
				    	  }
			    		  if (!lineContent[10].equals("_")) {
				    		  if (lineContent[11].equals("_")) {
				    			  effects.add(new Effect(lineContent[10], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[10], lineContent[0], Integer.parseInt(lineContent[11])));
					    	  }
				    		  if (!lineContent[12].equals("_")) {
					    		  if (lineContent[13].equals("_")) {
					    			  effects.add(new Effect(lineContent[12], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[12], lineContent[0], Integer.parseInt(lineContent[13])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  BeastCard newCard = new BeastCard(
	    				  lineContent[1],
	    				  lineContent[2],
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  Integer.parseInt(lineContent[5]),
	    				  effects,
	    				  Integer.parseInt(lineContent[14]),
	    				  false);
	    		  if (newCard.getAppearance().contains("pelt")) {
	    			  newCard.setSacrificiable(false);
	    		  }
	    		  sourcedeck.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("robot")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  RobotCard newCard = new RobotCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[13]),
	    				  false);
	    		  sourcedeck.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("undead")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  UndeadCard newCard = new UndeadCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[11]),
	    				  false);
	    		  sourcedeck.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("wizard")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  WizardCard newCard = new WizardCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[13]),
	    				  Integer.parseInt(lineContent[14]),
	    				  Integer.parseInt(lineContent[15]),
	    				  Integer.parseInt(lineContent[16]),
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[17]),
	    				  false);
	    		  sourcedeck.add(newCard);
	    	  }
	    	  line = br.readLine();
	      }
	      menu.setMainDeck1(maindeck);
	      menu.setSourceDeck1(sourcedeck);
	      fr.close();
	      
	      file = new File("save/deadcards.txt");    
	      // Créer l'objet File Reader
	      fr = new FileReader(file);  
	      // Créer l'objet BufferedReader        
	      br = new BufferedReader(fr); 
	      line = br.readLine();
	      line = br.readLine();
	      List<Card> deadcardslist = new ArrayList<>();
	      while(!line.equals("available dead cards:"))
	      {
	    	  String lineContent[] = line.split(";");
	    	  List<Effect> effects = new ArrayList<>();
	    	  
	    	  if (lineContent[0].equals("beast")) {
	    		  if (!lineContent[6].equals("_")) {
		    		  if (lineContent[7].equals("_")) {
		    			  effects.add(new Effect(lineContent[6], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[6], lineContent[0], Integer.parseInt(lineContent[7])));
			    	  }
		    		  
		    		  if (!lineContent[8].equals("_")) {
			    		  if (lineContent[9].equals("_")) {
			    			  effects.add(new Effect(lineContent[8], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[8], lineContent[0], Integer.parseInt(lineContent[9])));
				    	  }
			    		  if (!lineContent[10].equals("_")) {
				    		  if (lineContent[11].equals("_")) {
				    			  effects.add(new Effect(lineContent[10], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[10], lineContent[0], Integer.parseInt(lineContent[11])));
					    	  }
				    		  if (!lineContent[12].equals("_")) {
					    		  if (lineContent[13].equals("_")) {
					    			  effects.add(new Effect(lineContent[12], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[12], lineContent[0], Integer.parseInt(lineContent[13])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  BeastCard newCard = new BeastCard(
	    				  lineContent[1],
	    				  lineContent[2],
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  Integer.parseInt(lineContent[5]),
	    				  effects,
	    				  Integer.parseInt(lineContent[14]),
	    				  true);
	    		  deadcardslist.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("robot")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  RobotCard newCard = new RobotCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[13]),
	    				  true);
	    		  deadcardslist.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("undead")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  UndeadCard newCard = new UndeadCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[11]),
	    				  true);
	    		  deadcardslist.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("wizard")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  WizardCard newCard = new WizardCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[13]),
	    				  Integer.parseInt(lineContent[14]),
	    				  Integer.parseInt(lineContent[15]),
	    				  Integer.parseInt(lineContent[16]),
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[17]),
	    				  true);
	    		  deadcardslist.add(newCard);
	    	  }
	    	  line = br.readLine();
	      }
	      List<Card> availabledeadcardslist = new ArrayList<>();
	      line = br.readLine();
	      while(!line.equals("fin"))
	      {
	    	  String lineContent[] = line.split(";");
	    	  List<Effect> effects = new ArrayList<>();
	    	  
	    	  if (lineContent[0].equals("beast")) {
	    		  if (!lineContent[6].equals("_")) {
		    		  if (lineContent[7].equals("_")) {
		    			  effects.add(new Effect(lineContent[6], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[6], lineContent[0], Integer.parseInt(lineContent[7])));
			    	  }
		    		  
		    		  if (!lineContent[8].equals("_")) {
			    		  if (lineContent[9].equals("_")) {
			    			  effects.add(new Effect(lineContent[8], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[8], lineContent[0], Integer.parseInt(lineContent[9])));
				    	  }
			    		  if (!lineContent[10].equals("_")) {
				    		  if (lineContent[11].equals("_")) {
				    			  effects.add(new Effect(lineContent[10], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[10], lineContent[0], Integer.parseInt(lineContent[11])));
					    	  }
				    		  if (!lineContent[12].equals("_")) {
					    		  if (lineContent[13].equals("_")) {
					    			  effects.add(new Effect(lineContent[12], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[12], lineContent[0], Integer.parseInt(lineContent[13])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  BeastCard newCard = new BeastCard(
	    				  lineContent[1],
	    				  lineContent[2],
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  Integer.parseInt(lineContent[5]),
	    				  effects,
	    				  Integer.parseInt(lineContent[14]),
	    				  true);
	    		  if (newCard.getAppearance().contains("pelt")) {
	    			  newCard.setSacrificiable(false);
	    		  }
	    		  availabledeadcardslist.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("robot")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  RobotCard newCard = new RobotCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[13]),
	    				  true);
	    		  availabledeadcardslist.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("undead")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  UndeadCard newCard = new UndeadCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[11]),
	    				  true);
	    		  availabledeadcardslist.add(newCard);
	    	  }
	    	  if (lineContent[0].equals("wizard")) {
	    		  if (!lineContent[5].equals("_")) {
		    		  if (lineContent[6].equals("_")) {
		    			  effects.add(new Effect(lineContent[5], lineContent[0]));
			    	  } else {
			    		  effects.add(new Effect(lineContent[5], lineContent[0], Integer.parseInt(lineContent[6])));
			    	  }
		    		  
		    		  if (!lineContent[7].equals("_")) {
			    		  if (lineContent[8].equals("_")) {
			    			  effects.add(new Effect(lineContent[7], lineContent[0]));
				    	  } else {
				    		  effects.add(new Effect(lineContent[7], lineContent[0], Integer.parseInt(lineContent[8])));
				    	  }
			    		  if (!lineContent[9].equals("_")) {
				    		  if (lineContent[10].equals("_")) {
				    			  effects.add(new Effect(lineContent[9], lineContent[0]));
					    	  } else {
					    		  effects.add(new Effect(lineContent[9], lineContent[0], Integer.parseInt(lineContent[10])));
					    	  }
				    		  if (!lineContent[11].equals("_")) {
					    		  if (lineContent[12].equals("_")) {
					    			  effects.add(new Effect(lineContent[11], lineContent[0]));
						    	  } else {
						    		  effects.add(new Effect(lineContent[11], lineContent[0], Integer.parseInt(lineContent[12])));
						    	  }
					    	  }
				    	  }
			    	  }
		    	  }
	    		  WizardCard newCard = new WizardCard(
	    				  lineContent[1],
	    				  Integer.parseInt(lineContent[13]),
	    				  Integer.parseInt(lineContent[14]),
	    				  Integer.parseInt(lineContent[15]),
	    				  Integer.parseInt(lineContent[16]),
	    				  Integer.parseInt(lineContent[2]),
	    				  Integer.parseInt(lineContent[3]),
	    				  Integer.parseInt(lineContent[4]),
	    				  effects,
	    				  Integer.parseInt(lineContent[17]),
	    				  true);
	    		  availabledeadcardslist.add(newCard);
	    	  }
	    	  line = br.readLine();
	      }
	      menu.setDeadCardsList(deadcardslist);
	      menu.setAvailableDeadCardsList(availabledeadcardslist);
	      fr.close();
	    }
	    catch(IOException e)
	    {
	      e.printStackTrace();
	    }

	}

}
