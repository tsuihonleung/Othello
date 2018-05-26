package org.ivan.project;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.ivan.project.Othello;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author ivantsui
 *
 */
public class OthelloTest extends Othello {

	private Othello othello;
	
	@Before
	public void setUp() throws Exception {
		othello = new Othello();
		//Initial board:
		//
		// 1 --------
		// 2 --------
		// 3 --------
		// 4 ---OX---
		// 5 ---XO---
		// 6 --------
		// 7 --------
		// 8 --------
		//   abcdefgh
	}
	
	@Test
	public void testGetXIndex() {
		assertEquals(3, othello.getXIndex("1d"));
		assertEquals(4, othello.getXIndex("e2"));
	}
	
	@Test
	public void testGetYIndex() {
		assertEquals(0, othello.getYIndex("1d"));
		assertEquals(1, othello.getYIndex("e2"));
	}
	
	@Test
	public void testIsValidInput() {
		assertTrue(othello.isValidInput("e6"));
		assertTrue(othello.isValidInput("1f"));
		assertFalse(othello.isValidInput("0f"));
		assertFalse(othello.isValidInput("1i"));
		assertFalse(othello.isValidInput("11"));
		assertFalse(othello.isValidInput("aa"));
	}
	
	@Test
	public void testIsValidMove() {
		assertTrue(othello.isValidMove(3, 2));	// can capture opponent piece
		assertFalse(othello.isValidMove(4, 2));	// no opponent piece can be captured
		assertFalse(othello.isValidMove(0, 0));	// no nearby opponent piece
	}
	
	@Test
	public void testGetNumberOfPiece() {
		assertEquals(2, othello.getNumberOfPiece('X'));
		assertEquals(2, othello.getNumberOfPiece('O'));
		assertEquals(60, othello.getNumberOfPiece('-'));
	}
	
	@Test
	public void testHasPieceToCaptureHorizontally() {
		assertFalse(othello.hasPieceToCaptureHorizontally(2, 2, false));
		assertTrue(othello.hasPieceToCaptureHorizontally(2, 3, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(2, 4, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(2, 5, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(3, 2, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(3, 5, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(4, 2, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(4, 5, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(5, 2, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(5, 3, false));
		assertTrue(othello.hasPieceToCaptureHorizontally(5, 4, false));
		assertFalse(othello.hasPieceToCaptureHorizontally(5, 5, false));
	}
	
	@Test
	public void testHasPieceToCaptureVertically() {
		assertFalse(othello.hasPieceToCaptureVertically(2, 2, false));
		assertFalse(othello.hasPieceToCaptureVertically(2, 3, false));
		assertFalse(othello.hasPieceToCaptureVertically(2, 4, false));
		assertFalse(othello.hasPieceToCaptureVertically(2, 5, false));
		assertTrue(othello.hasPieceToCaptureVertically(3, 2, false));
		assertFalse(othello.hasPieceToCaptureVertically(3, 5, false));
		assertFalse(othello.hasPieceToCaptureVertically(4, 2, false));
		assertTrue(othello.hasPieceToCaptureVertically(4, 5, false));
		assertFalse(othello.hasPieceToCaptureVertically(5, 2, false));
		assertFalse(othello.hasPieceToCaptureVertically(5, 3, false));
		assertFalse(othello.hasPieceToCaptureVertically(5, 4, false));
		assertFalse(othello.hasPieceToCaptureVertically(5, 5, false));
	}
	
	@Test
	public void testHasPieceToCaptureLeftDiagonally() {
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(2, 2, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(2, 3, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(2, 4, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(2, 5, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(3, 2, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(3, 5, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(4, 2, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(4, 5, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(5, 2, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(5, 3, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(5, 4, false));
		assertFalse(othello.hasPieceToCaptureLeftDiagonally(5, 5, false));
	}
	
	@Test
	public void testHasPieceToCaptureRightDiagonally() {
		assertFalse(othello.hasPieceToCaptureRightDiagonally(2, 2, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(2, 3, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(2, 4, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(2, 5, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(3, 2, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(3, 5, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(4, 2, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(4, 5, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(5, 2, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(5, 3, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(5, 4, false));
		assertFalse(othello.hasPieceToCaptureRightDiagonally(5, 5, false));
	}
	
}
