import pandas as pd

nonTerminals, Terminals, nonTerminals_set, Terminals_set, first = list(), list(), set(), set(), dict()
States, Goto, Goto_States = list(), list(),list()

class Items:
    global nonTerminals, Terminals, nonTerminals_set, first
    def __init__(self, index, F, dindex):
        self.lp, self.rp = nonTerminals[index], list()
        self.index= index
        for i in range(len(Terminals[index])):
            self.rp.append(Terminals[index][i])
        self.rp.insert(dindex, '.')
        self.dindex, self.f = dindex, F
    
    def __eq__(self, other):
        if not isinstance(other, Items):
            return NotImplemented
        return (self.dindex == other.dindex and self.f == other.f and self.index == other.index)
        
def Closure(nt):
    I = []
    for i in range(len(nonTerminals)):
        if(nonTerminals[i] == nt):
            I.append(i)
    return I

def Verify_State(I, L):
    for i in range(len(L)):
        if(I == L[i]):
            return False
    return True

def FindGoto(L):
    for i in range(len(States)):
        S, k = States[i], 0
        if(len(L) == len(S)):
            for j in range(len(S)):
                if(L[j] == S[j]):
                    k += 1
        if(k == len(L)):
            return i
    return -1

def ComputeStates():
    global nonTerminals, Terminals, nonTerminals_set, first, States
    I, L, G, i = Items(0, '$', 0), list(), [], 0
    L.append(I)
    while(i < len(L)):
        I = L[i]
        di, rp = I.dindex, I.rp # rp consists of dotted rhs productions
        rp.append('#')# to mark end of line
        if(rp[di+1] in nonTerminals_set):
            f = '$'
            if(di+2 < len(rp) and rp[di+2] != '#'):
                if(rp[di+2] in nonTerminals_set):
                    f = first[rp[di+2]]
                else:
                    f = rp[di+2]
            elif(di+2 < len(rp) and rp[di+2] == '#'):
                f = I.f
            indices = Closure(rp[di+1])
            for ind in indices:
                It = Items(ind, f, 0)
                if(Verify_State(It, L)):
                    L.append(It)
        rp.pop()
        i += 1
    for i in range(len(L)):
        I = L[i]
        di = I.dindex
        if(di != len(I.rp)-1):
            G.append(I.rp[di+1])
    States.append(L)
    Goto.append(G)
        
def Compute_LR_1():
    global nonTerminals, Terminals, nonTerminals_set, first, States, Goto, Goto_States
    i = 0
    while(i < len(States)):
        S, G = States[i], Goto[i]
        GS = list()
        for p in range(len(G)):
            g, L, GG = G[p], list(), list()
            for j in range(len(S)):
                I = S[j]
                di, rp = I.dindex, I.rp
                if(di == len(I.rp)-1):
                    continue
                if(rp[di+1] == g):
                    new_I = Items(I.index, I.f, di+1)
                    L.append(new_I)
            if(len(L) > 0):
                k = 0
                while(k < len(L)):
                    I = L[k]
                    di, rp = I.dindex, I.rp
                    rp.append('#')
                    if(rp[di+1] in nonTerminals_set):
                        f = I.f
                        if(di+2 < len(rp) and rp[di+2] != '#'):
                            if(rp[di+2] in nonTerminals_set):
                                f = first[rp[di+2]]
                            else:
                                f = rp[di+2]
                        elif(di+2 < len(rp) and rp[di+2] == '#'):
                            f = I.f
                        indices = Closure(rp[di+1])
                        for ind in indices:
                             It = Items(ind, f, 0)
                             if(Verify_State(It, L)):
                                 L.append(It)
                    rp.pop()
                    k += 1
            for p in range(len(L)):
                I = L[p]
                di = I.dindex
                if(di != len(I.rp)-1):
                    GG.append(I.rp[di+1])
            SS = FindGoto(L)
            if(SS == -1 and len(L) != 0):
                States.append(L)
                Goto.append(GG)
                GS.append(len(States)-1)
            else:
                GS.append(SS)
        Goto_States.append(GS)
        i += 1
    
def LR1_Automaton():
    l, Tr = len(Goto), list()
    for i in range(l):
        G, GS = Goto[i], Goto_States[i]
        for j in range(len(G)):
            if(G != []):
                P = "S" + str(i) + " -> " + "S" + str(GS[j]) +" , " + G[j]
                if(P not in Tr):
                    Tr.append(P)
    for i in Tr:
        print(i) 
        
