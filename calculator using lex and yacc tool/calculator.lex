%{
#define YYSTYPE double
extern int yyerror(char *s);
#include "calculator.tab.h"
%}
number [0-9]+\.?|[0-9]*\.[0-9]+


%%


{number}	{ yylval=atof(yytext);return NUMBER; }


"+"     return PLUS;
"*"     return MULTIPLY;
"-"     return MINUS;
"/"     return DIVIDE;
"%"		return MOD;
"\n"    return NEWLINE;

[ \t]   ;

.      { yyerror("Invalid operator");exit(0);}

%%

int yywrap() {
 return 1;
}