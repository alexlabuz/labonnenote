/*
Mettre la ligne suivante :

import static com.company.saisirNombre.*;
 */
package donnee;

import java.util.Scanner;

public class saisirNombre {

    static int saisirEntier(int min, int max){
        Scanner input = new Scanner(System.in);
        try{
            int nombre = input.nextInt();
            if((nombre >= min && nombre <= max) || (min == 0 && max == 0)){
                return nombre;
            }else{
                System.out.print("Erreur de saisie, réessayez : ");
                return saisirEntier(min, max);
            }
        }
        catch (Exception e){
            System.out.print("Erreur de saisie, réessayez : ");
            return saisirEntier(min, max);
        }
    }

    static double saisirReel(double min, double max){
        Scanner input = new Scanner(System.in);
        try{
            double nombre = input.nextDouble();
            if((nombre >= min && nombre <= max) || (min == 0 && max == 0)){
                return nombre;
            }else{
                System.out.print("Erreur de saisie, réessayez : ");
                return saisirReel(min, max);
            }
        }
        catch (Exception e){
            System.out.print("Erreur de saisie, réessayez : ");
            return saisirReel(min, max);
        }
    }

}
