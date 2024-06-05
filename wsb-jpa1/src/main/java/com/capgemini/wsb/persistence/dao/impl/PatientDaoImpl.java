package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.TreatmentType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {


    @Override
    public List<PatientEntity> findByDoctor(String doctorFirstName, String doctorLastName) { // TODO - napisac query
        return entityManager.createQuery( "SELECT p FROM PatientEntity p " +
                                             "WHERE EXISTS (SELECT v FROM VisitEntity v " +
                                                           "JOIN v.doctor d " +
                                                           "WHERE LOWER(d.firstName) LIKE LOWER(:doctorFirstName) AND LOWER(d.lastName) LIKE LOWER(:doctorLastName) " +
                                                           "AND v.patient = p)", PatientEntity.class)
                .setParameter("doctorFirstName", doctorFirstName)
                .setParameter("doctorLastName", doctorLastName)
                .getResultList();
        //return new ArrayList<>();
    }

    @Override
    public List<PatientEntity> findPatientsHavingTreatmentType(TreatmentType treatmentType) { // TODO - napisac query
        return entityManager.createQuery( "SELECT p FROM PatientEntity p " +
                                             "WHERE EXISTS (SELECT v FROM VisitEntity v " +
                                                           "JOIN v.medicalTreatments t " +
                                                           "WHERE t.type = :treatmentType " +
                                                           "AND v.patient = p)", PatientEntity.class)
                .setParameter("treatmentType", treatmentType)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsSharingSameLocationWithDoc(String doctorFirstName, String doctorLastName) { // TODO - napisac query
        return entityManager.createQuery( "SELECT p FROM PatientEntity p " +
                                             "JOIN p.addresses pa " +
                                             "WHERE EXISTS (SELECT d FROM DoctorEntity d " +
                                                           "JOIN d.addresses da " +
                                                           "WHERE LOWER(d.firstName) LIKE LOWER(:doctorFirstName) AND LOWER(d.lastName) LIKE LOWER(:doctorLastName) " +
                                                           "AND da = pa)", PatientEntity.class)
                .setParameter("doctorFirstName", doctorFirstName)
                .setParameter("doctorLastName", doctorLastName)
                .getResultList();
        //return new ArrayList<>();
    }

    @Override
    public List<PatientEntity> findPatientsWithoutLocation() { // TODO - napisac query
        return entityManager.createQuery( "SELECT p FROM PatientEntity p " +
                                             "LEFT JOIN p.addresses pa " +
                                             "WHERE pa IS NULL ", PatientEntity.class)
                .getResultList();
        //return null;
    }
}
