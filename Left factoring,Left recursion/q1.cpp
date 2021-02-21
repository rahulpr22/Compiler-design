#include <iostream>
#include <vector>
#include <stdio.h> 
#include <string.h> 
using namespace std;
string trim(const string& str)
{
    size_t first = str.find_first_not_of(' ');
    if (string::npos == first)
    {
        return str;
    }
    size_t last = str.find_last_not_of(' ');
    return str.substr(first, (last - first + 1));
}
int main()
{
    int n;
    cout<<"\nEnter number of Productions: ";
    cin>>n;
    cout<<"\nEnter non terminals one by one :)\n";
    cout<<"-----------------------------------\n";
    
    vector<string> nonter(n);
    vector<int> leftrecr(n,0);
    for(int i=0;i<n;++i) {
            cout<<"\nNon terminal "<<i+1<<" : ";
        cin>>nonter[i];
    }
    vector<vector<string> > prod;
    cout<<"\nEnter '$' for null"<<endl;
        cin.ignore();
    for(int i=0;i<n;i++) {
        char s[10000];
        vector<string> temp;

        cout<<"Enter Production for Non terminal "<<nonter[i]<<endl;
       
        
        cin.getline(s,10000);
        char *token = strtok(s, "|"); 
        
        while (token != NULL) 
        { 
            temp.push_back(trim(token)); 
            token = strtok(NULL, "|"); 
        } 

        //check for left recursion if non terminal and first character of RHs match 
        for(int j=0;j<temp.size();j++) {
                        
            string abc=temp[j];
            if(nonter[i].length()<=abc.length()&&nonter[i].compare(abc.substr(0,nonter[i].length()))==0)
                leftrecr[i]=1;
        }
        prod.push_back(temp);
    }
    
    /*for(int i=0;i<n;i++) {
        cout<<leftrecr[i];
    }*/
    for(int i=0;i<n;i++) {
        if(leftrecr[i]==0)
            continue;
        
        nonter.push_back(nonter[i]+"'");
        vector<string> temp;
        for(int j=0;j<prod[i].size();++j) {
            if(nonter[i].length()<=prod[i][j].length()&&nonter[i].compare(prod[i][j].substr(0,nonter[i].length()))==0) {
                string abc=prod[i][j].substr(nonter[i].length(),prod[i][j].length()-nonter[i].length())+nonter[i]+"'";
                temp.push_back(abc);
                prod[i].erase(prod[i].begin()+j);
                --j;
            }
            else {
               
                prod[i][j]+=nonter[i]+"'";
                
            }
        }
        temp.push_back("$");
        prod.push_back(temp);
    }
    
    cout<<"\nNew set of non-terminals: ";
    for(int i=0;i<nonter.size();++i)
        cout<<nonter[i]<<" ";
    cout<<"\n\nNew set of productions: "<<endl;
    for(int i=0;i<nonter.size();++i) {
        int j;
        string rhs="";
        for(j=0;j<prod[i].size();++j) {
            rhs+= " "+prod[i][j]+" |";

        }
        cout<<nonter[i]<<"\t-> "<<rhs.substr(0,rhs.length()-1)<<endl;
    }
    return 0;
}
