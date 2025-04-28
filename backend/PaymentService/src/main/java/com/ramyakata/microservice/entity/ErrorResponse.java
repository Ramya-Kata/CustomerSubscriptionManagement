package com.ramyakata.microservice.entity;

import java.util.Objects;

public class ErrorResponse {

	private String message;
	private String code;
	private long timestamp;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ErrorResponse [message=" + message + ", code=" + code + ", timestamp=" + timestamp + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, message, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorResponse other = (ErrorResponse) obj;
		return Objects.equals(code, other.code) && Objects.equals(message, other.message)
				&& timestamp == other.timestamp;
	}

}