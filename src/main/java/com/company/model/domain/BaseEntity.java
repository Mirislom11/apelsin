package com.company.model.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date_time", updatable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate = LocalDateTime.now(); // local date modified date
    @Column(name = "version")
    private long version = 1L; // long version

}
