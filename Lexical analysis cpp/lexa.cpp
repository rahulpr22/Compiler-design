#include<bits/stdc++.h>
using namespace std;


bool isKeyword(char* str) 
{ 
	if (!strcmp(str, "if")   ||
		!strcmp(str, "else") || 
		!strcmp(str, "then") 
	   ) 
		return (true); 
	return (false); 
}
bool isDelimiter(char ch) 
{ 
	if (ch==' '|| ch == '>' || ch == '<' || ch == '=') 
		return (true); 

	return (false); 
} 

bool isOperator(char ch) 
{ 
	if (ch == '>' || ch == '<' || 
		ch == '=') 
		return (true); 
	return (false); 
} 
 
bool validIdentifier(char* str) 
{ 
	if (str[0] == '0' || str[0] == '1' || str[0] == '2' || 
		str[0] == '3' || str[0] == '4' || str[0] == '5' || 
		str[0] == '6' || str[0] == '7' || str[0] == '8' || 
		str[0] == '9' || isDelimiter(str[0]) == true) 
		return (false); 
	return (true); 
} 

bool isInteger(char* str) 
{ 
	int i, len = strlen(str); 

	if (len == 0) 
		return (false); 
	for (i = 0; i < len; i++) { 
		if (str[i] != '0' && str[i] != '1' && str[i] != '2'
			&& str[i] != '3' && str[i] != '4' && str[i] != '5'
			&& str[i] != '6' && str[i] != '7' && str[i] != '8'
			&& str[i] != '9' || (str[i] == '-' && i > 0)) 
			return (false); 
	} 
	return (true); 
} 
bool isnumb(char ch)
 {
 	if(ch=='0'||ch=='1'||ch=='2'||
 	   ch=='3'||ch=='4'||ch=='5'||
 	   ch=='6'||ch=='7'||ch=='8'||
 	   ch=='9'||ch==' ')
 		return true;

 	return false;
 }
bool isRealNumber(char* str) 
{ 
	int i, len = strlen(str); 
	bool hasDecimal = false; 

	if (len == 0) 
		return (false); 
	for (i = 0; i < len; i++) { 
		if (str[i] != '0' && str[i] != '1' && str[i] != '2'
			&& str[i] != '3' && str[i] != '4' && str[i] != '5'
			&& str[i] != '6' && str[i] != '7' && str[i] != '8'
			&& str[i] != '9' && str[i] != '.' || 
			(str[i] == '-' && i > 0)) 
			return (false); 
		if (str[i] == '.') 
			hasDecimal = true; 
	} 
	return (hasDecimal); 
} 
 
char* subString(char* str, int left, int right) 
{ 
	int i; 
	char* subStr = (char*)malloc( sizeof(char) * (right - left + 2)); 

	for (i = left; i <= right; i++) 
		subStr[i - left] = str[i]; 
	subStr[right - left + 1] = '\0'; 
	return (subStr); 
} 

void parse(char* str) 
{ 
	int left = 0, right = 0; 
	int len = strlen(str); 
	int temp=0;
	while (right <= len && left <= right) { 
		if (isDelimiter(str[right]) == false) 
			right++; 

		if (isDelimiter(str[right]) == true && left == right){
			 if(isOperator(str[right]) == true && isOperator(str[right+1])==true ) 
			{
				
				 cout<<"("<<str[right]<<str[right+1]<<", "<<"relop)"<<endl;	
			}
			else if(isOperator(str[right]) == true && isOperator(str[right+1])==false && isOperator(str[right-1])==false )
			{	
				if( isDelimiter(str[right-1])==false || isDelimiter(str[right+1])==true || isnumb(str[right+1])==true)
				    cout<<"("<<str[right]<<", "<<"relop"<<")"<<endl;
				
				
			}
			
			right++;
			left = right; 
		}


		else if (isDelimiter(str[right]) == true && left != right 
				|| (right == len && left != right)) { 
			char* subStr = subString(str, left, right - 1); 

			if (isKeyword(subStr) == true) 
				cout<<"("<<subStr<<", keyword"<<")"<<endl;
			
			else if (isInteger(subStr) == true) 
				cout<<"("<<subStr<<", "<<"number/digit"<<")"<<endl;

			else if (isRealNumber(subStr) == true) 
				cout<<"("<<subStr<<", "<<"number"<<")"<<endl;

			else if (validIdentifier(subStr) == true && isDelimiter(str[right - 1]) == false) 
				cout<<"("<<subStr<<", "<<"id"<<")"<<endl;

			else if (validIdentifier(subStr) == false && isDelimiter(str[right - 1]) == false) 
				cout<<"("<<subStr<<", "<<"invalid identifier"<<")"<<endl;

			left = right; 
		} 
	} 
	return; 
} 

int main()
{
	char s[100];
	cout<<"enter a string"<<endl;
	cin.getline(s,100);
	parse(s);
	return 0;

}