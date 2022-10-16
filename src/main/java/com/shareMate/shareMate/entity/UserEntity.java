package com.shareMate.shareMate.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

//@Data
//@AllArgsConstructor
@Builder
@Entity(name="user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false,unique = true)
    private int user_id;
//    @Column(nullable = false)
    private String email;
//    @Column(nullable = false)
    private String pwd;
//    @Column(nullable = false)
    private String name;
//    @Column(nullable = false)
    private String major;
//    @Column(nullable = false)
    private String grade;
//    @Column(nullable = false)
    private Date birth;
//    @Column(nullable = false)
    private String kakao_id;
//    @Column(nullable = false)
    private String profile_photo;
//    @Column(nullable = false)
    private Date create_at;
    @OneToOne
    @JoinColumn(name="user_id")
    private FavorEntity favor;




    public String getPwd(){
        return this.pwd;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
