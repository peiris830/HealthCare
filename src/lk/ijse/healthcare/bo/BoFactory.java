package lk.ijse.healthcare.bo;

import lk.ijse.healthcare.bo.custom.impl.DoctorBoImpl;
import lk.ijse.healthcare.bo.custom.impl.PatientBoImpl;
import lk.ijse.healthcare.dao.custom.impl.DoctorDaoImpl;
import lk.ijse.healthcare.dao.custom.impl.PatientDaoImpl;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){}
    public static BoFactory getInstance(){
        return boFactory==null?(boFactory= new BoFactory()):boFactory;
    }
    public enum BoType{PATIENT,DOCTOR}
    public <T> T getBo(BoType type){
        switch (type){
            case DOCTOR:
                return (T) new DoctorBoImpl();
            case PATIENT:
                return (T) new PatientBoImpl();
            default:
                return null;
        }
    }
}
