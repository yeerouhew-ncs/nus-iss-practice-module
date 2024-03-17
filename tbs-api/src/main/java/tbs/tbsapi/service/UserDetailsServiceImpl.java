package tbs.tbsapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.repository.SubjectRepository;

import java.util.Optional;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SubjectRepository subjectRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Subject subject = subjectRepository.findByEmail(email);

        if(subject == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return UserDetailsImpl.build(subject);
    }
}
