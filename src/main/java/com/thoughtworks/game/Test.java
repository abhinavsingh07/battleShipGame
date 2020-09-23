package com.thoughtworks.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.util.Iterator;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.annotation.Resources;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	static BattlePlayer p1 = null;
	static BattlePlayer p2 = null;

	public static void main(String[] args) {
		Test test = new Test();

		try {
			test.takeInputsForGame();
			test.showBattleBoard(p1, p2);
			test.runGame(p1, p2);
			test.showGameResult(p1, p2);
			test.showBattleBoard(p1, p2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BattleException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method take all inputs for game from "game_input.txt" file.
	 * 
	 * @throws BattleException
	 * @throws FileNotFoundException
	 */
	private void takeInputsForGame() throws BattleException, FileNotFoundException {
		String inputFileName = new File("").getAbsolutePath() + "/src/main/resources/game_input.txt";
		Scanner sc = new Scanner(new File(inputFileName));

		int i = 1;
		int len = 2;

		while (i <= len || sc.hasNextLine()) {
			// The first line of the input contains the width and height of the battle area
			// respectively.
			if (i == 1) {
				String wh = sc.nextLine();
				this.p1 = new BattlePlayer(wh);
				this.p2 = new BattlePlayer(wh);
			}
			// The second line of the input contains the number of battleships that each
			// player gets (N)
			else if (i == 2) {
				int noOfShips = Integer.parseInt(sc.nextLine());
				len = len + noOfShips;
				if (noOfShips > (this.p1.getbAWidth() * this.p1.getbAHeight())
						|| noOfShips > (this.p2.getbAWidth() * this.p2.getbAHeight()))
					throw new BattleException("No. of ships exceeding range");
			}
			// Read N lines where each line contains the type of the battleship, its
			// dimensions (width and height) and coordinates of ship for Player-1 and
			// Player-2.
			else if (i > 2 && i <= len) {
				String[] shipParam = sc.nextLine().split(" ");
				if (shipParam.length != 7 && shipParam.length != 5)
					throw new BattleException(
							"Ship parameters entered are not valid at game_input.txt file line no:" + i);
				// ship details having all inputs
				if (shipParam.length == 7) {
					new BattleShip(shipParam[0], shipParam[1], shipParam[2], shipParam[5], p1);
					new BattleShip(shipParam[0], shipParam[3], shipParam[4], shipParam[6], p2);
				}
				// ship input having only width of ships with other all inputs
				else if (shipParam.length == 5) {
					new BattleShip(shipParam[0], shipParam[1], shipParam[3], p1);
					new BattleShip(shipParam[0], shipParam[2], shipParam[4], p2);
				}
			}
			// The second last line contains the sequence of the target locations of
			// missiles fired by Player-1.
			else if (i == len + 1) {
				String[] noMissileP1 = sc.nextLine().split(" ");
				for (String s : noMissileP1)
					p1.addMissielLoc(s);
			}
			// The last line contains the sequence of the target locations of missiles fired
			// by Player-2.
			else if (i == len + 2) {
				String[] noMissileP2 = sc.nextLine().split(" ");
				for (String s : noMissileP2)
					p2.addMissielLoc(s);
			}
			i++;
		}

	}

	/**
	 * Runs Game After taking all inputs
	 * 
	 * @param p1 Player-1
	 * @param p2 Player-2
	 */
	private void runGame(BattlePlayer p1, BattlePlayer p2) {
		boolean p1MissileHit = false, p2MissileHit = false;
		System.out.println("\n# GAME STARTS..");
		while (true) {
			Iterator it1 = p1.getMissileLoc().iterator();
			while (it1.hasNext()) {
				String missile = (String) it1.next();
				if (p2.getMissileLoc().size() > 0 && p2MissileHit)
					break;
				p1MissileHit = p1.fireMissile(p2);
				if (p1MissileHit) {
					System.out.println("Player-1 fires a missile with target " + missile + " which got hit");
				} else {
					System.out.println("Player-1 fires a missile with target " + missile + " which got miss");
					break;
				}
				it1.remove();
			}

			Iterator it2 = p2.getMissileLoc().iterator();
			while (it2.hasNext()) {
				String missile = (String) it2.next();
				if (p1.getMissileLoc().size() > 0 && p1MissileHit)
					break;

				p2MissileHit = p2.fireMissile(p1);
				if (p2MissileHit) {
					System.out.println("Player-2 fires a missile with target " + missile + " which got hit");
				} else {
					System.out.println("Player-2 fires a missile with target " + missile + " which got miss");
					break;
				}
				it2.remove();
			}

			if (p1.getMissileLoc().isEmpty() && p2.getMissileLoc().isEmpty())
				break;

		}
	}

	/**
	 * Shows Game's Final Result
	 * 
	 * @param p1 Player-1
	 * @param p2 Player-2
	 */
	private void showGameResult(BattlePlayer p1, BattlePlayer p2) {
		if (p1.getShipsTotalLife() > p2.getShipsTotalLife())
			System.out.println("Player-1 won the battle");
		else if ((p2.getShipsTotalLife() > p1.getShipsTotalLife()))
			System.out.println("Player-2 won the battle");
		else
			System.out.println("Game ties");

	}

	/**
	 * Shows Battle Board in 2d Format
	 * 
	 * @param p1 Player-1
	 * @param p2 Player-2
	 */
	private void showBattleBoard(BattlePlayer p1, BattlePlayer p2) {
		System.out.println("\n-------BATTLE SHIP GAME--------\n");
		System.out.println("# Player-1 Info:" + p1 + "\n");
		p1.showBattleArea();
		System.out.println("-------       --------");
		System.out.println("# Player-2 Info:" + p2 + "\n");
		p2.showBattleArea();
	}

}
