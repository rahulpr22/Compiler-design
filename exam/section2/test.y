%{

#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>

void yyerror(char *str);
int count=0; /* keeps count of temporary label */
int error=0;

extern int yylineno;

int label = 0; 
int else_reference = 0;
int while_reference = 0;

#include "test.tab.h"
#include "lex.yy.c"
%}

%token LEFT_CURL RIGHT_CURL FOR AND ASSIGN COLON COMMA DEF DIV DOT ELSE END EQ EXITLOOP FLOAT FLOAT_CONST FORMAT FROM FUN GE MAIN GT IF INT LEFT_PAREN LEFT_SQ_BKT LE LT MINUS MOD MULT NE NOT NUL OR PLUS PRINT PRODUCT READ RETURN RETURNS RIGHT_PAREN RIGHT_SQ_BKT SEMICOLON SKIP STEP STRING TO WHILE

%left PLUS MINUS
%left MULT DIV MOD
%left AND OR 
%nonassoc NOT
%left LEFT_SQ_BKT RIGHT_SQ_BKT
%left LEFT_PAREN RIGHT_PAREN
%nonassoc IF
%nonassoc ELSE

%union
{ 	
	char val[100];
}
   
%type<val> exp relOP bExp
%token<val> ID INT_CONST

%start S
%%
S : prog {printf("%s\n", "Program Accepted"); return 1;}
  ;
prog
  : MAIN LEFT_PAREN RIGHT_PAREN LEFT_CURL stmtListO END RIGHT_CURL
  ;

stmtListO
  : stmtList
  |
  ;

stmtList
  : stmtList SEMICOLON stmt
  | stmt
  ;

stmt
  : assignmentStmt {};
  | readStmt{};
  | printStmt{};
  | ifStmt{};
  | whileStmt{};
  |forStmt {};
  ;

assignmentStmt
  : ID ASSIGN exp 
  ;

readStmt
  : READ ID 
  ;

printStmt
  : PRINT ID
  ;

ifStmt
  : IF bExp COLON stmtList elsePart END
  ;

elsePart
  : ELSE stmtList
  |
  ;

whileStmt
  : WHILE bExp COLON stmtList END
  ;

bExp
  : bExp OR bExp {};
  | bExp AND bExp {};
  | NOT bExp {};
  | LEFT_PAREN bExp RIGHT_PAREN {};
  | exp relOP exp {};
  ;

relOP
  : EQ {};
  | LE {};
  | LT {};
  | GE {};
  | GT {};
  | NE {};
  ;


exp
  : exp PLUS exp {};
  | exp MINUS exp {};
  | exp MULT exp  {};
  | exp DIV exp {};
  | exp MOD exp {};
  | MINUS exp {};
  | PLUS exp {};
  | LEFT_PAREN exp RIGHT_PAREN {};
  | ID              {};           
  | INT_CONST       {};              
  ;


forStmt : FOR assignmentStmt SEMICOLON bExp SEMICOLON assignmentStmt COLON
        stmtList END SEMICOLON ;


%%

int main() 
{ 
  extern FILE *yyin;  
  yyin = fopen("input.txt","r");
  yyparse(); 
  if(error == 0) 
  	printf("\nEnd\n");
} 

void yyerror(char *str) 
{  
  fprintf(stderr,"Error at Line: %d -- %s\n",yylineno,str); 
  error = 1; 
} 