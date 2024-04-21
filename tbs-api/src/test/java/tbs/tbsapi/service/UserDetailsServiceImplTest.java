package tbs.tbsapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.domain.enums.SubjectRole;
import tbs.tbsapi.repository.SubjectRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        String email = "test@example.com";
        Subject subject = new Subject();
        subject.setEmail(email);
        subject.setPassword("password123");
        subject.setSubjectRole(SubjectRole.ADMIN);

        when(subjectRepository.findByEmail(email)).thenReturn(subject);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        assertEquals(subject.getPassword(), userDetails.getPassword());
        verify(subjectRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {

        String email = "nonexistent@example.com";
        when(subjectRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
        verify(subjectRepository, times(1)).findByEmail(email);
    }

    @Test
    void getSubjectDetails_UserFound_ReturnsSubject() {

        String email = "test@example.com";
        Subject subject = new Subject();
        subject.setEmail(email);
        subject.setPassword("password123");

        when(subjectRepository.findByEmail(email)).thenReturn(subject);

        Subject result = userDetailsService.getSubjectDetails(email);

        assertEquals(email, result.getEmail());
        assertEquals(subject.getPassword(), result.getPassword());
        verify(subjectRepository, times(1)).findByEmail(email);
    }

    @Test
    void getSubjectDetails_UserNotFound_ThrowsException() {

        String email = "nonexistent@example.com";
        when(subjectRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.getSubjectDetails(email));
        verify(subjectRepository, times(1)).findByEmail(email);
    }
}
