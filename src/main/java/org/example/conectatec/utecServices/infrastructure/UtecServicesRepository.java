package org.example.conectatec.utecServices.infrastructure;


import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.conectatec.utecServices.domain.UtecServices;
@Repository
public interface UtecServicesRepository extends UserBaseRepository<UtecServices>{

}
