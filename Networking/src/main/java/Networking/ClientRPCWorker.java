package Networking;

import Entities.Angajat;
import Entities.Client;
import Entities.DTOForBuyTable;
import Services.IService;
import Services.Observer;
import jdk.vm.ci.meta.Local;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientRPCWorker implements Runnable, Observer {

    private IService service;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private volatile boolean connected;
    private LocalDate date;

    public ClientRPCWorker(IService srv, Socket sock){
        this.service = srv;
        this.socket = sock;
        try{
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(sock.getInputStream());
            connected = true;
        }
        catch(Exception ex){

        }

    }

    @Override
    public void update(ArrayList<DTOForBuyTable> cl) {
        try {
            Response response = new Response.Builder().type(ResponseType.NOTIFICARE).object(service.getAllDate(this.date)).build();
            sendResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while(connected){
            try{
                Object request = ois.readObject();
                Response response = handleRequest((Request) request);
                if(response!=null){
                    sendResponse(response);
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            try{
                Thread.sleep(1000);
            }
            catch(Exception ex){

            }
        }
        try{
            ois.close();
            oos.close();
            socket.close();
        }
        catch(Exception ex){

        }

    }

    public Response handleRequest(Request request){
        Response response = null;
        try {
            Method method = this.getClass().getDeclaredMethod("handle"+request.getRt(), Request.class);
            response = (Response) method.invoke(this, request);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }

    private Response handleLOGOUT(Request request){
        connected = false;
        return null;
    }

    private Response handleLOGIN(Request request) {

        try {
            service.login((Angajat) request.getObj(), this);
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).build();
        }
    }

    private Response handleALL(Request request){

        try{

            return new Response.Builder().type(ResponseType.OK).object(service.getAll()).build();

        }
        catch(Exception e){
            e.printStackTrace();
            return new Response.Builder().type(ResponseType.ERROR).build();
        }

    }

    private Response handleFIND(Request request){
        try{
            this.date = (LocalDate) request.getObj();
            return new Response.Builder().type(ResponseType.OK).object(service.getAllDate((LocalDate) request.getObj())).build();
        }
        catch(Exception e){
            e.printStackTrace();
            return new Response.Builder().type(ResponseType.ERROR).build();
        }
    }

    private Response handleSELL(Request request){

        try{
            service.ticketing((Client) request.getObj(), this);
            return new Response.Builder().type(ResponseType.NOTIFICARE).object(service.getAllDate(this.date)).build();
        }
        catch(Exception e){
            e.printStackTrace();
            return new Response.Builder().type(ResponseType.ERROR).build();
        }

    }

    public void sendResponse(Response response){

        try{
            oos.writeObject(response);
            oos.flush();
        }
        catch(Exception ex){

        }

    }

}
