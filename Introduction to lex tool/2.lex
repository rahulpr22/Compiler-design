%{ 
    #include<stdio.h>
	#include<string.h>
	void classifyKeyword(char *str);
%}   
%% 
[0-9] 							    {printf("%s --> digit\n",yytext);}
[0-9]+ 								{printf("%s --> digits\n",yytext);}
[0-9]+([.][0-9]+)?([E][+-]?[0-9]+)? {printf("%s --> number\n",yytext);}
[a-zA-z] 							{printf("%s --> letter\n",yytext);}
[a-zA-z]([a-zA-z]|[0-9])* 			{classifyKeyword(yytext);}
([<]|[>]|[<][=]|[>][=]|[<][>]|[=]|[=][=])  {printf("%s -->relop\n",yytext);}
["\n"" "] {}
%% 
int yywrap(){}  
int main() 
{ 
	printf("Enter String\n");
	yylex(); 
	return 0; 
} 
void classifyKeyword(char *str)
{
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
		printf("%s -->Then\n",yytext);
	}
	else
	{
		printf("%s -->id\n",yytext);
	}
}