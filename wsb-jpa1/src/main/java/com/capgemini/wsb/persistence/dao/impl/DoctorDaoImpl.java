package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.DoctorDao;
import com.capgemini.wsb.persistence.entity.DoctorEntity;
import com.capgemini.wsb.persistence.enums.Specialization;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorDaoImpl extends AbstractDao<DoctorEntity, Long> implements DoctorDao {
    @Override
    public List<DoctorEntity> findBySpecialization(Specialization specialization) { // TODO - napisac query
        return entityManager.createQuery("SELECT d FROM DoctorEntity d WHERE d.specialization = :specialization", DoctorEntity.class)
                .setParameter("specialization", specialization)
                .getResultList();
        //return new ArrayList<>();
    }

    @Override
    public long countNumOfVisitsWithPatient(String doctorFirstName, String doctorLastName, String patientFirstName, String patientLastName) { // TODO - napisac query
        return (long) entityManager.createQuery("SELECT COUNT(v) FROM VisitEntity v WHERE v.doctor.id IN " +
                        "(SELECT d.id FROM DoctorEntity d WHERE LOWER(d.firstName) LIKE LOWER(:doctorFirstName) AND LOWER(d.lastName) LIKE LOWER(:doctorLastName)) AND v.patient.id IN " +
                        "(SELECT p.id FROM PatientEntity p WHERE LOWER(p.firstName) LIKE LOWER(:patientFirstName) AND LOWER(p.lastName) LIKE LOWER(:patientLastName))", Long.class)
                .setParameter("doctorFirstName", doctorFirstName)
                .setParameter("doctorLastName", doctorLastName)
                .setParameter("patientFirstName", patientFirstName)
                .setParameter("patientLastName", patientLastName)
                .getSingleResult();
        //return (Long) 3L;
    }
}
