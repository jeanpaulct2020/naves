package com.jeancot.naves.service;

import com.jeancot.naves.dto.NaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface INaveService {

    Page<NaveDTO> findAllPagination(Pageable page) throws Exception;;
    NaveDTO findNaveById(Integer id) throws Exception;;
    NaveDTO save(NaveDTO naveDTO) throws Exception;;
    NaveDTO update(NaveDTO naveDTO, Integer id) throws Exception;
    void delete(Integer id) throws Exception;;

    List<NaveDTO> findByNombreContainsIgnoreCase(String nombre) throws Exception;;

}
