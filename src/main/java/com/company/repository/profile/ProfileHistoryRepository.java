package com.company.repository.profile;

import com.company.model.domain.profile.ProfileHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileHistoryRepository extends JpaRepository<ProfileHistoryEntity, Long> {

}
