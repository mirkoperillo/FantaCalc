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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NorthPanel extends JPanel {

	private static final long serialVersionUID = -7366184237972357430L;

	/**
	 * Create the panel.
	 */
	public NorthPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblScegliSquadra = new JLabel("Scegli squadra");
		lblScegliSquadra.setFont(new Font("Arial", Font.BOLD, 11));
		add(lblScegliSquadra);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "", "ane",
				"avanc", "bortol", "cugi", "loris", "mirko", "pelle", "polz" }));
		add(comboBox);

	}

}
