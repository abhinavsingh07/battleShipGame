package com.thoughtworks.game;

public class BattleException extends Exception {

	private String message;

	public BattleException(String message) {
		super(message);
	}

}
