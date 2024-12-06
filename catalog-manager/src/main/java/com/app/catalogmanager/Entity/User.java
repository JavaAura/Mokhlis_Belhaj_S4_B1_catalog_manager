package com.app.catalogmanager.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true)
	    @NotBlank(message = "Login is required")
	    private String login;

        @NotBlank(message = "Password is required")
	    private String password;

	    private Boolean active;

	    @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(
	        name = "user_roles",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "role_id")
	    )
	    private List<Role> roles;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", login='" + login + '\'' +
                    ", password='" + password + '\'' +
                    ", active=" + active +
                    ", roles=" + roles.toString() +
                    '}';
        }

}
