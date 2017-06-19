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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import app.model.Player;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < 2) {
			System.out.println("java -jar fantacalculator.jar fileFormazione fileVoti");
			System.exit(-1);
		}
		String newline = System.getProperty("line.separator");
		String path = System.getProperty("user.dir");
		String pathVoti = args[1];
		String pathFile = args[0];
		String fileName = pathFile.substring(pathFile.lastIndexOf(File.separator) + 1, pathFile.length());

		logger.debug("File Formazione : " + pathFile);
		logger.debug("File Voti : " + pathVoti);

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(path + File.separator + "config" + File.separator + "config.properties"));
		} catch (FileNotFoundException e2) {
			logger.error(e2);
		} catch (IOException e2) {
			logger.error(e2);
		}

		TeamLoader tl = new TeamLoader();
		TeamManager tc = new TeamManager(new FileMatchDataManager(fileName));
		List<Player> list = tl.read(new File(pathFile));
		list = tc.completeTeam(list, pathVoti);
		Float total = tc.calculateScore(list);
		File directoryOut = new File(
				path + File.separator + "resources" + File.separator + prop.getProperty("nome_directory_result"));
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
			out.write("g = goal segnati; ag = autogoal; gs = goal subiti; ass = assist; amm = ammonizione;" + newline
					+ "esp = espulsione; rp = rigore parato; rse = rigore segnato; rsb = rigore sbagliato");
			out.write(newline + newline);
			out.write("TOTAL SCORE: " + total + newline);
		} catch (IOException e1) {
			logger.error(e1);
		}

		for (int i = 0; i < list.size(); i++) {
			Player p = list.get(i);
			try {
				out.write(p.toString());
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
}
