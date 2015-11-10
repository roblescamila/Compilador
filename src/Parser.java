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






//#line 2 "G.y"
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
    0,    0,    0,    0,    1,    1,    4,    4,    5,    5,
    3,    3,    3,    3,    6,    6,    6,    7,    7,    7,
    2,    2,    2,    2,    8,    8,    8,    8,    8,    8,
    8,   10,   10,   11,   11,   11,   17,   16,   16,   19,
   20,   21,   21,   21,   21,   21,   22,   22,   22,   22,
   22,   22,   22,   22,   12,   12,   23,   24,   24,   18,
   18,   18,    9,    9,    9,    9,   25,   26,   13,   13,
   13,   13,   14,   14,   14,   15,   15,   15,   15,   15,
   15,   15,   27,   27,   27,   27,   27,   27,   27,   28,
   28,   28,   28,
};
final static short yylen[] = {                            2,
    3,    4,    3,    3,    2,    1,    2,    1,    1,    3,
    3,    3,    3,    3,    3,    1,    3,    1,    1,    1,
    2,    2,    1,    1,    2,    2,    1,    1,    1,    1,
    2,    3,    3,    4,    4,    2,    1,    4,    4,    1,
    3,    3,    3,    3,    3,    3,    7,    7,    7,    7,
    7,    7,    7,    7,    2,    2,    1,    2,    1,    3,
    3,    1,    4,    3,    4,    4,    2,    1,    5,    5,
    5,    5,    5,    5,    5,    3,    3,    1,    3,    3,
    3,    3,    3,    3,    1,    3,    3,    3,    3,    1,
    1,    2,    1,
};
final static short yydefred[] = {                         0,
    0,   18,   19,   20,    0,    0,    6,    0,    0,    0,
    0,   57,    0,    0,    0,    0,   23,   24,    0,   27,
   28,   29,   30,    0,    0,    0,    0,    0,    0,    0,
    5,    0,   16,    0,    0,    0,   31,    0,    0,    0,
    0,    0,   67,    0,    4,   21,   22,   13,    0,   26,
   25,   37,   40,    0,   36,    0,   62,    0,   56,   55,
    0,    0,    0,    0,    0,    9,    0,    8,    3,    0,
    1,   14,   12,   11,    0,   91,   90,   93,    0,    0,
    0,   85,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   17,   15,    0,    0,   59,    0,
    0,    0,    0,    0,   68,   64,    0,    0,    7,    2,
    0,    0,    0,    0,   92,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   41,   39,   38,    0,    0,
    0,    0,   35,   34,    0,   60,   58,    0,    0,    0,
    0,   10,   65,    0,   63,    0,    0,    0,   86,   88,
    0,    0,    0,    0,   87,   83,   89,   84,    0,   72,
    0,    0,    0,    0,    0,   71,   70,   69,   75,   74,
   73,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yydgoto[] = {                          5,
    6,   15,   66,   67,   68,   16,    8,   17,   18,   19,
   20,   21,   22,   23,   80,   24,   54,   60,   55,   40,
   88,   25,   26,  100,   27,  106,   81,   82,
};
final static short yysindex[] = {                      -143,
  242,    0,    0,    0,    0,  258,    0, -192,   -9,  -13,
   12,    0, -107,   29, -160,   32,    0,    0,  -50,    0,
    0,    0,    0, -122,  175, -235,  199,   93, -107,  -90,
    0,   18,    0,   21,   63, -184,    0, -183,   85, -171,
 -239,   63,    0,  109,    0,    0,    0,    0, -156,    0,
    0,    0,    0,  175,    0, -163,    0,  391,    0,    0,
 -184, -154,  -17, -142,  172,    0,  216,    0,    0,  -27,
    0,    0,    0,    0,  146,    0,    0,    0, -135,    8,
   57,    0, -112, -129,  107,   14,   15,  127,  175,  128,
  133,    8,  137,  176,    0,    0,  -83,  -83,    0,  380,
  -81, -144, -231,   34,    0,    0,  -39,  188,    0,    0,
  114,  114,  157,  157,    0,  120,  125,  131,  136,   63,
  138,   63,   63,  142,   63,    0,    0,    0,  148,  -47,
  151,  -45,    0,    0,   -9,    0,    0,   63,   63,   63,
  147,    0,    0,   -9,    0,   97,   57,   57,    0,    0,
   97,   57,   97,   57,    0,    0,    0,    0,   -8,    0,
    8,    8,  146,    8,    8,    0,    0,    0,    0,    0,
    0,   -7,   -2,    2,  -37,  -15,   63,   63,   63,   63,
   63,   63,  153,    8,    8,    8,    8,    8,    8,  146,
    8,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   39,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -56,
  -41,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -52,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -114,    0,    0,    0,    0,    0,
    0,    0,    0,  -62,    0,    0,  -19,    3,    0,    0,
   25,   47,   69,   91,    0,    0,    0,    0,    0,    0,
  149,  155,  164,  177,  184,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  271,  300,  303,  323,  326,  346,  349,
  369,
};
final static short yygindex[] = {                         0,
    0,   28,  121,    0,  160,    7,    0,   64,   52,   13,
    0,    0,    0,    0,  372,    0,    0,  -35,   43,    0,
    0,    0,    0,    0,    0,   48,  546,  551,
};
final static int YYTABLESIZE=670;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         78,
   38,   78,   33,   78,  113,  111,   32,  112,   51,  114,
   61,  168,   38,  171,   34,   43,   90,   78,   98,   37,
   61,   81,   38,   81,  140,   81,   39,  116,   91,  117,
   38,   37,  141,   30,  116,  116,  117,  117,   62,   81,
  116,   37,  117,   79,  116,   79,  117,   79,   84,   37,
  116,   41,  117,  128,   65,  113,  111,  116,  112,  117,
  114,   79,   66,   32,   49,   82,   47,   82,   44,   82,
  104,   83,   33,  101,  103,   49,   72,   49,   46,   74,
   56,   47,   16,   82,   85,  105,   89,   76,   59,   76,
   48,   76,  142,   46,  108,    9,   10,   16,  118,   95,
   11,  102,   12,  119,   29,   76,   42,   79,   96,   80,
   56,   80,    1,   80,   14,   45,   47,   97,    2,  139,
    7,   99,   33,    3,    4,   35,   31,   80,   46,   79,
  115,   77,   38,   77,  120,   77,   52,   53,  113,  133,
  134,   61,   61,  114,   61,   61,   61,  121,   61,   77,
   61,   37,  127,   79,  143,  145,   61,   35,   79,   47,
   61,   61,   42,  137,   79,   70,   10,  126,  129,   79,
   11,   46,   12,  130,   29,   79,   53,  131,  113,  111,
   79,  112,  138,  114,   14,   71,   79,  113,  111,   45,
  112,   79,  114,   66,   66,   44,  160,   79,   66,   33,
   66,   79,   66,   32,   46,   50,  166,   33,  167,  169,
  170,   32,   66,   66,   78,   78,  132,   42,  116,   78,
  117,   78,   78,   78,   43,   33,  109,   78,   78,   78,
   35,   78,    0,   78,   36,   78,   81,   81,    0,  181,
  182,   81,   35,   81,   81,   81,   36,   33,  110,   81,
   81,   81,   35,   81,    0,   81,   36,   81,   79,   79,
   35,  183,    0,   79,   36,   79,   79,   79,  177,  178,
  123,   79,   79,   79,  179,   79,   73,   79,  180,   79,
   82,   82,  122,  124,  125,   82,    0,   82,   82,   82,
    0,    0,    0,   82,   82,   82,  105,   82,    0,   82,
    0,   82,   76,   76,    0,    0,    0,   76,    0,   76,
   76,   76,  105,    0,    0,   76,   76,   76,   75,   76,
    0,   76,    0,   76,   80,   80,    0,   76,   77,   80,
   78,   80,   80,   80,    0,    0,    0,   80,   80,   80,
   86,   80,    0,   80,    0,   80,   77,   77,    0,   76,
   77,   77,   78,   77,   77,   77,    0,   33,    0,   77,
   77,   77,   35,   77,   93,   77,   36,   77,   69,  146,
    0,    0,    0,   76,   77,  151,   78,    0,   76,   77,
  153,   78,    0,    0,   76,   77,  155,   78,    0,   76,
   77,  157,   78,    0,    0,   76,   77,  163,   78,    0,
   76,   77,  175,   78,    0,    0,   76,   77,  190,   78,
   87,   76,   77,   92,   78,   94,    0,   76,   77,    0,
   78,   76,   77,    0,   78,    0,    0,    9,   10,    0,
    9,   10,   11,    0,   12,   11,   29,   12,    0,   56,
    0,    0,    0,  144,   10,   57,   14,   58,   11,   14,
   12,    0,   29,    0,   63,   10,    0,    0,    0,   11,
    2,   12,   14,   29,    0,    3,    4,    0,    0,    0,
   64,  107,   10,   14,    0,    0,   11,    2,   12,    0,
   29,    0,    3,    4,    0,    0,    0,   64,    0,    0,
   14,  159,    0,  161,  162,  164,  165,    9,   10,    0,
    0,    0,   11,    0,   12,    0,   13,    0,    0,  172,
  173,  174,  176,   28,   10,    0,   14,    0,   11,    2,
   12,    0,   29,    0,    3,    4,   48,   48,    0,    0,
    0,   48,   14,   48,    0,   48,    0,    0,    0,    0,
    0,   48,    0,   48,    0,   48,    0,    0,  184,  185,
  186,  187,  188,  189,  191,   49,   49,    0,   50,   50,
   49,    0,   49,   50,   49,   50,    0,   50,    0,    0,
   49,    0,   49,   50,   49,   50,    0,   50,   51,   51,
    0,   52,   52,   51,    0,   51,   52,   51,   52,    0,
   52,    0,    0,   51,    0,   51,   52,   51,   52,    0,
   52,   53,   53,    0,   54,   54,   53,    0,   53,   54,
   53,   54,    0,   54,    0,    0,   53,    0,   53,   54,
   53,   54,    0,   54,   47,   47,    0,    0,    0,   47,
    0,   47,    0,   47,    0,  135,   10,    0,    0,   47,
   11,   47,   12,   47,   56,    0,    9,   10,    0,    0,
  136,   11,    0,   12,   14,   56,  147,  148,    0,    0,
    0,  152,  154,  149,  150,   14,    0,    0,  156,  158,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   40,   43,   59,   45,   42,   43,   59,   45,   59,   47,
  125,   59,   40,   59,    8,  123,  256,   59,   54,   59,
  256,   41,   40,   43,  256,   45,   40,   43,  268,   45,
   40,   59,  264,    6,   43,   43,   45,   45,  274,   59,
   43,   59,   45,   41,   43,   43,   45,   45,   36,   59,
   43,   40,   45,   89,   27,   42,   43,   43,   45,   45,
   47,   59,  125,  256,   44,   41,   15,   43,   40,   45,
   64,  256,  265,   61,   62,   44,   59,   44,   15,   59,
  265,   30,   44,   59,  268,  125,  258,   41,   25,   43,
   59,   45,   59,   30,   67,  256,  257,   59,   42,  256,
  261,  256,  263,   47,  265,   59,  270,   45,  265,   41,
  265,   43,  256,   45,  275,  276,   65,   54,  262,  264,
    0,   58,  265,  267,  268,  270,    6,   59,   65,   45,
  266,   41,   40,   43,  264,   45,  259,  260,   42,   97,
   98,  256,  257,   47,  259,  260,  261,   41,  263,   59,
  265,   59,   89,   45,  107,  108,  271,  270,   45,  108,
  275,  276,  270,  100,   45,  256,  257,   41,   41,   45,
  261,  108,  263,   41,  265,   45,  260,   41,   42,   43,
   45,   45,  264,   47,  275,  276,   45,   42,   43,   41,
   45,   45,   47,  256,  257,   41,   59,   45,  261,  256,
  263,   45,  265,  256,   41,  256,   59,  264,  256,   59,
  256,  264,  275,  276,  256,  257,   41,   41,   43,  261,
   45,  263,  264,  265,   41,  265,   67,  269,  270,  271,
  270,  273,   -1,  275,  274,  277,  256,  257,   -1,  277,
  256,  261,  270,  263,  264,  265,  274,  265,  276,  269,
  270,  271,  270,  273,   -1,  275,  274,  277,  256,  257,
  270,  277,   -1,  261,  274,  263,  264,  265,  277,  277,
  256,  269,  270,  271,  277,  273,  256,  275,  277,  277,
  256,  257,  269,  269,  270,  261,   -1,  263,  264,  265,
   -1,   -1,   -1,  269,  270,  271,  125,  273,   -1,  275,
   -1,  277,  256,  257,   -1,   -1,   -1,  261,   -1,  263,
  264,  265,  125,   -1,   -1,  269,  270,  271,  256,  273,
   -1,  275,   -1,  277,  256,  257,   -1,  265,  266,  261,
  268,  263,  264,  265,   -1,   -1,   -1,  269,  270,  271,
  256,  273,   -1,  275,   -1,  277,  256,  257,   -1,  265,
  266,  261,  268,  263,  264,  265,   -1,  265,   -1,  269,
  270,  271,  270,  273,  256,  275,  274,  277,  276,  256,
   -1,   -1,   -1,  265,  266,  256,  268,   -1,  265,  266,
  256,  268,   -1,   -1,  265,  266,  256,  268,   -1,  265,
  266,  256,  268,   -1,   -1,  265,  266,  256,  268,   -1,
  265,  266,  256,  268,   -1,   -1,  265,  266,  256,  268,
   39,  265,  266,   42,  268,   44,   -1,  265,  266,   -1,
  268,  265,  266,   -1,  268,   -1,   -1,  256,  257,   -1,
  256,  257,  261,   -1,  263,  261,  265,  263,   -1,  265,
   -1,   -1,   -1,  256,  257,  271,  275,  273,  261,  275,
  263,   -1,  265,   -1,  256,  257,   -1,   -1,   -1,  261,
  262,  263,  275,  265,   -1,  267,  268,   -1,   -1,   -1,
  272,  256,  257,  275,   -1,   -1,  261,  262,  263,   -1,
  265,   -1,  267,  268,   -1,   -1,   -1,  272,   -1,   -1,
  275,  120,   -1,  122,  123,  124,  125,  256,  257,   -1,
   -1,   -1,  261,   -1,  263,   -1,  265,   -1,   -1,  138,
  139,  140,  141,  256,  257,   -1,  275,   -1,  261,  262,
  263,   -1,  265,   -1,  267,  268,  256,  257,   -1,   -1,
   -1,  261,  275,  263,   -1,  265,   -1,   -1,   -1,   -1,
   -1,  271,   -1,  273,   -1,  275,   -1,   -1,  177,  178,
  179,  180,  181,  182,  183,  256,  257,   -1,  256,  257,
  261,   -1,  263,  261,  265,  263,   -1,  265,   -1,   -1,
  271,   -1,  273,  271,  275,  273,   -1,  275,  256,  257,
   -1,  256,  257,  261,   -1,  263,  261,  265,  263,   -1,
  265,   -1,   -1,  271,   -1,  273,  271,  275,  273,   -1,
  275,  256,  257,   -1,  256,  257,  261,   -1,  263,  261,
  265,  263,   -1,  265,   -1,   -1,  271,   -1,  273,  271,
  275,  273,   -1,  275,  256,  257,   -1,   -1,   -1,  261,
   -1,  263,   -1,  265,   -1,  256,  257,   -1,   -1,  271,
  261,  273,  263,  275,  265,   -1,  256,  257,   -1,   -1,
  271,  261,   -1,  263,  275,  265,  111,  112,   -1,   -1,
   -1,  116,  117,  113,  114,  275,   -1,   -1,  118,  119,
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
"ambit_declarations : ambit_declarations ambit_dec_sentence",
"ambit_declarations : ambit_dec_sentence",
"ambit_dec_sentence : declaration",
"ambit_dec_sentence : GLOBAL var_list ';'",
"declaration : type var_list ';'",
"declaration : type var_list error",
"declaration : error var_list ';'",
"declaration : type error ';'",
"var_list : var_list ',' ID",
"var_list : ID",
"var_list : var_list ',' error",
"type : INT",
"type : FLOAT",
"type : STRING",
"exe : exe sentence",
"exe : exe ambit",
"exe : sentence",
"exe : ambit",
"sentence : asignation ';'",
"sentence : asignation error",
"sentence : selection",
"sentence : iteration",
"sentence : print",
"sentence : conversion",
"sentence : error ';'",
"asignation : ID ASIGNATION expression",
"asignation : error ASIGNATION expression",
"selection : selection_simple else_ bloque cerrar_selection",
"selection : selection_simple else_ sentence cerrar_selection",
"selection : selection_simple cerrar_selection",
"else_ : ELSE",
"selection_simple : IF condition_if THEN bloque",
"selection_simple : IF condition_if THEN sentence",
"cerrar_selection : ENDIF",
"condition_if : '(' condition ')'",
"condition : expression COMPARATOR expression",
"condition : expression ASIGNATION expression",
"condition : expression error expression",
"condition : error COMPARATOR expression",
"condition : expression COMPARATOR error",
"it_cond : open_loop FROM asignation TO expression BY expression",
"it_cond : error FROM asignation TO expression BY expression",
"it_cond : open_loop error asignation TO expression BY expression",
"it_cond : open_loop FROM error TO expression BY expression",
"it_cond : open_loop FROM asignation error expression BY expression",
"it_cond : open_loop FROM asignation TO error BY expression",
"it_cond : open_loop FROM asignation TO expression error expression",
"it_cond : open_loop FROM asignation TO expression BY error",
"iteration : it_cond bloque",
"iteration : it_cond sentence",
"open_loop : LOOP",
"bloque_exe_sentences : bloque_exe_sentences sentence",
"bloque_exe_sentences : sentence",
"bloque : BEGIN bloque_exe_sentences END",
"bloque : BEGIN bloque_exe_sentences error",
"bloque : END",
"ambit : abrir ambit_declarations exe cerrar",
"ambit : abrir exe cerrar",
"ambit : abrir ambit_declarations error cerrar",
"ambit : abrir ambit_declarations exe error",
"abrir : ID '{'",
"cerrar : '}'",
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
"expression : error '-' term",
"expression : expression '-' error",
"expression : error '+' term",
"expression : expression '+' error",
"term : term '*' factor",
"term : term '/' factor",
"term : factor",
"term : error '*' factor",
"term : term '*' error",
"term : error '/' factor",
"term : term '/' error",
"factor : CONSTANT",
"factor : ID",
"factor : '-' CONSTANT",
"factor : STRING",
};

