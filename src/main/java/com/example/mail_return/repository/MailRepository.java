package com.example.mail_return.repository;

import com.example.mail_return.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<MailEntity, Long> {
    List<MailEntity> findByActiveTrue();
}
