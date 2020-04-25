grammar SmartMessage;

program : (message | priorita | invia | stats)+ exit;

priorita  : 'Priorità' NUM ;

invia : 'Invia';

message : new_mess utente+ duepunti titolo corpo opzioni;
opzioni : (cc)? (img)? (format)?;
titolo : 'Titolo' TESTO;
utente: TESTO;
corpo : 'Corpo' TESTO;
cc : 'CC';
img : 'Img' TESTO;
format : 'Formattazione' TESTO;

new_mess : 'Messaggio a';
duepunti : ':';

stats: 'Stats' (utente)?;

exit : EXIT;


TESTO
    :  '"' (~('\\'|'"') )* '"'
    ;

NUM
    :  [0-9]
    ;

EXIT : 'Exit';

LINE_COMMENT
    : '//' (~('\n'|'\r'))* -> skip;

WS: [ \n\t\r]+ -> skip;

