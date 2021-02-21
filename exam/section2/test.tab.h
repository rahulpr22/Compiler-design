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

#ifndef YY_YY_TEST_TAB_H_INCLUDED
# define YY_YY_TEST_TAB_H_INCLUDED
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
     LEFT_CURL = 258,
     RIGHT_CURL = 259,
     FOR = 260,
     AND = 261,
     ASSIGN = 262,
     COLON = 263,
     COMMA = 264,
     DEF = 265,
     DIV = 266,
     DOT = 267,
     ELSE = 268,
     END = 269,
     EQ = 270,
     EXITLOOP = 271,
     FLOAT = 272,
     FLOAT_CONST = 273,
     FORMAT = 274,
     FROM = 275,
     FUN = 276,
     GE = 277,
     MAIN = 278,
     GT = 279,
     IF = 280,
     INT = 281,
     LEFT_PAREN = 282,
     LEFT_SQ_BKT = 283,
     LE = 284,
     LT = 285,
     MINUS = 286,
     MOD = 287,
     MULT = 288,
     NE = 289,
     NOT = 290,
     NUL = 291,
     OR = 292,
     PLUS = 293,
     PRINT = 294,
     PRODUCT = 295,
     READ = 296,
     RETURN = 297,
     RETURNS = 298,
     RIGHT_PAREN = 299,
     RIGHT_SQ_BKT = 300,
     SEMICOLON = 301,
     SKIP = 302,
     STEP = 303,
     STRING = 304,
     TO = 305,
     WHILE = 306,
     ID = 307,
     INT_CONST = 308
   };
#endif


#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{
/* Line 2058 of yacc.c  */
#line 34 "test.y"
 	
	char val[100];


/* Line 2058 of yacc.c  */
#line 115 "test.tab.h"
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

#endif /* !YY_YY_TEST_TAB_H_INCLUDED  */
