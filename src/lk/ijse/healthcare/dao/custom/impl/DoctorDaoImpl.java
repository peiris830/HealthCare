package lk.ijse.healthcare.dao.custom.impl;


import lk.ijse.healthcare.dao.CrudUtil;
import lk.ijse.healthcare.dao.custom.DoctorDao;
import lk.ijse.healthcare.db.DatabaseConnection;
import lk.ijse.healthcare.dto.DoctorDto;
import lk.ijse.healthcare.entity.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorDaoImpl implements DoctorDao {
    @Override
    public boolean save(Doctor d) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO doctor VALUES (?,?,?,?)",
                d.getdId(),d.getName(),d.getAddress(),d.getContact());
    }

    @Override
    public ArrayList<Doctor> getAll() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM doctor");
        ArrayList<Doctor> doctors = new ArrayList();
        while (set.next()) {
            doctors.add(new Doctor(
                    set.getString(1), set.getString(2),
                    set.getString(3), set.getString(4)));
        }
        return doctors;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM doctor WHERE dId=?",id);
    }

    @Override
    public boolean update(Doctor d) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE doctor SET name=?," +
                        " address=?, contact=? WHERE dId=?",
                d.getName(),d.getAddress(),d.getContact(),d.getdId());
    }

    @Override
    public Doctor get(String ID) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Doctor> search(String text) throws SQLException, ClassNotFoundException {
        String tempText="%"+text+"%";
        ResultSet set =
          CrudUtil.execute("SELECT * FROM doctor WHERE name LIKE ? || address LIKE ?",
                        tempText,tempText);
        ArrayList<Doctor> doctors = new ArrayList();
        while (set.next()) {
            doctors.add(new Doctor(
                    set.getString(1), set.getString(2),
                    set.getString(3), set.getString(4)));
        }
        return doctors;
    }
}
