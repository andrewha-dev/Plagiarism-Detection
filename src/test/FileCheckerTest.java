package test;

import org.junit.Test;

import plagiarismDetectorMain.FileChecker;

public class FileCheckerTest
{
	//In an ideal world, should be purposelly trying to see if the exception was caught
	@Test
	public void IsArgumentsValidTest()
	{
		FileChecker c = new FileChecker();
		String[] args = {"Should Crash"};
		FileChecker.isArgumentsValid(args);
	}
	
	public void InvalidArgumentsInteger()
	{
		FileChecker c = new FileChecker();
		String[] args = {"Shouldnt Crash", "Shouldnt Crash","SynonymsFile",  "This should make it crash"};
		FileChecker.isArgumentsValid(args);
		
	}
	
	public void ArgumentsOptional()
	{
		FileChecker c = new FileChecker();
		String[] args = {"Shouldnt Crash", "Shouldnt Crash", "SynonymsFile", "5"};
		FileChecker.isArgumentsValid(args);
	}

}
