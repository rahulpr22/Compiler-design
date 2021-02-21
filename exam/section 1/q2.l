%{ 
    #include<stdio.h>
%} 
  
%% 
^[-+]?[0-9]* {printf("Valid Integer number\n"); }
^[-+]?[0-9]*[.][0-9]+$ {printf("Valid Float number\n");  }
.* {    printf("Not valid Integer/Float number\n"); }; 
%% 
  
int main() 
{ 
    printf("Enter input:"); 
  yylex(); 
  
  return 0; 
} 
int yywrap() {return 1;}