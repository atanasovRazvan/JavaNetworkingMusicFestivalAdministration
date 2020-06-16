package Services;

import Entities.Client;
import Entities.DTOForBuyTable;

import java.util.ArrayList;
import java.util.List;

public interface Observer {

    void update(ArrayList<DTOForBuyTable> cl);

}
