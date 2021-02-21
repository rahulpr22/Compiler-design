%{ 
#include "ast.tab.h"
#include<stdio.h> 
%} 

/* Rule Section */
%% 
[0-9]+ { 
		node *newnode=(node *)malloc(sizeof(node));
		newnode->OpFlag=0;
		newnode->left=NULL;
		newnode->right=NULL;
		newnode->value=yytext[0];
		yylval=newnode;
		return NUMBER;		
	} 
[\t] ; 
[\n] return 0; 
. return yytext[0]; 
%% 
int yywrap() 
{ 
	return 1; 
}
