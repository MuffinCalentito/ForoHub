package com.challenge.forohub.domain.usuario_profile;

import com.challenge.forohub.domain.profile.Perfil;
import com.challenge.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuario_perfil")
@Entity(name = "Usuario_Perfil")
@Getter
@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(of = "id")

public class Usuario_Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")

    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")

    private Perfil perfil;

}
