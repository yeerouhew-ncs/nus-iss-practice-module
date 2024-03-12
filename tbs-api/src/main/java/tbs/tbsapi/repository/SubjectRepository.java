package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
}
