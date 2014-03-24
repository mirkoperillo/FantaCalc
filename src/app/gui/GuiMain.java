package app.gui;

/**
 * Copyright (C) 2008 Mirko Perillo

 This file is part of FantaCalc.

 FantaCalc is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 FantaCalc is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with FantaCalc.  If not, see <http://www.gnu.org/licenses/>.
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import app.TeamCompleter;
import app.model.Player;

public class GuiMain extends JFrame implements ActionListener, ItemListener {

	private static final Logger logger = Logger.getLogger(GuiMain.class);
	private JPanel formazione;
	private JButton okButton;

	private JComboBox squadre;

	String pathVoti;

	String squadraAttiva;

	private static final long serialVersionUID = -3105473766201188342L;

	// TODO da spostare in classe modello
	int portieriSchierati = 0;
	int difensoriSchierati = 0;
	int ccSchierati = 0;
	int attaccantiSchierati = 0;
	int panchinari = 0;
	List<String> playerInfo = new ArrayList<String>();

	public GuiMain(String pathVoti) {
		this.pathVoti = pathVoti;
		init();
		this.setSize(700, 700);
		this.setResizable(false);
		setVisible(true);
	}

	private void init() {
		formazione = new JPanel(new GridLayout(26, 4, 0, 3));

		okButton = new JButton("OK");
		okButton.addActionListener(this);

		List<String> teams = new ArrayList<String>(getTeams());
		teams.add(0, "");
		Collections.sort(teams);
		squadre = new JComboBox(teams.toArray(new String[1]));
		squadre.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				squadraAttiva = (String) ((JComboBox) arg0.getSource())
						.getSelectedItem();
				formazione.removeAll();
				initPanelloFormazione(formazione);
				formazione.revalidate();

			}
		});

		this.add(squadre, BorderLayout.NORTH);
		this.add(formazione, BorderLayout.CENTER);

		// this.add(new SouthPanel(this), BorderLayout.SOUTH);
		this.add(okButton, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public int getPortieriSchierati() {
		return portieriSchierati;
	}

	public void setPortieriSchierati(int portieriSchierati) {
		this.portieriSchierati = portieriSchierati;
	}

	public int getDifensoriSchierati() {
		return difensoriSchierati;
	}

	public void setDifensoriSchierati(int difensoriSchierati) {
		this.difensoriSchierati = difensoriSchierati;
	}

	public int getCcSchierati() {
		return ccSchierati;
	}

	public void setCcSchierati(int ccSchierati) {
		this.ccSchierati = ccSchierati;
	}

	public int getAttaccantiSchierati() {
		return attaccantiSchierati;
	}

	public void setAttaccantiSchierati(int attaccantiSchierati) {
		this.attaccantiSchierati = attaccantiSchierati;
	}

	public int getPanchinari() {
		return panchinari;
	}

	public void setPanchinari(int panchinari) {
		this.panchinari = panchinari;
	}

	private JPanel initPanelloFormazione(JPanel toUpdate) {

		String rosaPath = System.getProperty("user.dir") + "/"
				+ "resources/teams" + "/" + squadraAttiva + ".txt";
		try {
			BufferedReader bread = new BufferedReader(new FileReader(rosaPath));
			String line = null;
			int rows = 1;
			while ((line = bread.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("#")) {
					continue;
				}
				if (rows > 25) {
					break;
				}
				rows++;
				System.out.println(line);
				String[] parts = line.split(",");
				JLabel nomeGiocatore = new JLabel(parts[0]);
				nomeGiocatore.setFont(new Font("Arial", Font.BOLD, 11));

				JRadioButton titolareButton = new JRadioButton();
				titolareButton.setActionCommand("T");
				JRadioButton panchinaButton = new JRadioButton();
				panchinaButton.setActionCommand("P");
				JRadioButton tribunaButton = new JRadioButton();
				tribunaButton.setActionCommand("");

				ButtonGroup buttonGroup = new ButtonGroup();
				buttonGroup.add(titolareButton);
				buttonGroup.add(panchinaButton);
				buttonGroup.add(tribunaButton);

				JComboBox box = new JComboBox(new Integer[] { 1, 2, 3, 4, 5, 6,
						7 });

				box.addItemListener(this);

				if (parts.length > 1) {
					if (parts[1].equals("T")) {
						titolareButton.setSelected(parts[1].equals("T"));

					}
					if (parts[1].equals("P")) {
						panchinaButton.setSelected(parts[1].equals("P"));
						box.setSelectedItem(new Integer(parts[2]));

					}
					if (parts[1].equals("")) {
						tribunaButton.setSelected(parts[1].equals(""));
					}
				}

				formazione.add(nomeGiocatore);
				formazione.add(titolareButton);
				formazione.add(panchinaButton);
				formazione.add(tribunaButton);
				formazione.add(box);

				// titolareButton.addChangeListener(new MyChangeListener());
				// panchinaButton.addChangeListener(new MyChangeListener());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return formazione;
	}

	public static void main(String[] args) {
		new GuiMain(args[0]);
	}

	private List<String> getTeams() {
		String teamsFolder = System.getProperty("user.dir")
				+ "/resources/teams";
		File folder = new File(teamsFolder);
		List<String> teams = new ArrayList<String>();
		for (String team : folder.list()) {
			teams.add(team.substring(0, team.lastIndexOf(".")));
		}
		return teams;

	}

	private void save(Writer w) throws Exception {
		int index = 0;
		Component c = null;
		while (index < formazione.getComponentCount()
				&& (c = formazione.getComponent(index)) != null) {
			String giocatore = ((JLabel) c).getText();
			String schierato = ((JRadioButton) formazione
					.getComponent(index + 1)).isSelected() ? "T" : "";
			schierato = ((JRadioButton) formazione.getComponent(index + 2))
					.isSelected() ? "P" : schierato;
			schierato = ((JRadioButton) formazione.getComponent(index + 3))
					.isSelected() ? "" : schierato;

			String posto = ((JRadioButton) formazione.getComponent(index + 2))
					.isSelected() ? ((JComboBox) formazione
					.getComponent(index + 4)).getSelectedItem().toString() : "";
			w.write(giocatore + (schierato.length() > 0 ? "," + schierato : "")
					+ (posto.length() > 0 ? "," + posto : "") + "\n");
			index = index + 5;
		}

		w.close();

	}

	public void actionPerformed(ActionEvent event) {
		int index = 0;
		int titIndex = 0;
		int panIndex = 0;

		Component c = null;
		Element[] tit = new Element[11];
		Element[] pan = new Element[7];
		try {
			FileWriter fwrite = new FileWriter(System.getProperty("user.dir")
					+ "/" + "resources" + "/" + squadraAttiva + ".txt");
			save(fwrite);
		} catch (Exception e) {
			logger.error("Error writing settings of teams");
		}

		while (index < formazione.getComponentCount()
				&& (c = formazione.getComponent(index)) != null) {
			if (c instanceof JLabel) {
				if (((JRadioButton) formazione.getComponent(index + 1))
						.isSelected()) {
					tit[titIndex] = new Element(((JLabel) c).getText(), -1);
					titIndex++;
				}
				if (((JRadioButton) formazione.getComponent(index + 2))
						.isSelected()) {
					pan[panIndex] = new Element(((JLabel) c).getText(),
							(Integer) ((JComboBox) formazione
									.getComponent(index + 4)).getSelectedItem());
					panIndex++;
				}
			}
			index = index + 5;
		}

		List<Element> el = new ArrayList<Element>(Arrays.asList(tit));

		List<Element> p = Arrays.asList(pan);
		Collections.sort(p);
		el.addAll(p);
		List<Player> list = new ArrayList<Player>();
		for (Element temp : el) {
			Player pl = new Player();
			pl.setName(temp.nome);
			list.add(pl);
		}

		// preso da Main
		String path = System.getProperty("user.dir");
		String newline = System.getProperty("line.separator");

		String fileName = squadraAttiva + ".txt";
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(path + File.separator + "config"
					+ File.separator + "config.properties"));
		} catch (FileNotFoundException e2) {
			logger.error(e2);
		} catch (IOException e2) {
			logger.error(e2);
		}

		TeamCompleter tc = new TeamCompleter();
		list = tc.completeTeam(list, pathVoti);
		Float total = tc.calculate(list);
		File directoryOut = new File(path + File.separator + "resources"
				+ File.separator + prop.getProperty("nome_directory_result"));
		File o = null;
		boolean createDirectory = false;
		if (!directoryOut.exists() || directoryOut.isFile()) {
			createDirectory = directoryOut.mkdir();
		}
		o = new File(directoryOut.getAbsolutePath() + File.separator + fileName);

		FileWriter out = null;
		try {
			out = new FileWriter(o);
		} catch (IOException e) {
			logger.error(e);
		}
		try {
			out.write("g = goal segnati; ag = autogoal; gs = goal subiti; ass = assist; amm = ammonizione;"
					+ newline
					+ "esp = espulsione; rp = rigore parato; rse = rigore segnato; rsb = rigore sbagliato");
			out.write(newline + newline);
			out.write("TOTAL SCORE: " + total + newline);
		} catch (IOException e1) {
			logger.error(e1);
		}

		for (int i = 0; i < list.size(); i++) {
			Player pp = list.get(i);
			try {
				if (pp != null) {
					out.write(pp.toString());
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}

		try {
			out.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private int getComponetIndex(Component c) {
		if (c != null) {
			java.awt.Container container = c.getParent();
			for (int index = 0; index < container.getComponentCount(); index++) {
				if (container.getComponent(index).equals(c)) {
					return index;
				}
			}
		}

		return -1;
	}

	public void itemStateChanged(ItemEvent ie) {
		Component source = (Component) ie.getSource();
		if (ie.getStateChange() == ItemEvent.ITEM_STATE_CHANGED) {
			int index = getComponetIndex(source);
			if (index != -1) {
				((JRadioButton) source.getParent().getComponent(index - 3))
						.setSelected(false);
				((JRadioButton) source.getParent().getComponent(index - 2))
						.setSelected(true);
				((JRadioButton) source.getParent().getComponent(index - 1))
						.setSelected(false);
				this.validate();
			}
		}
	}

	private void updatePlayerNumber(int index) {
		if (playerInfo.get(index).equals("p")) {
			portieriSchierati--;
		}

		if (playerInfo.get(index).equals("d")) {
			difensoriSchierati--;
		}

		if (playerInfo.get(index).equals("c")) {
			ccSchierati--;
		}

		if (playerInfo.get(index).equals("a")) {
			attaccantiSchierati--;
		}
	}

	private class MyChangeListener implements ChangeListener {

		public void stateChanged(ChangeEvent arg0) {
			JRadioButton button = (JRadioButton) arg0.getSource();
			int index = getComponetIndex(button);
			if (button.getActionCommand().equals("T")) {
				updatePlayerNumber(index);
			}
			if (button.getActionCommand().equals("P")) {
				panchinari--;
			}

			if (button.getActionCommand().equals("")) {

			}

		}

	}
}

class Element implements Comparable<Element> {
	String nome;
	int posizione;

	public Element(String n, int p) {
		nome = n;
		posizione = p;
	}

	public int compareTo(Element arg0) {
		int result = 0;
		if (this.posizione > arg0.posizione) {
			result = 1;
		}
		if (this.posizione < arg0.posizione) {
			result = -1;
		}
		return result;
	}

}
