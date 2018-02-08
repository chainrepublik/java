grammar test;

program 
   : 'Agent' '{' globals? functions '}';

functions :  sys_function*;

sys_function : function_user 
               | function_init 
               | function_onTrans 
               | function_onMes
               | function_onDonate
               | function_onBlock 
               | function_cleanup;

function_user : 'function' VAR '(' func_params? ')' '{' lines (ret)? '}';

function_init : 'function init()' '{' lines '}';

function_onTrans : 'function onTrans()' '{' lines '}';

function_onMes : 'function onMes()' '{' lines '}';

function_onDonate : 'function onDonate()' '{' lines '}';

function_onBlock : 'function onBlock()' '{' lines '}';

function_cleanup : 'function cleanup()' '{' lines '}';

lines 
   : (line)*;

line 
   : assign ';'
   | func_call ';'
   | stat_if
   | stat_for
   | stat_while
   | stat_switch;

func_call : 
   predef_func #f_predef
   | VAR '(' params ')' #f_user;

predef_func : 'print' '(' expr_math ')' #f_print
   | 'exit' '(' expr_math ')' #f_exit
   | 'query' '(' expr_math ',' var ',' expr_math ')' #f_query
   | 'balance' '(' expr_math ',' expr_math ')' #f_balance
   | 'send' '(' expr_math ',' expr_math ',' expr_math ',' expr_math ')' #f_send
   | 'mes' '(' expr_math ',' expr_math ',' expr_math ')' #f_mes
   | 'email' '(' expr_math ',' expr_math ',' expr_math ')' #f_email
   | 'callURL' '(' expr_math ')' #f_url   
   | 'reject_trans' '()' #f_reject_trans
   | 'sysinfo' '(' sys_query ',' var ')' #f_sysinfo
   | 'getPrice' '(' expr_math ',' expr_math ')' #f_get_price
   | 'trade' '(' expr_math ',' expr_math ',' expr_math ',' expr_math ',' expr_math ')' #f_trade 
   | 'dividends' '(' expr_math ')' #f_dividends
   | 'renew' '(' expr_math ')' #f_renew
   | 'nextRow' '(' var ')' #f_next_row
   | 'getCol' '(' expr_math ',' expr_math ')' #f_get_col;

sys_query : 'SELECT * FROM ' TAB_NAME ' WHERE ' sys_query_comp+;

sys_query_comp : '('? COL_NAME DB_COMP (DB_STR | number) ')'?; 

assign : 
   var ASSIGN_OP expr_math  #assign_expr
   | var INCDEC_OP  #assign_inc_dec
   | var ASSIGN_OP array  #assign_array;

globals : 
    global_assign+;

global_assign : 
    'var' var ASSIGN_OP var_value ';';

global_var : 'this' '.' var;

expr_math 
   : '(' expr_math ')' #expr_paran
     | bool #expr_bool
     | func_call #expr_func
     | expr_math MATH_OP expr_math #expr_op_expr
     | number #expr_nume
     | var #expr_var
     | string #expr_string
     | var_function #expr_var_func
     | sys_data #expr_sys_data;

expr_comp
    : '(' expr_comp ')' #expr_comp_paran
    | expr_math COMP_OP expr_math #expr_comp_op
    | expr_comp LOGIC_OP expr_comp #expr_comp_logic
    | var #expr_comp_bool;

stat_if
    : 'if' '(' expr_comp ')' '{' lines '}' ('else' '{' lines '}')?
    | 'if' '(' expr_comp ')' '{' lines '}' ('else' line)?
    | 'if' '(' expr_comp ')' line ('else' '{' lines '}')?
    | 'if' '(' expr_comp ')' line ('else' line)?;

stat_for
    : 'for' '(' assign ';' expr_comp ';' var INCDEC_OP ')' line
    | 'for' '(' assign ';' expr_comp ';' var INCDEC_OP ')' '{' lines '}';

stat_while
    : 'while' '(' expr_comp ')' line
    | 'while' '(' expr_comp ')' '{' lines '}';

stat_switch
    : 'switch' '(' var ')' '{' ('case' ( misc_var ) ':' lines BREAK)+ '}';

func_params : VAR (',' VAR)*;

params : expr_math (',' expr_math)*;

var_value : number 
          | string;

number 
    : INT
    | dec;

dec
   : INT '.' INT;

var :
    global_var 
    | var_array
    | VAR;

var_array : 
    VAR '[' expr_math ']';

var_function : var '.' (STR_FUNC | ARRAY_FUNC) '(' params? ')' #var_function_var
             | 'MATH' '.' MATH_FUNC '(' params? ')' #var_function_math
             | 'CRYPTO' '.' CRYPTO_FUNC '(' params? ')' #var_function_crypto;

