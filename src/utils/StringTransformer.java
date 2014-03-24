package utils;

/**
 * Copyright (C) 2008 Mirko Perillo
 * 
 * This file is part of FantaCalc.
 * 
 * FantaCalc is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * FantaCalc is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * FantaCalc. If not, see <http://www.gnu.org/licenses/>.
 */
public class StringTransformer {

	public String onlyChars(String s) {
		char[] ss = s.toCharArray();
		char[] buf = new char[ss.length];
		int j = 0;
		for (int i = 0; i < ss.length; i++) {
			if (Character.isLetter(ss[i])) {
				buf[j] = ss[i];
				j++;
			}
		}

		return new String(buf).trim();
	}

}
