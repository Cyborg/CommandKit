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

import com.alta189.cyborg.api.event.Listener;
import com.alta189.cyborg.api.event.channel.MessageEvent;
import org.pircbotx.Colors;

import static com.alta189.cyborg.commandkit.CommandKit.getDatabase;

public class SeenListener implements Listener {

	   public static void onMessage(MessageEvent event) {
		   SeenEntry entry = getDatabase().select(SeenEntry.class).where().equal("name", event.getUser().getNick().toLowerCase()).and().equal("channel", event.getChannel().getName().toLowerCase()).execute().findOne();
		   if (entry == null) {
			   entry = new SeenEntry();
			   entry.set(event.getUser());
			   entry.setChannel(event.getChannel().getName().toLowerCase());
		   }
		   
		   entry.setSaying(Colors.removeFormattingAndColors(event.getMessage()));
		   entry.setTimestamp(event.getTimestamp());

		   getDatabase().save(SeenEntry.class, entry);
	   }

}
