package com.tinyurl.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * This is an entity class of URL table
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "url")
public class TinyURL {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private Date expiryDate;
}
