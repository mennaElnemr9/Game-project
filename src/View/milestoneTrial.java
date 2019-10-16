package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.CommandCenter;
import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import disasters.Collapse;
import disasters.Disaster;
import disasters.Fire;
import disasters.GasLeak;
import disasters.Infection;
import disasters.Injury;
import infrastructure.ResidentialBuilding;
import people.Citizen;
import people.CitizenState;
import units.Ambulance;
import units.DiseaseControlUnit;
import units.Evacuator;
import units.FireTruck;
import units.GasControlUnit;
import units.Unit;
import units.UnitState;
import simulation.Simulator;

public class milestoneTrial extends JFrame implements ActionListener, ChangeListener {
	public static String[] a;
	private static ArrayList<JButton> buttons = new ArrayList<>();
	private static ImageIcon deadciti = new ImageIcon("deadcitizen.jfif");
	private static ImageIcon deadbuil = new ImageIcon("deadbuilding.jfif");
	private JTextArea Data = new JTextArea(15, 25);
	private static JPanel total = new JPanel();
	private static JPanel u = new JPanel();
	private static JTextArea disasters = new JTextArea(15, 25);
	private static Vector<String> v1 = new Vector<>();
	private static ArrayList<JButton> units = new ArrayList<>();
	private static JTextArea un = new JTextArea();
	private static ImageIcon ambulance = new ImageIcon("ambulance.png");
	private static ImageIcon firetruck = new ImageIcon("FireTruck.png");
	private static ImageIcon diseaseunit = new ImageIcon("diseasecontrol.png");
	private static ImageIcon gascontrol = new ImageIcon("Gascontrol.png");
	private static ImageIcon evacuator = new ImageIcon("evacuator.jpeg");
	private static ImageIcon fire = new ImageIcon("fireee.gif.gif");
	private static ImageIcon gasleak = new ImageIcon("gas.gif");
	private static ImageIcon infection = new ImageIcon("sick.gif");
	private static ImageIcon injury = new ImageIcon("blood.gif");
	private static ImageIcon collapse = new ImageIcon("Collapse.gif");
	private static ImageIcon Icitizen = new ImageIcon("Icitizen.jfif.jpeg");
	private static ImageIcon Ibuilding = new ImageIcon("Ibuilding.jfif.jpeg");
	private static JComboBox<String> rescuable;
	private static String unitID;
	private static CommandCenter c;
	private static JSlider caus = new JSlider();
	private static JFrame gameOver = new JFrame("Game overrrr");
	private static JPanel gameOverPanel = new JPanel();
	/*
	 * private static JButton b1 = new JButton(ambulance); private static JButton b2
	 * = new JButton(firetruck); private static JButton b3 = new
	 * JButton(diseaseunit); private static JButton b4 = new JButton(gascontrol);
	 * private static JButton b5 = new JButton(evacuator);
	 */
	private static JPanel p1 = new JPanel();
	private static JPanel p2 = new JPanel();
	private static JPanel p3 = new JPanel();
	private static JPanel p4 = new JPanel();
	private static JPanel p = new JPanel();
	private static milestoneTrial frame = new milestoneTrial();
	private static JTextArea status=new JTextArea();
	private static StringBuilder sbs = new StringBuilder();

	public void addListener(JButton b) {
		b.addActionListener(this);
	}

	public JTextArea gettext() {
		return Data;
	}

	public void addlisetener2(JSpinner p) {
		p.addChangeListener(this);
	}

	public void addlistener3(JComboBox<String> b) {
		b.addActionListener(this);
	}

