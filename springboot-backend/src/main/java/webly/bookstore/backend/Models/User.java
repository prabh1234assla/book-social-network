package webly.bookstore.backend.Models;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import webly.bookstore.backend.Models.Utils.BaseEntity;
import webly.bookstore.backend.Models.Utils.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
public class User extends BaseEntity implements UserDetails {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "username", nullable = false)
    @NotNull(message = "Username must be specified.")
    private String username;

    @Column(name = "email", nullable = false, length = 40)
    @NotNull(message = "Email must be specified.")
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Password must be specified.")
    private String password;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private Set<Course> coursesTaught;

    @Column(name = "semester", nullable = false)
    @NotNull(message = "Semester must be specified.")
    private Integer semester;

    @Column(name = "isStudentEnrolled", nullable = false)
    @NotNull(message = "if student Enrolled must be specified.")
    private boolean isStudentEnrolled;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("student")
    private Set<Fee> fees;

    @OneToMany(mappedBy = "studentEnrolled", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("studentEnrolled")
    private Set<StudentEnrollment> studentEnrollments;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("candidate")
    private Set<Marks> marks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
