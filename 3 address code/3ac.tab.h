/* A Bison parser, made by GNU Bison 2.7.  */

/* Bison interface for Yacc-like parsers in C
   
      Copyright (C) 1984, 1989-1990, 2000-2012 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

#ifndef YY_YY_3AC_TAB_H_INCLUDED
# define YY_YY_3AC_TAB_H_INCLUDED
/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     AND = 258,
     ASSIGN = 259,
     COLON = 260,
     COMMA = 261,
     DEF = 262,
     DIV = 263,
     DOT = 264,
     ELSE = 265,
     END = 266,
     EQ = 267,
     EXITLOOP = 268,
     FLOAT = 269,
     FLOAT_CONST = 270,
     FORMAT = 271,
     FROM = 272,
     FUN = 273,
     GE = 274,
     GLOBAL = 275,
     GT = 276,
     IF = 277,
     INT = 278,
     LEFT_PAREN = 279,
     LEFT_SQ_BKT = 280,
     LE = 281,
     LT = 282,
     MINUS = 283,
     MOD = 284,
     MULT = 285,
     NE = 286,
     NOT = 287,
     NUL = 288,
     OR = 289,
     PLUS = 290,
     PRINT = 291,
     PRODUCT = 292,
     READ = 293,
     RETURN = 294,
     RETURNS = 295,
     RIGHT_PAREN = 296,
     RIGHT_SQ_BKT = 297,
     SEMICOLON = 298,
     SKIP = 299,
     STEP = 300,
     STRING = 301,
     TO = 302,
     WHILE = 303,
     ID = 304,
     INT_CONST = 305
   };
#endif


#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{
/* Line 2058 of yacc.c  */
#line 34 "3ac.y"
 	
	char val[100];


/* Line 2058 of yacc.c  */
#line 112 "3ac.tab.h"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;

#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */

#endif /* !YY_YY_3AC_TAB_H_INCLUDED  */
