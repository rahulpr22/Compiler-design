%{
#include <stdio.h>
#include <math.h>
#include "lex.yy.c"
int yyerror(char *s);
%}

%token NUMBER NEWLINE
%left PLUS MINUS
%left MULTIPLY DIVIDE MOD

%%

start: start Input
       | 
       ;

Input: E 
	     { printf("Result : %lf\n", $1); printf("\n Valid Arithmetic Expression!!\n");}
	     | NEWLINE {exit(0);}
         ;

E: NUMBER           { $$ = $1; }
      | E PLUS E   { $$ = $1 + $3; }
      | E MULTIPLY E  { $$ = $1 * $3; }
      | E MINUS E  { $$ = $1 - $3; }
      | E DIVIDE E { $$ = $1 / $3; }
      | E MOD E {$$ = fmod($1,$3);}




%%
extern int yylex();

int yyerror(char *s) {
  fprintf(stderr, "%s\n", s);
  return 0;
}

int main() {
  printf("\nEnter an arithmetic Expression which consists either adition, subtraction, multiplication, division or modulus operators :\n");
  yyparse();
  return 0;
}