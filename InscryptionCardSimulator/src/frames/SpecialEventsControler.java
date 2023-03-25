package frames;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;

import cards.BeastCard;
import cards.Card;
import cards.CardFactory;
import cards.RobotCard;
import events.Alter;
import events.CampFire;
import events.CostCards;
import events.RandomCards;
import events.RandomSourceCards;
import events.TraderCards;
import events.Trapper;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;
import frames.duelbuttons.CardPanel;

public class SpecialEventsControler implements ActionListener,MouseListener {
	private SpecialEvents specialEvents;
	
	public SpecialEventsControler(SpecialEvents specialEvents) {
		this.specialEvents = specialEvents;
		//menu.getButtonduel().addActionListener(this);
		specialEvents.getButtonSimulation().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof JButton) {
			System.out.println("mouseClicked");
			if (specialEvents.getTypeEvent().getSelectedValue().equals("alter")) {
				try {
					new Alter(specialEvents.getMenu());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else if (specialEvents.getTypeEvent().getSelectedValue().equals("backpack")) {
					try {
						new RandomSourceCards(specialEvents.getMenu());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			} else if (specialEvents.getTypeEvent().getSelectedValue().equals("cost")) {
				try {
					new CostCards(specialEvents.getMenu());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else if (specialEvents.getTypeEvent().getSelectedValue().equals("random")) {
				try {
					new RandomCards(specialEvents.getMenu());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else if (specialEvents.getTypeEvent().getSelectedValue().equals("campfire")) {
				try {
					new CampFire(specialEvents.getMenu());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else if (specialEvents.getTypeEvent().getSelectedValue().equals("trapper")) {
				try {
					new Trapper(specialEvents.getMenu());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else if (specialEvents.getTypeEvent().getSelectedValue().equals("trader")) {
				try {
					new TraderCards(specialEvents.getMenu());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
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
