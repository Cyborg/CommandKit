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
package com.alta189.cyborg.commandkit.twitter;

import com.alta189.cyborg.api.command.CommandContext;
import com.alta189.cyborg.api.command.CommandResult;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.command.ReturnType;
import com.alta189.cyborg.api.command.annotation.Command;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.pircbotx.Colors;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

import static com.alta189.cyborg.api.command.CommandResultUtil.get;

public class TwitterCommands {
	private static final PeriodFormatter timeFormatter = new PeriodFormatterBuilder()
			.appendYears().appendSuffix(" years").appendSeparator(", ")
			.appendMonths().appendSuffix(" months").appendSeparator(", ")
			.appendWeeks().appendSuffix(" weeks").appendSeparator(", ")
			.appendDays().appendSuffix(" days").appendSeparator(", ")
			.appendHours().appendSuffix(" hours").appendSeparator(", ")
			.appendMinutes().appendSuffix(" minutes").appendSeparator(", ")
			.appendSeconds().appendSuffix(" seconds")
			.toFormatter();
	private static final String lineBreak = System.getProperty("line.separator");
	private final String consumerKey;
	private final String consumerSecret;
	private final Twitter twitter;


	public TwitterCommands(String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret);
		twitter = new TwitterFactory(cb.build()).getInstance();
	}

	@Command(name = "twitter", desc = "displays the last tweet of a twitter user", aliases = {"twit"})
	public CommandResult twitter(CommandSource source, CommandContext context) {
		if (context.getArgs() == null || context.getArgs().length < 1) {
			return get(ReturnType.NOTICE, "Correct usage is .twitter <user>", source, context);
		}

		try {
			List<Status> statusList = twitter.getUserTimeline(context.getArgs()[0]);
			Status status = statusList.get(0);
			status.getUser();
			StringBuilder builder = new StringBuilder();
			builder.append(status.getUser().getScreenName()).append(Colors.BLUE).append(": ").append(Colors.NORMAL).append(status.getText()).append(" (").append(timeFormatter.print(new Period(new DateTime(status.getCreatedAt()), new DateTime()))).append(")");
			return get(ReturnType.MESSAGE, builder.toString().replace(lineBreak, " "), source, context);
		} catch (TwitterException e) {
			if (e.getStatusCode() == 404) {
				return get(ReturnType.NOTICE, "User not found!", source, context);
			} else {
				e.printStackTrace();
				return get(ReturnType.MESSAGE, "There was an internal error!", source, context);
			}
		}
	}
}
