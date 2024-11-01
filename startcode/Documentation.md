Wat ik heb geïmplementeerd:

| ID   | Behaald?|
| ---- |---------|
| AL01 | Ja      |
| AL02 | Ja      |
| AL03 | Ja      |
| AL04 | Ja      |

| ID   | Behaald?|
|------|---------|
| PA00 | Ja      |
| PA01 | Ja      |
| PA02 | Ja      |
| PA03 | Ja      |
| PA04 | Ja      |
| PA05 | Ja      |


| ID   | Behaald?|
|------|---------|
| CH00 | Ja      |
| CH01 | Ja      |
| CH02 | Ja      |
| CH03 | Ja      |
| CH04 | Ja      |
| CH05 | Ja      |
| CH06 | Ja      |

| ID   | Behaald?|
|------|---------|
| TR01 | Ja      |
| TR02 | Ja      |

| ID   | Behaald?|
|------|---------|
| GE01 | Ja      |
| GE02 | Ja      |

Eigen uitbreidingen:

| Omschrijving                                                                              | Behaald? | Afgesproken punten |
|-------------------------------------------------------------------------------------------|----------|--------------------|
| Extra reken functies: delen, negatieve scalers en haakjes                                 | Ja       | 5                  |
| Boolexpressions: ==, !=, <=, >=, < en >                                                   | Ja       | 5                  |
| Herbruikbare code blokken/ mixins (alleen tot en met het parsen gemaakt(is uitgecomment)) | Nee      | 5                  |
| Functies/ herbruikbare formules (alleen tot en met het parsen gemaakt(is uitgecomment))   | Nee      | 5                  |  
Voor bewijs zie: [src/main/resources/level5.icss]

Git short log (van github: [https://github.com/MatthijsHAN/icss2022-sep.git]:



Technische keuzes:
1. Ik heb er voor gekozen dat wanneer er wordt gevonden dat een pixelliteral en percantageliteral in dezelfde operatie 
   worden gevonden. Het type van de linker tak wordt doorgegeven om de rest van de expressie te checken.

Bekende bugs:
1. Als je de min operator gebruikt is het mogelijk om negatieve pixels en percentages te krijgen.
2. Omdat dingen in een if of else worden toegevoegd aan de body van het ding wat er boven zit worden deze aan het eind 
   aangesloten en wordt de volgorde van declaraties niet volledig behouden.


