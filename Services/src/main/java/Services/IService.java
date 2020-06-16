package Services;

import Entities.Angajat;
import Entities.Client;
import Entities.DTOForBuyTable;
import Entities.DTOForTable;

import java.time.LocalDate;
import java.util.List;

public interface IService {

    void login(Angajat angajat, Observer observer) throws Exception;
    List<DTOForTable> getAll() throws Exception;
    List<DTOForBuyTable> getAllDate(LocalDate ldt) throws Exception;
    void ticketing(Client cl, Observer observer) throws Exception;
    void logout(String user);

}
