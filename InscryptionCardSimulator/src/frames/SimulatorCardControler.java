package frames;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;

import cards.BeastCard;
import cards.CardFactory;
import cards.RobotCard;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;
import frames.duelbuttons.CardPanel;

public class SimulatorCardControler implements ActionListener,MouseListener {
	private SimulatorCard simulatorCard;
	
	public SimulatorCardControler(SimulatorCard simulatorCard) {
		this.simulatorCard = simulatorCard;
		//menu.getButtonduel().addActionListener(this);
		simulatorCard.getButtonSimulation().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof JButton) {
			System.out.println("mouseClicked");
			if (simulatorCard.getTypeCard().getSelectedValue().equals("beast")) {
				try {
					//simulatorCard.getResultCard().remove(null);
					BeastCard card = CardFactory.beastCard(
							Integer.parseInt(simulatorCard.getModuloText().getText()),
							Integer.parseInt(simulatorCard.getMultiplicatorText().getText()),
							Integer.parseInt(simulatorCard.getGlobalstrengh().getText()),
							Integer.parseInt(simulatorCard.getRaritystrengh().getText()),
							Integer.parseInt(simulatorCard.getSeed().getText())
					);
					simulatorCard.getResultCard().repaint(card);
					//.setResultCard(new CardPanel(card));
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (simulatorCard.getTypeCard().getSelectedValue().equals("robot")) {
				try {
					//simulatorCard.getResultCard().remove(null);
					RobotCard card = CardFactory.robotCard(
							Integer.parseInt(simulatorCard.getModuloText().getText()),
							Integer.parseInt(simulatorCard.getMultiplicatorText().getText()),
							Integer.parseInt(simulatorCard.getGlobalstrengh().getText()),
							Integer.parseInt(simulatorCard.getRaritystrengh().getText()),
							Integer.parseInt(simulatorCard.getSeed().getText())
					);
					simulatorCard.getResultCard().repaint(card);
					//.setResultCard(new CardPanel(card));
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
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
