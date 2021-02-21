You have to run the Grammar.java file to see the results by using the following command
javac Grammar.java
java Grammar

Then here you can choose a grammar either Grammar-1 or Grammar-2
For Grammar-1 it is straight Forward.
For the first grammar the Input grammar file is "GrammarLL_1.txt".
First,Follow sets are computed and parse table is written into table.txt file

For Grammar-2 
Grammar-2 consists of terminals and nonterminals which are of multiple characters so I have to encode them to single character terminals and non terminals and thereby modify the grammar accordingly as follows.
Input Grammar is taken into grammer2.txt
This grammer2.txt is encoded to GrammarLL2.txt using readGrammar() method in Grammar.java
Input program is taken in program.txt
This program is parsed into tokens using lex tool and tokens are written into tokens.txt
These tokens are encoded to new Grammar recognizable tokens into mappedTokens.txt
Now this mappedTokens are passed as input to verify whether Input program is accepted by the grammar or not.



