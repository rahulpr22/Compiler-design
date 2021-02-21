Commands for running the code :

1. flex 3ac.lex
2. yacc -d 3ac.y
3. gcc 3ac.tab.c
4. a.exe

Approach:
--------------------------------------------------------------------

In my input I have used nested If loops and nested while loops and the logic is as follows..

(a) count --> to keep track of number of temporary variables used eg: T1,T2,... This variable is incremented at productions involving computations.
(b) label --> to keep track of no. of labels introduced eg: L1, L2 ... this variable is incremented at conditional loops or statements.

 **If statement**
--------------------

If !(condition 1) goto (L1)
......
......
If !(condition 2) goto (L2)
.......
.......
L2 : statements    	--> control reaches here if condition 2 fails
.........
.........
goto (L3)     		--> get out of If logic
L1: ......    		--> control reaches here if condition 1 fails
.........
.........
L3 : rest of the code



 **While Statement**
---------------------------------------- 

In while loop we use two labels, one label at the beginning of while loop and the other label to get out of while loop.

Let us say label_1 and label_2 are the two labels used.

L<label_1> : If !(condition) goto (L<label_2>)
........................
........................
goto (L<label_1>)
L<label_2> : 
rest of the code

In the code while_reference refers to label_1

Input is provided in the input.txt file

   **Sample output**
------------------------

Three Address Code:
------------------------------------------
x = 1
T0 = x + 5
y = T0
T1 = y + x
z = T1
print x
T2 = Read y
T3 = p != q
T4 = a == 5
T5 = T3 and T4
T6 = T5
If ! (T6) goto L0
print c
T7 = b > 0
T8 = T7
If ! (T8) goto L1
print c
goto L2
L1 : T9 = b + 1
b = T9
L2 : print q
goto L3
L0 : print a
print b
L3 : L4 : T10 = x > y
If ! (T10) goto L5
T11 = x + 1
p = T11
L6 : T12 = a > b
If ! (T12) goto L7
T13 = y + 1
q = T13
goto L6
L7 : goto L4
L5 : End



