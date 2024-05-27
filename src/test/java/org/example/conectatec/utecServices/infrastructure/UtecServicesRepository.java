package org.example.conectatec.utecServices.infrastructure;


import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.springframework.stereotype.Repository;
@Repository
public interface UtecServicesRepository extends UserBaseRepository<UtecServices> {
}
