%{
	#include<stdio.h>
%}
%%
(1|(01*01*))* {
				printf("Accepted\n");
				return 1;
			  }

.* 			  {
				printf("Rejected\n");
				return 2;
			  }
%%
int yywrap(void) 
{
	return 1;
}

int main(){
	int count=0;
	int i=1;
	printf("Enter how many inputs you want to test:");
	scanf("%d",&count);
	while(count!=0){
		
		printf("Enter input %d",i);
		yylex();
		i=i+1;
		count= count-1;
	}
		
	return 0;
}