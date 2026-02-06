package com.shelter_ms.repository;

import com.shelter_ms.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    Optional<Profile> findByEmail(String email);

}
