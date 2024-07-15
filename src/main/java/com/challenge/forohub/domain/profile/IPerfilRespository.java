package com.challenge.forohub.domain.profile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IPerfilRespository extends JpaRepository<Perfil,Long> {

    Page<Perfil> findAll(Pageable pageable);
}
