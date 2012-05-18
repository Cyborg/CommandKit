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

import java.util.List;

public class GoogleResults {
	private ResponseData responseData;

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public String toString() {
		return "ResponseData[" + responseData + "]";
	}

	public static class ResponseData {
		private List<Result> results;

		public List<Result> getResults() {
			return results;
		}

		public void setResults(List<Result> results) {
			this.results = results;
		}

		public String toString() {
			return "Results[" + results + "]";
		}
	}

	public static class Result {
		private String url;
		private String titleNoFormatting;
		private String content;

		public String getUrl() {
			return url;
		}

		public String getTitle() {
			return titleNoFormatting;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setTitle(String title) {
			this.titleNoFormatting = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String toString() {
			return "Result[url:" + url + ",title:" + titleNoFormatting + "]";
		}
	}
}