array : array_var (',' array_var)*;
        
array_var : string | number;

string : 
     CHAR_SEQ; 

bool :
     TRUE 
     | FALSE;

misc_var :
     string 
   | number 
   | var 
   | func_call;

ret : 'return' '(' (expr_math)? ')' ';';

sys_data : 'SYS.BLOCK.' SYS_DATA_BLOCK
         | 'SYS.MES.' SYS_DATA_MES
         | 'SYS.TRANS.' SYS_DATA_TRANS
         | 'SYS.COM.' SYS_DATA_COM;

SYS_DATA_COM : 'ADR' 
               | 'COMID' 
               | 'NAME' 
               | 'SYMBOL' 
               | 'EXPIRES' 
               | 'PIC';

SYS_DATA_BLOCK : 'BLOCK'
               | 'PREV_HASH'
               | 'PAYLOAD_HASH'
               | 'SIGNER'
               | 'DEL_POWER'
               | 'SIGN'
               | 'TSTAMP'
               | 'NONCE'
               | 'HASH';

SYS_DATA_MES : 'SENDER'
               | 'REC' 
               | 'SUBJ' 
               | 'HASH'
               | 'MES';

SYS_DATA_TRANS : 'SRC'
               | 'AMOUNT'
               | 'CUR'
               | 'ESCROWER'
               | 'MES'
               | 'HASH';

BREAK : 'break' ';';

STR_FUNC : 'length' 
          | 'indexOf' 
          | 'lastIndexOf' 
          | 'substring' 
          | 'replace' 
          | 'toUpperCase' 
          | 'toLowerCase' 
          | 'concat' 
          | 'charAt' 
          | 'charCodeAt' 
          | 'split';

MATH_FUNC : 'PI' 
          | 'round' 
          | 'pow' 
          | 'sqrt' 
          | 'abs' 
          | 'ceil' 
          | 'floor' 
          | 'sin' 
          | 'cos' 
          | 'min' 
          | 'max' 
          | 'random' 
          | 'tan';

ARRAY_FUNC : 'toString' 
          | 'join' 
          | 'push' 
          | 'pop' 
          | 'shift' 
          | 'unshift' 
          | 'splice' 
          | 'concat' 
          | 'slice';

CRYPTO_FUNC : 'digest'
            | 'base64_encode'
            | 'base64_decode';

MATH_OP : 
        '*'
      | '/'
      | '+'
      | '-'
      | '%';

ASSIGN_OP : 
        '='
      | '+='
      | '-='
      | '*='
      | '/=';

COMP_OP : 
        '=='
      | '==='
      | '!='
      | '!=='
      | '>'
      | '<'
      | '>='
      | '<=';


BITWISE_OP :
      '&' 
      | '|'
      | '~'
      | '^'
      | '<<'
      | '>>'
      | '>>>';
      
LOGIC_OP :
       '&&'
     | '||';

INCDEC_OP :
       '++'
     | '--';

NOT_OP : 
     '!';

TRUE : 'true';

FALSE : 'false'; 

VAR 
   : [a-zA-Z]+ [0-9]*;

CHAR_SEQ 
   : '"' [a-zA-Z0-9 !@#$%^&*()_\-{}[\]:;'|\\<>,./?=]+ '"';

INT 
   : [0-9]+
   ; 

EQUAL : '=';

COL_NAME : [a-zA-Z0-9_]+;

DB_STR : '\'' [a-zA-Z0-9_]+ '\'';

DB_COMP : 'OR' | 'AND';

TAB_NAME : 'adata' 
         | 'adr' 
         | 'adr_attr'
         | 'ads'
         | 'allow_trans'
         | 'assets'
         | 'assets_owners'
         | 'assets_mkts'
         | 'assets_nkts_pos'
         | 'assets_mkts_trades'
         | 'blocks'
         | 'bonuses'
         | 'com_prods'
         | 'comments'
         | 'companies'
         | 'countries'
         | 'dividends'
         | 'endorsers'
         | 'escrowed'
         | 'items_consumed'
         | 'laws'
         | 'laws_votes'
         | 'rent_contracts'
         | 'rewards'
         | 'stocuri'
         | 'taxes'
         | 'tipuri_companii'
         | 'tipuri_licente'
         | 'tipuri_produse'
         | 'tweets'
         | 'tweets_follow'
         | 'tweets_read'
         | 'votes' 
         | 'wars' 
         | 'wars_fighters' 
         | 'work_procs' 
         | 'workplaces';


WS
   : [ \r\n\t] + -> skip
   ;
   

