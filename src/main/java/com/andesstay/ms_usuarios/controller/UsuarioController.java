package com.andesstay.ms_usuarios.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getMiPerfil(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(Map.of(
                "mensaje", "Perfil recuperado con éxito",
                "sub", jwt.getSubject(),
                "name", jwt.getClaimAsString("name") != null ? jwt.getClaimAsString("name") : "Usuario Autenticado"
        ));
    }

    @GetMapping("/protegido")
    @PreAuthorize("hasAuthority('SCOPE_access_as_user') or hasAuthority('SCOPE_OT.Create')")
    public ResponseEntity<Map<String, String>> getRecursoProtegido() {
        return ResponseEntity.ok(Map.of(
                "estado", "Acceso Concedido",
                "detalle", "¡Token JWT verificado y Scope autorizado correctamente por Spring Boot!"
        ));
    }
}