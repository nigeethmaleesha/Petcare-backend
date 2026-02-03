package com.shelter_ms.repository;

import com.shelter_ms.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    Profile findByEmail(String email);
}
