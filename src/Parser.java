//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Gramatica.y"
import java.util.Vector;
import java.util.Enumeration;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short THEN=258;
public final static short ELSE=259;
public final static short ENDIF=260;
public final static short PRINT=261;
public final static short INT=262;
public final static short LOOP=263;
public final static short TO=264;
public final static short ID=265;
public final static short CONSTANT=266;
public final static short FLOAT=267;
public final static short STRING=268;
public final static short COMPARATOR=269;
public final static short ASIGNATION=270;
public final static short END=271;
public final static short GLOBAL=272;
public final static short BEGIN=273;
public final static short FROM=274;
public final static short TOFLOAT=275;
public final static short EOF=276;
public final static short BY=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    3,    3,    3,    3,
    5,    5,    4,    4,    4,    2,    2,    2,    2,    6,
    6,    6,    6,    6,    6,    8,    8,    8,    8,    9,
    9,    9,   14,   14,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   17,   17,   15,   15,
   15,   18,   18,   19,   19,    7,    7,    7,    7,    7,
   16,   16,   16,   16,   16,   16,   11,   11,   11,   11,
   12,   12,   12,   13,   13,   13,   20,   20,   20,   21,
   21,   21,   21,
};
final static short yylen[] = {                            2,
    3,    4,    3,    3,    2,    1,    3,    3,    3,    3,
    3,    1,    1,    1,    1,    2,    2,    1,    1,    1,
    1,    1,    1,    1,    2,    4,    4,    4,    3,    4,
    4,    2,    4,    4,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,    2,    1,    3,    3,
    1,    2,    1,    1,    3,    5,    4,    5,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,    3,    3,    1,    3,    3,    1,    1,
    1,    2,    1,
};
final static short yydefred[] = {                         0,
    0,   13,   14,   15,    0,    0,    6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   18,   19,   20,   21,
   22,   23,   24,    0,    0,    0,    0,    5,    0,   12,
    0,    0,    0,   25,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    4,   16,   17,    9,    0,
    0,   32,    3,    0,    1,   10,    8,    7,   81,   80,
   83,    0,    0,    0,   79,    0,    0,    0,   54,    0,
   53,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   11,    0,    0,
   51,    0,    0,    0,    2,   82,   28,    0,    0,    0,
    0,    0,    0,    0,   52,    0,    0,    0,    0,    0,
   34,   33,    0,    0,    0,    0,    0,    0,   27,   26,
   57,    0,    0,    0,    0,   48,    0,   31,   30,    0,
    0,   77,   78,    0,   55,   58,   70,    0,    0,    0,
    0,    0,   69,   68,   67,    0,    0,    0,    0,    0,
   59,    0,   56,   73,   72,   71,    0,   49,   47,    0,
   62,   65,   64,   66,   63,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   37,
   38,   39,   40,   41,   42,   43,   44,   45,    0,   36,
   35,
};
final static short yydgoto[] = {                          5,
    6,   15,   69,    8,   16,   17,   18,   19,   20,   21,
   22,   23,   63,   24,   94,   39,  127,   70,   71,   64,
   65,
};
final static short yysindex[] = {                      -211,
 -122,    0,    0,    0,    0,  185,    0, -153,  -30,  -35,
  -14, -232,  -91,   -1, -157,  -10,    0,    0,    0,    0,
    0,    0,    0,  -64,  -40,  -91,  135,    0,   63,    0,
  -29,   53, -139,    0, -209, -124,   53,  -42, -103, -155,
 -107, -145,   53,  151,   43,    0,    0,    0,    0,  -98,
  198,    0,    0,  -39,    0,    0,    0,    0,    0,    0,
    0, -104,   27,   95,    0, -100,  -84,  -84,    0,  151,
    0,  148,  -37,  -76,  -41,  198,  158,  161,  -67,  -62,
 -161,   -7,   33,   82,  168,  165,   34,    0,  -24,  -59,
    0,  224,  -44,  -43,    0,    0,    0,   53,   53,   53,
   53,  -52,   21,   93,    0,  160,   53,   53,   53,   49,
    0,    0,  174,  -47,  -45,  -17,  -13, -141,    0,    0,
    0,   -9,  104,  195,  -46,    0,  221,    0,    0,   95,
   95,    0,    0,    5,    0,    0,    0,  118,  133,  139,
  214,  -34,    0,    0,    0,    7,    9,   12,   16, -182,
    0,  -30,    0,    0,    0,    0,  -24,    0,    0,   19,
    0,    0,    0,    0,    0,    0,   25,   28,   29,   31,
   35, -118,   -2,    4,   15,   36,   39,   47,   51, -234,
   41,   45,   55,   65,   67,   68,   69,   70,  -99, -148,
 -148, -148, -148, -148, -148, -148, -148, -148,  201,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -24,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -111,  -18,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,
   26,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -71,    0,    0,    0,    0,  -88,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   66,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,   11,  121,    0,   10,   13,    6,    0,    0,    0,
    0,    0,   23,    0,  191,    0,    0,  226,   17,   99,
  100,
};
final static int YYTABLESIZE=499;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         36,
   36,   98,   62,   99,   38,   98,  166,   99,   98,   36,
   99,  145,  156,   29,   50,   36,   27,   31,   34,   34,
   48,  188,   76,   41,   76,   40,   76,   47,   34,   58,
   36,   44,   48,   50,   34,   98,   50,   99,   45,   47,
   76,   42,  189,   74,    1,   74,   67,   74,   49,   34,
    2,  120,    2,   60,   84,    3,    4,    3,    4,   73,
   75,   74,   68,   93,   50,   82,   75,   87,   75,   98,
   75,   99,   36,  171,  125,   12,   98,  103,   99,  135,
  104,  172,   35,   35,   75,   97,  105,   62,  111,   48,
   12,   34,   35,   62,  117,  123,   47,   62,    9,   10,
   77,  105,   29,   11,  126,   12,   76,   26,  118,   48,
   80,   30,   78,   35,  149,  151,   47,   14,   46,   81,
    7,   56,   91,  150,   92,   66,   28,   74,   48,  138,
  139,  140,  142,    9,   10,   47,  100,  179,   11,  159,
   12,  101,   13,   72,   29,   29,  180,   29,   29,   29,
   75,   29,   14,   29,   76,   35,  198,   79,  161,   29,
   98,   96,   99,   29,   29,  199,   88,   50,   50,  102,
   50,   50,   50,  162,   50,   98,   50,   99,   43,  163,
   30,   98,   50,   99,   60,   60,   50,   50,  106,   60,
   46,   60,  108,   60,   51,   52,  130,  131,  113,  132,
  133,  114,  115,   60,   60,  124,  121,  116,  144,  155,
   43,  210,  134,   74,  109,  128,  129,  136,  137,  146,
   37,  165,   59,   60,   30,   61,   57,  110,  153,   32,
   32,  107,  143,   33,   33,   53,   95,   76,   76,   32,
   76,   76,   76,   33,   76,   32,   76,  147,  119,   33,
   76,  148,   76,  154,  164,   30,   76,   76,   74,   74,
   32,   74,   74,   74,   33,   74,  112,   74,  160,   85,
  167,   74,  168,   74,  181,  169,    0,   74,   74,  170,
  182,   75,   75,  173,   75,   75,   75,    0,   75,  174,
   75,  183,  175,  176,   75,  177,   75,   30,   86,  178,
   75,   75,   32,    0,  141,  190,   33,   59,   60,  191,
   61,    0,  184,   59,   60,  185,   61,   59,   60,  192,
   61,   46,   46,  186,   46,   46,   46,  187,   46,  193,
   46,  194,  195,  196,  197,    0,   46,    9,   10,    0,
   46,   46,   11,    0,   12,    0,   26,    0,    9,   10,
    0,    0,    0,   11,    0,   12,   14,   26,    0,  152,
   10,    0,    0,    0,   11,    0,   12,   14,   26,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   14,    0,
  200,  201,  202,  203,  204,  205,  206,  207,  208,  211,
   54,   10,    0,    0,    0,   11,    0,   12,    0,   26,
    0,    0,    0,    0,    0,    0,   83,   10,    0,   14,
   55,   11,    2,   12,    0,   26,    0,    3,    4,    0,
    0,    0,   68,  122,   10,   14,    0,    0,   11,    2,
   12,    0,   26,    0,    3,    4,    0,    0,    0,   68,
   25,   10,   14,    0,    0,   11,    2,   12,    0,   26,
    0,    3,    4,   89,   10,    0,  209,   10,   11,   14,
   12,   11,   90,   12,    0,   90,    0,    0,   91,    0,
   92,   91,   14,   92,    0,   14,  157,   10,    0,   89,
   10,   11,    0,   12,   11,   90,   12,    0,   90,    0,
    0,  158,    0,    0,    0,   14,    0,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   43,   45,   45,   40,   43,   41,   45,   43,   40,
   45,   59,   59,  125,   44,   40,    6,    8,   59,   59,
   15,  256,   41,  256,   43,   40,   45,   15,   59,   59,
   40,  123,   27,   44,   59,   43,  125,   45,   40,   27,
   59,  274,  277,   41,  256,   43,  256,   45,   59,   59,
  262,   59,  262,  125,   44,  267,  268,  267,  268,   37,
   38,   59,  272,   51,   44,   43,   41,   45,   43,   43,
   45,   45,   40,  256,   41,   44,   43,   68,   45,   59,
   70,  264,  123,  123,   59,   59,   70,   45,   76,   84,
   59,   59,  123,   45,  256,   85,   84,   45,  256,  257,
  256,   85,  256,  261,   92,  263,  125,  265,  270,  104,
  256,  265,  268,  123,  256,  125,  104,  275,  276,  265,
    0,   59,  271,  265,  273,  265,    6,  125,  123,  107,
  108,  109,  110,  256,  257,  123,   42,  256,  261,  127,
  263,   47,  265,  268,  256,  257,  265,  259,  260,  261,
  125,  263,  275,  265,  258,  123,  256,  265,   41,  271,
   43,  266,   45,  275,  276,  265,  265,  256,  257,  270,
  259,  260,  261,   41,  263,   43,  265,   45,  270,   41,
  265,   43,  271,   45,  256,  257,  275,  276,   41,  261,
  125,  263,  269,  265,  259,  260,   98,   99,   41,  100,
  101,   41,  270,  275,  276,   41,  125,  270,  256,  256,
  270,  199,  265,  256,  256,  260,  260,  125,   59,  265,
  256,  256,  265,  266,  265,  268,  256,  269,  125,  270,
  270,  269,   59,  274,  274,  276,  276,  256,  257,  270,
  259,  260,  261,  274,  263,  270,  265,  265,  256,  274,
  269,  265,  271,   59,   41,  265,  275,  276,  256,  257,
  270,  259,  260,  261,  274,  263,   76,  265,  264,   44,
  264,  269,  264,  271,  277,  264,   -1,  275,  276,  264,
  277,  256,  257,  265,  259,  260,  261,   -1,  263,  265,
  265,  277,  265,  265,  269,  265,  271,  265,  256,  265,
  275,  276,  270,   -1,  256,  265,  274,  265,  266,  265,
  268,   -1,  277,  265,  266,  277,  268,  265,  266,  265,
  268,  256,  257,  277,  259,  260,  261,  277,  263,  265,
  265,  265,  265,  265,  265,   -1,  271,  256,  257,   -1,
  275,  276,  261,   -1,  263,   -1,  265,   -1,  256,  257,
   -1,   -1,   -1,  261,   -1,  263,  275,  265,   -1,  256,
  257,   -1,   -1,   -1,  261,   -1,  263,  275,  265,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  275,   -1,
  190,  191,  192,  193,  194,  195,  196,  197,  198,  199,
  256,  257,   -1,   -1,   -1,  261,   -1,  263,   -1,  265,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,  275,
  276,  261,  262,  263,   -1,  265,   -1,  267,  268,   -1,
   -1,   -1,  272,  256,  257,  275,   -1,   -1,  261,  262,
  263,   -1,  265,   -1,  267,  268,   -1,   -1,   -1,  272,
  256,  257,  275,   -1,   -1,  261,  262,  263,   -1,  265,
   -1,  267,  268,  256,  257,   -1,  256,  257,  261,  275,
  263,  261,  265,  263,   -1,  265,   -1,   -1,  271,   -1,
  273,  271,  275,  273,   -1,  275,  256,  257,   -1,  256,
  257,  261,   -1,  263,  261,  265,  263,   -1,  265,   -1,
   -1,  271,   -1,   -1,   -1,  275,   -1,   -1,  275,
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","THEN","ELSE","ENDIF","PRINT","INT",
"LOOP","TO","ID","CONSTANT","FLOAT","STRING","COMPARATOR","ASIGNATION","END",
"GLOBAL","BEGIN","FROM","TOFLOAT","EOF","BY",
};
final static String yyrule[] = {
"$accept : p",
"p : declarations exe EOF",
"p : declarations exe error EOF",
"p : declarations error EOF",
"p : error exe EOF",
"declarations : declarations declaration",
"declarations : declaration",
"declaration : type var_list ';'",
"declaration : type var_list error",
"declaration : error var_list ';'",
"declaration : type error ';'",
"var_list : var_list ',' ID",
"var_list : ID",
"type : INT",
"type : FLOAT",
"type : STRING",
"exe : exe sentence",
"exe : exe ambit",
"exe : sentence",
"exe : ambit",
"sentence : asignation",
"sentence : selection",
"sentence : iteration",
"sentence : print",
"sentence : conversion",
"sentence : error ';'",
"asignation : ID ASIGNATION expression ';'",
"asignation : ID ASIGNATION expression error",
"asignation : error ASIGNATION expression ';'",
"asignation : error ASIGNATION expression",
"selection : selection_simple ELSE bloque ENDIF",
"selection : selection_simple ELSE sentence ENDIF",
"selection : selection_simple ENDIF",
"selection_simple : IF condition THEN bloque",
"selection_simple : IF condition THEN sentence",
"iteration : LOOP FROM ID ASIGNATION ID TO ID BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO ID BY ID sentence",
"iteration : error FROM ID ASIGNATION ID TO ID BY ID bloque",
"iteration : LOOP error ID ASIGNATION ID TO ID BY ID bloque",
"iteration : LOOP FROM error ASIGNATION ID TO ID BY ID bloque",
"iteration : LOOP FROM ID error ID TO ID BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION error TO ID BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID error ID BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO error BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO ID error ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO ID BY error bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO ID BY ID error",
"bloque_exe_sentences : bloque_exe_sentences sentence",
"bloque_exe_sentences : sentence",
"bloque : BEGIN bloque_exe_sentences END",
"bloque : BEGIN bloque_exe_sentences error",
"bloque : END",
"ambit_declarations : ambit_declarations ambit_dec_sentence",
"ambit_declarations : ambit_dec_sentence",
"ambit_dec_sentence : declaration",
"ambit_dec_sentence : GLOBAL var_list ';'",
"ambit : ID '{' ambit_declarations exe '}'",
"ambit : ID '{' exe '}'",
"ambit : error '{' ambit_declarations exe '}'",
"ambit : ID '{' ambit_declarations error '}'",
"ambit : ID '{' ambit_declarations exe error",
"condition : '(' expression COMPARATOR expression ')'",
"condition : error expression COMPARATOR expression ')'",
"condition : '(' expression COMPARATOR expression error",
"condition : '(' expression error expression ')'",
"condition : '(' error COMPARATOR expression ')'",
"condition : '(' expression COMPARATOR error ')'",
"print : PRINT '(' STRING ')' ';'",
"print : PRINT '(' STRING ')' error",
"print : PRINT '(' error ')' ';'",
"print : error '(' STRING ')' ';'",
"conversion : TOFLOAT '(' expression ')' ';'",
"conversion : TOFLOAT '(' expression ')' error",
"conversion : TOFLOAT '(' error ')' ';'",
"expression : expression '+' term",
"expression : expression '-' term",
"expression : term",
"term : term '*' factor",
"term : term '/' factor",
"term : factor",
"factor : CONSTANT",
"factor : ID",
"factor : '-' CONSTANT",
"factor : STRING",
};

