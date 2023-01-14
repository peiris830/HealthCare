package lk.ijse.healthcare.bo.custom.impl;

import lk.ijse.healthcare.bo.custom.DoctorBo;
import lk.ijse.healthcare.dao.DaoFactory;
import lk.ijse.healthcare.dao.custom.DoctorDao;
import lk.ijse.healthcare.dto.DoctorDto;
import lk.ijse.healthcare.entity.Doctor;

import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorBoImpl implements DoctorBo {
    DoctorDao dao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.DOCTOR);
    @Override
    public boolean saveDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException {
        return dao.save(new Doctor(dto.getdId(),dto.getName()
                ,dto.getAddress(),dto.getContact()));
    }

    @Override
    public ArrayList<DoctorDto> getAllDoctors() throws SQLException, ClassNotFoundException {
       ArrayList<DoctorDto> dtos= new ArrayList<>();
        for (Doctor d:dao.getAll()
             ) {
            dtos.add(new DoctorDto(d.getdId(),d.getName(),d.getAddress(),d.getContact()));
        }
        return dtos;
    }

    @Override
    public boolean deleteDoctor(String id) throws SQLException, ClassNotFoundException {
        return dao.delete(id);
    }

    @Override
    public boolean updateDoctor(DoctorDto t) throws SQLException, ClassNotFoundException {
        return dao.update(new Doctor(t.getdId(),t.getName()
                ,t.getAddress(),t.getContact()));
    }

    @Override
    public DoctorDto getDoctor(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<DoctorDto> search(String text) throws SQLException, ClassNotFoundException {
        ArrayList<DoctorDto> dtos= new ArrayList<>();
        for (Doctor d:dao.search(text)
        ) {
            dtos.add(new DoctorDto(d.getdId(),d.getName(),d.getAddress(),d.getContact()));
        }
        return dtos;
    }
}