def LR1_ParseTable():
    global nonTerminals, Terminals, nonTerminals_set, first, States, Goto, Goto_States
    r, c = len(States), len(nonTerminals_set) + len(Terminals_set) + 1 
    ParseTable = [['' for j in range(c)] for i in range(r)]
    NT_list, T_list = list(nonTerminals_set), list(Terminals_set)
    NT_list.sort(), T_list.sort()
    for i in range(len(States)):
        S, G, GS = States[i], Goto[i], Goto_States[i]
        r = i
        for j in range(len(S)):
            I = S[j]
            di = I.dindex
            rp = I.rp
            if(di == len(rp)-1):
                index, F = I.index, I.f
                if(F == "$"):
                    c = 0
                    if(index == 0):
                        ParseTable[r][c] = "Accept"
                    else:
                        if(ParseTable[r][c] == ""):
                            ParseTable[r][c] = nonTerminals[index] + '->' + ''.join(Terminals[index])
                        elif((nonTerminals[index] + '->' + ''.join(Terminals[index])) not in ParseTable[r][c]):
                            ParseTable[r][c] += ", " +  nonTerminals[index] + '->' + ''.join(Terminals[index])
                else:
                    for k in range(len(F)):
                        c = T_list.index(F[k]) + 1
                        if(ParseTable[r][c] == ""):
                            ParseTable[r][c] = nonTerminals[index] + '->' + ''.join(Terminals[index])
                        elif((nonTerminals[index] + '->' + ''.join(Terminals[index])) not in ParseTable[r][c]):
                            ParseTable[r][c] += ", " + nonTerminals[index] + '->' + ''.join(Terminals[index])
            else:
                index = G.index(rp[di+1])
                if(rp[di+1] in nonTerminals_set):
                    c = NT_list.index(rp[di+1]) + len(T_list) + 1
                elif(rp[di+1] in Terminals_set):
                    c = T_list.index(rp[di+1]) + 1
                else:
                    c = 0
                if(ParseTable[r][c] == ""):
                    ParseTable[r][c] = 'S' + str(GS[index])            
                elif(('S' + str(GS[index])) not in ParseTable[r][c]):
                    ParseTable[r][c] += "," + 'S' + str(GS[index])
    Symbols = ["$"] + T_list + NT_list
    df = pd.DataFrame(ParseTable, columns = Symbols)
    return df
    
def First(index):
    global nonTerminals, Terminals, nonTerminals_set, first
    nt, t = nonTerminals[index], Terminals[index]
    if(nt not in first):
        first[nt] = list()
    if(t[0] not in first[nt]):
        first[nt].append(t[0])

#takes care of epsilon productions
def FindFirst(index):
    global nonTerminals, Terminals, nonTerminals_set, first
    nt, t, l = nonTerminals[index], Terminals[index], len(Terminals[index])
    for i in range(0, l-1):
        if(t[i] in nonTerminals_set and '#' in first[t[i]]):
            first[nt].append(t[i+1])
        elif(t[i] in nonTerminals_set and '#' not in first[t[i]]):
            break
        elif(t[i] not in nonTerminals_set):
            break

 # recursive function for final first set computation       
def FinalFirst(nt):
    global nonTerminals, Terminals, nonTerminals_set, first
    L, i = first[nt], 0
    while(i < len(L)):
        if(L[i] == nt and '#' not in L):
            L.pop(i)
            i -= 1
        elif(L[i] in nonTerminals_set):
            F = first[L[i]]
            for j in range(len(F)):
                if(F[j] not in L):
                    L.append(F[j])
            L.pop(i)
            i -= 1
        i += 1
    return L

if __name__ == "__main__":
    S = list()
    Grammar=open("Grammar.txt")
    S=Grammar.readlines()
    for i in range(len(S)):
        s = S[i].replace('->', '')
        s = s.replace('|', ' | ')
        s = s.split()
        L = list()
        for j in range(1, len(s)):
            if(s[j] == '|'):
                nonTerminals.append(s[0])
                Terminals.append(L)
                L = list()
            else:
                L.append(s[j])
        nonTerminals.append(s[0])
        Terminals.append(L)
    nonTerminals_set = set(nonTerminals)
    N = list(nonTerminals_set)
    for i in range(len(nonTerminals)):
        First(i)
    for i in range(len(nonTerminals)):
        FindFirst(i)
    for i in range(len(N)):
        first[N[i]] = FinalFirst(N[i])
    for i in range(len(Terminals)):
        for j in range(len(Terminals[i])):
            k = Terminals[i][j]
            if(k not in nonTerminals_set):
                 Terminals_set.add(k)
    
    
    ComputeStates()
    Compute_LR_1()
    print("------------------------------------------------------------\nOutput:\n")
    print("First Sets:\n")
    print(first)
    print("\nItems in each state of LR(1) Automaton: \n")
    for i in range(len(States)):
        S = States[i]
        print("\u0332".join("State -" + str(i))+" :\n")
        for j in range(len(S)): 
            I = S[j]
            print(I.lp + ' -> ' + ' '.join(I.rp) + ' , ' + str(I.f))
        print("\n")
    
    print("------------------------------------------------------------\n")
    print("DFA transitions\n")
    LR1_Automaton()
    print("------------------------------------------------------------\n")
    print("LR(1) ParseTable :\n")
    df = LR1_ParseTable()
    if("S'" in df.columns):
        df = df.drop("S'", axis = 1)
        print(df.to_markdown())
    print("\n")
    