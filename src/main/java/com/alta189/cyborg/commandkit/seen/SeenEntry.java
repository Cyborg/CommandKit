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

package com.alta189.cyborg.commandkit.seen;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.pircbotx.User;

@Table(name = "seen")
public class SeenEntry {

	@Id
	private int id;

	@Field
	private String name;

	@Field
	private String hostmask;

	@Field
	private String channel;

	@Field
	private long timestamp;

	@Field
	private String saying;
	
	// Non-persistent data
	private static final PeriodFormatter timeFormatter = new PeriodFormatterBuilder().appendPrefix("Years ").appendYears().appendSeparator(", ").appendPrefix("Months ").appendMonths().appendSeparator(", ").appendPrefix("Weeks ").appendWeeks().appendSeparator(", ").appendPrefix("Days ").appendDays().appendSeparator(", ").appendPrefix("Hours ").appendHours().appendSeparator(", ").appendPrefix("Minutes ").appendMinutes().appendSeparator(", ").appendPrefix("Seconds ").appendSeconds().appendSuffix(" ago").toFormatter();
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostmask() {
		return hostmask;
	}

	public void setHostmask(String hostmask) {
		this.hostmask = hostmask;
	}

	public String getChannel() {
		return channel;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSaying() {
		return saying;
	}

	public void setSaying(String saying) {
		this.saying = saying;
	}
	
	public void set(User user) {
		setName(user.getNick().toLowerCase());
		setHostmask(user.getLogin() + "@" + user.getHostmask());
	}
	
	public String getDifference(long time) {
		DateTime begin = new DateTime(time);
		DateTime end = new DateTime(timestamp);
		Period period = new Period(end, begin);
		return timeFormatter.print(period.normalizedStandard());
	}
}
