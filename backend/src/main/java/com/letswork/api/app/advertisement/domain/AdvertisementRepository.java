package com.letswork.api.app.advertisement.domain;

import com.letswork.api.app.user.domain.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface AdvertisementRepository extends Repository<AdvertisementEntity, Long> {

    void save(AdvertisementEntity advertisement);

    List<AdvertisementEntity> findAll();

    List<AdvertisementEntity> findAllByCategoryName(String categoryName);

    Optional<AdvertisementEntity> findById(Long advertisementId);

    void deleteAllByUserEmail(String userEmail);
}