//#line 349 "Gramatica.y"

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}

AnalizadorLexico analyzer;
Messages msj;
TS table;
Stack s;

public void setLexico(AnalizadorLexico al, Stack s) {
	analyzer = al;
	table = al.getTS();
	this.s = s;
}

public void setMensajes(Messages ms) {
	msj = ms;
}

int yylex()
{
	int val = analyzer.yylex();
	yylval = new ParserVal(analyzer.getToken());
	yylval.ival = analyzer.getLine();
	
	return val;
}
//#line 468 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 13 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(1),"Sintactico"); msj.addError(e);}
break;
case 3:
//#line 14 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 4:
//#line 15 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(3),"Sintactico"); msj.addError(e);}
break;
case 7:
//#line 22 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
									Enumeration e = ((Vector<Token>)val_peek(1).obj).elements();
									while (e.hasMoreElements()){
										Token token = (Token)e.nextElement();
										token.getETS().setType(((Token) val_peek(2).obj).getLexema());
									}
									}
break;
case 8:
//#line 30 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 9:
//#line 31 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
break;
case 10:
//#line 32 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
break;
case 11:
//#line 35 "Gramatica.y"
{	Vector<Token> tokens = (Vector<Token>)val_peek(2).obj;
										Token token = (Token)val_peek(0).obj;
										token.setType("ID");
										tokens.add(token);
										yyval.obj = tokens;									
								}
