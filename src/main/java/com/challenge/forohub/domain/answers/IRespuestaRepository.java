package com.challenge.forohub.domain.answers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRespuestaRepository extends JpaRepository<Respuestas, Long> {

    Page<Respuestas> findAll(Pageable pageable);

}
