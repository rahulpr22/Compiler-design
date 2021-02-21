Input Grammar :
Grammar is taken from a txt file as input.
Note: Input Grammar should have space seperated terminals and non terminals.
Example: S -> A b | id  (input is space seperated because to identify id as id but not as i, d)

Basic Prerequisites to be installed: pandas, tabulate.
Commands to install Prerequisites:
>$ pip install pandas
>$ pip install tabulate

Commands to execute:
>$ python LR1_Parser.py

Procedure:
1.Read input from Grammar.txt file into a list.
2.compute first set for each nonTerminal
3.Class items consist of a constructor which has parameters as index, left over production to the right of dot  and dot index.
4.Then we implement computeStates function which uses closure and goto functions.
5.Transitions in Dfa can be viewed from LR1_Automaton function.
6.From this computed states we build LR1_Parse Table.
