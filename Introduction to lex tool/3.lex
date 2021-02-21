%{
	#include<stdio.h>
	#include<string.h>
	void classifyKeyword(char *str);
	int line=1;
	
%}
%%
[0-9] 							    {printf("%s --> digit\n",yytext);}
[0-9]+ 								{printf("%s --> digits\n",yytext);}
[0-9]+([.][0-9]+)?([E][+-]?[0-9]+)? {printf("%s --> number\n",yytext);}
[a-zA-z] 							{printf("%s --> letter\n",yytext);}
[a-zA-z]([a-zA-z]|[0-9])* 			{classifyKeyword(yytext);}
[=]									{printf("%s -->Assignment\n",yytext);}
[;]									{printf("%s -->Seperator\n",yytext);}
([+][=]|[+][+])						{printf("%s -->Compound Assignment\n",yytext);}
([+]|[-]|[*]|[/])					{printf("%s -->Arithmetic Operator\n",yytext);}
([<]|[>]|[<][=]|[=][=]|[>][=])      {printf("%s -->relop\n",yytext);}
[" ""("")""()""{}"] 				{}
["\n""{""}"] 						{line++;}
.									{printf("Invalid Token\n");}
%%


int yywrap(void) 
{
	return 1;
}

int main(){
	extern FILE *yyin; 
	yyin = fopen("program.txt", "r");
	printf("Output\n");
	printf("--------------------------------------------------------\n"); 
	yylex();
	printf("Number of Lines: %d\n",line);	
	return 0;
}
void classifyKeyword(char *str)
{
	if(strcmp(str,"main")==0)
	{
		printf("%s -->Main function\n",yytext);
	}
	if(strcmp(str,"if")==0)
	{
		printf("%s -->If Keyword\n",yytext);
	}
	else if(strcmp(str,"else")==0)
	{
		printf("%s -->Else Keyword\n",yytext);
	}
	else if(strcmp(str,"then")==0)
	{
		printf("%s -->Then Keyword\n",yytext);
	}
	else if(strcmp(str,"for")==0)
	{
		printf("%s -->for Keyword\n",yytext);
	}
	else if(strcmp(str,"while")==0)
	{
		printf("%s -->while Keyword\n",yytext);
	}
	else if(strcmp(str,"print")==0)
	{
		printf("%s -->Print Keyword\n",yytext);
	}
	else if(strcmp(str,"INT")==0 || strcmp(str,"int")==0)
	{
		printf("%s -->INT Keyword\n",yytext);
	}
	else if(strcmp(str,"FLOAT")==0 || strcmp(str,"float")==0)
	{
		printf("%s -->Float Keyword\n",yytext);
	}
	else if(strcmp(str,"read")==0)
	{
		printf("%s -->Read Keyword\n",yytext);
	}
	else
	{
		printf("%s -->id\n",yytext);
	}
}