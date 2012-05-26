/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of CommandKit
 *
 * CommandKit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CommandKit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.cyborg.commandkit.hastebin;

import com.alta189.cyborg.commandkit.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PastebinUtil {

	private static final Pattern pastebinUrlPatter = Pattern.compile("pastebin\\.com\\/([0-9A-Za-z]){1,}");
	private static final String pastebinUrl = "http://pastebin.com/";
	private static final String pastebinRawUrl = "http://pastebin.com/raw.php?i=";
	private static final Map<String, String> languageToExtention = new HashMap<String, String>();

	static {
		setupMap();
	}

	public static boolean isPastebinURL(String url) {
		return pastebinUrlPatter.matcher(url).find();
	}

	public static String getPasteId(String url) {
		String id = null;

		Matcher matcher = pastebinUrlPatter.matcher(url);
		if (matcher.find()) {
			String match = matcher.group();
			id = match.substring(match.indexOf("/") + 1);
		}
		return id;
	}

	public static String getPasteLanguage(String pasteId) {
		Document doc = Jsoup.parse(HttpUtil.readURL(pastebinUrl + pasteId));
		Element codeFrame = doc.getElementById("code_frame");
		for (Element e : codeFrame.getAllElements()) {
			if (!e.id().equalsIgnoreCase("code_buttons") && e.parent().equals(codeFrame)) {
				return e.className();
			}
		}
		return null;
	}

	public static String getExtensionFromLanuage(String lang) {
		String result = languageToExtention.get(lang);
		if (result == null) {
			result = "";
		}
		return result;
	}

	public static String getRawContent(String pasteId) {
		return HttpUtil.readURL(pastebinRawUrl + pasteId);
	}

	private static void setupMap() {
		languageToExtention.put("4cs", ".4cs");
		languageToExtention.put("6502kickass", ".prg");
		languageToExtention.put("abap", ".abap");
		languageToExtention.put("actionscript", ".as");
		languageToExtention.put("actionscript3", ".as3");
		languageToExtention.put("autoconf", ".autoconf");
		languageToExtention.put("autohotkey", ".ahk");
		languageToExtention.put("bash", ".sh");
		languageToExtention.put("bf", ".bf");
		languageToExtention.put("c", ".c");
		languageToExtention.put("csharp", ".cs");
		languageToExtention.put("cpp", ".cpp");
		languageToExtention.put("coffeescript", ".coffee");
		languageToExtention.put("css", ".css");
		languageToExtention.put("go", ".go");
		languageToExtention.put("groovy", ".groovy");
		languageToExtention.put("haskell", ".hs");
		languageToExtention.put("ini", ".ini");
		languageToExtention.put("java", ".java");
		languageToExtention.put("java5", "java");
		languageToExtention.put("javascript", ".js");
		languageToExtention.put("lua", ".lua");
		languageToExtention.put("text", "");
		languageToExtention.put("php", ".php");
		languageToExtention.put("python", ".py");
		languageToExtention.put("yaml", "yml");
		languageToExtention.put("xml", ".xml");
	}
}
