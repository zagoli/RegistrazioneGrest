package ApplicationContext;

import java.util.HashMap;


public class ApplicationContext{

    private static HashMap<String,Object> beanFactory;
    
    public static HashMap<String,Object> getContext() { 
        if (beanFactory == null) {
            beanFactory = new HashMap<>();
        }
        return beanFactory ;
    }


    public ApplicationContext () {
        //evita di istanziare l'ApplicatinContext
    }


}
