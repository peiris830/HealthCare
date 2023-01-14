package lk.ijse.healthcare.dao;

import lk.ijse.healthcare.dao.custom.impl.DoctorDaoImpl;
import lk.ijse.healthcare.dao.custom.impl.PatientDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;private DaoFactory(){}
    public static DaoFactory getInstance(){
        return daoFactory==null?(daoFactory= new DaoFactory()):daoFactory;
    }
    public enum DaoType{
        PATIENT,DOCTOR
    }
    public <T> T getDao(DaoType type){
        switch (type){
            case DOCTOR:
                return (T) new DoctorDaoImpl();
            case PATIENT:
                return (T) new PatientDaoImpl();
            default:
                return null;
        }
    }
}
