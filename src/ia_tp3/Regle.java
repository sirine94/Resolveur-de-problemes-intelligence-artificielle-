
package ia_tp3;
import ia_tp3.Etat ;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Regle {
  
    public int id; 
    public String etat_initial ;
    public String etat_final;
    public ArrayList<String> conditions= new ArrayList<String>() ;
  
    int Calculer(ArrayList<Integer> Pile,ArrayList<String> Pile2)
    {int number=0;
        if(Pile2.get(0) .equals("+"))    number= Pile.get(0)+ Pile.get(1);
        if (Pile2.get(0).equals( "-"))  number= Pile.get(0) - Pile.get(1);
        
        Pile.clear();
        Pile2.clear();
       return number;
    } 
    public boolean condition(String S )//La chaine S reprèsente Unification d'état initial
    {   S=S.trim();
        int x=-1;int y=-1;
        if (S == "") return true;
        if (S.contains("echec")) return false;
        String[] tab = S.split(" ");

       if (S.contains("?x") )
       {String X= tab[0].substring(tab[0].indexOf("/")+1);
       x= Integer.parseInt(X);
        if (S.contains("?y") )
       {String Y= tab[1].substring(tab[1].indexOf("/")+1);
       y= Integer.parseInt(Y);
       
       }
       }
       else if (S.contains("?y") )
       { 
           String Y= tab[0].substring(tab[0].indexOf("/")+1);
       y= Integer.parseInt(Y);
       }   
       int i=0;
      boolean bool= true;
      while(bool &&  i< conditions.size())
      {  
          ArrayList<Integer> Pile= new ArrayList<Integer>();
         ArrayList<String> Pile2 =new ArrayList <String>();
          String[] symboles = conditions.get(i).split(" ");
      
         int j=0;
          while(j < symboles.length)
          { symboles[j]=symboles[j].trim();
           
              if(symboles[j].contains("?x")) 
              {Pile.add(x);  }
              else if(symboles[j].contains("?y")) Pile.add(y);
              else if(symboles[j].contains("+")) Pile2.add("+");
              else if(symboles[j].contains("-") ) Pile2.add("-");
              else if((symboles[j].contains("<")) || (symboles[j].contains("<=")) ||
                    (symboles[j].contains( ">=")) || (symboles[j].contains("==") || (symboles[j].contains( ">")))) 
            {  if(Pile.size()>1)
            { int number=Calculer(Pile,Pile2);
               Pile.add(number); }
           
          
               Pile2.add(symboles[j]);
            } 
            else{
                int n= Integer.parseInt(symboles[j]);
                Pile.add(n);
            }
            
            j++;
          }
          if(Pile2.get(0).equals( "==")) 
          if(Pile.get(0)== Pile.get(1)) bool= true  ;else bool=false;
          if(Pile2.get(0).equals("<"))
          if(Pile.get(0) <Pile.get(1)){ bool= true; } else bool=false;
          if(Pile2.get(0).equals("<="))   
          if(Pile.get(0)<= Pile.get(1)) {bool= true;} else bool=false;
            if(Pile2.get(0).equals(">"))   
          if(Pile.get(0)> Pile.get(1)){  bool= true; } else bool=false;
             if(Pile2.get(0).equals(">="))  
          if(Pile.get(0)>= Pile.get(1)) {bool= true;} else bool=false;      
      i++;  
      } return bool;
      }    
    public int Calc_expr(String S,Etat init )
    {  int n1=0; int n2=0;
  
        String[] tab= S.split(" ");
      
       if (tab[0].contains("?x") ) n1= init.x;
       else if( tab[0].contains("?y")) n1=init.y;
       else n1=Integer.parseInt(tab[0]);
         if (tab[2].contains("?x") ) n2= init.x;
       else if( tab[2].contains("?y")) n2=init.y;
       else n2=Integer.parseInt(tab[2]);
         if(tab[1].equals("+")) return n2+n1 ;
         if(tab[1].equals("-")) return n1 - n2 ;
         return 0;
    }    
    public Etat Etat_final(Etat init) 
    {  int x=0; int y=0;
        String X= etat_final.substring(etat_final.indexOf("(")+1, etat_final.indexOf(",")).trim();
        String Y= etat_final.substring(etat_final.indexOf(",")+1,etat_final.lastIndexOf(")")).trim();
      int   k=0;
        if (X.contains("(") || Y.contains("("))
        { String expr="";
            if(X.contains("("))
          expr= X.substring(X.indexOf("(")+1,X.indexOf(")"));
            
             if(Y.contains("("))
          expr= Y.substring(Y.indexOf("(")+1,Y.indexOf(")"));
           int n= Calc_expr(expr,init);
             String N= Integer.toString(n);
           
             if(X.contains("(")) 
              X=X.replace("("+ expr + ")", N); 
             
             if(Y.contains("(")) Y=Y.replace("(" + expr + ")", N);
           
             k++;
        }
        
        if(X.split(" ").length == 1) 
        {
            if(X.contains("?x")) x=init.x;
           else if(X.contains("?y")) x=init.y;
            else x=Integer.parseInt(X); 
        }
        else {
            x=Calc_expr(X,init);
        } 
       
         if(Y.split(" ").length == 1) 
        {
            if(Y.contains("?x")) y=init.x;
           else if(Y.contains("?y")) y=init.y;
            else y=Integer.parseInt(Y); 
        }
        else { 
             
            y=Calc_expr(Y,init);
        }
         return  new Etat(x,y);
    }
            }
