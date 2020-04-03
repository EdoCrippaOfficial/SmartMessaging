## GRAMMATICA

S0 -> M S .  
S0 -> priorita numero S .  
S0 -> invia S .  
S -> M S .  
S -> priorita numero S .  
S -> invia S .  
S -> .  
M -> messaggio_a TO duepunti titolo testo corpo testo O1 .  
TO -> testo TO2 .  
TO2 -> testo TO2 .  
TO2 -> .  
O1 -> img testo O2 .  
O1 -> O2 .  
O2 -> cc .  
O2 -> .  

## ANALISI

	Sanity Checks
		All nonterminals are reachable.
		All nonterminals are realizable.
		The grammar contains no cycles.
		The grammar is null unambiguous.

	Nonterminals
		| Symbol | Nullable? | Endable? | First set                    | Follow set                      |
        |--------|-----------|----------|------------------------------|---------------------------------|
        | S0     |           | Endable  | priorita, invia, messaggio_a | $                               |
        | S      | Nullable  | Endable  | priorita, invia, messaggio_a | $                               |
        | M      |           | Endable  | messaggio_a                  | priorita, invia, messaggio_a, $ |
        | TO     |           |          | testo                        | duepunti                        |
        | TO2    | Nullable  |          | testo                        | duepunti                        |
        | O1     | Nullable  | Endable  | img, cc                      | priorita, invia, messaggio_a, $ |
        | O2     | Nullable  | Endable  | cc                           | priorita, invia, messaggio_a, $ |

	Parsing Algorithms
	LL(1)	The grammar is LL(1).	
	LR(0)	Not LR(0) — it contains a shift-reduce conflict.
	LR(1)	The grammar is LR(1).
	LALR(1)	The grammar is LALR(1).

