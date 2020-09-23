package com.thoughtworks.game;

import java.util.Arrays;

public class BattleArea {

	private int[][] battleArea;
	private int bAWidth, bAHeight;
	private String bAHeightIdx = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Initialize Battle Area according to user input.
	 * 
	 * @param m Width of battle area.
	 * @param n Height of battle area.
	 * @throws BattleException
	 */
	public BattleArea(String wh) throws BattleException {
		// checking width lies within 1-9 and Height A-Z and of 3 characters long with
		// space separator.
		if ((wh.length() == 3 && wh.charAt(1) == ' ')
				&& ((wh.charAt(0) > 48 && wh.charAt(0) <= 57) && (wh.charAt(2) >= 65 && wh.charAt(2) <= 90))) {

			String m = wh.split(" ")[0];
			String n = wh.split(" ")[1];
			bAWidth = Integer.parseInt(m);
			bAHeight = bAHeightIdx.indexOf(n) + 1;
			this.createBattleArea(bAWidth, bAHeight);
		} else {
			throw new BattleException(
					"Provide Battle Area Width In Range From 1-9 And Height In Range From A-Z In Format '1-9 A-Z'");
		}
	}

	/**
	 * Create Battle area initialize with zero
	 * 
	 * @param w width of battle area
	 * @param h height of battle area
	 */
	private void createBattleArea(int w, int h) {
		this.battleArea = new int[w][h];
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				battleArea[i][j] = 0;
	}

	/**
	 * Public method accessible to outside classes. Return Index for 2d Array(Battle
	 * Area) and sets to mIdx & nIdx.
	 * 
	 * @param coordinates coordinate in Form Of A1,A2,B1,B2.
	 * @return index in string.
	 */
	public BattleCoordinate getCellIndex(String coordinates) {
		return this.locateCell(coordinates);
	}

	/**
	 * Return Index for 2d Array(Battle Area) in form like BattleCoordinate object
	 * have M and N indexes.
	 * 
	 * @param coordinate coordinate in Form Of A1,A2,B1,B2
	 * @return BattleCoordinate object have M and N indexes.
	 */
	private BattleCoordinate locateCell(String coordinate) {
		int len = coordinate.length();
		if (len < 1 || len > 2)
			return null;

		int mIdx = bAHeightIdx.indexOf(coordinate.charAt(0));
		int nIdx = Integer.parseInt(Character.toString(coordinate.charAt(1)));
		return new BattleCoordinate(mIdx, nIdx);
	}

	/**
	 * Checks coordinate constraint
	 * 
	 * @param coordinate like 'A1' 'B2'
	 * @return
	 */
	public boolean checkCoordinate(String coordinate) {
		if (coordinate.length() != 2 || coordinate.contains(" ") || !Character.isAlphabetic(coordinate.charAt(0))
				|| !Character.isDigit(coordinate.charAt(1)))
			return false;

		int hIdx = bAHeightIdx.indexOf(coordinate.charAt(0)) + 1;
		int wIdx = Integer.parseInt(Character.toString(coordinate.charAt(1)));

		if ((wIdx >= 1 && wIdx <= this.bAWidth) && (hIdx >= 1 && hIdx <= this.bAHeight))
			return true;

		return false;
	}

	/**
	 * Print Battle Area in 2d Array Format showing 1 as P ships and 2 as Q ships
	 */
	public void showBattleArea() {
		for (int i = 0; i < bAWidth; i++) {
			for (int j = 0; j < bAHeight; j++) {
				System.out.print(this.battleArea[i][j]);
			}
			System.out.println();
		}

	}

	/**
	 * Get Height as integer
	 * 
	 * @param ch Height as a Alphabet from A-Z
	 * @return Alphabet position as integer A=1,B=2 and so on..
	 */
	public int getbAHeightIdx(String ch) {
		return bAHeightIdx.indexOf(ch) + 1;
	}

	/** Getters and Setters */
	public int[][] getBattleArea() {
		return battleArea;
	}

	public void setBattleArea(int[][] battleArea) {
		this.battleArea = battleArea;
	}

	public int getbAWidth() {
		return this.bAWidth;
	}

	public void setbAWidth(int bAWidth) {
		this.bAWidth = bAWidth;
	}

	public int getbAHeight() {
		return this.bAHeight;
	}

	public void setbAHeight(int bAHeight) {
		this.bAHeight = bAHeight;
	}

	@Override
	public String toString() {
		return "BattleArea [battleArea=" + Arrays.toString(battleArea) + ", bAWidth=" + bAWidth + ", bAHeight="
				+ bAHeight + ", bAHeightIdx=" + bAHeightIdx + "]";
	}

}
