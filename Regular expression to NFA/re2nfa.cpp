#include<bits/stdc++.h>
#define pb push_back

using namespace std;

const int N = 109;
const int ALPHABET_SZ = 26;

struct node {
	// op: operator, ss: start state, es: end state
	int op, ss, es; 
};

node nn[N];
int n, m = ALPHABET_SZ, ss, es, timer;

int prio(char c) {
	if(c == '*') return 3;
	if(c == '.') return 2;
	if(c == '+') return 1;
	return 0;
}

vector<int> t[N][N];

string infix_to_postfix(string s) {
	stack<char> st;
	st.push('(');
	s += ')';
	int n = s.size();
	string ret;
	for(int i=0; i<n; i++) {
		if(prio(s[i])) {
			while(!st.empty()) {
				if(prio(st.top()) <= prio(s[i])) {
					st.push(s[i]);
					break;
				}
				ret += st.top();
				st.pop();
			}
		} else if(s[i] == '(') {
			st.push(s[i]);
		} else if(s[i] == ')') {
			while(!st.empty()) {
				if(st.top() == '(') {
					st.pop();
					break;
				}
				ret += st.top();
				st.pop();
			}
		} else if(isalpha(s[i])) {
			ret += s[i];
		}
	}
	cout<<"Postfix expression: "<<ret<<endl;
	return ret;
	
}

// builds the final nfa
void postfix_to_nfa(string s) {

	// modifying stack elements from char to struct
	n = s.size();
	for(int i=0; i<n; i++) {
		if(prio(s[i]) == 0) {
			nn[i].ss = ss = timer++;
			nn[i].es = es = timer++;
			nn[i].op = 0;
			int sym = s[i] - 'a' + 1;
			t[ss][sym].pb(es);
		} else {
			nn[i].ss = nn[i].es = ss = es = timer++;
			nn[i].op = prio(s[i]);
		}
	}

	// pushing non-operators; poping and mergeing for each operator 
	// introduces two new states for each operator
	stack<node> stt;
	for(int i=0; i<n; i++) {
		if(nn[i].op == 0) {
			stt.push(nn[i]);
		} else if(nn[i].op == 1) { // +
			node ftop = stt.top(); 
			stt.pop();
			node stop = stt.top(); 
			stt.pop();
			node temp;
			temp.ss = ss = timer++;
			temp.es = es = timer++;
			temp.op = 0;
			t[ss][0].pb(ftop.ss); 
			t[ss][0].pb(stop.ss);
			t[ftop.es][0].pb(es); 
			t[stop.es][0].pb(es);
			stt.push(temp);
			
		} else if(nn[i].op == 2) { // .
			node ftop = stt.top(); stt.pop();
			node stop = stt.top(); stt.pop();
			node temp;
			temp.ss = ss = timer++; temp.es = es = timer++;
			temp.op = 0;
			t[ss][0].pb(stop.ss); 
			t[stop.es][0].pb(ftop.ss); 
			t[ftop.es][0].pb(es);
			stt.push(temp);
		} else if(nn[i].op == 3) { // *
			node ftop = stt.top(); stt.pop();
			node temp;
			temp.ss = ss = timer++; temp.es = es = timer++;
			temp.op = 0;
			t[ss][0].pb(ftop.ss); t[ss][0].pb(es); 
			t[ftop.es][0].pb(ftop.ss); t[ftop.es][0].pb(es);
			stt.push(temp);
		}
	}
	cout<<"Individual e-NFA transitions according to postfix expression\n"<<endl;
	cout<<" operator:0 --> character\n operator:1 --> +(or)\n operator:2 --> .(concatenation)\n operator:3 --> *(kleene closure)\n"<<endl;
	cout<<"-------------------------------------------"<<endl;
	cout<<"S.state\t\toperator\tE.state\t"<<endl;
	cout<<"-------------------------------------------"<<endl;
	for(int i=0;i<n;i++)
	{
		
		cout<<nn[i].ss<<"\t\t"<<nn[i].op<<"\t\t"<<nn[i].es<<"\t\t"<<endl;
	}
}

// test the string s
void test(string s) {
	n = s.size();
		queue<pair<int, int> > q;
		q.push(make_pair(ss, 0));
		int iter = 1000000;
		bool found = 0;
		int cur, pos;
		while(!q.empty() && --iter) {
			cur = q.front().first; pos = q.front().second;
			q.pop();
			if(pos == n) {
				if(cur == es) {
					found = 1;
					break;
				}
			}
			for(int k=0; k<t[cur][0].size(); k++) {
				q.push(make_pair(t[cur][0][k], pos));
			}
			for(int k=0; k<t[cur][s[pos]-'a'+1].size(); k++) {
				q.push(make_pair(t[cur][s[pos]-'a'+1][k], pos+1));
			}
		}
		cout << (found ? "Accepted" : "Rejected") << endl;
}

int main() {

	cout<<"Input Format :\n\t '.' --> concatenation\n\t '+' --> or\n\t '*' --> closure\n Example: a*.(b+a).a\n Some of the strings accepted are aaba, aaaa \n"<<endl;

	cout << "Enter regex: ";

	string regex; cin >> regex;
	regex = infix_to_postfix(regex);
	postfix_to_nfa(regex);

	cout << "Enter no of strings to test: ";
	int cases; cin >> cases;
	while(cases--) {
		cout << "Enter test string: ";
		string s; cin >> s;
		test(s);
	}


	return 0;
}