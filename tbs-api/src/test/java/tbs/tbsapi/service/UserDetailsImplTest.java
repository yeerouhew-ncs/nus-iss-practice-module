package tbs.tbsapi.service;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.domain.enums.SubjectRole;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsImplTest {

    @Test
    void build_ReturnsUserDetailsImpl() {
        // Given
        Subject subject = new Subject();
        subject.setSubjectId(1);
        subject.setEmail("test@example.com");
        subject.setPassword("password");
        subject.setSubjectRole(SubjectRole.ADMIN);

        // When
        UserDetailsImpl userDetails = UserDetailsImpl.build(subject);

        // Then
        assertEquals(subject.getSubjectId(), userDetails.getId());
        assertEquals(subject.getEmail(), userDetails.getUsername());
        assertEquals(subject.getPassword(), userDetails.getPassword());

        Collection<? extends SimpleGrantedAuthority> authorities = (Collection<? extends SimpleGrantedAuthority>) userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ADMIN", authorities.iterator().next().getAuthority());
    }

    @Test
    void equals_WhenObjectsAreEqual_ReturnsTrue() {
        // Given
        UserDetailsImpl user1 = new UserDetailsImpl(1, "test@example.com", "password", List.of(new SimpleGrantedAuthority("ADMIN")));
        UserDetailsImpl user2 = new UserDetailsImpl(1, "test@example.com", "password", List.of(new SimpleGrantedAuthority("ADMIN")));

        // Then
        assertEquals(user1, user2);
    }

    @Test
    void equals_WhenObjectsAreNotEqual_ReturnsFalse() {
        // Given
        UserDetailsImpl user1 = new UserDetailsImpl(1, "test@example.com", "password", List.of(new SimpleGrantedAuthority("ADMIN")));
        UserDetailsImpl user2 = new UserDetailsImpl(2, "test@example.com", "password", List.of(new SimpleGrantedAuthority("ADMIN")));

        // Then
        assertEquals(false, user1.equals(user2));
    }
}
