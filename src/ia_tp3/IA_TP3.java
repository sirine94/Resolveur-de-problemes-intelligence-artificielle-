package ia_tp3;

import ia_tp3.* ;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class IA_TP3 {

    public static void main(String[] args) throws FileNotFoundException {
      Resolution Res = new Resolution();
      Etat init=new Etat(0,0);
      Etat but= new Etat(2,0);
     // Res.genereOperateursApplicables(init);
     Res.rechercher(init, but);
   
    }
    
}