//#line 767 "G.y"

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}

AnalizadorLexico analyzer;
Messages msj;
TS table;
Stack s;
String suffix = "_0";
String[] names = new String[100];
boolean level_up = false;
int ambit_level = 0;
String name;
String tipo;
Vector<Integer> pila;

public void setLexico(AnalizadorLexico al) {
	analyzer = al;
	table = al.getTS();
	this.s = s;
	names[0] = "0";
	for (int i = 1; i< 100 ;i++)
	names[i]="";
	name="";
	tipo ="";
	s=new Stack(table);
	pila = new Vector<Integer>();
}

public void imprimirStack(){
	s.imprimir();
}

public void resetearambitos( )
{
 names = new String[100];
 names[0] = "0";
 name="";
 suffix = "_0";
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
//#line 534 "Parser.java"
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
case 1:
//#line 12 "G.y"
{table.limpiar();}
break;
case 2:
//#line 13 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(1),"Sintactico"); msj.addError(e);}
break;
case 3:
//#line 14 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 4:
//#line 15 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(3),"Sintactico"); msj.addError(e);}
break;
case 9:
//#line 26 "G.y"
{
										Enumeration e = ((Vector<Token>)val_peek(0).obj).elements();
										while (e.hasMoreElements()){
											Token token = (Token)e.nextElement();
											 token.getETS().setLexema(token.getLexema());
										}
									}
break;
case 10:
//#line 33 "G.y"
{   Vector<Token>  tokens=   ((Vector<Token>)val_peek(1).obj);
					                        for (int j =0; j < tokens.size(); j++)
											{ Token token = tokens.elementAt(j);
											token.getETS().setLexema(token.getLexema()+"_0");
											token.getETS().setType(table.getTSEntry(token.getLexema()+suffix).getType());
											String a = token.getLexema();
											token.setLexema(token.getLexema()+"_0");
											table.setkey(a,token);
												table.remove(token.getLexema()+suffix.substring(2,suffix.length()))	;	
													System.out.println("quiero borrar a " +token.getLexema()+suffix.substring(2,suffix.length() ));
											
											}
					     
					  }
break;
case 11:
//#line 49 "G.y"
{
										ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
										Enumeration e = ((Vector<Token>)val_peek(1).obj).elements();
										while (e.hasMoreElements()){
											System.out.println("entre");
											Token token = (Token)e.nextElement();
											if (table.hasLexema(token.getLexema()) && table.isDeclared(token.getLexema()) )
											{
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico");
													msj.addError(er);
											}
											else
											{token.getETS().setType(tipo);
											token.getETS().setLexema(token.getLexema()+suffix);
											String a = token.getLexema();
											token.setLexema(token.getLexema()+suffix);
											table.setkey(a,token); 
											
											System.out.println("setie el tipo de "+ token.getLexema() + " a " + ((Token) val_peek(2).obj).getLexema());
										}
										
										}
										yyval.obj = val_peek(1).obj;
									}
break;
case 12:
//#line 74 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 13:
//#line 75 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
break;
case 14:
//#line 76 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
break;
case 15:
//#line 79 "G.y"
{	Vector<Token> tokens = (Vector<Token>)val_peek(2).obj;
										Token token = (Token)val_peek(0).obj;
										token.setType("ID");
										tokens.add(token);
										yyval.obj = tokens;									
								}
break;
case 16:
//#line 85 "G.y"
{	Vector<Token> tokens = new Vector<Token>();
										Token token = (Token)val_peek(0).obj;
										token.setType("ID");
										tokens.add(token);
										yyval.obj = tokens;
								}
break;
case 17:
//#line 92 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
break;
case 18:
//#line 96 "G.y"
{tipo="INT";}
break;
case 19:
//#line 97 "G.y"
{tipo="FLOAT";}
break;
case 20:
//#line 98 "G.y"
{tipo="STRING";}
break;
case 26:
//#line 110 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 31:
//#line 115 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
break;
case 32:
//#line 118 "G.y"
{	
											ES es = new ES(analyzer.getLine(), analyzer.getMessage(202)); 
											msj.addStructure(es); 
											String string = ((Token)val_peek(2).obj).getLexema();
											String string2 = (String)val_peek(0).obj;
											TSEntry op2 = table.getTSEntry(string2);
											TSEntry op1 = table.getTSEntry(string+ suffix);
											char c = string2.charAt(0);
											String  a= suffix;
											int b= ambit_level;
											if((!table.hasLexema(string+ a)) || (!op1.isDeclared())){
												while ((b != -1)&&((!table.hasLexema(string+ a)) )){ 
													String[] separada = a.split("_"); 
													a="";
													for (int i = 1; i<=b; i++)
														a+="_"+separada[i];	
													b--;
												}
												if (b ==-1){
													Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico"); 
													msj.addError(er);
												}
											} 
											op1 = table.getTSEntry(string+ a);
											Terceto t = new Terceto(s.size(), "=", string+ a, string2);
											if (c == '[') {
												String subst = string2.substring(1,string2.length()-1);
												Terceto op = s.get(subst);
												if (table.hasLexema(string+ a)){
													if (op1.isDeclared() && op1.getType() != op.getType()){	/* preguntar si esta en la tabla, si esta me fijo si los tipos son compatibles. sino pongo quen o esta declarada.*/
														Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
														msj.addError(er);
														t.setType("error");
													}
												}
												else{
													Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico"); 
													msj.addError(er);
												}
											}
											else
											{
											op2=table.getTSEntry(string2);
											if (table.hasLexema(string)&& table.hasLexema(string2))
											{
											if (op1.getType() != op2.getType())
											{
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
														msj.addError(er);
														t.setType("error");
											}
											}
											else
											{
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico"); 
													msj.addError(er);
											}
										
										 }
										 	s.add(t);
											yyval.obj = t;
										 }
break;
case 33:
//#line 180 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
break;
case 34:
//#line 183 "G.y"
{
															ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); 
															msj.addStructure(s);
															
															
															}
