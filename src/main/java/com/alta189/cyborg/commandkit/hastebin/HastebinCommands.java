package com.alta189.cyborg.commandkit.hastebin;

import com.alta189.cyborg.api.command.CommandContext;
import com.alta189.cyborg.api.command.CommandResult;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.command.ReturnType;
import com.alta189.cyborg.commandkit.util.HttpUtil;

import java.util.regex.Pattern;

import static com.alta189.cyborg.api.command.CommandResultUtil.get;

public class HastebinCommands {

	private static final Pattern urlPattern = Pattern.compile("((https?):((//)|(\\\\\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)");

	public CommandResult hastebin(CommandSource source, CommandContext context) {
		if (source.getSource() != CommandSource.Source.USER) {
			return null;
		}

		if (context.getArgs() == null || context.getArgs().length < 1) {
			return get(ReturnType.MESSAGE, "Correct usage is .hastebin <url>", source, context);
		}

		String url = context.getArgs()[0];

		if (!urlPattern.matcher(url).matches()) {
			return get(ReturnType.MESSAGE, "That was not a url! Correct usage is .hastebin <url>", source, context);
		}

		if (PastebinUtil.isPastebinURL(url)) {
			String pasteId = PastebinUtil.getPasteId(url);
			String lang = PastebinUtil.getPasteLanguage(pasteId);
			String ext = PastebinUtil.getExtensionFromLanuage(lang);
			String data = PastebinUtil.getRawContent(pasteId);

			return get(ReturnType.MESSAGE, "Here is your prettier paste: " + HttpUtil.hastebin(data) + ext, source, context);
		} else if (PastieUtil.isPastieUrl(url)) {
			return get(ReturnType.MESSAGE, "Here is your prettier paste: " + HttpUtil.hastebin(PastieUtil.getRawContents(url)), source, context);
		} else {
			return get(ReturnType.MESSAGE, "I currently only support pastebin.com and pastie.org!", source, context);
		}
	}
}
