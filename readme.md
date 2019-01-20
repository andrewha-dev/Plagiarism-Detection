# Plagiarism Detector
#### Purpose:
Your assignment is to write a command-line program that performs plagiarism detection using a N-tuple comparison algorithm allowing for synonyms in the text.

Your program should take in 3 required arguments, and one optional.  In other cases such as no arguments, the program should print out usage instructions.
1.	File name for a list of synonyms
2.	Input file 1
3.	Input file 2
4.	(Optional) the number N, the tuple size.  If not supplied, the default should be N=3.

The synonym file has lines each containing one group of synonyms.  For example a line saying "run sprint jog" means these words should be treated as equal. 

The input files should be declared plagiarized based on the number of N-tuples in file1 that appear in file2, where the tuples are compared by accounting for synonyms as described above.  

For example, the text "go for a run" has two 3-tuples, ["go for a", "for a run"] both of which appear in the text "go for a jog".

The output of the program should be the percent of tuples in file1 which appear in file2.  So for the above example, the output would be one line saying "100%".  In another example, for texts "go for a run" and "went for a jog" and N=3 we would output "50%" because only one 3-tuple in the first text appears in the second one.

#### Assumptions:
There isn't any form of decaying value for the amount of times a tuple is repeated. Example, if the same tuple appears more than once then it will be counted as an entry each time, instead of having a decaying value (First time: 1 --> Second time: 1.5 instead of 2)

Synonym file will always be in the format of: Word Synonym Synonym ... Synonym
Words file will be in proper format. 
We will not look at the different permuations a tuple can be, only taking it as seen


#### File Validations handled:
1.) File1 Empty. If the file1 is empty, then a message will appear stating that the file specified is empty
2.) File2 Empty. If the file2 is empty, then a message will appear stating that the file specified is empty
3.) If size of tuple requested is greater than number of words in both files, then a message will appear stating that an invalid tuple value has been selected
4.) Missing files. If File1, File2, or Synonyms is missing then a message will appear stating that one of the files is missing
#### Tuples Validations handled:
5.) If size of tuple is requested is greater than number of words in one of the files. tuple size will default to the number of words that is smaller. For example tuple N is 3, one file only has 5 words, another has 2 words. Tuple will default to 2
6.) If size of tuple is less than 1, then a message will appear stating that an invalid tuple number has been entered
#### Arguments Validations handled:
7.) Number of arguments less than 3. greater then 4. Than a message will appear stating that invalid number of arguments has been entered
8.) Number of arguments entered is 3, then tuple size is taken as 3 by default

### Test Cases:
1.) File1 and File2 have some tuples that match - should display the match percentage
2.) File1 has ‘go for a run’ and File2 has ‘go for a jog’- should display a 100% match arguments
3.) File1 has ‘go for a run’ and File2 has ‘went for a jog’- should display a 50% match arguments
4.) File1 has 'go for a run' and File2 has 'not going to a crawl'- should display a 0% match arguments