break;
case 35:
//#line 189 "G.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 36:
//#line 190 "G.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 37:
//#line 193 "G.y"
{
					Terceto t = new Terceto(s.size(), "BI", "", "-");
					s.add(t);
					Integer i = pila.remove(0);
					s.get(Integer.toString(i)).setOp2("[" + Integer.toString(s.size()) + "]");
					pila.add(s.size()-1);
				}
break;
case 40:
//#line 204 "G.y"
{
							Integer i = pila.remove(0);
							s.get(Integer.toString(i)).setOp1("[" + Integer.toString(s.size()) + "]");
						 }
break;
case 41:
//#line 210 "G.y"
{  
									String a = "[";
									a += Integer.toString(s.size()-1);
									a += "]";
									Terceto t = new Terceto (s.size(), "BF", a, "-");
									s.add(t);      
									pila.add(s.size()-1);
								}
break;
case 42:
//#line 220 "G.y"
{
													String op1 = ((String)val_peek(2).obj);
													String op = ((Token)val_peek(1).obj).getLexema();
													String op2 = ((String)val_peek(0).obj);
													Terceto t = new Terceto (s.size(), op, op1, op2);
													s.add(t);
													yyval.obj = t;
												}
break;
case 43:
//#line 229 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
break;
case 44:
//#line 230 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
break;
case 45:
//#line 231 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 46:
//#line 232 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 47:
//#line 235 "G.y"
{
																	Terceto ter = new Terceto (s.size(),"BF", "[" + Integer.toString(s.size()-1) + "]" ,"-");
																	s.add(ter);
																	pila.add(s.size()-1);
																	Terceto t  = (Terceto)val_peek(4).obj;
																	if (t.getType() != "INT"){
																		Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
																		msj.addError(er);
																	}
																	String m = (String) val_peek(2).obj;
																	String j = (String) val_peek(0).obj;
																	String subst;
																	char c = m.charAt(0);
																	if (c == '['){
																		subst = m.substring(1, m.length()-1);
																		if (s.get(subst).getType() != "INT"){
																			Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
																			msj.addError(er);
																		}	
																	}
																	c = j.charAt(0);
																	String op2;
																	if (c == '['){
																		subst = j.substring(1, j.length()-1);
																		if (s.get(subst).getType() != "INT"){
																			Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
																			msj.addError(er);
																		}
																		op2 = "[" + s.get(subst).getId() + "]";
																	}
																	else 
																		op2 = j;
																	Terceto terceto = new Terceto (s.size(), "<=", "[" + t.getId() + "]", op2);
																	s.add(terceto);
																}
