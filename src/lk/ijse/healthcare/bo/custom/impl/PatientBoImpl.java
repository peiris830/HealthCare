package lk.ijse.healthcare.bo.custom.impl;

import lk.ijse.healthcare.bo.custom.PatientBo;
import lk.ijse.healthcare.dao.DaoFactory;
import lk.ijse.healthcare.dao.custom.DoctorDao;
import lk.ijse.healthcare.dao.custom.PatientDao;
import lk.ijse.healthcare.dto.PatientDto;
import lk.ijse.healthcare.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;

public class PatientBoImpl implements PatientBo {

    PatientDao dao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.PATIENT);
    @Override
    public boolean savePatient(PatientDto dto) throws SQLException, ClassNotFoundException {
        return dao.save(new Patient(dto.getpId(),
                dto.getName(),dto.getAddress(),dto.getContact()));
    }

    @Override
    public ArrayList<PatientDto> getAllPatient() throws SQLException, ClassNotFoundException {
        ArrayList<PatientDto> dtos= new ArrayList<>();
        for (Patient p:dao.getAll()
             ) {
            dtos.add(new PatientDto(p.getpId(),p.getName(),p.getAddress(),p.getContact()));
        }
        return dtos;
    }

    @Override
    public boolean deletePatient(String id) throws SQLException, ClassNotFoundException {
        return dao.delete(id);
    }

    @Override
    public boolean updatePatient(PatientDto t) throws SQLException, ClassNotFoundException {
        return dao.update(new Patient(t.getpId(),
                t.getName(),t.getAddress(),t.getContact()));
    }

    @Override
    public PatientDto getPatient(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