break;
case 12:
//#line 41 "Gramatica.y"
{	Vector<Token> tokens = new Vector<Token>();
										Token token = (Token)val_peek(0).obj;
										token.setType("ID");
										tokens.add(token);
										yyval.obj = tokens;
								}
break;
case 25:
//#line 67 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
break;
case 26:
//#line 70 "Gramatica.y"
{	ES es = new ES(analyzer.getLine(), analyzer.getMessage(202)); 
											msj.addStructure(es); /*TODO: chequear declaraciones*/
											String op1 = ((Token)val_peek(3).obj).getLexema();
											String op2 = (String)val_peek(1).obj;
											Terceto t = new Terceto(s.size(), "=", op1, op2);
											s.add(t);
											yyval.obj = t;
											}
break;
case 27:
//#line 78 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 28:
//#line 79 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
break;
case 29:
//#line 80 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(8),"Sintactico"); msj.addError(e);}
break;
case 30:
//#line 83 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 31:
//#line 84 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 32:
//#line 85 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 35:
//#line 92 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);}
break;
case 36:
//#line 93 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);}
break;
case 37:
//#line 94 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(18),"Sintactico"); msj.addError(e);}
break;
case 38:
//#line 95 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(19),"Sintactico"); msj.addError(e);}
break;
case 39:
//#line 96 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 40:
//#line 97 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
break;
case 41:
//#line 98 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 42:
//#line 99 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 43:
//#line 100 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 44:
//#line 101 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(21),"Sintactico"); msj.addError(e);}
break;
case 45:
//#line 102 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 46:
//#line 103 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 49:
//#line 110 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(206)); msj.addStructure(s);}
break;
case 50:
//#line 111 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(9),"Sintactico"); msj.addError(e);}
break;
case 51:
//#line 112 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(10),"Sintactico"); msj.addError(e);}
break;
case 56:
//#line 123 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
break;
case 57:
//#line 124 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
break;
case 58:
//#line 125 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(17),"Sintactico"); msj.addError(e);}
break;
case 59:
//#line 126 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 60:
//#line 127 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(16),"Sintactico"); msj.addError(e);}
break;
case 62:
//#line 131 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(11),"Sintactico"); msj.addError(e);}
break;
case 63:
//#line 132 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(12),"Sintactico"); msj.addError(e);}
break;
case 64:
//#line 133 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
break;
case 65:
//#line 134 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 66:
//#line 135 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 67:
//#line 138 "Gramatica.y"
{	ES es = new ES(analyzer.getLine(), analyzer.getMessage(205)); 
									msj.addStructure(es);
									String lexema = ((Token)val_peek(2).obj).getLexema();
									Terceto t = new Terceto (s.size(), "PRINT", lexema, "");
									s.add(t);
									yyval.obj = t;
								}
