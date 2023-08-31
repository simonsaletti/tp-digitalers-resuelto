package com.digitalers.spring.boot.mappers;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.digitalers.spring.boot.dto.UsuarioResponseDTO;
import com.digitalers.spring.boot.entidades.Usuario;

public class UsuarioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioResponseDTO fromUsuarioToUsuarioResponseDTO(Usuario usuario) {
        return this.modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> fromUsuariosToUsuarioResponseDTOs(List<Usuario> usuarios) {
        return usuarios.stream().map(usuario -> this.fromUsuarioToUsuarioResponseDTO(usuario)).collect(Collectors.toList());
    }
}
