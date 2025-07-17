package com.example.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy="uuid2")
    private String id;
    private String title;
    @Column(columnDefinition="text")
    private String description;
    private String cover;
    private Integer durationMinutes;
    @Enumerated(EnumType.STRING)
    private Status status; // DRAFT,PUBLISHED
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public enum Status { DRAFT, PUBLISHED }
}
