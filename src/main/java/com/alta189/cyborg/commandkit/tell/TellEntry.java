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
package com.alta189.cyborg.commandkit.tell;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

@Table("tell")
public class TellEntry {
	@Id
	private int id;

	@Field
	private String receiver;

	@Field
	private String sender;

	@Field
	private String message;

	@Field
	private long timestamp;

	@Field
	private boolean alerted = false;

	@Field
	private boolean received = false;

	// Non-persistent data
	private static final PeriodFormatter timeFormatter = new PeriodFormatterBuilder()
			.appendYears().appendSuffix(" years").appendSeparator(", ")
			.appendMonths().appendSuffix(" months").appendSeparator(", ")
			.appendWeeks().appendSuffix(" weeks").appendSeparator(", ")
			.appendDays().appendSuffix(" days").appendSeparator(", ")
			.appendHours().appendSuffix(" hours").appendSeparator(", ")
			.appendMinutes().appendSuffix(" minutes").appendSeparator(", ")
			.appendSeconds().appendSuffix(" seconds")
			.toFormatter();

	public int getId() {
		return id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isAlerted() {
		return alerted;
	}

	public void setAlerted(boolean alerted) {
		this.alerted = alerted;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public String getDifference(long time) {
		DateTime begin = new DateTime(time);
		DateTime end = new DateTime(timestamp);
		Period period = new Period(end, begin);
		return timeFormatter.print(period.normalizedStandard()) + " ago";
	}
}
