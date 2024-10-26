package com.jeancot.naves.controller;

import com.jeancot.naves.dto.NaveDTO;
import com.jeancot.naves.service.INaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nave")
@Tag(name = "Nave recurso")
public class NaveController {

    @Autowired
    private INaveService service;

    @Operation(summary = "Get all naves")
    @GetMapping("/pagination")
    public ResponseEntity<Page<NaveDTO>> readAll(
            @RequestParam(name = "page", defaultValue = "0" ) int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(service.findAllPagination(pageRequest), HttpStatus.OK);
    }

    @Operation(summary = "Get a category given a nave id")
    @GetMapping("/{id}")
    public ResponseEntity<NaveDTO> readById(@PathVariable("id") Integer id) throws Exception {
        return new ResponseEntity<>(service.findNaveById(id), HttpStatus.OK);
    }
    @Operation(summary = "Save in DB a nave given a nave from boody ")
    @PostMapping
    public ResponseEntity<NaveDTO> create(@Valid @RequestBody NaveDTO nave) throws Exception {
        return new ResponseEntity<>(service.save(nave), HttpStatus.CREATED);
    }
    @Operation(summary = "Update in DB a nave given a nave from body ")
    @PutMapping("/{id}")
    public ResponseEntity<NaveDTO> update(@Valid @RequestBody NaveDTO nave, @PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(service.update(nave, id), HttpStatus.OK);
    }

    @Operation(summary = "Delete in DB a nave given a nave Id ")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get a nave by nave name")
    //query personalizada
    @GetMapping("/find-nombre")
    public ResponseEntity<List<NaveDTO>> findByNombre(@RequestParam("nombre") String nombre) throws Exception {
        List<NaveDTO> categoriesByName = service.findByNombreContainsIgnoreCase(nombre);
        return new ResponseEntity<>(categoriesByName, HttpStatus.OK);
    }
}
