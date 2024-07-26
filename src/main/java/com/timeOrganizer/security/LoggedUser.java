package com.timeOrganizer.security;

import com.timeOrganizer.helper.AvailableLocales;
import com.timeOrganizer.model.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class LoggedUser implements UserDetails {
    private long id;
    private String email;
    private String password;
    private UserRole role;
    private boolean isStayLoggedIn;
    private boolean has2FA;
    private String name;
    private String surname;
    private AvailableLocales currentLocale;
    private ZoneId timezone;
    private User reference;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return this.email;
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
