To execute program follow thes commands in the order:

1. flex calculator.lex
2. yacc -d calculator.y
3. gcc calculator.tab.c
4. a.exe

Answer for Q1 part can be found in ASSIGNMENT-8 Q1.pdf

The CFG for Q1 would be:
-----------------------------------------
E -> E + T | E - T | T
T -> T * F | T / F | F
F -> number
-----------------------------------------

Proof of this grammar is suitable for LR parsing can be found in ASSIGNMENT-8 Q1.pdf

Example: 2 + 3 * 4 (Left Recursive Derivation)

E -> E + T -> E + T * F  -> E + T * number -> E + F * number -> E + number * number -> T + number * number -> F + number * number -> number + number * number

**Note:** For YACC implementation, I have simply used E -> E + E | E – E | E * E | E / E | number , as associativity/precedence can be handled by YACC.