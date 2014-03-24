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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SouthPanel extends JPanel {

	private static final long serialVersionUID = 4267746989942817922L;
	private JTextField portieri;
	private JTextField centrocampisti;
	private JTextField difensori;
	private JTextField attaccanti;
	private JTextField panchina;
	private JTextField punteggio;

	private GuiMain parent;

	/**
	 * Create the panel.
	 */
	public SouthPanel(GuiMain parent) {
		this.parent = parent;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblGiocatoriSchierati = new JLabel("Giocatori schierati");
		lblGiocatoriSchierati.setFont(new Font("Arial", Font.BOLD, 11));
		GridBagConstraints gbc_lblGiocatoriSchierati = new GridBagConstraints();
		gbc_lblGiocatoriSchierati.anchor = GridBagConstraints.WEST;
		gbc_lblGiocatoriSchierati.insets = new Insets(0, 0, 5, 5);
		gbc_lblGiocatoriSchierati.gridx = 0;
		gbc_lblGiocatoriSchierati.gridy = 0;
		add(lblGiocatoriSchierati, gbc_lblGiocatoriSchierati);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblPortieri = new JLabel("Portieri");
		lblPortieri.setFont(new Font("Arial", Font.PLAIN, 10));
		panel.add(lblPortieri);

		portieri = new JTextField();
		portieri.setEditable(false);
		panel.add(portieri);
		portieri.setColumns(3);
		portieri.setText("" + parent.getPortieriSchierati());

		JLabel lblDifensori = new JLabel("Difensori");
		lblDifensori.setFont(new Font("Arial", Font.PLAIN, 10));
		panel.add(lblDifensori);

		difensori = new JTextField();
		difensori.setEditable(false);
		panel.add(difensori);
		difensori.setColumns(3);
		difensori.setText("" + parent.getDifensoriSchierati());

		JLabel lblCentrocampisti_1 = new JLabel("Centrocampisti");
		lblCentrocampisti_1.setFont(new Font("Arial", Font.PLAIN, 10));
		panel.add(lblCentrocampisti_1);

		centrocampisti = new JTextField();
		centrocampisti.setEditable(false);
		panel.add(centrocampisti);
		centrocampisti.setColumns(3);
		centrocampisti.setText("" + parent.getCcSchierati());

		JLabel lblAttaccanti = new JLabel("Attaccanti");
		lblAttaccanti.setFont(new Font("Arial", Font.PLAIN, 10));
		panel.add(lblAttaccanti);

		attaccanti = new JTextField();
		attaccanti.setEditable(false);
		panel.add(attaccanti);
		attaccanti.setColumns(3);
		attaccanti.setText("" + parent.getAttaccantiSchierati());

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblPanchina = new JLabel("Panchina");
		lblPanchina.setFont(new Font("Arial", Font.PLAIN, 10));
		panel_1.add(lblPanchina);

		panchina = new JTextField();
		panchina.setEditable(false);
		panel_1.add(panchina);
		panchina.setColumns(3);
		panchina.setText("" + parent.getPanchinari());

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 3;
		add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnCalcola = new JButton("Calcola");
		btnCalcola.setFont(new Font("Arial", Font.BOLD, 11));
		panel_2.add(btnCalcola);

		punteggio = new JTextField();
		punteggio.setEditable(false);
		panel_2.add(punteggio);
		punteggio.setColumns(3);

	}
}
