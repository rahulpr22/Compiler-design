%{
#include "lex.yy.c"
void yyerror(char *str);
char genCode(char opr, char src1,char src2);
char p='A'-1;
%}
%union
{
    char dval;
}
%token NUMBER ID
%left '+' '-'
%left '*' '/'
%nonassoc UMINUS
%type<dval> S
%type<dval> E NUMBER ID
%%
S : E {printf("\n t = %c",$$);}
;
E : NUMBER {$$ = (char)$1;}
| ID {$$ = (char)$1;}
| E '+' E {$$ =genCode('+',$1,$3);}
| E '-' E {$$ =genCode('-',$1,$3);}
| E '*' E {$$ =genCode('*',$1,$3);}
| E '/' E {$$ =genCode('/',$1,$3);}

;
%%

char genCode(char opr, char src1,char src2)
{
    p++;
    printf("\n %c = %c %c %c ",p,src1,opr,src2);
    return p;
}
void yyerror(char *str)
{
printf("Encountered Invalid character");
}
void main()
{
printf("Enter Expression : ");
yyparse();
}