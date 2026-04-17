package com.guilherme.Janus.Domain.Entities;

import com.guilherme.Janus.Domain.enums.Tipo;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notificacao {

    private UUID id;
    private Usuario userId;
    private Tipo tipo;
    private String titulo;
    private String mensagem;
    private boolean lido;
    private LocalDateTime createdAt;

}
