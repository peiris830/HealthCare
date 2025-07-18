package lk.ijse.healthcare.dao.custom;


import lk.ijse.healthcare.dao.CrudDAO;
import lk.ijse.healthcare.dto.DoctorDto;
import lk.ijse.healthcare.entity.Doctor;


import java.sql.SQLException;
import java.util.ArrayList;

public interface DoctorDao extends CrudDAO<Doctor,String> {
    public ArrayList<Doctor> search(String text) throws SQLException, ClassNotFoundException;
}
