package com.letswork.api.app.advertisement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.letswork.api.app.category.domain.CategoryEntity;
import com.letswork.api.app.job_application.domain.JobApplicationEntity;
import com.letswork.api.app.shared.BaseEntity;
import com.letswork.api.app.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "advertisements")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementEntity extends BaseEntity {

    @Column(length = 40, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne
    private UserEntity user;

    @JsonIgnore
    @ManyToOne
    private CategoryEntity category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "advertisement", cascade = CascadeType.REMOVE)
    private List<JobApplicationEntity> jobApplications;
}
