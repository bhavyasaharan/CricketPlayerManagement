package com.example.demo.customUserDetails;

import com.example.demo.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = switch (member.getMemberType()) {
            case 1 -> "ROLE_superAdmin";
            case 2 -> "ROLE_admin";
            case 3 -> "ROLE_manager";
            case 4 -> "ROLE_player";
            default -> "ROLE_unknown";
        };
        return List.of(() -> role);
    }


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    // this will just return the username of the member entity
    public String getUsername() {
        return member.getUserName();
    }

    // Add your custom method to get user ID
    public int getUserId() {
        return  member.getMemberId();
    }

    // this will return all the fields of the member entity
    public Member getMember(){
        return member;
    }

    // Standard UserDetails methods (return true for all if not needed)
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}