grammar SmartMessage;

program : (message | priorita | invia | stats)+ exit;

priorita  : 'Priorità' NUM ;

invia : 'Invia';

stats: 'Stats' (utente)?;

message : new_mess dest+ duepunti titolo corpo opzioni;

new_mess : 'Messaggio a';
dest: utente;
duepunti : ':';
titolo : 'Titolo' TESTO;
corpo : 'Corpo' TESTO;
opzioni : (cc)? (img)? (format)?;

utente: TESTO;

cc : 'CC';
img : 'Img' TESTO;
format : 'Formattazione' TESTO;

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