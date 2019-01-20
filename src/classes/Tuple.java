/**
 * 
 */
package classes;

import java.util.ArrayList;

/**
 * @author Andrew
 *
 */
public class Tuple
{
	private int tupleSize;
	private ArrayList<String> words;
	
	public Tuple(int _tupleSize)
	{
		this.tupleSize = _tupleSize;		
		words = new ArrayList<String>(_tupleSize);
	}		
		
	
	public Tuple(int tupleSize, ArrayList<String> words)
	{
		super();
		this.tupleSize = tupleSize;
		this.words = words;
	}

	public int getTupleSize()
	{
		return tupleSize;
	}

	private void setTupleSize(int tupleSize)
	{
		this.tupleSize = tupleSize;
	}

	public ArrayList<String> getWords()
	{
		return words;
	}

	private void setWords(ArrayList<String> words)
	{
		this.words = words;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + tupleSize;
		result = prime * result + ((words == null) ? 0 : words.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof Tuple))
		{
			return false;
		}
		Tuple other = (Tuple) obj;
		if (tupleSize != other.tupleSize)
		{
			return false;
		}
		if (words == null)
		{
			if (other.words != null)
			{
				return false;
			}
		}
		else
			if (!words.equals(other.words))
			{
				return false;
			}
		return true;
	}


}