	public static void main(String[] args) throws Exception {
		a=args;
		c = new CommandCenter();
		Simulator s = c.gete();
  
		Vector<String> v = new Vector<>();
		for (Citizen cc : s.helperCitizens()) {
			v.add("Citizen: " + cc.getNationalID());
		}
		for (ResidentialBuilding b : s.ghelperBuildings()) {
			v.add("Building: " + b.getLocation().getX() + "," + b.getLocation().getY());

		}
		rescuable = new JComboBox<>(v);
		JScrollPane scroll2 = new JScrollPane(rescuable);
		frame.addlistener3(rescuable);

		// frame.validate();
		Font font = new Font("Segoe Script", Font.ROMAN_BASELINE, 15);

		Border border = BorderFactory.createTitledBorder(new RoundedBorder(10), "", 0, 0,
				new Font("", Font.CENTER_BASELINE, 20), Color.PINK);
		Border border1 = BorderFactory.createTitledBorder(new RoundedBorder(4), "Cell Data", 0, 0,
				new Font("", Font.CENTER_BASELINE, 20), Color.blue);

		frame.gettext().setEditable(false);
		JScrollPane scroll = new JScrollPane(frame.gettext());
		JPanel DataPanel = new JPanel();
		JScrollPane scrolll = new JScrollPane(disasters);
		DataPanel.add(scroll);
		DataPanel.add(scrolll);
		disasters.setEditable(false);
		DataPanel.setBorder(border1);
		DataPanel.setPreferredSize(new Dimension(300, 300));
		frame.gettext().setBorder(border);
		frame.add(DataPanel, BorderLayout.WEST);
		scroll.getPreferredSize();
		JPanel grid = new JPanel(new GridLayout(10, 10));

		// name.setPreferredSize(new Dimension(100, 100));
		Border border2 = BorderFactory.createTitledBorder(new RoundedBorder(4), "Player's Data & the current cycle", 0,
				0, new Font("", Font.CENTER_BASELINE, 20), Color.blue);

		// total.setBorder(border2);
		total.setPreferredSize(new Dimension(50000, 130));
		frame.add(total, BorderLayout.SOUTH);
		JPanel user = new JPanel();
		user.setPreferredSize(new Dimension(500, 130));
		user.setBackground(Color.pink);

		user.setBorder(border2);
		total.add(user);
		JLabel cycle = new JLabel("current cycle = ");
		JSpinner count = new JSpinner();
		((JSpinner.DefaultEditor) count.getEditor()).getTextField().setEditable(false);

		count.setPreferredSize(new Dimension(50, 50));
		frame.addlisetener2(count);
		user.add(cycle);
		// count.setEditor(false);
		user.add(count);

		for (int i = 1; i <= 10; i++) {
			JButton button = new JButton();

			button.setPreferredSize(new Dimension(57, 57));
			button.setActionCommand(i - 1 + ",0");
			button.setBorder(new RoundedBorder(30));
			frame.addListener(button);
			buttons.add(button);
			if (i % 2 == 0) {
				button.setBackground(Color.PINK);
				button.setForeground(Color.BLACK);
			} else {
				// button.setBackground(Color.BLUE);
				button.setForeground(Color.BLACK);
			}
			grid.add(button);
			for (int j = 2; j <= 10; j++) {
				JButton button1 = new JButton();
				button1.setPreferredSize(new Dimension(57, 57));
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						// button1.setBackground(Color.BLUE);
						button1.setForeground(Color.BLACK);
					} else {
						button1.setBackground(Color.PINK);
						button1.setForeground(Color.BLACK);
					}
				} else {
					if (j % 2 == 0) {
						button1.setBackground(Color.PINK);
						button1.setForeground(Color.BLACK);
					} else {
						// button1.setBackground();
						button1.setForeground(Color.BLACK);
					}
				}
				grid.add(button1);
				buttons.add(button1);
				frame.addListener(button1);
				button1.setActionCommand(i - 1 + "," + (j - 1));
				button1.setBorder(new RoundedBorder(30));
			}
		}
		JPanel Grid = new JPanel();
		Grid.add(grid);
		frame.add(Grid, BorderLayout.CENTER);
		// JPanel p1 = new JPanel();

		p.setBackground(Color.BLACK);

		frame.add(p, BorderLayout.EAST);
		p1.setPreferredSize(new Dimension(350, 200));

		/*
		 * b5.setPreferredSize(new Dimension(50, 50)); b1.setPreferredSize(new
		 * Dimension(50, 50)); b2.setPreferredSize(new Dimension(50, 50));
		 * b3.setPreferredSize(new Dimension(50, 50)); b4.setPreferredSize(new
		 * Dimension(50, 50));
		 */

		for (Unit u : c.gete().getEmergencyUnits()) {
			JButton b = new JButton();
			b.setActionCommand(u.getUnitID());
			b.setPreferredSize(new Dimension(50, 50));
			v1.addElement(u.getUnitID() + " " + u.getState());
			if (u instanceof Ambulance) {
				b.setIcon(ambulance);
			} else if (u instanceof FireTruck) {
				b.setIcon(firetruck);
			} else if (u instanceof Evacuator) {
				b.setIcon(evacuator);
			} else if (u instanceof DiseaseControlUnit) {
				b.setIcon(diseaseunit);
			} else {
				b.setIcon(gascontrol);
			}
			p1.add(b);
			b.addActionListener(frame);
			units.add(b);
		}
		JComboBox<String> unitt = new JComboBox<>(v1);
		JScrollPane scroll4 = new JScrollPane(unitt);
		JLabel b = new JLabel("choose unit from here");
		p2.add(b);
		// p2.add(scroll4);
		JScrollPane scroll3 = new JScrollPane(p1);
		p.add(scroll3);

		JLabel target = new JLabel("Select target from here");
		target.setForeground(Color.black);
		// JPanel p2 = new JPanel();
		p2.add(target, BorderLayout.WEST);
		p2.add(scroll2);
		Border border3 = BorderFactory.createTitledBorder(new RoundedBorder(4), "Units", 0, 0,
				new Font("", Font.CENTER_BASELINE, 20), Color.blue);
		Border border4 = BorderFactory.createTitledBorder(new RoundedBorder(4), "Responding Units", 0, 0,
				new Font("", Font.CENTER_BASELINE, 20), Color.blue);
		Border border5 = BorderFactory.createTitledBorder(new RoundedBorder(4), "Treating Units", 0, 0,
				new Font("", Font.CENTER_BASELINE, 20), Color.blue);
		p1.setBorder(border3);
		p2.setBorder(border4);

		p2.setPreferredSize(new Dimension(350, 200));
		p2.setBackground(Color.lightGray);
		// JPanel p3 = new JPanel();
		p3.setBorder(border5);
		p3.setPreferredSize(new Dimension(350, 200));
		p3.setBackground(Color.pink);

		p.setPreferredSize(new Dimension(350, 1000));
		// p.add(p1);
		p.add(p2);
		p.add(p3);
		JLabel caus1 = new JLabel("Causalties =");
		caus.setEnabled(false);
		user.add(caus1);

		u.setPreferredSize(new Dimension(400, 150));
		un.setPreferredSize(new Dimension(300, 150));
		un.setEditable(false);
		un.setForeground(Color.black);
		JScrollPane sc = new JScrollPane(un);
		sc.setPreferredSize(new Dimension(300, 150));
		u.add(sc, BorderLayout.SOUTH);
		total.add(u);
		caus.setPreferredSize(new Dimension(250, 50));
		caus.setMaximum(c.gete().helperCitizens().size());
		caus.setPaintTicks(true);
		caus.setPaintLabels(true);
		caus.setMajorTickSpacing(1);
		caus.setMinorTickSpacing(1);
		caus.setValue(0);

		frame.startup();
		user.add(caus);
		// bonus
