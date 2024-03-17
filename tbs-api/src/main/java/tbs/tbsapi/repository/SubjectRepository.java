package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.domain.Subject;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

    Subject findByEmail(String email);
}
