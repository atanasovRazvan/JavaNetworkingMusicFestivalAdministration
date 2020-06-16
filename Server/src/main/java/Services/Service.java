package Services;

import Entities.Client;
import Entities.DTOForBuyTable;
import Entities.DTOForTable;
import Entities.Spectacol;
import Repositories.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {

    private AngajatRepository angajatRepository;
    private ArtistRepository artistRepository;
    private ClientRepository clientRepository;
    private ScenaRepository scenaRepository;
    private SpectacolRepository spectacolRepository;
    private Map<String, Observer> logusers;

    public Service(AngajatRepository angajatRepository, ArtistRepository artistRepository, ClientRepository clientRepository, ScenaRepository scenaRepository, SpectacolRepository spectacolRepository) {

        this.angajatRepository=angajatRepository;
        this.artistRepository=artistRepository;
        this.clientRepository=clientRepository;
        this.scenaRepository=scenaRepository;
        this.spectacolRepository=spectacolRepository;
        logusers = new HashMap<>();

    }

    @Override
    public void login(Entities.Angajat angajat, Observer observer) throws Exception {

        if(angajatRepository.findOne(angajat.getId()).getPassword().equals(angajat.getPassword())){
            logusers.put(angajat.getId().toString(), observer);
        }
        else{
            throw new Exception("Coco s-a suparaaaat");
        }

    }

    @Override
    public List<Entities.DTOForTable> getAll() {

        List<Spectacol> data = new ArrayList<>();
        data = spectacolRepository.getAll();
        List<DTOForTable> Data = new ArrayList<>();
        for(Spectacol s : data){
            DTOForTable dto = new DTOForTable(artistRepository.findOne(s.getId_artist()).getNume(), s.getDate().toString(), scenaRepository.findOne(s.getId_scena()).getDenumire(),
                    String.valueOf(s.getLocuri_libere()), String.valueOf(s.getLocuri_ocupate()));
            Data.add(dto);
        }
        return Data;
    }

    @Override
    public List<Entities.DTOForBuyTable> getAllDate(LocalDate ld) {

        List<DTOForBuyTable> spectacole = new ArrayList<>();
        for(Spectacol sp : spectacolRepository.getAll()){
            String ldt = sp.getDate().toString();
            String[] parts = ldt.split("T");
            String date = parts[0];
            String time = parts[1];
            if(!ld.toString().equals(date)) continue;
            DTOForBuyTable dto = new DTOForBuyTable(artistRepository.findOne(sp.getId_artist()).getNume(),
                    scenaRepository.findOne(sp.getId_scena()).getDenumire(), time, sp.getLocuri_libere().toString(),
                    sp.getId_artist(), sp.getId_scena());
            spectacole.add(dto);
        }
        return spectacole;

    }

    @Override
    public void logout(String user) {
        logusers.remove(user);
    }

    @Override
    public void ticketing(Entities.Client cl, Observer sender) {
        clientRepository.add(cl);
        Spectacol sp = spectacolRepository.findOne(cl.getId_artist(), cl.getId_scena());
        sp.setLocuri_libere(sp.getLocuri_libere()-cl.getNr_Locuri());
        sp.setLocuri_ocupate(sp.getLocuri_ocupate()+cl.getNr_Locuri());
        spectacolRepository.update(sp);
        System.out.println(logusers.size());
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (Observer observer : logusers.values()) {
            if (sender != observer) {
                System.out.println("ceva");
                executorService.execute(() -> observer.update(null));
            }
        }
        executorService.shutdown();
    }
}
