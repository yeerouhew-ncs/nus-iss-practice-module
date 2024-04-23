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
import tbs.tbsapi.factory.SubjectFactoryImpl;
import tbs.tbsapi.repository.SubjectRepository;
import tbs.tbsapi.vo.response.RegisterUserResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private SubjectFactoryImpl subjectFactory;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void testRegisterUser() {
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

        lenient().when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(subjectFactory.addSubject(any(AddSubjectDto.class))).thenReturn(subject);
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        RegisterUserResponse response = authService.registerUser(addSubjectDto);

        assertEquals("test@example.com", response.getEmail());
        assertEquals(SubjectStatus.ACTIVE, response.getStatus());
        assertEquals("Test User", response.getFullName());
        assertEquals("ADMIN", String.valueOf(response.getSubjectRole()));
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    public void testGetUserByEmail() {

        String email = "test@example.com";
        Subject subject = Subject.builder()
                .email(email)
                .fullName("Test User")
                .subjectRole(SubjectRole.ADMIN)
                .status(SubjectStatus.ACTIVE)
                .password("encodedPassword")
                .build();

        when(subjectRepository.findByEmail(email)).thenReturn(subject);

        RegisterUserResponse response = authService.getUserByEmail(email);

        assertEquals("test@example.com", response.getEmail());
        assertEquals(SubjectStatus.ACTIVE, response.getStatus());
        assertEquals("Test User", response.getFullName());
        assertEquals("ADMIN", String.valueOf(response.getSubjectRole()));
        verify(subjectRepository, times(1)).findByEmail(email);
    }
}
