/**
 * 
 */
package plagiarismDetectorMain;
import plagiarismDetectorBLL.PlagiarismBLL;
import exceptions.InvalidArgumentsException;;

/**
 * @author Andrew
 *
 */
public class FileChecker
{
	
	/**
	 * @param args
	 */
	private static String synonymFileName;
	private static String fileOneName;
	private static String fileTwoName;
	//The amount of words per tuple. 
	private static int numberOfWords;
	//Importing the plagiarismBLL class
	private static PlagiarismBLL plagiarismChecker;
	public static void main(String[] args)
	{
		//Instiantiating object
		plagiarismChecker = new PlagiarismBLL();
		//running validation check on the arguments first
		if (isArgumentsValid(args)){
		plagiarismChecker.generateSynonymsMap(synonymFileName);
		plagiarismChecker.setUpTuples(fileOneName, fileTwoName, numberOfWords);
			if (plagiarismChecker.isValid)
			{
				System.out.println("Percent File 1 Matches File 2: " + plagiarismChecker.calculateMatch() + "%");
			}
		
		}	
		
	}
	public static boolean isArgumentsValid(String[] args)
	{
		System.out.println("< = = Started: = = >");
		try {
			if (args.length != 3 && args.length != 4)
			{
				throw new InvalidArgumentsException("Invalid number of arguments given to command line");
			}
			else
			{
				synonymFileName = args[0];
				fileOneName = args[1];
				fileTwoName = args[2];
				if (args.length == 4)
					numberOfWords = Integer.parseInt(args[3]);
				else
					numberOfWords = 3;
				
				return true;
			}
		}
		catch(InvalidArgumentsException e) {
			System.out.println(e.getMessage());
			return false;
		}
		catch(NumberFormatException e){
			System.out.println("Invalid number for tuple size: " + e.getMessage());
			return false;
		}
		catch(Exception e) {
			System.out.println("Error processing command line inputs: " + e.getMessage());
			return false;
		}
	}

}
