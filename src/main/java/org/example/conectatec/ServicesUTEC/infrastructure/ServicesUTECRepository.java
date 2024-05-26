package org.example.conectatec.servicesUTEC.infrastructure;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.conectatec.servicesUTEC.domain.ServicesUTEC;
@Repository
public interface ServicesUTECRepository extends JpaRepository<ServicesUTEC,Long> {
}
