package ua.axiom;

import ua.axiom.app.Application;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length == 1) {
            Application application = new Application(args[0]);
            application.run();
        }

    }
}
