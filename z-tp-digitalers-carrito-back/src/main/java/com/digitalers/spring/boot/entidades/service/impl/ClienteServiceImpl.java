package com.digitalers.spring.boot.entidades.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digitalers.spring.boot.entidades.Usuario;
import com.digitalers.spring.boot.entidades.repository.ClienteRepository;
import com.digitalers.spring.boot.entidades.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository dao;

    @Autowired
    public ClienteServiceImpl(ClienteRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> registros = this.dao.findAll();
        return registros;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtener(Long id) {
        Usuario cliente = this.dao.findById(id).orElseThrow();
        return cliente;
    }

}
