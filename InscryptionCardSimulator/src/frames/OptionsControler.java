package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;

import frames.menubuttons.ButtonToDuel;

public class OptionsControler  implements ActionListener,MouseListener {
	Options options;
	public OptionsControler(Options options) {
		this.options = options;
		options.getSaveButton().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() instanceof JButton && ((JButton)e.getSource()).getText().equals("Save")) {
			File file = new File("save/options.txt");

			// créer le fichier s'il n'existe pas
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			String content = "options:\n";
			content = content + "typeP1: " + options.getTypeCardP1().getSelectedValue() + "\n";
			content = content + "moduloP1: " + options.getModuloTextP1().getText() + "\n";
			content = content + "multiplierP1: " + options.getMultiplicatorTextP1().getText() + "\n";
			content = content + "global_strenghtP1: " + options.getGlobalstrenghP1().getText() + "\n";
			content = content + "rarity_strenghtP1: " + options.getRaritystrenghP1().getText() + "\n";
			content = content + "lifePointsP1: " + options.getLifePointsP1().getText() + "\n";
			content = content + "nbMainCardsP1: " + options.getNbMainCardsP1().getText() + "\n";
			content = content + "nbSourceCardsP1: " + options.getNbSourceCardsP1().getText() + "\n";
			
			content = content + "typeP2: " + options.getTypeCardP2().getSelectedValue() + "\n";
			content = content + "moduloP2: " + options.getModuloTextP2().getText() + "\n";
			content = content + "multiplierP2: " + options.getMultiplicatorTextP2().getText() + "\n";
			content = content + "global_strenghtP2: " + options.getGlobalstrenghP2().getText() + "\n";
			content = content + "rarity_strenghtP2: " + options.getRaritystrenghP2().getText() + "\n";
			content = content + "lifePointsP2: " + options.getLifePointsP2().getText() + "\n";
			content = content + "nbMainCardsP2: " + options.getNbMainCardsP2().getText() + "\n";
			content = content + "nbSourceCardsP2: " + options.getNbSourceCardsP2().getText() + "\n";
			content = content + "nbSupCardsP2: " + options.getNbSupCardsP2().getText() + "\n";
			
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
			
			options.getMenu().setTypecards1(options.getTypeCardP1().getSelectedValue());
			options.getMenu().setModulo1(Integer.parseInt(options.getModuloTextP1().getText()));
			options.getMenu().setMultiplier1(Integer.parseInt(options.getMultiplicatorTextP1().getText()));
			options.getMenu().setGlobalStrenght1(Integer.parseInt(options.getGlobalstrenghP1().getText()));
			options.getMenu().setRarityStrenght1(Integer.parseInt(options.getRaritystrenghP1().getText()));
			options.getMenu().setLifePointsP1(Integer.parseInt(options.getLifePointsP1().getText()));
			options.getMenu().setNbMainCards1(Integer.parseInt(options.getNbMainCardsP1().getText()));
			options.getMenu().setNbSourceCards1(Integer.parseInt(options.getNbSourceCardsP1().getText()));
			
			options.getMenu().setTypecards2(options.getTypeCardP2().getSelectedValue());
			options.getMenu().setModulo2(Integer.parseInt(options.getModuloTextP2().getText()));
			options.getMenu().setMultiplier2(Integer.parseInt(options.getMultiplicatorTextP2().getText()));
			options.getMenu().setGlobalStrenght2(Integer.parseInt(options.getGlobalstrenghP2().getText()));
			options.getMenu().setRarityStrenght2(Integer.parseInt(options.getRaritystrenghP2().getText()));
			options.getMenu().setLifePointsP2(Integer.parseInt(options.getLifePointsP2().getText()));
			options.getMenu().setNbMainCards2(Integer.parseInt(options.getNbMainCardsP2().getText()));
			options.getMenu().setNbSourceCards2(Integer.parseInt(options.getNbSourceCardsP2().getText()));
			options.getMenu().setNbSupCards2(Integer.parseInt(options.getNbSupCardsP2().getText()));
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
