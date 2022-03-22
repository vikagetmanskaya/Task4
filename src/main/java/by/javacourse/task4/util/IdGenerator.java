package by.javacourse.task4.util;

public class IdGenerator {
    private IdGenerator(){

    }
    public static int generateShipId(){
        return (int) (Math.random() * 100) + 1;
    }
    public static int generatePierId(){
        return (int) (Math.random() * 20) + 1;
    }
}
