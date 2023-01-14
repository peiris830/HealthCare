package lk.ijse.healthcare.dao.custom;

import lk.ijse.healthcare.dao.CrudDAO;
import lk.ijse.healthcare.dto.PatientDto;
import lk.ijse.healthcare.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PatientDao extends CrudDAO<Patient,String> {
   }
