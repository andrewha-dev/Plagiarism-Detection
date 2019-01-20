package test;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.InvalidArgumentsException;
import plagiarismDetectorBLL.PlagiarismBLL;

public class PlagiarismBLLTest
{

	@Test
	public void TestPlagiarismBestCaseOptional()
	{
		PlagiarismBLL plagiarismChecker = new PlagiarismBLL();
		plagiarismChecker.generateSynonymsMap("syns.txt");
		plagiarismChecker.setUpTuples("file1.txt", "file2.txt", 3);
		assertEquals(100.00, plagiarismChecker.calculateMatch(), 0);
		assertEquals(2, plagiarismChecker.firstFileTuplesMap.keySet().size());
		assertEquals(2, plagiarismChecker.secondFileTuplesSet.size());

	}
	
	public void TestPlagiarismBestCase()
	{
		PlagiarismBLL plagiarismChecker = new PlagiarismBLL();
		plagiarismChecker.generateSynonymsMap("syns.txt");
		plagiarismChecker.setUpTuples("file1.txt", "file2.txt", 4);
		assertEquals(100.00, plagiarismChecker.calculateMatch(), 0);
		assertEquals(1, plagiarismChecker.firstFileTuplesMap.keySet().size());
		assertEquals(1, plagiarismChecker.secondFileTuplesSet.size());

	}
	
	@Test (expected = InvalidArgumentsException.class)
	public void TestPlagiarismInvalidTupleNumber()
	{
		try {
			PlagiarismBLL plagiarismChecker = new PlagiarismBLL();
			plagiarismChecker.generateSynonymsMap("syns.txt");
			plagiarismChecker.setUpTuples("fileEmpty.txt", "fileEmpty.txt", 5);
		}
		catch(Exception e) {
			assertTrue(e instanceof InvalidArgumentsException);
		}

	}
	
	@Test
	//tests to make sure that if one of the files doesnt match the tuple length, it will be defaulted to the amount of words it contains
	public void TestPlagiarismChangeTupleSize()
	{
		PlagiarismBLL plagiarismChecker = new PlagiarismBLL();
		plagiarismChecker.generateSynonymsMap("syns.txt");
		plagiarismChecker.setUpTuples("file3.txt", "file4.txt", 3);
		assertEquals(0, plagiarismChecker.calculateMatch(), 0);
		assertEquals(10, plagiarismChecker.firstFileTuplesMap.keySet().size());
		assertEquals(1, plagiarismChecker.secondFileTuplesSet.size());
	}
	@Test
	//tests to make sure that if one of the files doesnt match the tuple length, it will be defaulted to the amount of words it contains
	public void TestPlagiarismPartial()
	{
		PlagiarismBLL plagiarismChecker = new PlagiarismBLL();
		plagiarismChecker.generateSynonymsMap("syns.txt");
		plagiarismChecker.setUpTuples("file3.txt", "file5.txt", 4);
		assertEquals(37.5, plagiarismChecker.calculateMatch(), 0);
		assertEquals(8, plagiarismChecker.firstFileTuplesMap.keySet().size());
		assertEquals(9, plagiarismChecker.secondFileTuplesSet.size());
	}
	
	
	

}
