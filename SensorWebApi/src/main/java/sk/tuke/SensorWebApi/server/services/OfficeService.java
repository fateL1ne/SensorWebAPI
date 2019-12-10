package sk.tuke.SensorWebApi.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.Office;
import sk.tuke.SensorWebApi.server.repositories.OfficeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    public List<String> getOfficeNames() {
        List<String> officeNames = new ArrayList<>();
        List<Office> offices = officeRepository.findAll();
        offices.forEach((office -> officeNames.add(office.getOfficeName())));

        return officeNames;
    }
}
