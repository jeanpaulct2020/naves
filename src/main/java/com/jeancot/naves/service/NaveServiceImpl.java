package com.jeancot.naves.service;

import com.jeancot.naves.config.CacheConfig;
import com.jeancot.naves.dto.NaveDTO;
import com.jeancot.naves.exception.ModelNotFoundException;
import com.jeancot.naves.exception.ValueNotValidException;
import com.jeancot.naves.model.Nave;
import com.jeancot.naves.repository.INaveRepository;
import com.jeancot.naves.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NaveServiceImpl implements INaveService{

    @Autowired
    private INaveRepository repo;

    @Autowired
    @Qualifier("naveMapper")
    private ModelMapper mapper;

    @Override
    @Cacheable(value = CacheConfig.CACHE_NAME)
    public Page<NaveDTO> findAllPagination(Pageable pageable) {
        // Parámetros de paginación
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        //para utilizacion del caché
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        //obtener naves de Nave
        Page<Nave> pageNave = repo.findAll(pageRequest);
        //convertir de model a dto para respuesta
        return pageNave.map(nave -> mapper.map(nave, NaveDTO.class));
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_NAME)
    public NaveDTO findNaveById(Integer id) {

        //para utilizacion del caché
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        Nave naveFromDB = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND " + id));
        NaveDTO naveResponse = mapper.map(naveFromDB, NaveDTO.class);
        return naveResponse;
    }

    @Override
    public NaveDTO save(NaveDTO naveDTO) throws Exception {

        //validar tipo
        if(!Util.isValidMedioVisual(naveDTO.getMedioVisual()))
            throw new ValueNotValidException("Medio visual inválido; debe ser SERIE o PELICULA");

        //convertir dto a model para guardar
        Nave naveToSave = mapper.map(naveDTO, Nave.class);

        //guardar en bd
        repo.save(naveToSave);

        //convertir nuevamente
        NaveDTO naveResponse = mapper.map(naveToSave, NaveDTO.class);
        return naveResponse;
    }

    @Override
    public NaveDTO update(NaveDTO nave, Integer id) throws Exception {
        Nave naveFromDB = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND " + id));

        Nave naveToUpdate= mapper.map(nave, Nave.class);
        Nave naveUpdated = repo.save(naveToUpdate);
        NaveDTO naveResponse = mapper.map(naveUpdated, NaveDTO.class);

        return naveResponse;
    }

    @Override
    public void delete(Integer id) throws Exception{
        Nave naveFromDB = repo.findById(id).orElse(null);
        if(naveFromDB == null){
            throw new ModelNotFoundException("ID NOT FOUND" + id);
        }
        repo.delete(naveFromDB);
    }

    @Override
    public List<NaveDTO> findByNombreContainsIgnoreCase(String nombre) throws Exception {
        List<Nave> naveList = repo.findByNombreContainsIgnoreCase(nombre);
        return naveList.stream().map(n -> mapper.map(n, NaveDTO.class)).collect(Collectors.toList());
    }
}
