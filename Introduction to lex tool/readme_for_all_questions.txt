All the commands are for Linux operating system


	1a.lex corresponds to 1.1
	To run 1a use commands 
		flex 1a.lex
		gcc lex.yy.c
		./a.out
	Sample Execution:
	it will ask to enter how many tests u want perform: 2
	Enter input 1: 1000
	Rejected
	Enter input 2: 1
	Accepted

	1c.lex corresponds to 1.3
	To run 1a use commands 
		flex 1c.lex
		gcc lex.yy.c
		./a.out
	Sample Execution:
	it will ask to enter how many tests u want perform: 3
	Enter input 1: 1000
	Rejected
	Enter input 2: 100001
	Accepted
	Enter input 3: 011
	Accepted

	2.lex corresponds to 2.1 & 2.2
	To run 1a use commands 
		flex 1a.lex
		gcc lex.yy.c
		./a.out
	Sample Execution:
	Enter String:
					if x then y<=10 else y <> invalid
	Output:
	if -->If Keyword
	x --> letter
	then -->id
	y --> letter
	<= -->relop
	10 --> digits
	else -->Else Keyword
	y --> letter
	<> -->relop
	invalid -->id


	3.lex corresponds to 3
	program.txt is the input file for testing 3.lex
	To run 1a use commands 
		flex 3.lex
		gcc lex.yy.c
		./a.out