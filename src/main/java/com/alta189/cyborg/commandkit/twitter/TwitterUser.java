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

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@Table("twitterusrs")
public class TwitterUser {

	@Id
	private int id;

	@Field
	private String permUser;

	@Field
	private String accessToken;

	@Field
	private String accessTokenSecret;

	public int getId() {
		return id;
	}

	public String getPermUser() {
		return permUser;
	}

	public void setPermUser(String permUser) {
		this.permUser = permUser;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public TwitterUser setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public TwitterUser setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
		return this;
	}

	public TwitterUser setAccessTokenObject(AccessToken accessToken) {
		this.accessToken = accessToken.getToken();
		this.accessTokenSecret = accessToken.getTokenSecret();
		return this;
	}

	public AccessToken getAccessTokenObject() {
		return new AccessToken(accessToken, accessTokenSecret);
	}
}
