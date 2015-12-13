package battle263;

import battle263.GUI.*;


public class Main {

    public static void main(String[] args) {
        //so... the whole thing runs on EDT

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }

}
