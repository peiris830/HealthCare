package lk.ijse.healthcare.bo.custom;

import lk.ijse.healthcare.dto.DoctorDto;
import lk.ijse.healthcare.entity.Doctor;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DoctorBo {
    public boolean saveDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException;
    public ArrayList<DoctorDto> getAllDoctors() throws SQLException, ClassNotFoundException;
    public boolean deleteDoctor(String id) throws SQLException, ClassNotFoundException;
    public boolean updateDoctor(DoctorDto t) throws SQLException, ClassNotFoundException;
    public DoctorDto getDoctor(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<DoctorDto>
    search(String text) throws SQLException, ClassNotFoundException;

}
