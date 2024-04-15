package tbs.tbsapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.domain.enums.SubjectRole;
import tbs.tbsapi.domain.enums.SubjectStatus;
import tbs.tbsapi.dto.AddSubjectDto;
import tbs.tbsapi.repository.SubjectRepository;
import tbs.tbsapi.vo.response.RegisterUserResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void testRegisterUser() {
        // Given
        AddSubjectDto addSubjectDto = new AddSubjectDto();
        addSubjectDto.setEmail("test@example.com");
        addSubjectDto.setFullName("Test User");
        addSubjectDto.setPassword("password");
        addSubjectDto.setSubjectRole(SubjectRole.ADMIN);

        Subject subject = Subject.builder()
                .email(addSubjectDto.getEmail())
                .fullName(addSubjectDto.getFullName())
                .subjectRole(addSubjectDto.getSubjectRole())
                .status(SubjectStatus.ACTIVE)
                .password("encodedPassword")
                .build();

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        // When
        RegisterUserResponse response = authService.registerUser(addSubjectDto);

        // Then
        assertEquals("test@example.com", response.getEmail());
        assertEquals(SubjectStatus.ACTIVE, response.getStatus());
        assertEquals("Test User", response.getFullName());
        assertEquals("ADMIN", String.valueOf(response.getSubjectRole()));
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    public void testGetUserByEmail() {
        // Given
        String email = "test@example.com";
        Subject subject = Subject.builder()
                .email(email)
                .fullName("Test User")
                .subjectRole(SubjectRole.ADMIN)
                .status(SubjectStatus.ACTIVE)
                .password("encodedPassword")
                .build();

        when(subjectRepository.findByEmail(email)).thenReturn(subject);

        // When
        RegisterUserResponse response = authService.getUserByEmail(email);

        // Then
        assertEquals("test@example.com", response.getEmail());
        assertEquals(SubjectStatus.ACTIVE, response.getStatus());
        assertEquals("Test User", response.getFullName());
        assertEquals("ADMIN", String.valueOf(response.getSubjectRole()));
        verify(subjectRepository, times(1)).findByEmail(email);
    }
}
