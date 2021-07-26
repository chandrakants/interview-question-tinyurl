package com.tinyurl.app.repository;

import com.tinyurl.app.model.TinyURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TinyURLRepository extends JpaRepository<TinyURL, Long> {
}
