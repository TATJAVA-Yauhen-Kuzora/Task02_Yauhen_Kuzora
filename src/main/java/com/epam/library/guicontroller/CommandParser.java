/**
 * 
 */
package com.epam.library.guicontroller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eugene13
 *
 */
class CommandParser {
	private String regExp;

	private static final CommandParser instance = new CommandParser();

	private CommandParser() {
		this.regExp = "[0-9A-Za-z-_]+";
	}

	ArrayList<String> parse(String request) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(request);
		ArrayList<String> arrayList = new ArrayList<>();
		while (matcher.find()) {
			arrayList.add(matcher.group());
		}
		return arrayList;
	}

	static CommandParser getInstance() {
		return instance;
	}
}