break;
case 48:
//#line 270 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(18),"Sintactico"); msj.addError(e);}
break;
case 49:
//#line 271 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(19),"Sintactico"); msj.addError(e);}
break;
case 50:
//#line 272 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 51:
//#line 273 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 52:
//#line 274 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 53:
//#line 275 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(21),"Sintactico"); msj.addError(e);}
break;
case 54:
//#line 276 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 55:
//#line 279 "G.y"
{
								ES es = new ES(analyzer.getLine(), analyzer.getMessage(204)); 
								msj.addStructure(es);
								Integer i = pila.remove(0);
								s.get(Integer.toString(i)).setOp2("[" + Integer.toString(s.size()) + "]");
								pila.add(s.size()-1);
							}
break;
case 56:
//#line 286 "G.y"
{
								ES es = new ES(analyzer.getLine(), analyzer.getMessage(204)); 
								msj.addStructure(es);
								Integer i = pila.remove(0);
								s.get(Integer.toString(i)).setOp2("[" + Integer.toString(s.size()) + "]");
								pila.add(s.size()-1);
							}
break;
case 57:
//#line 295 "G.y"
{
					Terceto t = new Terceto(s.size(), "BI", Integer.toString(s.size()-1), "-");
					s.add(t);
				 }
break;
case 60:
//#line 304 "G.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(206)); msj.addStructure(s);}
break;
case 61:
//#line 305 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(9),"Sintactico"); msj.addError(e);}
break;
case 62:
//#line 306 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(10),"Sintactico"); msj.addError(e);}
break;
case 63:
//#line 309 "G.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
break;
case 64:
//#line 310 "G.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
break;
case 65:
//#line 312 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 66:
//#line 313 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(16),"Sintactico"); msj.addError(e);}
break;
case 67:
//#line 316 "G.y"
{
				name = ((Token) val_peek(1).obj).getLexema();
				Token token =((Token) val_peek(1).obj);
				if (token.getETS().isDeclared()){
					Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"semantico"); 
					msj.addError(er);
				}
				else{
					token.getETS().setType("AMBITO");
					ambit_level++;
					names [ambit_level]=name;
					String suf= "";
					for (int i = 0 ; i <= ambit_level; i++)	
						suf = suf + "_" + names[i];
					suffix = suf;
					System.out.println("abri el ambito "+ suffix );
				}

}
break;
case 68:
//#line 337 "G.y"
{
				System.out.println("cerre el ambito "+ names [ambit_level]);
				ambit_level--;
				if (ambit_level == 0)
					resetearambitos();
			}
