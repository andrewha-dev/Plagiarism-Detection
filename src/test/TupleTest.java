/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import classes.Tuple;

/**
 * @author Andrew
 *
 */
public class TupleTest
{

	@Test
	public void TupleSettersAndGettersTest()
	{
		//Testing the setters and getters for the Tuple
		ArrayList<String> a = new ArrayList<String>();
		a.add("Test");
		Tuple t = new Tuple(5, a);
		assertEquals(1, t.getWords().size());
		assertEquals(5, t.getTupleSize());
		
		Tuple t2 = new Tuple(5, a);
		assertEquals(true, t.equals(t2));
	}

}
