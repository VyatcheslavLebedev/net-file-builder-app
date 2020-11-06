import java.io.IOException;

public class NetFileBuilderApp {

    public static void main(String[] args){
        double minDepth = 90.0;
        boolean isEnteredGoodValue = false;
        while(!isEnteredGoodValue) {
            System.out.print("Enter min Depth (C) : ");
            try {
                minDepth = System.in.read();
                isEnteredGoodValue = true;
            } catch (IOException e) {
                System.out.println("You entered not valid value");
            }
        }
        MainMethod mainMethod = new MainMethodImpl();
        mainMethod.mainMethod(minDepth);

    }
}