break;
case 69:
//#line 345 "G.y"
{	ES es = new ES(analyzer.getLine(), analyzer.getMessage(205)); 
									msj.addStructure(es);
									String lexema = ((Token)val_peek(2).obj).getLexema();
									Terceto t = new Terceto (s.size(), "PRINT", lexema, "");
									s.add(t);
									yyval.obj = t;
								}
break;
case 70:
//#line 352 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 71:
//#line 353 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
break;
case 72:
//#line 354 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(14),"Sintactico"); msj.addError(e);}
break;
case 73:
//#line 357 "G.y"
{
												ES es = new ES(analyzer.getLine(), analyzer.getMessage(208)); 
												msj.addStructure(es);
												String lexema = (String)val_peek(2).obj;
												Terceto t = new Terceto (s.size(), "TOFLOAT", lexema, "-");
												char c = lexema.charAt(0);
												if (c == '[') {
													String subst = lexema.substring(1, lexema.length()-1);
													Terceto t1 = s.get(subst);
													if (t1.getType().equals("FLOAT")){ 	
														Error er = new Error(analyzer.getLine(),analyzer.getMessage(307),"Semantico");
														msj.addError(er);
														t1.setType("error");
													}
													else
														s.toFloat(t1); /*recorre los tercetos convirtiendo valores a float*/
												}
												else{
													if (!table.hasLexema(lexema)){
														Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
														msj.addError(er);
														t.setType("error");
													}
													else if (table.getTSEntry(lexema).getType().equals("FLOAT")){ 	
														Error er = new Error(analyzer.getLine(),analyzer.getMessage(307),"Semantico");
														msj.addError(er);
														t.setType("error");
													}
													else 
														table.getTSEntry(lexema).setType("FLOAT");	
												}
												s.add(t);
												yyval.obj = t;
											}
