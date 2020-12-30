package com.wjiec.springaio.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @CreatedDate
    @Column(name = "created_at", insertable = false)
    private LocalDateTime createdAt;

}
