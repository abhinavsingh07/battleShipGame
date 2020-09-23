package com.thoughtworks.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.thoughtworks.game.BattleException;
import com.thoughtworks.game.BattlePlayer;
import com.thoughtworks.game.BattleShip;

public class TestGame {

	BattlePlayer p1;

	@Test
	public void testBattleAreaConstraints() {
		String badString = "512";
		BattleException actual=assertThrows(BattleException.class, () -> {
			BattlePlayer p1 = new BattlePlayer(badString);
		});
		String expected="Provide Battle Area Width In Range From 1-9 And Height In Range From A-Z In Format '1-9 A-Z'";
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void testBattleShipConstraints() {

		try {
			this.p1 = new BattlePlayer("5 E");
		} catch (BattleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String width = "1";
		String shipType = "H", shipHeight = "A";
		String shipCoordinate = "A1";

		BattleException actual=assertThrows(BattleException.class, () -> {
			BattleShip s1 = new BattleShip(shipType, width, shipHeight, shipCoordinate, p1);
		});
		String expected="Invalid Ship Constraints";
		assertEquals(expected, actual.getMessage());
		
	}

	@Test
	public void testShipOnBattleArea() {
		BattlePlayer p1 = null;
		String shipType = "Q", height = "A";
		String shipCoordinate = "A1";
		String shipWidth = "1";
		try {
			p1 = new BattlePlayer("5 E");
			BattleShip s1 = new BattleShip(shipType, shipWidth, height, shipCoordinate, p1);
		} catch (BattleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int output = p1.getBattleArea()[0][0];// means A1 in context of game
		// 2 if shipType 'P', 1 if shipType 'Q'
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test
	public void testShipCoordOverlap() {

		try {
			this.p1 = new BattlePlayer("5 E");
		} catch (BattleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String shipWidth = "1";
		String shipType = "Q", shipHeight = "A";
		String shipCoordinate1 = "A2", shipCoordinate2 = "A2";

		  assertThrows(BattleException.class, () -> {
			BattleShip s1 = new BattleShip(shipType, shipWidth, shipHeight, shipCoordinate1, p1);
			BattleShip s2 = new BattleShip(shipType, shipWidth, shipHeight, shipCoordinate2, p1);
		});
	}

	@Test
	public void testShipCoordOutRange() {

		try {
			this.p1 = new BattlePlayer("5 E");
		} catch (BattleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String shipWidth = "2";
		String shipType = "Q", shipHeight = "A";
		// On D5 coordinate we can't create a ship of width 2 so this throws BattleException
		String shipCoordinate = "D5";

		BattleException actual=assertThrows(BattleException.class, () -> {
			BattleShip s1 = new BattleShip(shipType, shipWidth, shipHeight, shipCoordinate, p1);
		});
		String expected="Cannot Place ship on these coordinates";
		assertEquals(expected, actual.getMessage());
	}
	
	@Test
	public void testMissileCoordinate() {

		try {
			this.p1 = new BattlePlayer("5 E");
		} catch (BattleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//in 5 E range D6 is out of bound
		BattleException actual=assertThrows(BattleException.class, () -> {
			this.p1.addMissielLoc("D6");
		});
		String expected="Invalid Missile Coordinate Entered";
		assertEquals(expected, actual.getMessage());
	}
	
	@Test
	public void testFireMissileHit() {
		String wh="5 E";
		try {
			BattlePlayer p1 = new BattlePlayer(wh);
			p1.addMissielLoc("D3");
			BattlePlayer p2 = new BattlePlayer(wh);
			
			BattleShip s1 = new BattleShip("P", "1", "A", "B1", p1);
			BattleShip s2 = new BattleShip("Q", "1", "A", "D3", p2);
			
			boolean missileHit=p1.fireMissile(p2);
			assertTrue(missileHit);
			
		} catch (BattleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFireMissileMiss() {
		String wh="5 E";
		try {
			BattlePlayer p1 = new BattlePlayer(wh);
			p1.addMissielLoc("D2");
			BattlePlayer p2 = new BattlePlayer(wh);
			
			BattleShip s1 = new BattleShip("P", "1", "A", "B1", p1);
			BattleShip s2 = new BattleShip("Q", "1", "A", "D3", p2);
			
			boolean missileHit=p1.fireMissile(p2);
			assertFalse(missileHit);
			
		} catch (BattleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}