package com.sportyshoes.modules.users.entity;

import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.users.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@NamedQueries(value={
        @NamedQuery(name = "query_get_all_user",
                query = "Select u From users u"),
        @NamedQuery(name = "query_get_user_by_email",
                query = "Select u From users u Where u.email = :login"),

})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    // mappedBy makes User the owner of the relationship
    @OneToMany(mappedBy = "user")
    private Set<Purchase> purchases;


    public UserDto toDto() {
        UserDto userDto = UserDto.builder().
                id(this.id).
                name(this.name).
                email(this.email).
                password(this.password).
                userType(this.userType).
                build();

        return userDto;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
