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

import com.alta189.cyborg.Cyborg;
import com.alta189.cyborg.api.event.EventHandler;
import com.alta189.cyborg.api.event.Listener;
import com.alta189.cyborg.api.event.channel.MessageEvent;
import java.util.List;

import static com.alta189.cyborg.commandkit.CommandKit.getDatabase;

public class TellListener implements Listener {

	@EventHandler
	public synchronized void onMessage(MessageEvent event) {
		List<TellEntry> entries = getDatabase().select(TellEntry.class).where().equal("recipient", event.getUser().getNick()).and().equal("alerted", false).and().equal("read", false).execute().find();
		if (entries.size() > 1) {
			entries = getDatabase().select(TellEntry.class).where().equal("recipient", event.getUser().getNick()).and().equal("read", false).execute().find();
			Cyborg.getInstance().sendNotice(event.getUser(), "You have '" + entries.size() + "' unread tells. Type .showtells to see them");
			for (TellEntry entry : entries) {
				entry.setAlerted(true);
				getDatabase().save(TellEntry.class, entry);
			}
		} else if (entries.size() == 1) {
			TellEntry entry = entries.get(0);
			
			StringBuilder builder = new StringBuilder();
			builder.append(entry.getSender()).append(" said ").append(entry.getDifference(System.currentTimeMillis())).append(": ").append(entry.getMessage());
			
			Cyborg.getInstance().sendNotice(event.getUser(), builder.toString());
			
			entry.setAlerted(true);
			entry.setRead(true);
			getDatabase().save(TellEntry.class, entry);
		}
	}
}
