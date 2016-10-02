package automata.conwaysgameoflife.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import automata.conwaysgameoflife.BitArray2d;

public class GameOfLifeTest
{
	private BitArray2d board1, board2;
	
	@Before
	public void before()
	{
		board1 = new BitArray2d(100, 100);
		board2 = new BitArray2d(100, 100);
	}
	
	/**
	 * Makes sure a blank board stays blank after it evolves
	 */
	@Test
	public void testBlankBoard()
	{
		BitArray2d.evolve(board1, board2);
		BitArray2d.evolve(board2, board1);
		for (int x = 0; x < 100; x++)
		{
			for (int y = 0; y < 100; y++)
			{
				assertFalse(board1.get(x, y));
				assertFalse(board2.get(x, y));
			}
		}
	}
	
	/**
	 * Makes sure a 2x2 square doesn't change as the board evolves
	 */
	@Test
	public void testSquare()
	{
		board1.set(2, 2, true);
		board1.set(2, 3, true);
		board1.set(3, 2, true);
		board1.set(3, 3, true);
		BitArray2d.evolve(board1, board2);
		BitArray2d.evolve(board2, board1);
		assertTrue(board1.get(2, 2));
		assertTrue(board1.get(2, 3));
		assertTrue(board1.get(3, 2));
		assertTrue(board1.get(3, 3));
		assertTrue(board2.get(2, 2));
		assertTrue(board2.get(2, 3));
		assertTrue(board2.get(3, 2));
		assertTrue(board2.get(3, 3));
		assertFalse(board1.get(1, 1));
		assertFalse(board1.get(1, 2));
		assertFalse(board1.get(4, 4));
		assertFalse(board1.get(3, 4));
		assertFalse(board2.get(1, 1));
		assertFalse(board2.get(1, 2));
		assertFalse(board2.get(4, 4));
		assertFalse(board2.get(3, 4));
	}
	
	/**
	 * Tests to see if a 1x3 block alternates between horizontal and vertical
	 */
	@Test
	public void testBlinker()
	{
		board1.set(10, 2, true);
		board1.set(10, 3, true);
		board1.set(10, 4, true);
		BitArray2d.evolve(board1, board2);
		BitArray2d.evolve(board2, board1);
		assertTrue(board1.get(10, 2));
		assertTrue(board1.get(10, 3));
		assertTrue(board1.get(10, 4));
		assertTrue(board2.get(9, 3));
		assertTrue(board2.get(10, 3));
		assertTrue(board2.get(11, 3));
	}
}
