# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](https://github.com/selabs-ur1/VV-TP2/blob/master/exercises/designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer

On peut appliquer la règle suivante :
`//IfStatement[.//IfStatement[.//IfStatement]]`
```XML
<rule name=""
      language="java"
      message=""
      class="net.sourceforge.pmd.lang.rule.XPathRule">
   <description>

   </description>
   <priority>3</priority>
   <properties>
      <property name="version" value="2.0"/>
      <property name="xpath">
         <value>
<![CDATA[
//IfStatement[.//IfStatement[.//IfStatement]]
]]>
         </value>
      </property>
   </properties>
</rule>
```

Par exemple sur le code suivant on matche bien les deux premiers nodes:
```JAVA
public class test {
    public static void prout(){
        int pouet = 0;
        if (true) {
            if (false){
                if (pouet == 0) {
                    if (pouet == 2) {
                        System.out.println(pouet);
                    }
                }
            }
        }
    }
}
```