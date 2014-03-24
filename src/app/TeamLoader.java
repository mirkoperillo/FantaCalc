package app;

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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import app.model.Player;

public class TeamLoader {

	private static Logger logger = Logger.getLogger(TeamLoader.class);

	private List<Player> list = new ArrayList<Player>();

	public List<Player> read(File f) {
		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			logger.error(e);
		}

		String buffer = null;
		String[] parts = null;
		Player p = null;
		try {
			while ((buffer = r.readLine()) != null) {
				buffer = buffer.trim();
				// string vuota o commento salta a prossima riga
				if (buffer.length() == 0 || buffer.startsWith("#")) {
					continue;
				}
				parts = buffer.split(",");
				p = new Player();
				p.setName(parts[0]);
				p.setRole(new Short(parts[1]));
				list.add(p);
			}
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return list;

	}
}
