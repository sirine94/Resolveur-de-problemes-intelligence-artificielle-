
package ia_tp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Unification {
    public String unifier(List<String> expr1,List<String> expr2) {
//Tester si l'une des expressions est un atome
if (estAtome(expr1) || estAtome(expr2)) {
return unifierAtomes(expr1, expr2);
}
//récupérer le premier element de la première expression
String f1 = expr1.get(0);
//sauvegarder les termes non traités de la première expression
expr1.remove(0);
List<String> t1 = expr1;
//récupérer le premier element de la deuxième expression
String f2 = expr2.get(0);
//sauvegarder les termes non traités de la deuxième expression
expr2.remove(0);
List<String> t2 = expr2;
List<String> e1 = new ArrayList<String>();
e1.add(f1);
List<String> e2 = new ArrayList<String>();
e2.add(f2);
//unifier les têtes de deux expressions
String z1 = unifier(e1, e2);
//echec d'unification
if(z1.equalsIgnoreCase("echec"))
return "echec";
//Application des changements sur les termes non traités des expressions
List<String> g1 = changer(t1, z1);
List<String> g2 = changer(t2, z1);
//unifier les termes non traités de deux expressions
String z2 = unifier(g1, g2);
//echec d'unification
if (z2.equalsIgnoreCase("echec"))
return "echec";
return z1 + " " + z2;
}
    /**
* appliquer l'unification sur l'expression t1
* @param t1
* @param z1
*/
public  List<String> changer(List<String> t1,String z1) {
String[] chg = z1.trim().split("\\s+");
List<String> b = new ArrayList<String>();
for (int i = 0; i < chg.length; i++) {
b.addAll(Arrays.asList(chg[i].split("/")));
}
if (!z1.equalsIgnoreCase("")) {
for (int i = 0; i < t1.size(); i++) {
// if(t1.get(i).equalsIgnoreCase(b[0]))
for (int j = 0; j < b.size(); j += 2)
t1.set(i,t1.get(i).replaceAll("\\" + b.get(j), b.get(j+ 1)));
}
}
return t1;
}
private  String unifierAtomes(List<String> expr1, List<String> expr2) {
String e1 = expr1.get(0);
String e2 = expr2.get(0);
//e1 et e2 sont identiques
if (e1.equalsIgnoreCase(e2))
return "";
//e1 est une variable
if (e1.charAt(0) == '?') {
if (e2.contains(e1))
return "echec";
else
return e1 + "/" + e2;
}
//e2 est un variable
if (e2.charAt(0) == '?')
return e2 + "/" + e1;
//e1 et e2 sont des fonctions
if (e1.contains("(") && e2.contains("(")) {
List<String> l1 = extractExpression(e1);
List<String> l2 = extractExpression(e2);
return unifier(l1, l2);
}
//echec
return "echec";
}

public ArrayList<String> extractExpression(String e1) {
String S = e1.substring(e1.indexOf("(")+1, e1.lastIndexOf(")"));
//System.out.println(S);
ArrayList<String> list = new ArrayList<String>();
int k = 0 ;int j = 0 ;String ch ;
	for(int i = 0 ; i<S.length();i++){	
	if (S.charAt(i)== "(".charAt(0)) k++;
	if (S.charAt(i)== ")".charAt(0)) k--;
	if (S.charAt(i)==",".charAt(0)&& k==0)
		{
			ch = S.substring(j, i);
                        //  System.out.println(ch);
			list.add(ch);
			j=i+1;
			}	
		}
		ch = S.substring(j, S.length());
		list.add(ch);
                //System.out.println(ch);
		 //System.out.println(list);
                 return (list);
               
}
 private  Boolean estAtome(List<String> s){
            
		if (s.size() == 1)
			return true;
		return false;
            }
}