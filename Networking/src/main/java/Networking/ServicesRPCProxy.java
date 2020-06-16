package Networking;

import Entities.*;
import Services.IService;
import Services.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ServicesRPCProxy implements IService {

    private String host;
    private Integer port;
    private Observer client;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket;
    private BlockingDeque<Response> responses;
    private volatile boolean finished;

    public ServicesRPCProxy(String localhost, int i) {

        this.host = localhost;
        this.port = i;
        this.responses = new LinkedBlockingDeque<>();

    }

    @Override
    public void login(Angajat angajat, Observer observer) throws Exception {

        initializeConnection();
        Request request = new Request.Builder().type(RequestType.LOGIN).object(angajat).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getRt().equals(ResponseType.ERROR)) {
            closeConnection();
            throw new Exception("ERROR");
        }
        else{
            client=observer;
        }

    }

    public void setClient(Observer obs){
        client = obs;
    }

    public void closeConnection(){

        try {
            finished = true;
            ois.close();
            oos.close();
            socket.close();
            client = null;
        }
        catch(Exception e){

        }

    }

    public Response readResponse(){

        Response response = null;

        try{
            response = responses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;

    }

    public void sendRequest(Request request){
        try{

            oos.writeObject(request);
            oos.flush();

        }
        catch(Exception ex){

        }
    }

    private void initializeConnection() {

        try{
            this.socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            finished = false;
            startRead();
        }
        catch(Exception e){

        }

    }

    private void startRead() {

        Thread t = new Thread(new ReaderThread());
        t.start();

    }

    private boolean isUpdate(Response response) {
        return response.getRt().equals(ResponseType.NOTIFICARE);
    }

    private void handleUpdate(Response response) {
        System.out.println("pusca");
        client.update((ArrayList<DTOForBuyTable>) response.getObj());
    }

    private class ReaderThread implements Runnable{
        @Override
        public void run() {
            while(!finished){
                try{

                    Object obj = ois.readObject();
                    if(isUpdate( (Response) obj))
                        handleUpdate((Response) obj);
                    else
                        responses.put((Response) obj);

                }
                catch(Exception e){

                }
            }
        }
    }

    @Override
    public List<DTOForTable> getAll() throws Exception {

        Request request = new Request.Builder().type(RequestType.ALL).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getRt().equals(ResponseType.ERROR)){
            closeConnection();
            throw new Exception("ERROR");
        }
        return (List<DTOForTable>) response.getObj();
    }

    @Override
    public List<DTOForBuyTable> getAllDate(LocalDate ldt) throws Exception {

        Request request = new Request.Builder().type(RequestType.FIND).object(ldt).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getRt().equals(ResponseType.ERROR)){
            closeConnection();
            throw new Exception("ERROR");
        }
        return (List<DTOForBuyTable>) response.getObj();

    }

    @Override
    public void ticketing(Client cl, Observer observer) throws Exception {
        Request request = new Request.Builder().type(RequestType.SELL).object(cl).build();
        sendRequest(request);
    }

    @Override
    public void logout(String user) {
        finished = true;
        try {
            ois.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
