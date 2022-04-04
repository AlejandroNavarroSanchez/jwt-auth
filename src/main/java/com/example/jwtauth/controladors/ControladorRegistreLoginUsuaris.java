package com.example.jwtauth.controladors;

import com.example.jwtauth.model.entitats.Usuari;
import com.example.jwtauth.model.entitats.UsuariConsultaDTO;
import com.example.jwtauth.model.serveis.ServeiUsuari;
import com.example.jwtauth.seguretat.jwt.JwtProvider;
import com.example.jwtauth.seguretat.jwt.LoginPassword;
import com.example.jwtauth.seguretat.jwt.UsuariJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorRegistreLoginUsuaris {
    private final ServeiUsuari serveiUsuaris;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<UsuariJwt> login(@RequestBody LoginPassword userPassword)
    {
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userPassword.getUsername(),userPassword.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        Usuari usu=(Usuari)auth.getPrincipal();
        String jwtToken = tokenProvider.generateToken(auth);
        UsuariJwt usu2=new UsuariJwt(usu.getUsername(),usu.getAvatar(),usu.getRol(),jwtToken);
        //es retorna userName, Avatar, Rol i Token
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usu2);
    }


    @GetMapping("/login")
    public UsuariConsultaDTO login(@AuthenticationPrincipal Usuari usu){
        UsuariConsultaDTO usu2=new UsuariConsultaDTO(usu.getUsername(),usu.getAvatar(),usu.getRol());
        return usu2;
    }



}