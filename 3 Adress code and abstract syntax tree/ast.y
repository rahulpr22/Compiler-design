%{ 
   /* Definition section */
  #include<stdio.h>
  #include<stdlib.h>
  typedef struct node{
	  int OpFlag;
	  char value;
	  struct node *left;
	  struct node *right;
  }node;
  #define YYSTYPE node*
  node* makeNode(int opFlag,char opr,node* left,node*right);
  void yyerror(char *s);
  void preorder(node *x);
  #include "lex.yy.c"

%} 
%start Start
%token NUMBER 
  
%left '+' '-'
  
%left '*' '/' '%'
  
/* Rule Section */
%% 
  
Start: exp                  { 
                                printf("\nOutput\n"); 
                                preorder($1);
                                return 0; 
                            }; 

exp:exp'+'term 
                            {
                               $$=makeNode(1,'+',$1,$3);
                            } 
|exp'-'term 
                            {
                               $$=makeNode(1,'-',$1,$3);
                            } 
|term
                            {
                               $$=$1;
                            };

term: term'*'factor 
                            {
                               $$=makeNode(1,'*',$1,$3);
                            } 
  
|term'/'factor 
                            {
                               $$=makeNode(1,'/',$1,$3);
                            }
  
| factor    {$$=$1;} ;

factor: NUMBER {	$$=$1;};
  
%% 
void main() 
{ 
   printf("Enter an expression :"); 
   yyparse(); 
}
node* makeNode(int opFlag,char opr,node* left,node*right)
{
    node *newnode=(node *)malloc(sizeof(node));
    newnode->OpFlag=opFlag;
    newnode->value=opr;
    newnode->left=left;
    newnode->right=right;
    return newnode;
}
void preorder(node *x)
{
    if(x==NULL)
        return;
    if(x->OpFlag)
   	    printf("%c ",x->value);
	else
        printf("%c ",x->value);

	preorder(x->left);
	preorder(x->right);
}
void yyerror(char *s) 
{ 
   printf("Encountered Invalid character\n"); 
}
