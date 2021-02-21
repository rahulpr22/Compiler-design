%{
#include<stdio.h>
%}

%%

(1|0)*101(1|0)* {printf("Accepted");}

.* {printf("Rejected");};
%%

int yywrap() {return 1;}

int main(){

	printf("Enter input:");
	yylex();
		
	return 0;
}