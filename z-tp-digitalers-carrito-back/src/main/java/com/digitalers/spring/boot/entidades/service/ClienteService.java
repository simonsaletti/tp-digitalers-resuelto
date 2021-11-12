package com.digitalers.spring.boot.entidades.service;

import java.util.List;
import com.digitalers.spring.boot.entidades.Usuario;

public interface ClienteService {

    public List<Usuario> listarTodos();

    public Usuario obtener(Long id);

}
