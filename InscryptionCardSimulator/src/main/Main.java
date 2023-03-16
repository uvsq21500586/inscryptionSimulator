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
import effects.Effect;

class Main {

	public static void main(String[] args) throws IOException, FontFormatException {
		// TODO Auto-generated method stub
		Menu menu = new Menu();
		menu.open();
		try
	    {
	      // Le fichier d'entrée
	      File file = new File("save/deck.txt");    
	      // Créer l'objet File Reader
	      FileReader fr = new FileReader(file);  
	      // Créer l'objet BufferedReader        
	      BufferedReader br = new BufferedReader(fr);      
	      String line;
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
					    			  effects.add(new Effect(lineContent[11], lineContent[12]));
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
					    			  effects.add(new Effect(lineContent[11], lineContent[12]));
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
	    	  line = br.readLine();
	      }
	      menu.setMainDeck1(maindeck);
	      menu.setSourceDeck1(sourcedeck);
	      fr.close();
	    }
	    catch(IOException e)
	    {
	      e.printStackTrace();
	    }

	}

}
