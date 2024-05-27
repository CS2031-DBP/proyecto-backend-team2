package org.example.conectatec.UTECServices.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.conectatec.UTECServices.domain.UTECServices;
@Repository
public interface ServicesUTECRepository extends JpaRepository<UTECServices,Long> {
}
