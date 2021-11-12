package com.digitalers.spring.boot.entidades.service;

import java.util.List;

public interface CrudService<RequestDTO, ResponseDTO> {

    public List<ResponseDTO> listarTodos();

    public ResponseDTO obtener(Long id);

    public ResponseDTO insertar(RequestDTO dto) throws Exception;

    public void borrar(Long id);

}
