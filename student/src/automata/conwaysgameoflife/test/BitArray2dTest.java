package automata.conwaysgameoflife.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import automata.conwaysgameoflife.src.BitArray2d;

public class BitArray2dTest
{
	private BitArray2d array1;
	private BitArray2d array2;
	
	@Before
	public void before()
	{
		array1 = new BitArray2d(1,60);
		array2 = new BitArray2d(200,200);
	}
	
	/**
	 * This test should pass once you have a working get method, even if you have no set method
	 */
	@Test
	public void testGetOneRow()
	{
		assertFalse(array1.get(0, 2));
		assertFalse(array1.get(0, 28));
		assertFalse(array1.get(0, 0));
		assertFalse(array1.get(0, 59));
		assertFalse(array2.get(64, 0));
		assertFalse(array2.get(199, 199));
		assertFalse(array2.get(100, 63));
		array1.bits[0] = -1L;
		assertTrue(array1.get(0,1));
		assertTrue(array1.get(0, 3));
		assertTrue(array1.get(0, 59));
	}
	
	/**
	 * This test should pass once you have a working get method, even if you have no set method
	 */
	@Test
	public void testGetMultipleRows()
	{
		array2.bits[10] = -1L;
		for (int x = 40; x < 104; x++)
		{
			assertTrue(array2.get(x, 3));
		}
		assertFalse(array2.get(104, 3));
	}
	
	/**
	 * This test should pass when you have working get and set methods
	 */
	@Test
	public void testGetSet1()
	{
		array1.set(0, 0, true);
		assertTrue(array1.get(0, 0));
		array1.set(0, 0, false);
		assertFalse(array1.get(0, 0));
		array1.set(0, 59, true);
		assertTrue(array1.get(0, 59));
		array1.set(0, 59, false);
		assertFalse(array1.get(0, 59));
	}
	
	/**
	 * This test should pass when you have working get and set methods
	 */
	@Test
	public void testGetSet2()
	{
		array2.set(0, 0, true);
		assertTrue(array2.get(0, 0));
		array2.set(0, 0, false);
		assertFalse(array2.get(0, 0));
		array2.set(199, 199, true);
		assertTrue(array2.get(199, 199));
		array2.set(199, 199, false);
		assertFalse(array2.get(199, 199));
	}
	
	/**
	 * This test should pass when you have proper bounds checking in place
	 */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testBoundsChecking1()
	{
		array1.get(0, 64);
	}
	
	/**
	 * This test should pass when you have proper bounds checking in place
	 */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testBoundsChecking2()
	{
		array2.get(100, 200);
	}
}
