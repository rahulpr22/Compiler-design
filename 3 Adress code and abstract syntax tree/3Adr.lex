%{
	#include<stdio.h>
	#include<stdlib.h>
	#include<string.h>
#include "3Adr.tab.h"
%}
%%
[0-9]+ {yylval.dval=(char)yytext[0];return NUMBER;}
[a-z]+ {yylval.dval=(char)yytext[0];return ID;}
[\t] ;
[\n] return 0;
. {return yytext[0];}
%%

int yywrap() 
{ 
	return 1; 
}