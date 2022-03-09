# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.

## Answer
PMD lancé sur le projet Apache Commons CLI avec la commande
`pmd.bat -f html -R rulesets/java/quickstart.xml > errorFile.html -d ./`
Dans le répertoire `tp2-q2\src\main\java`

Vrais positif src\main\java\org\apache\commons\cli\Option.java - ligne 620
```java
public String[] getValues() {
    return hasNoValues() ? null : values.toArray(new String[values.size()]);
}
```
L'appel de toArray n'a pas besoin d'une array de la bonne taille car si elle ne rentre pas dans l'array donnée une nouvelle sera générée.
```java
    return hasNoValues() ? null : values.toArray(new String[0]);
```

Faux positif src\main\java\org\apache\commons\cli\DefaultParser.java - ligne 606
```java
        if (getLongPrefix(token) != null && !token.startsWith("--")) {
            // -LV
            return true;
        }
        return false;
```
Il y a plusieurs returns dans cette méthode, on pourrais renvoyer directement le résultat du dernier if, mais je pense que cela nuirait a la lisibilité du code pour un gain très très minime.