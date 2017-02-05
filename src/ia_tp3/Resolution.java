
package ia_tp3;
import ia_tp3.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Resolution {
     public ArrayList<Regle> Extract() throws FileNotFoundException
    {  String S; 
       ArrayList<String> regles = new ArrayList<String>();
       ArrayList<Regle> Regles =new ArrayList<Regle>();
       int k=0;
        // Lire le fichier ligne par ligne
     Scanner scanner=new Scanner(new File("C:\\Users\\sirine\\Documents\\NetBeansProjects\\IA_TP3\\Regles.txt"));
		while (scanner.hasNextLine()) {
                       S = scanner.nextLine(); 
                       regles.add(S);
                      
		       k++; }
                regles.remove(10);
                
   for(int i=0; i< regles.size(); i++)
   {   Regles.add(new Regle());
       Regles.get(i).id = i;
       String premisse = regles.get(i).substring(regles.get(i).indexOf(":")+1,regles.get(i).indexOf(" = "));
     
       String conclusion= regles.get(i).substring(regles.get(i).indexOf(" = ")+3);
       Regles.get(i).etat_initial= premisse.substring(premisse.indexOf("("), premisse.indexOf(")")+1);
       Regles.get(i).etat_final= conclusion;
       String[] conds = premisse.substring(premisse.indexOf(")")+1).split("et");
       for(int j=0; j< conds.length;j++ )
       {    if(!conds[j].equals("") && !conds[j].equals(" "))
       {   conds[j]=conds[j].trim();  
           Regles.get(i).conditions.add(conds[j]); }
    
       }
       
    }
   return Regles;
}   
     public ArrayList<Etat> genereOperateursApplicables(Etat init) throws FileNotFoundException
     {  ArrayList<Etat> etats = new ArrayList<Etat>();
         ArrayList<Regle> regles= Extract();
         Unification un= new Unification();
          String S= "(" + Integer.toString(init.x)+ "," +Integer.toString(init.y)+ ")";
         
         ArrayList<String> S1 = new ArrayList<String>(); S1.add(S);
         ArrayList<String> S2 = new ArrayList<String>(); 
        
         for(int i=0; i< regles.size(); i++)
         { 
             S2.add(regles.get(i).etat_initial);
         
          
            if(regles.get(i).condition(un.unifier(S2,S1))) 
            {   
                int x= regles.get(i).Etat_final(init).x;
               int y= regles.get(i).Etat_final(init).y;
               //  System.out.println("On a généré : Regle " + (regles.get(i).id+1)+" "+un.unifier(S1, S2)+ "  => ("+x + ","+ y+ ")" );
              etats.add( new Etat(x,y));
               
            }
          S2.clear();  
         } 
         return etats;
     }
     public void rechercher(Etat init,Etat but ) throws FileNotFoundException
     {  int x= init.x;
        int y= init.y;
        int g=0;
        int examine_index =0;
        Etat examine = init;
        ArrayList<Etat> ouverts = new ArrayList<Etat>();
        ouverts.add(init);
        ArrayList<Etat> fermes = new ArrayList<Etat>();
      
        while(ouverts.size() != 0)
      
        { 
            if( examine.x==but.x && examine.y==but.y )
        {
            System.out.println("Et enfin on a trouvé le but !!:( "+ but.x+ "," + but.y+ ")");
            return;
      
        }  
             
       g=g+1;
            ArrayList<Etat> generes = genereOperateursApplicables(examine);
            for(Etat gen : generes)
            {  if(  non_existe(gen,ouverts))
                 { ouverts.add(gen);
                      gen.g=g; } ;
            }
          
            fermes.add(examine);
            
              ouverts.remove(examine);
                       
          for(int i=0;i< ouverts.size();i++)
          { ouverts.get(i).g= g;       
            //calcul heuristique h
        ouverts.get(i).h= Math.abs(ouverts.get(i).x-2);
            ouverts.get(i).f= g + ouverts.get(i).h;
             
          }
         examine= ouverts.get(0);
     
      int  l=0; boolean B=true;
             while( B && l< fermes.size() )
         {
             if((fermes.get(l).x == ouverts.get(0).x && (fermes.get(l).y == ouverts.get(0).y)))
             {    
              B=false;break;
             }
           
           l++;
             
         }  
           if(!B)
             System.out.println("  fermé :("+ ouverts.get(0).x + "," +  ouverts.get(0).y + ")");  
             else 
                  System.out.println(" ouvert :("+ ouverts.get(0).x + "," +  ouverts.get(0).y + ")");
             
          
         for( int j=1; j< ouverts.size(); j++)
         {  // System.out.println("("+ ouverts.get(j).x + "," +  ouverts.get(j).y + ")");
             boolean bool=true;int k=0;
       
             while(bool && k< fermes.size() )
         {
             if((fermes.get(k).x == ouverts.get(j).x && (fermes.get(k).y == ouverts.get(j).y)))
             {    
                 bool=false;break; }
            
           k++;
             
         }  
     
             if (bool)
            System.out.println(" ouvert :("+ ouverts.get(j).x + "," +  ouverts.get(j).y + ")");
             else 
                  System.out.println("  fermé :("+ ouverts.get(j).x + "," +  ouverts.get(j).y + ")");
               if((   ouverts.get(j).f < ouverts.get(j-1).f ) && bool) 
               {examine= ouverts.get(j); examine_index=j; }
         }
            System.out.println("Au niveau " + g + ", on a l'etat (" + examine.x + ","+ examine.y + ") avec l'heuristique f= " + examine.f );
        }
         
     }
     public boolean non_existe(Etat gen, ArrayList<Etat> generes)
     { 
       for(int i=0;i< generes.size();i++)
       {
           if(generes.get(i).x == gen.x && generes.get(i).y == gen.y )
               return false;
       }
         return true;
     }
     public Etat min(ArrayList<Etat> ouverts)
     {  int k=0;
         for(int i = 1; i< ouverts.size();i++)
         {
             if (ouverts.get(i).f < ouverts.get(i-1).f)
                 k= i;
         }
         return ouverts.get(k);
     }
}