//		frame.add(p4,BorderLayout.NORTH);
//		p4.setPreferredSize(new Dimension(200, 50));
//		Border borderP4 = BorderFactory.createTitledBorder(new RoundedBorder(4), "H E L P", 1, 0,
//				new Font("", Font.CENTER_BASELINE, 20), Color.blue);
//		p4.setBorder(borderP4);
//		p4.setBackground(Color.red);
//		JButton bP4 = new JButton();
//		bP4.setActionCommand("Call a friend");
//         frame.addListener(bP4);
//		bP4.setPreferredSize(new Dimension(120, 20));
//		p4.add(bP4);

	}

	public void startup() {
		for (Citizen cb : c.gete().helperCitizens()) {
			for (JButton j : buttons) {
				if (j.getActionCommand().equals(cb.getLocation().getX() + "," + cb.getLocation().getY())) {
					j.setIcon(Icitizen);

				}
			}
		}
		for (ResidentialBuilding cb : c.gete().ghelperBuildings()) {
			for (JButton j : buttons) {
				if (j.getActionCommand().equals(cb.getLocation().getX() + "," + cb.getLocation().getY())) {
					j.setIcon(Ibuilding);

				}
			}
		}
		this.setTitle("MileStone");
		this.setVisible(true);
		this.setSize(1500, 1500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.validate();
	}

	private static class RoundedBorder implements Border {

		private int radius;

		RoundedBorder(int radius) {
			this.radius = radius;
		}

		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
		}

		public boolean isBorderOpaque() {
			return true;
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
		}
	}

	@Override
	public void stateChanged(ChangeEvent evt) {
		if (c.gete().checkGameOver()) {
			int score = c.gete().helperCitizens().size() - c.gete().calculateCasualties();
			JOptionPane.showMessageDialog(frame, "GAME OVER" + "\n" + "your score is:" + score);

			frame.dispatchEvent((new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
			/*
			 * frame.setVisible(false); gameOver.setPreferredSize(new Dimension(50000,
			 * 50000)); //frame.getDefaultCloseOperation(); gameOver.setVisible(true);
			 * 
			 * gameOver.setName("Game Is Over"); gameOverPanel.setPreferredSize(new
			 * Dimension(100,100)); JTextArea text = new JTextArea(); int score =
			 * c.gete().helperCitizens().size() - c.gete().calculateCasualties();
			 * text.setText("Your Score is: " + score); gameOverPanel.add(text);
			 * 
			 * text.setEditable(false); gameOver.setDefaultCloseOperation(EXIT_ON_CLOSE);
			 * gameOver.validate(); gameOver.repaint(); gameOverPanel.revalidate();
			 */
			return;
		}
		JSpinner spinner = (JSpinner) evt.getSource();
		Object value = spinner.getValue();
		StringBuilder ss = new StringBuilder();

		int counter = 0;
		for (Citizen cc : c.gete().helperCitizens()) {
			if (cc.getState() == CitizenState.DECEASED) {
				counter++;
			}
		}
		caus.setValue(caus.getValue() + counter);

		try {
			c.gete().nextCycle();
		} catch (CitizenAlreadyDeadException e) {
			JOptionPane.showMessageDialog(frame, "DEADDD");

		} catch (BuildingAlreadyCollapsedException e) {
			JOptionPane.showMessageDialog(frame, "COLLAPSED");
		}
		for (Citizen c1 : c.gete().helperCitizens()) {
			if (c1.getState() == CitizenState.DECEASED) {
				counter++;
			}
		}
		for (Disaster d : c.gete().disatersnow()) {

			if (d.isActive()) {
				if (d instanceof Injury) {
					ss.append("Injury disaster:" + "\n");
				} else if (d instanceof Fire) {
					ss.append("fire disaster:" + "\n");
				} else if (d instanceof GasLeak) {
					ss.append("gasleak disaster:" + "\n");
				} else if (d instanceof Infection) {
					ss.append("Infectin disaster:" + "\n");
				} else {
					ss.append("collapse disaster:" + "\n");
				}
				ss.append("start cycle:" + d.getStartCycle() + "\n" + "" + "Target:");
				if (d.getTarget() instanceof ResidentialBuilding) {
					ss.append("building at " + d.getTarget().getLocation().getX() + ","
							+ d.getTarget().getLocation().getY() + "\n");

				} else {
					ss.append("Citizen at " + d.getTarget().getLocation().getX() + ","
							+ d.getTarget().getLocation().getY() + "with the nationalID :"
							+ ((Citizen) d.getTarget()).getNationalID() + "\n");
				}

			}
		}
		disasters.removeAll();
		disasters.setText(ss.toString());
		for (Unit u : c.gete().getEmergencyUnits()) {
			if (u.getState() == UnitState.IDLE) {
				JButton bb = null;
				for (JButton b : units) {
					if (b.getActionCommand().equals(u.getUnitID())) {
						bb = b;
						break;
					}
				}
				p1.add(bb);
				p2.remove(bb);
				p3.remove(bb);
			} else if (u.getState() == UnitState.RESPONDING) {

				JButton bb = null;
				for (JButton b : units) {
					if (b.getActionCommand().equals(u.getUnitID())) {
						bb = b;
						break;
					}
				}
				p1.remove(bb);
				p2.add(bb);
				p3.remove(bb);
			} else {

				JButton bb = null;
				for (JButton b : units) {

					if (b.getActionCommand().equals(u.getUnitID())) {
						bb = b;
						break;
					}
				}
				p1.remove(bb);
				p2.remove(bb);
				p3.add(bb);
			}
			p1.revalidate();
			p2.revalidate();
			p3.revalidate();
			frame.repaint();
		}
		for (ResidentialBuilding bb : c.gete().ghelperBuildings()) {

			int x = bb.getLocation().getX();
			int y = bb.getLocation().getY();
			JButton b2 = null;
			if (bb.getDisaster() != null) {

				if (bb.getDisaster() instanceof Fire) {

					for (JButton b1 : buttons) {
						if (b1.getActionCommand().equals(x + "," + y)) {
							b1.setIcon(fire);
							b2 = b1;
						}
					}

				}
				if (bb.getDisaster() instanceof GasLeak) {

					for (JButton b1 : buttons) {
						if (b1.getActionCommand().equals(x + "," + y)) {
							b1.setIcon(gasleak);
							b2 = b1;
						}
					}
				}
				if (bb.getDisaster() instanceof Collapse) {
					for (JButton b1 : buttons) {
						if (b1.getActionCommand().equals(x + "," + y)) {
							b1.setIcon(collapse);
							b2 = b1;
						}
					}
				}
			}
			for (Unit u : c.gete().getEmergencyUnits()) {
				if (u.getLocation().getX() == x && u.getLocation().getY() == y) {
					for (JButton b : units) {
						if (u.getUnitID().equals(b.getActionCommand())) {
							b2.setIcon(b.getIcon());
						}
					}
				}
			}
		}
		for (Citizen c1 : c.gete().helperCitizens()) {
			JButton b2 = null;
			if (c1.getDisaster() != null) {
				int x = c1.getLocation().getX();
				int y = c1.getLocation().getY();
				for (ResidentialBuilding bb : c.gete().ghelperBuildings()) {
					if (bb.getLocation().getX() == x && bb.getLocation().getY() == y) {
						continue;
					} else {
						if (c1.getDisaster() instanceof Injury) {
							for (JButton b : buttons) {
								if (b.getActionCommand().equals(x + "," + y)) {
									b.setIcon(injury);
									b2 = b;
								}
							}
						} else if (c1.getDisaster() instanceof Infection) {
							for (JButton b : buttons) {
								if (b.getActionCommand().equals(x + "," + y)) {
									b.setIcon(infection);
									b2 = b;
								}
							}
						}
					}
					for (Unit u : c.gete().getEmergencyUnits()) {
						if (u.getLocation().getX() == x && u.getLocation().getY() == y) {
							for (JButton b : units) {
								if (u.getUnitID().equals(b.getActionCommand())) {
									b2.setIcon(b.getIcon());
								}
							}
						}
					}
				}
				for (ResidentialBuilding bb : c.gete().ghelperBuildings()) {
					if (bb.getFireDamage() == 0 && bb.getGasLevel() == 0 && bb.getFoundationDamage() == 0) {
						int xb = bb.getLocation().getX();
						int yb = bb.getLocation().getY();
						for (JButton b : buttons) {
							if (b.getActionCommand().equals(xb + "," + yb)) {
								b.setIcon(Ibuilding);
								b2 = b;
							}
						}

					}
				}

				outer: for (Citizen c2 : c.gete().helperCitizens()) {
					boolean flag = false;
					if (c2.getState() == CitizenState.RESCUED || c2.getState() == CitizenState.SAFE
							|| c2.getState() == CitizenState.DECEASED) {
						int xc = c2.getLocation().getX();
						int yc = c2.getLocation().getY();
						for (ResidentialBuilding b : c.gete().ghelperBuildings()) {
							if (b.getLocation().getX() == xc && b.getLocation().getY() == yc) {
								flag = true;
								break;
							}
						}
						for (JButton b : buttons) {
							if (b.getActionCommand().equals(xc + "," + yc) && !flag) {
								b2 = b;
								b.setIcon(Icitizen);
							}
						}
					}
				}

			}

		}
		for (Citizen cd : c.gete().helperCitizens()) {
			if (cd.getState() == CitizenState.DECEASED) {
				int xx = cd.getLocation().getX();
				int yy = cd.getLocation().getY();
				for (JButton bd : buttons) {

					if (bd.getActionCommand().equals(xx + "," + yy)) {

						bd.setIcon(deadciti);

					}
				}
			}
		}
		for (ResidentialBuilding cd : c.gete().ghelperBuildings()) {
			if (cd.getStructuralIntegrity() == 0) {
				int xx = cd.getLocation().getX();
				int yy = cd.getLocation().getY();
				for (JButton bd : buttons) {
					if (bd.getActionCommand().equals(xx + "," + yy)) {
						bd.setIcon(deadbuil);

					}
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		StringBuilder hell = new StringBuilder();

		if (action.contains(",")) {

			StringBuilder sb = new StringBuilder();
			int x = Integer.parseInt(action.charAt(0) + "");
			int y = Integer.parseInt(action.charAt(2) + "");
			for (Unit u : c.gete().getEmergencyUnits()) {
				if (u.getLocation().getX() == x && u.getLocation().getY() == y) {
					if (u instanceof Ambulance) {
						sb.append("There is an Ambulance here" + "\n");
					}
					if (u instanceof GasControlUnit) {
						sb.append("There is a Gas Control Unit here" + "\n");
					}
					if (u instanceof FireTruck) {
						sb.append("There is a Fire Truck here" + "\n");
					}
				}
			}
			for (ResidentialBuilding b : c.gete().ghelperBuildings()) {

				if (b.getLocation().getX() == x && b.getLocation().getY() == y) {
					sb.append("Building: " + b.getLocation().getX() + "," + b.getLocation().getY() + "\n");
					if (b.getGasLevel() != 0) {
						sb.append("The building suffers from Gas Leak" + "\n");
					} else if (b.getFireDamage() != 0) {
						sb.append("The building suffers from Fire" + "\n");
					} else if (b.getFoundationDamage() != 0) {
						sb.append("The building suffers from Collapse" + "\n");
					}

					sb.append("Structural Integrity: " + b.getStructuralIntegrity() + "\n" + "Foundation Damage: "
							+ b.getFoundationDamage() + "\n" + "Fire Damage: " + b.getFireDamage() + "\n"
							+ "Gas Level: " + b.getGasLevel() + "\n" + "number of occupents " + b.getOccupants().size()
							+ "\n");
				}
			}
			for (Citizen c1 : c.gete().helperCitizens()) {
				if (c1.getLocation().getX() == x && c1.getLocation().getY() == y) {
					if (c1.getBloodLoss() != 0) {
						sb.append("This Citize suffers from Blood Loss" + "\n");
					} else if (c1.getToxicity() != 0) {
						sb.append("This Citizen suffers from Toxcicty" + "\n");
					}
					sb.append("Name: " + c1.getName() + "\n" + "State: " + c1.getState() + "\n" + "ID: "
							+ c1.getNationalID() + "\n" + "Age: " + c1.getAge() + "\n" + "Blood Loss: "
							+ c1.getBloodLoss() + "\n" + "Toxcicty: " + c1.getToxicity() + "\n" + "Health Points"
							+ c1.getHp() + "\n");
				}
			}
			Data.setText(sb.toString());
		} else {

			if (action.equals("comboBoxChanged")) {
				String s = rescuable.getSelectedItem().toString();

				if (unitID == null) {
					JOptionPane.showMessageDialog(frame, "CHOOCE THE UNIT FIRST");
				} else {
					if (s.charAt(0) == 'C') {
						s = s.substring(9);
						outer: for (Unit u : c.gete().getEmergencyUnits()) {
							if (u.getUnitID().equals(unitID)) {
								if (u instanceof Evacuator || u instanceof FireTruck || u instanceof GasControlUnit) {
									JOptionPane.showMessageDialog(frame,
											"WRONG UNIT , choose a new unit with a new target");
									unitID = null;
									break outer;
								}
								for (Citizen cc : c.gete().helperCitizens()) {

									if (cc.getNationalID().equals(s)) {

										try {

											u.respond(cc);
											if (u.getState() == UnitState.TREATING) {
												JButton bb = null;

												for (JButton b : units) {
													if (b.getActionCommand().equals(u.getUnitID())) {
														bb = b;
														break;
													}
												}
												p1.remove(bb);
												p2.remove(bb);
												p3.add(bb);
												p1.revalidate();
												p2.revalidate();
												p3.revalidate();
												frame.repaint();
											}
										} catch (IncompatibleTargetException e1) {

											JOptionPane.showMessageDialog(frame, "Incompatible Target");
										} catch (CannotTreatException e1) {

											JOptionPane.showMessageDialog(frame, "this citizen cannot be treated ");
										}
										unitID = null;
										break outer;
									}

								}
							}
						}
					} else {
						s = s.substring(10);
						int x = Integer.parseInt(s.charAt(0) + "");
						int y = Integer.parseInt(s.charAt(2) + "");

						outer: for (Unit u : c.gete().getEmergencyUnits()) {
							if (u.getUnitID().equals(unitID)) {

								if (u instanceof Ambulance || u instanceof DiseaseControlUnit) {
									JOptionPane.showMessageDialog(frame,
											"WRONG UNIT , choose a new unit with a new target");
									unitID = null;

									break outer;
								}
								for (ResidentialBuilding bb : c.gete().ghelperBuildings()) {

									if (bb.getLocation().getX() == x && bb.getLocation().getY() == y) {

										try {
											u.respond(bb);

										} catch (IncompatibleTargetException e1) {
											JOptionPane.showMessageDialog(frame, "IncompatibleTarget ");
										} catch (CannotTreatException e1) {
											JOptionPane.showMessageDialog(frame, "this building cannot be treated ");
										}
										unitID = null;
										break outer;
									}

								}
							}
						}
					}
				}
			} else {
				unitID = action;
				for (Unit u : c.gete().getEmergencyUnits()) {

					if (action.equals(u.getUnitID())) {
						if (u instanceof Evacuator) {
							String target = "";
							if (u.getTarget() != null) {
								target = +((ResidentialBuilding) u.getTarget()).getLocation().getX() + ","
										+ ((ResidentialBuilding) u.getTarget()).getLocation().getY();
							}
							hell.append("Evacutor " + "\n");
							hell.append("UnitId :" + u.getUnitID() + "\n" + "target building :" + "\n"
									+ "steps per cycle: " + +u.getStepsPerCycle() + "number of passengers :"
									+ ((Evacuator) u).getPassengers().size() + "\n" + "location:"
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\n" + "State:"
									+ u.getState() + "\n" + "Maximum apacity:" + ((Evacuator) u).getMaxCapacity()
									+ "\n");
							hell.append("passengers :");
							for (Citizen c1 : ((Evacuator) u).getPassengers()) {
								hell.append("Name: " + c1.getName() + "\n" + "State: " + c1.getState() + "\n" + "ID: "
										+ c1.getNationalID() + "\n" + "Age: " + c1.getAge() + "\n" + "Blood Loss: "
										+ c1.getBloodLoss() + "\n" + "Toxcicty: " + c1.getToxicity() + "\n"
										+ "Health Points" + c1.getHp() + "\n");
							}
						} else {
							String target = "";
							if (u.getTarget() != null && u.getTarget() instanceof Citizen) {
								target = "Citizen" + ((Citizen) u.getTarget()).getNationalID();
							} else if (u.getTarget() != null) {
								target = "building :" + u.getTarget().getLocation().getX() + ","
										+ u.getTarget().getLocation().getY();

							}
							if (u instanceof Ambulance) {
								hell.append("Ambulance");

							} else if (u instanceof FireTruck) {
								hell.append("Firetruck");
							} else if (u instanceof DiseaseControlUnit) {
								hell.append("disease control unit");
							} else {
								hell.append("gas control unit");
							}

							hell.append("UnitId :" + u.getUnitID() + "\n" + "steps per cycle: " + u.getStepsPerCycle()
									+ "\n" + "location:" + u.getLocation().getX() + "," + u.getLocation().getY() + "\n"
									+ "State:" + u.getState() + "\n" + "target :" + target);
						}
					}
				}

				un.setText(hell.toString());

			}
		}
		if(action.equals("Call a friend")) {
			int counter1 = 0;
			int counter2 = 0;
			for (Citizen cc : c.gete().helperCitizens()) {
				if (cc.getState() == CitizenState.DECEASED) {
					counter1++;
				}
			}
			for (ResidentialBuilding b : c.gete().ghelperBuildings()) {
				if(b.getStructuralIntegrity() == 0) {
					counter2++;
				}
			}
			sbs.append("Number of dead people is: " + counter1 );
			sbs.append("Number of fallen down buildings is: " + counter2 );
			status.setText(sbs.toString());
			
			
			
			
		}
	}

}
