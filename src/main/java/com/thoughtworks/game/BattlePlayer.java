package com.thoughtworks.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BattlePlayer extends BattleArea {

	private int shipsTotalLife;
	private List<String> missileLoc = new LinkedList<>();

	/**
	 * Initialize Battle Player Object and sets width and height of Battle Area
	 * 
	 * @param wh "1-9A-Z"
	 * @throws BattleException
	 */
	public BattlePlayer(String wh) throws BattleException {
		super(wh);
	}

	/**
	 * Add missile location for target player
	 * 
	 * @param coordinate 2d array coordinates for missile like "A-Z1-9"
	 * @throws BattleException
	 */
	public void addMissielLoc(String coordinate) throws BattleException {
		if (!this.checkCoordinate(coordinate))
			throw new BattleException("Invalid Missile Coordinate Entered");

		this.missileLoc.add(coordinate);
	}



	/**
	 * Fires missile to target player battle area
	 * 
	 * @param player target player
	 */
	public boolean fireMissile(BattlePlayer player) {
		boolean isMissileHit = player.takeMissileHit(this.missileLoc.get(0));
//		this.missileLoc.remove(0);
		return isMissileHit;
	}

	/**
	 * Take missile hit on Player's Battle Area by checking coordinates Hit or Miss.
	 * 
	 * @param coordinates coordinates in form like "A1" ,"D3" etc.
	 */
	private boolean takeMissileHit(String coordinates) {
		int[][] ba = this.getBattleArea();
		BattleCoordinate btc = this.getCellIndex(coordinates);
		int m = btc.getM();
		int n = btc.getN() - 1;
		btc = null;// for garbage collection of coordinate object
		// Missile misses here
		if (ba[m][n] == 0)
			return false;

		ba[m][n] = ba[m][n] - 1;
		int shipsTotalLife = this.getShipsTotalLife();
		shipsTotalLife = shipsTotalLife - 1;
		this.setShipsTotalLife(shipsTotalLife);
		return true;
	}

	/** Getters and Setters */
	public int getShipsTotalLife() {
		return shipsTotalLife;
	}

	public void setShipsTotalLife(int shipsTotalLife) {
		this.shipsTotalLife = shipsTotalLife;
	}

	public List<String> getMissileLoc() {
		return missileLoc;
	}

	@Override
	public String toString() {
		return "BattlePlayer [Ships Total Life=" + shipsTotalLife + ", Missiles=" + missileLoc
				+ " ('2' Are Type 'Q' ships And '1' Are Type 'P' Ships)]";
	}

}
