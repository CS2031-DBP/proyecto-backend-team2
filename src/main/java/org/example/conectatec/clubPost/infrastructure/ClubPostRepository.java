package org.example.conectatec.clubPost.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubPost.domain.ClubPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubPostRepository extends JpaRepository<ClubPost, Long> {
    ClubPost findByCareer(Career career);
}
