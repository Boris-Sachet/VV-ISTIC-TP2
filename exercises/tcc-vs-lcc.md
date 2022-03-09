# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer
On pourrais avoir un TCC et un LCC égaux dans le cas d'une classe a un seul paramètre et quelques méthodes qui interragissent avec ce parramètre uniquement. Par exemple un getter, un setter et une bête fonction qui multiplie le param par deux.

```java
public class q1 {
    private int pouet;

    public q1() {
        this.pouet = 0;
    }

    public int getPouet() {
        return pouet;
    }

    public void setPouet(int pouet) {
        this.pouet = pouet;
    }

    public int doublePouet(){
        return pouet * 2;
    }
}
```