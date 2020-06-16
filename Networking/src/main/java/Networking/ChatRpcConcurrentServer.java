package Networking;

import Services.IService;

import java.net.Socket;

public class ChatRpcConcurrentServer extends AbstractConcurrentSever {
    private IService chatServer;

    public ChatRpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientRPCWorker worker=new ClientRPCWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
