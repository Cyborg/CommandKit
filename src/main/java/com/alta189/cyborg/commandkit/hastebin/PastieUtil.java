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
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

public class PastieUtil {

	public static final Pattern pastieUrlPatter = Pattern.compile("pastie\\.org\\/([0-9A-Za-z]{1,}|private\\/[0-9A-Za-z]{1,})");

	public static boolean isPastieUrl(String url) {
		return pastieUrlPatter.matcher(url).find();
	}

	public static String getRawContents(String url) {
		Elements elements = Jsoup.parse(HttpUtil.readURL(url)).getElementsByAttributeValue("class", "textmate-source");
		if (elements.size() == 1) {
			return elements.get(0).text();
		}
		return null;
	}
}
