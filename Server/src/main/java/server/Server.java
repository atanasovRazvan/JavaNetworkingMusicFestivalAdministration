package server;


import Networking.AbstractServer;
import Networking.ChatRpcConcurrentServer;
import Repositories.*;
import Services.Service;

public class Server {

    public static void main(String[] args) {

        AngajatRepository angajatRepository = new AngajatRepository();
        ArtistRepository artistRepository = new ArtistRepository();
        ClientRepository clientRepository = new ClientRepository();
        ScenaRepository scenaRepository = new ScenaRepository();
        SpectacolRepository spectacolRepository = new SpectacolRepository();

        Service service = new Service(angajatRepository, artistRepository, clientRepository, scenaRepository, spectacolRepository);

        AbstractServer server = new ChatRpcConcurrentServer(5678, service);
        try{
            server.start();
        }
        catch(Exception e){

        }
        finally{
            try {
                server.stop();
            }
            catch (Exception e){

            }
        }


    }

}
