
Question 1:

	Commands for Execution: g++ q1.cpp
							./a.out

	Sample Input Format :  
							Enter number of Productions: 
							3
							Enter non terminals one by one: 
							Non terminal 1 : 
							E
							Non terminal 2 : 
							T
							Non terminal 3 : 
							F

							Enter '$' for null
							Enter Production for Non terminal E
							E+T | T
							Enter Production for Non terminal T
							T*F | F   
							Enter Production for Non terminal F
							(E) | id
	Sample Output :
							New set of non-terminals: E T F E' T' 

							New set of productions: 
							E -> TE'
							T -> FT'
							F -> (E)
							F -> id
							E' -> +TE'
							E' -> $
							T' -> *FT'
							T' -> $

Question 2 :

    Commands for Execution: javac q2.java
							java q2

	Sample Input  :
				Example Input: A--> qB | qC 
				Enter a Production : 
				A-->aAB|aAc|aBc
	
	Sample Output :
				left factored 1 time 
				A-->aX0
				X0--> AB|Ac|Bc

				left factored 2 time 
				X0-->Bc|AX1
				X1--> B|c
				--------------------------------------
				The result of left factoring is 

				A-->aX0
				X0-->Bc|AX1
				X1-->B|c

 Question 3 :

    Commands for Execution: javac q3.java
							java q3

	Sample Input  :
				Given Input productions

				E->TA
				A->+TA|$
				T->FC
				C->*FC|$
				F->(E)|id

				Sample input1 :  id+id
				Sample input2 :  id*id

				Enter Input String :
				(id)*id

	Sample Output :
				Accepted
