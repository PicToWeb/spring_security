package fr.diginamic.spring_security.utilitaire;

public class TestSingleton {

    //mémoire permanente
    private final static TestSingleton INSTANCE = new TestSingleton();
    private TestSingleton() {}
    public static TestSingleton getInstance() {
        return INSTANCE;
    }
    public void test(){
        System.out.println("coucou form singleton");
    }
}
