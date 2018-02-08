grammar SQL;

statement : 'SELECT * FROM ' TAB_NAME ('WHERE' where_logic)? ('ORDER BY' order_cond)? #sel_stat
          | 'INSERT INTO ' TAB_NAME 'SET' (set_exp)? #insert_stat
          | 'UPDATE ' TAB_NAME 'SET' (set_exp)? ('WHERE' where_logic)? #update_stat
          | 'DELETE FROM ' TAB_NAME ('WHERE' where_logic)? #del_stat;

order_cond : USER_COL (',' USER_COL)*;

where_logic : comp_exp (LOGIC_OP comp_exp)*;

comp_exp : USER_COL COMP val;

set_exp : USER_COL COMP  val (',' set_exp)*;

dec
   : INT '.' INT;

num 
    : INT 
    | dec;

val 
    : INT 
    | dec 
    | STRING
    | USER_COL;

COMP : '=' 
     | '>' 
     | '>=' 
     | '<' 
     | '<=' 
     | 'LIKE';
         
USER_COL : 's1' | 's2' | 's3' | 's4' | 's5' | 's6' | 's7' | 's8' | 's9' | 's10'
         | 'n1' | 'n2' | 'n3' | 'n4' | 'n5' | 'n6' | 'n7' | 'n8' | 'n9' | 'n10';

LOGIC_OP : 'AND' | 'OR';

STRING : '\'' [a-zA-Z0-9 !@#$%^&*()_\-{}[\]:;|\\<>,./?=]+ '\'';

INT 
   : [0-9]+; 

TAB_NAME
    : [a-zA-Z]+ [0-9]*;

WS
   : [ \r\n\t] + -> skip
   ;