The following is information regarding all the files in the directory:
1.rules.lex  --> lex file for tokenizing input grammar.
2.rules.y --> grammar declaration and parsing.
3.input1, input2, input3 are the given input files to test.
	Change the inputfile in rules.y file to test desired input.

Commands to run in the order:

1.flex rules.lex
2.yacc -d rules.y
3.gcc rules.tab.c
4.a.exe