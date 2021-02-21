Commands for testing the Abstract syntax tree.

1. flex ast.lex
2. yacc -d ast.y
3. gcc ast.tab.c
4. a.exe

Explanation:

**AST Explanation:
-----------------------------------------------------------------------------------------------
I have declared a structure node to process the information at each node in ast, i.e to traverse through all nodes in ast.

typedef struct node{
	  int OpFlag; //Decides whether an operator is involved or not; OpFlag=0 for no operator else flag=1;
	  char value; // if OpFlag = 1 then (+,addition), (-,subtraction), (*,multiplication),( /,division); else it holds a number
	  struct node *left; //The left child node
	  struct node *right; //The right child node
  }node;

The grammar declared is :

Start:  exp
exp ->    exp + term         {exp.val = new PlusNode(e.val, term.val) }
	| exp -> exp – term  {exp.val = new MinusNode(e.val, t.val) }
	| term 		     { exp.val = term.val}

term -> term * factor {term.val = new MultNode(term.val, factor.val)}
      | term / factor {term.val = new DivNode(term.val, factor.val)}  
      | factor        {term.val=F.val} 
 
factor -> NUMBER {factor.val = new ValNode}

Here, by PlusNode we refer to creating a node with OPflag set to 1 as it involves operator and value set to '+'. Similar is the case for MinusNode, MultNode and DivNode and value will be set to '-','*','/' respectively.
For valnode, left and right are NULL as AST stops at leaf node. Also, OpFlag = 0 because this is just assigning number and no operator is involved and value holds actual identifier here it is number.
factor -> (NUMBER) {factor.val = number} //That is, the actual numerical value.

Here we note that for each semantic action, we are creating a node with symbol as root and expressions as subtrees.
Now to showcase the nodes in ast we perform the preorder traversal.

Let expression to be parsed is : 1 + 2 * 3
Parser actions: Start -> exp -> exp + term -> exp + term * factor -> term + term * term -> factor + factor * factor


We start from the bottom and move to the top. All the literals 'factor' create a node 'ValNode', and then term + term * term creates 'PlusNode' and 'MultNode' respectively by their semantic actions.
PlusNode would be processed earlier, followed by MultNode and same would be the order in the syntax tree.
Output: + 1  * 2 3 , ( Preorder traversal )i.e
		
		+
	       / \
	      1   *
		 / \
		2   3


**3-Address Code Generation:
-----------------------------------------------------------------------------------------------------------------------
Commands for testing the Abstract syntax tree.

1. flex 3Adr.lex
2. yacc -d 3Adr.y
3. gcc 3Adr.tab.c
4. a.exe

Grammar and semantic rules:

Start -> E and semantic action involved is {printf("t=%c",$$);}
E -> E + E {$$=genCode('+',$1, $3)}
   | E – E {$$=genCode('-',$1, $3)}
   | E * E {$$=genCode('*',$1, $3)}
   | E / E {$$=genCode('/',$1, $3)}
   |NUMBER {$$ = (char)$1;} 
   |ID     {(char)$1;}; 

Here $$ refers to the result (LHS), while $1, $2 ,$3 refers to the RHS tokens, in order.
Since in 3-address-code we can only have 3 operators in RHS at maximum, everytime we find that, we assign that subcomponent as a result to a new token which is to be processed further and will be consumed by other nodes.

Let expression to be parsed: 1+2*3 

Parser actions: Start ->  E ->  E ->  E + E -> E + E * E ->  NUMBER + NUMBER * NUMBER
1.In the 1st step we do E -> E + E, it performs genCode('+',E,E) and returns LHS as 'A'
2.In the 2nd step we do E -> A * E, which performs genCode('*',A,E) and returns LHS as 'B'. 

Output: 

A = 2 * 3
B = 1 + A
t = B 

