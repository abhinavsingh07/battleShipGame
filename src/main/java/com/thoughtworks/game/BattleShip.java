package com.thoughtworks.game;

import java.util.List;

public class BattleShip {

	private String shipType;
	private String shipWidth;
	private String shipHeight;
	private String shipStartCoord;

	/**
	 * This constructor initializes ship which have height parameter with all other
	 * parameters passed
	 * 
	 * @param shipType       Either "P" or "Q"
	 * @param shipWidth      Ship width "1-9"
	 * @param shipHeight     Ship Height "A-Z"
	 * @param shipCoordinate Ship coordinate "A-Z1-9"
	 * @param player         Player-1 or Player-2
	 * @throws BattleException BattleExcetion
	 */
	public BattleShip(String shipType, String shipWidth, String shipHeight, String shipCoordinate, BattlePlayer player)
			throws BattleException {
		super();
		this.shipType =shipType;
		this.shipWidth = shipWidth;
		this.shipHeight = shipHeight;
		this.shipStartCoord = shipCoordinate;
		this.makeShipCellsOnBA(player);
	}

	/**
	 * This constructor initializes ship which have no height parameter with all
	 * other parameters passed
	 *
	 * @param shipType       Either "P" or "Q"
	 * @param shipWidth      Ship width "1-9"
	 * @param shipHeight     Ship Height "A-Z"
	 * @param shipCoordinate Ship coordinate "A-Z1-9"
	 * @param player         Player-1 or Player-2
	 * @throws BattleException BattleExcetion
	 */
	public BattleShip(String shipType, String shipWidth, String shipCoordinate, BattlePlayer player) throws BattleException {
		super();
		this.shipType = shipType;
		this.shipWidth = shipWidth;
		this.shipHeight = "A";//default ship height would be 1 if height not passes in input from user
		this.shipStartCoord = shipCoordinate;
		this.makeShipCellsOnBA(player);
	}

	/**
	 * This method make ship cell on battle Area [1 shows P type and 2 shows Q type]
	 * ships respectively.
	 * 
	 * @param ship   ship object
	 * @param player player object
	 * @throws BattleException
	 */
	public void makeShipCellsOnBA(BattlePlayer player) throws BattleException {
		String shipWH = this.shipHeight + "" + this.shipWidth;
		// check ship type & if ship width and height and ship start coordinate lies //
		// within battle area
		if ( shipType.length()==2 || (!shipType.equals("P") && !shipType.equals("Q")) || !(player.checkCoordinate(this.shipStartCoord) && player.checkCoordinate(shipWH)))
			throw new BattleException("Invalid Ship Constraints");
		
		
		int shipHeight = player.getbAHeightIdx(this.shipHeight);// get ship height in a number format
		int shipWidth=Integer.parseInt(this.shipWidth);
	
		
		// calculate start index for ship to place on Battle Area as m,n
		BattleCoordinate btc = player.getCellIndex(shipStartCoord);
		int m = btc.getM();
		int n = btc.getN() - 1;

		// Get particular Battle Area using that's player object
		int[][] battleArea = player.getBattleArea();
		int shipsTotalLife = player.getShipsTotalLife();
		// Place ship on battle area according to start index(m and n)
		for (int i = 1, k = n; i <= shipWidth; i++, k++)
			for (int j = 1, l = m; j <= shipHeight; j++, l++) {

				if (k >= player.getbAWidth() || l >= player.getbAHeight())
					throw new BattleException("Cannot Place ship on these coordinates");

				if (battleArea[l][k] != 0)
					throw new BattleException(
							"Overlapping ship coordinates please change ship coordinates for ship type :"
									+ this);

				battleArea[l][k] = this.shipType.equals("Q") ? 2 : 1;// as Q type ship have double power
				shipsTotalLife = shipsTotalLife + battleArea[l][k];
			}

		player.setBattleArea(battleArea);
		player.setShipsTotalLife(shipsTotalLife);
		btc = null;
	}

	/** Getters and Setters */

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getShipWidth() {
		return shipWidth;
	}

	public void setShipWidth(String shipWidth) {
		this.shipWidth = shipWidth;
	}

	public String getShipHeight() {
		return shipHeight;
	}

	public void setShipHeight(String shipHeight) {
		this.shipHeight = shipHeight;
	}

	public String getShipCoordinate() {
		return shipStartCoord;
	}

	public void setShipCoordinate(String shipCoordinate) {
		this.shipStartCoord = shipCoordinate;
	}

	@Override
	public String toString() {
		return "BattleShip [shipType=" + shipType + ", shipWidth=" + shipWidth + ", shipHeight=" + shipHeight
				+ ", shipStartCoord=" + shipStartCoord + "]";
	}

}