break;
case 74:
//#line 391 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 75:
//#line 392 "G.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
break;
case 76:
//#line 395 "G.y"
{ 	
									String string = (String)val_peek(2).obj;
									String string2 = (String)val_peek(0).obj;
									TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
									TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
									char c = string.charAt(0);
									char c2 = string2.charAt(0);
									Terceto t = new Terceto(s.size(),"+", (String)val_peek(2).obj, (String)val_peek(0).obj);
									if ((c == '[')  && (c2 == '[')){
										String subst = string.substring(1,string.length()-1);
										String subst2 = string2.substring(1,string.length()-1);
										Terceto op11 = s.get(subst);
										Terceto op22 = s.get(subst2);
										if (!op11.getType().equals(op22.getType())){ 	
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
											msj.addError(er);
											t.setType("error");
										}
										else
											t.setType(op11.getType());
									}
									else{
										if (c == '[') {
											String subst = string.substring(1,string.length()-1);
											Terceto op = s.get(subst);
											if (!op.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op2.getType());
										}
										else if (c2 == '[') {
											String subst = string2.substring(1,string.length()-1);
											Terceto op = s.get(subst);
											if (!op1.getType().equals(op.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else 
										{     if ((table.hasLexema((String)val_peek(2).obj)) && (table.hasLexema((String)val_peek(0).obj))){
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else
										{
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
										}
									}
									s.add(t);
									yyval.obj = "[" + t.getId() + "]";
								}
								}
break;
case 77:
//#line 459 "G.y"
{ 	
									String string = (String)val_peek(2).obj;
									String string2 = (String)val_peek(0).obj;
									TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
									TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
									char c = string.charAt(0);
									char c2 = string2.charAt(0);
									Terceto t = new Terceto(s.size(),"-", (String)val_peek(2).obj, (String)val_peek(0).obj);
									if ((c == '[')  && (c2 == '[')){
										String subst = string.substring(1,string.length()-1);
										String subst2 = string2.substring(1,string.length()-1);
										Terceto op11 = s.get(subst);
										Terceto op22 = s.get(subst2);
										if (!op11.getType().equals(op22.getType())){ 	
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
											msj.addError(er);
											t.setType("error");
										}
										else
											t.setType(op11.getType());
									}
									else{
										if (c == '[') {
											String subst = string.substring(1,string.length()-1);
											Terceto op = s.get(subst);
											if (!op.getType().equals(op2.getType())){ 	
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
											msj.addError(er);
											t.setType("error");
											}
											else
												t.setType(op2.getType());
										}
										else if (c2 == '[') {
											String subst = string2.substring(1,string.length()-1);
											Terceto op = s.get(subst);
											if (!op1.getType().equals(op.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else 
										{     if ((table.hasLexema((String)val_peek(2).obj)) && (table.hasLexema((String)val_peek(0).obj))){
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else
										{
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
										}
									}
									s.add(t);
									yyval.obj = "[" + t.getId() + "]";
								}
								}
break;
case 78:
//#line 524 "G.y"
{
					yyval.obj = ((String)val_peek(0).obj);
				 }
break;
case 79:
//#line 528 "G.y"
{Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
break;
case 80:
//#line 530 "G.y"
{Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
break;
case 81:
//#line 532 "G.y"
{Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
break;
case 82:
//#line 534 "G.y"
{Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
break;
case 83:
//#line 539 "G.y"
{ 	String string = (String)val_peek(2).obj;
							TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
							TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
							char c = string.charAt(0);
							Terceto viejo;
							Terceto t = new Terceto(s.size(),"*", (String)val_peek(2).obj, (String)val_peek(0).obj);
							if (c == '[') {
								String subst = string.substring(1,string.length()-1);
								viejo = s.get(subst);
								if (!viejo.getType().equals(op2.getType())){ 	
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
									msj.addError(er);
									t.setType("error");
								}
								else
									t.setType(viejo.getType());
							}
							else  
										{     if ((table.hasLexema((String)val_peek(2).obj)) && (table.hasLexema((String)val_peek(0).obj))){
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else
										{
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
										}
									}
									s.add(t);
									yyval.obj = "[" + t.getId() + "]";
								}
break;
case 84:
//#line 576 "G.y"
{ 
							String string = (String)val_peek(2).obj;
							TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
							TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
							char c = string.charAt(0);
							Terceto viejo;
							Terceto t = new Terceto(s.size(),"/", (String)val_peek(2).obj, (String)val_peek(0).obj);
							if (c == '[') {
								String subst = string.substring(1,string.length()-1);
								viejo = s.get(subst);
								if (!viejo.getType().equals(op2.getType())){ 	
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
									msj.addError(er);
									t.setType("error");
								}
								else
									t.setType(viejo.getType());
							}
							else {
						 
										{     if ((table.hasLexema((String)val_peek(2).obj)) && (table.hasLexema((String)val_peek(0).obj))){
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else
										{
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
										}
									}
									s.add(t);
									yyval.obj = "[" + t.getId() + "]";
								}
								}
break;
case 85:
//#line 615 "G.y"
{ 
					String s1 = (String) val_peek(0).obj;
					TSEntry entry = table.getTSEntry(s1);
					yyval.obj = s1;
					}
break;
case 86:
//#line 622 "G.y"
{Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
break;
case 87:
//#line 624 "G.y"
{Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
break;
case 88:
//#line 626 "G.y"
{Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
break;
case 89:
//#line 628 "G.y"
{Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
break;
case 90:
//#line 632 "G.y"
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
case 91:
//#line 679 "G.y"
{ String lexema = ((Token)val_peek(0).obj).getLexema();
	   
			  					TSEntry entry = table.getTSEntry(lexema);
			  	String  a= suffix;
											int b= ambit_level;
											if((!table.hasLexema(lexema+ a)) || (!entry.isDeclared()))
											{while ((b != -1)&&((!table.hasLexema(lexema+ a)) ))
											{ String[] separada = a.split("_"); 
											    for (int i = 1 ;i<=b; i++)
											
												a="";
												for (int i = 1; i<=b; i++)
												a+="_"+separada[i];	
												b--;
											
											}
											if (b ==-1)
											{
										   
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"semantico"); 
												msj.addError(er);
											}
					
			  
			  }
			  yyval.obj = lexema+a;
			}
break;
case 92:
//#line 706 "G.y"
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
									table.getTable().remove(lexema);
							}
							if (anda){
								if (entry.getRefCounter() == 1){   
									if (table.getTable().contains(newLexema)){
										((TSEntry)table.getTable().get(newLexema)).incCounter();
									}
									else {	 
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
//#line 1576 "Parser.java"
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
