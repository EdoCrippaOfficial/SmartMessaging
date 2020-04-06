## GRAMMATICA

S0 -> S1 exit .  
S1 -> M S2 .  
S1 -> priorita numero S2 .  
S1 -> invia S2 .  
S2 -> M S2 .  
S2 -> priorita numero S2 .  
S2 -> invia S2 .  
S2 -> .  
M -> messaggio_a TO duepunti titolo testo corpo testo O1 .  
TO -> testo TO2 .  
TO2 -> testo TO2 .  
TO2 -> .  
O1 -> img testo O2 .  
O1 -> O2 .  
O2 -> cc O3 .  
O2 -> O3 .  
O3 -> format testo .  
O3 -> .  

## ANALISI

#### Sanity Checks
    All nonterminals are reachable.
    All nonterminals are realizable.
    The grammar contains no cycles.
    The grammar is null unambiguous.

#### Nonterminals

| Symbol | Nullable? | Endable? | First set                    | Follow set                   |
|--------|-----------|----------|------------------------------|------------------------------|
| S0     |           | Endable  | priorita, invia, messaggio_a | &#x2199;                     |
| S1     |           |          | priorita, invia, messaggio_a | exit                         |
| S2     | Nullable  |          | priorita, invia, messaggio_a | exit                         |
| M      |           |          | messaggio_a                  | priorita, invia, messaggio_a |
| TO     |           |          | testo                        | duepunti                     |
| TO2    | Nullable  |          | testo                        | duepunti                     |
| O1     | Nullable  |          | img, cc, format              | priorita, invia, messaggio_a |
| O2     | Nullable  |          | cc, format                   | priorita, invia, messaggio_a |
| O3     | Nullable  |          | format                       | priorita, invia, messaggio_a |

#### Parsing Algorithms
    LL(1)	The grammar is LL(1).	
    LR(0)	Not LR(0) — it contains a shift-reduce conflict.
    LR(1)	The grammar is LR(1).
    LALR(1)	The grammar is LALR(1).
