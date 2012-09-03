package com.alta189.cyborg.commandkit.wolframalpha;

import com.alta189.cyborg.api.command.CommandContext;
import com.alta189.cyborg.api.command.CommandResult;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.command.CommandSource.Source;
import com.alta189.cyborg.api.command.ReturnType;
import com.alta189.cyborg.api.command.annotation.Command;
import com.alta189.cyborg.api.command.annotation.Usage;
import com.alta189.cyborg.api.util.StringUtils;
import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

import static com.alta189.cyborg.api.command.CommandResultUtil.get;

public class WolframAlphaCommands {
	private static final WAEngine ENGINE = new WAEngine();
	private static final String APP_ID = "RKKE3L-Q7PYHA92X3";

	static {
		ENGINE.setAppID(APP_ID);
		ENGINE.addFormat("plaintext");

	}

	@Command(name = "wolframalpha", desc = "Runs a WolframAlpha computational engine lookup", aliases = {"wa", "wolframalpha"})
	@Usage(".wa <lookup keywords>")
	public CommandResult lookup(CommandSource source, CommandContext context) {
		if (source.getSource() == Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		if (context.getArgs() == null || context.getArgs().length < 1) {
			return get(ReturnType.MESSAGE, "Correct usage is .wa <lookup keywords>...", source, context);
		}
		final WAQueryResult results;
		try {
			results = ENGINE.performQuery(ENGINE.createQuery(StringUtils.toString(context.getArgs())));
		} catch (Exception ex) {
			return get(ReturnType.MESSAGE, "Query could not be performed: " + ex.getMessage(), source, context);
		}
		if (results.isError()) {
			return get(ReturnType.MESSAGE, "Query error: " + results.getErrorMessage(), source, context);
		} else if (!results.isSuccess()) {
			return get(ReturnType.MESSAGE, "No results available.", source, context);
		} else {
			final StringBuilder builder = new StringBuilder();
			for (WAPod pod : results.getPods()) {
				if (!pod.isError()) {
					builder.append("{[").append(pod.getTitle()).append("] ");
					for (WASubpod subpod : pod.getSubpods()) {
						for (Object element : subpod.getContents()) {
							if (element instanceof WAPlainText) {
								builder.append(((WAPlainText) element).getText().
										replaceAll("\\Q|\\E", ":").
										replaceAll("\n", " | ").
										replaceAll("\\s+", " "));
							}
						}
					}
					builder.append("} ");
				}
			}
			return get(ReturnType.MESSAGE, builder.toString(), source, context);
		}
	}
}
