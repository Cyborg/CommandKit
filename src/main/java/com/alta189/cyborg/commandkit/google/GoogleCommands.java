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
package com.alta189.cyborg.commandkit.google;

import com.alta189.cyborg.api.command.CommandContext;
import com.alta189.cyborg.api.command.CommandResult;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.command.ReturnType;
import com.alta189.cyborg.api.command.annotation.Command;
import com.alta189.cyborg.api.command.annotation.Usage;
import com.alta189.cyborg.api.util.StringUtils;
import com.alta189.cyborg.commandkit.util.HttpUtil;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.pircbotx.Colors;

import static com.alta189.cyborg.api.command.CommandResultUtil.get;

public class GoogleCommands {
	public static final String webUrl = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&safe=moderate&q=";
	public static final String imagesUrl = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&safe=moderate&q=";

	@Command(name = "google", desc = "Google Web Search", aliases = {"g", "goog", "search"})
	@Usage(".google <your search here>...")
	public CommandResult google(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		
		if (context.getArgs() == null || context.getArgs().length < 1) {
			return get(ReturnType.MESSAGE, "Correct usage is .google <your search here>...", source, context);
		}
		
		String query = HttpUtil.encode(StringUtils.toString(context.getArgs()));

		if (query == null) {
			return null;
		}
		
		String json = HttpUtil.readURL(webUrl + query);
		
		if (json == null || json.isEmpty()) {
			return get(ReturnType.MESSAGE, "No results found", source, context);
		}
		
		GoogleResults results = new Gson().fromJson(json, GoogleResults.class);
		
		if (results != null) {
			if (results.getResponseData().getResults() != null) {
				if (results.getResponseData().getResults().size() >= 1) {
					GoogleResults.Result result = results.getResponseData().getResults().get(0);
					StringBuilder builder = new StringBuilder();
					builder.append(result.getUrl())
							.append(" -- ").
							append(Colors.BOLD).
							append(result.getTitle())
							.append(Colors.NORMAL)
							.append(": ")
							.append(Jsoup.parse(result.getContent()).text());
					return get(ReturnType.MESSAGE, builder.toString(), source, context);
				}
			}
		}

		return get(ReturnType.NOTICE, "No results found", source, context);
	}

	@Command(name = "googleimage", desc = "Google Immage Search", aliases = {"gis", "gimage", "image"})
	@Usage(".googleimage <your search here>...")
	public CommandResult googleimage(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}

		if (context.getArgs() == null || context.getArgs().length < 1) {
			return get(ReturnType.MESSAGE, "Correct usage is .googleimage <your search here>...", source, context);
		}

		String query = HttpUtil.encode(StringUtils.toString(context.getArgs()));

		if (query == null) {
			return null;
		}

		String json = HttpUtil.readURL(imagesUrl + query);

		if (json == null || json.isEmpty()) {
			return get(ReturnType.MESSAGE, "No results found", source, context);
		}

		GoogleResults results = new Gson().fromJson(json, GoogleResults.class);

		if (results != null) {
			if (results.getResponseData().getResults() != null) {
				if (results.getResponseData().getResults().size() >= 1) {
					GoogleResults.Result result = results.getResponseData().getResults().get(0);
					return get(ReturnType.MESSAGE, result.getUrl(), source, context);
				}
			}
		}

		return get(ReturnType.MESSAGE, "No results found", source, context);
	}
}
