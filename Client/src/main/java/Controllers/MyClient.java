package Controllers;

import Networking.ServicesRPCProxy;

public class MyClient {

    private static ServicesRPCProxy client;
    private static MyClient single_instance = null;
    private String user;

    private MyClient() {
        this.client = new ServicesRPCProxy("localhost", 5678);
    }

    public static MyClient getInstance(){
        if(single_instance == null){
            single_instance = new MyClient();
        }
        return single_instance;
    }

    public ServicesRPCProxy getClient() {
        return client;
    }

    public void setUser(String user){ this.user = user; }

    public String getUser(){ return this.user; }
}
