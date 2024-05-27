package org.example.conectatec.sUTEC.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.conectatec.sUTEC.domain.SUTEC;
@Repository
public interface SUTECRepository extends JpaRepository<SUTEC,Long> {
}
