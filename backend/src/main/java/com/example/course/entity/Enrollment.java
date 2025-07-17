package com.example.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="enrollments",
       uniqueConstraints=@UniqueConstraint(columnNames={"user_id","course_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy="uuid2")
    private String id;
    private String userId;
    @Column(name = "course_id")
    private String courseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;
    @Enumerated(EnumType.STRING)
    private Status status; // NOT_STARTED,IN_PROGRESS,COMPLETED
    private Integer progressPercent;
    private Boolean favorite;
    public enum Status { NOT_STARTED, IN_PROGRESS, COMPLETED }
}
