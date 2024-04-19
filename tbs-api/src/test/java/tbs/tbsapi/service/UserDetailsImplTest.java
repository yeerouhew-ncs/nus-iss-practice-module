package tbs.tbsapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tbs.tbsapi.domain.Subject;
import tbs.tbsapi.domain.enums.SubjectRole;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {
    @Test
    void testConstructorAndMethods() {
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetailsImpl userDetails = new UserDetailsImpl(1, "test@example.com", "password", authorities);

        assertEquals(1, userDetails.getId());
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("test@example.com", userDetails.getEmail());
        assertEquals("password", userDetails.getPassword());
        assertEquals(authorities, userDetails.getAuthorities());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void build_ReturnsUserDetailsImpl() {

        Subject subject = new Subject();
        subject.setSubjectId(1);
        subject.setEmail("test@example.com");
        subject.setPassword("password");
        subject.setSubjectRole(SubjectRole.ADMIN);

        UserDetailsImpl userDetails = UserDetailsImpl.build(subject);

        assertEquals(subject.getSubjectId(), userDetails.getId());
        assertEquals(subject.getEmail(), userDetails.getUsername());
        assertEquals(subject.getPassword(), userDetails.getPassword());

        Collection<? extends SimpleGrantedAuthority> authorities = (Collection<? extends SimpleGrantedAuthority>) userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ADMIN", authorities.iterator().next().getAuthority());
    }

    @Test
    void testEquals() {
        UserDetailsImpl userDetails1 = new UserDetailsImpl(1, "test@example.com", "password", Collections.emptyList());
        UserDetailsImpl userDetails2 = new UserDetailsImpl(1, "test@example.com", "password", Collections.emptyList());
        UserDetailsImpl userDetails3 = new UserDetailsImpl(2, "test2@example.com", "password2", Collections.emptyList());
        UserDetailsImpl userDetails4 = new UserDetailsImpl(1, "test@example.com", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        assertTrue(userDetails1.equals(userDetails2)); // Same ID and all other fields are the same
        assertFalse(userDetails1.equals(userDetails3)); // Different ID
        assertFalse(userDetails1.equals(null)); // Comparison with null
        assertFalse(userDetails1.equals("string")); // Comparison with a different type
        assertTrue(userDetails1.equals(userDetails1)); // Reflexive property
        assertTrue(userDetails1.equals(userDetails4)); // Different authorities but all other fields are the same
    }
}
