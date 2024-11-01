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

Git short log (van github: [https://github.com/MatthijsHAN/icss2022-sep.git]):
Matthijs Pieterse (22):                                                 
    level 0 van de grammatica gemaakt en een start gemaakt met level 1
    completed lvl 1
    Created a 4th test file (not auto included in tests yet), fixed calculation rules and made the if expressions better
    Made a start to the listener
    created hanlinkedlist and han stack
    Reorganised the grammer because of problems with the listener
    Reworked the listener. Operations and if/else are not functional yet
    changed the listener and grammer a bit to get the listener fully working.
    Completed level 0 of the checker
    Made a expressionchecker, but it doesn't check for scaler in multiply
    fixed the expression checker and began working on the declaratiions again
    Almost finished (only need to implement differnt levels) and created a way to make bool expressions for a if statement such as 5 == 5
    added comment
    finished the checking including checking my own aditions and variable scopes
    Began working on the evaluator, currently can only transform expressions
    finished evaluator
    Finished the compiler and did the first round of cleaning the code up
    added reminder
    added the classes needed for own additions
    Made some changes to the grammer and classes related to functionParameters
    parsing for functions, mixins and boolexpressions done
    Commented everything related to functions and mixins and finished the documentation

michel (1):
    Initial commit february version

Technische keuzes:
1. Ik heb er voor gekozen dat wanneer er wordt gevonden dat een pixelliteral en percantageliteral in dezelfde operatie 
   worden gevonden. Het type van de linker tak wordt doorgegeven om de rest van de expressie te checken.

Bekende bugs:
1. Als je de min operator gebruikt is het mogelijk om negatieve pixels en percentages te krijgen.
2. Omdat dingen in een if of else worden toegevoegd aan de body van het ding wat er boven zit worden deze aan het eind 
   aangesloten en wordt de volgorde van declaraties niet volledig behouden.