break;
case 68:
//#line 145 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 69:
//#line 146 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
break;
case 70:
//#line 147 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(14),"Sintactico"); msj.addError(e);}
break;
case 71:
//#line 150 "Gramatica.y"
{ES es = new ES(analyzer.getLine(), analyzer.getMessage(208)); 
											msj.addStructure(es);
											String lexema = (String)val_peek(2).obj;
											Terceto t = new Terceto (s.size(), "TOFLOAT", lexema, "");
											s.add(t);
											yyval.obj = t;
											}
break;
case 72:
//#line 157 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 73:
//#line 158 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
break;
case 74:
//#line 161 "Gramatica.y"
{ 	
									String string = (String)val_peek(2).obj;
									String subst = string.substring(1,string.length()-1);
									TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
									TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
									Terceto viejo = s.get(subst);
									Terceto t = new Terceto(s.size(),"+", (String)val_peek(2).obj, (String)val_peek(0).obj);
									t.setType(op2.getType());
									yyval.obj = "[" + t.getId() + "]";
									s.add(t);
									if (!viejo.getType().equals(op2.getType())){ 									
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
										msj.addError(er);
										t.setType("error");
									}
								}
break;
case 75:
//#line 177 "Gramatica.y"
{ 	
									String string = (String)val_peek(2).obj;
									String subst = string.substring(1,string.length()-1);
									TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
									TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
									Terceto viejo = s.get(subst);
									Terceto t = new Terceto(s.size(),"-", (String)val_peek(2).obj, (String)val_peek(0).obj);
									t.setType(op2.getType());
									yyval.obj = "[" + t.getId() + "]";
									s.add(t);
									if (!viejo.getType().equals(op2.getType())){ 
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
										msj.addError(er);
										t.setType("error");
									}
								}
