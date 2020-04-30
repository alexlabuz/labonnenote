package donnee;

public class alea {

    public static Integer alea(Integer Min, Integer Max){
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }

}