break;
case 76:
//#line 193 "Gramatica.y"
{
					yyval.obj = ((String)val_peek(0).obj);
				}
break;
case 77:
//#line 198 "Gramatica.y"
{ 	TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
							TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
							Terceto t = new Terceto(s.size(),"*", (String)val_peek(2).obj, (String)val_peek(0).obj);
							t.setType(op2.getType());
							yyval.obj = "[" + t.getId() + "]";
							s.add(t);
							if (!op1.getType().equals(op2.getType())){ 
								Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
								msj.addError(er);
								t.setType("error");
							}
						}
break;
case 78:
//#line 210 "Gramatica.y"
{ 
							TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
							TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
							Terceto t = new Terceto(s.size(), "/", (String)val_peek(2).obj, (String)val_peek(0).obj);
							t.setType(op2.getType());
							yyval.obj = "[" + t.getId() + "]"; 
							s.add(t);
							if (!op1.getType().equals(op2.getType())){ 
								Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
								msj.addError(er);
								t.setType("error");

							}
						}
break;
case 79:
//#line 224 "Gramatica.y"
{ 
					String s1 = (String) val_peek(0).obj;
					TSEntry entry = table.getTSEntry(s1);
					try {
						if ((!table.hasLexema(s1) || !entry.isDeclared()) && entry.getId() == 265){
							Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico"); 
							msj.addError(er);
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					yyval.obj = s1;
				}
break;
case 80:
//#line 239 "Gramatica.y"
{	String newLexema = ((Token)val_peek(0).obj).getLexema();
					TSEntry entry = (TSEntry)table.getTable().get(newLexema);
					boolean anda = false;
					String a =	entry.getType();
					if (a == "INT"){
						Long e = Long.valueOf(newLexema);
						if ( e.longValue() <= Short.MAX_VALUE )
							anda = true;
						else{
							Error er = new Error(analyzer.getLine(),analyzer.getMessage(104),"Lexico"); 
							msj.addError(er);
						}
					}
					else{
						Double f = Double.valueOf(newLexema);								
						if (f.doubleValue() >= 1.17549435E-38 && f.doubleValue() <= 3.40282347E38)
							anda = true;
						else{
							Error er = new Error(analyzer.getLine(),analyzer.getMessage(101),"Lexico"); 
							msj.addError(er);
				
						}
					}
					if (!anda)	table.getTable().remove(newLexema);
					if (anda){
						if (entry.getRefCounter() == 1){
							if (table.getTable().contains(newLexema))
								((TSEntry)table.getTable().get(newLexema)).incCounter();
							else {	 
							TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
								table.addTSEntry(newEntry.getLexema(), newEntry);
								newEntry.setType(a);
							}
						}
						else {
							entry.decCounter();
							if (table.getTable().containsKey(newLexema))
								((TSEntry)table.getTable().get(newLexema)).incCounter();
							else {   
								TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
								table.addTSEntry(newEntry.getLexema(), newEntry);
								newEntry.setType(a);
							}
						}
					}  
					yyval.obj = newLexema;
				}
break;
case 81:
//#line 286 "Gramatica.y"
{ String lexema = ((Token)val_peek(0).obj).getLexema();
			  yyval.obj = lexema;
			}
break;
case 82:
//#line 289 "Gramatica.y"
{	
							String lexema = ((Token)val_peek(0).obj).getLexema();
							TSEntry entry = (TSEntry)table.getTable().get(lexema);
							String newLexema = "-" + lexema;
							boolean anda = false;
							String a =	entry.getType();
							if (a == "INT"){
								Long e = Long.valueOf(newLexema);
								if ( e.longValue() >= Short.MIN_VALUE )
									anda = true;
								else
								{    
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(104),"Lexico"); 
									msj.addError(er);
								
								}
							}
							else {	
								Double f = Double.valueOf(newLexema);								
								if (f.doubleValue() <= -1.17549435E-38 && f.doubleValue() >= -3.40282347E38)
									anda = true;
								else{    
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(101),"Lexico"); 
									msj.addError(er);
									
								}
							}
							if (!anda){
								table.getTable().remove(newLexema);
									table.getTable().remove(lexema);}
							if (anda){
								if (entry.getRefCounter() == 1){   
									if (table.getTable().contains(newLexema)){
										((TSEntry)table.getTable().get(newLexema)).incCounter();
									/*table.getTable().remove(lexema);*/
									}else {	 
											TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
											table.addTSEntry(newEntry.getLexema(), newEntry);
											newEntry.setType(a);
											table.getTable().remove(lexema);
									}
									
								}
								else {    
									entry.decCounter();
									if (table.getTable().containsKey(newLexema))
										((TSEntry)table.getTable().get(newLexema)).incCounter();
									else {   
										TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
										table.addTSEntry(newEntry.getLexema(), newEntry);
										newEntry.setType(a);
									}
								}
							}  
							yyval.obj = newLexema;
						}
break;
//#line 1055 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
