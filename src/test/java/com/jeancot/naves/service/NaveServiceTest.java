package com.jeancot.naves.service;

import com.jeancot.naves.dto.NaveDTO;
import com.jeancot.naves.model.MedioVisual;
import com.jeancot.naves.model.Nave;
import com.jeancot.naves.repository.INaveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NaveServiceTest {

    @InjectMocks
    private NaveServiceImpl service;

    @Mock
    private INaveRepository repo;

    @Mock
    private ModelMapper mapper;

    private Nave NAVE_1;
    private Nave NAVE_2;
    private Nave NAVE_3;
    //private List<Category> categories;
    private NaveDTO NAVEDTO_1;
    private NaveDTO NAVEDTO_2;
    private NaveDTO NAVEDTO_3;
    //metodo que inicia los valores para realizar pruebas

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        //inicianlizando objetos de prueba
        NAVE_1 = new Nave(1, "USS Enterprise", "tipo 1", "desc 1", "caracte 1", MedioVisual.SERIE, "Star trek");
        NAVE_2 = new Nave(2, "X-Wing Starfighter", "tipo 2", "desc 2", "caracte 1", MedioVisual.SERIE, "Star trek");
        NAVE_3 = new Nave(3, "Millennium Falcon", "tipo 3", "desc 2", "caracte 1", MedioVisual.PELICULA, "Star Wars");

        NAVEDTO_1 = new NaveDTO(1, "USS Enterprise", "tipo 1", "desc 1", "caracte 1", MedioVisual.SERIE.name(), "Star trek");
        NAVEDTO_2 = new NaveDTO(2, "X-Wing Starfighter", "tipo 2", "desc 2", "caracte 1", MedioVisual.SERIE.name(), "Star trek");
        NAVEDTO_3 = new NaveDTO(3, "Millennium Falcon", "tipo 3", "desc 2", "caracte 1", MedioVisual.PELICULA.name(), "Star Wars");
    }


    @Test
    public void readAllTest() throws Exception{
        //datos para simular naves
        List<Nave> naveList = Arrays.asList(NAVE_1, NAVE_2, NAVE_3);

        //datos de paginacion
        Pageable pageRequest = PageRequest.of(0 , 3);
        Page<Nave> navePage = new PageImpl<>(naveList, pageRequest, naveList.size());

        // Configuramos el mock para que naveRepository.findAll devuelva la página simulada
        Mockito.when(repo.findAll(pageRequest)).thenReturn(navePage);

        //conversion de nave a naveDTO
        Mockito.when(mapper.map(NAVE_1,NaveDTO.class)).thenReturn(NAVEDTO_1);
        Mockito.when(mapper.map(NAVE_2,NaveDTO.class)).thenReturn(NAVEDTO_2);
        Mockito.when(mapper.map(NAVE_3,NaveDTO.class)).thenReturn(NAVEDTO_3);

        // Llamamos al método que queremos probar
        Page<NaveDTO> result = service.findAllPagination(pageRequest);

        // Verificamos los resultados
        Assertions.assertEquals(3, result.getTotalElements()); // Total de elementos esperados
        Assertions.assertEquals(1, result.getTotalPages());    // Total de páginas esperadas
        Assertions.assertEquals("USS Enterprise", result.getContent().get(0).getNombre()); // Verificar el nombre de la primera nave
        Assertions.assertEquals("tipo 1", result.getContent().get(0).getTipoNave()); // Verificar el tipo de la primera nave
    }

    @Test
    public void readByIdTest() throws Exception{
        int ID = 1;

        // Configuramos el mock para que naveRepository.findById devuelva una Nave
        Mockito.when(repo.findById(ID)).thenReturn(Optional.of(NAVE_1));
        Mockito.when(mapper.map(NAVE_1,NaveDTO.class)).thenReturn(NAVEDTO_1);

        NaveDTO naveDto = service.findNaveById(ID);

        Assertions.assertNotNull(naveDto);
        Assertions.assertEquals("tipo 1", naveDto.getTipoNave()); // Verificar el tipo de la primera nave
    }

    @Test
    public void saveTest() throws Exception{

        // Configuramos el mock para que naveRepository.save guarde una nave
        Mockito.when(mapper.map(NAVEDTO_1,Nave.class)).thenReturn(NAVE_1);

        Mockito.when(repo.save(ArgumentMatchers.any())).thenReturn(NAVE_1);

        Mockito.when(mapper.map(NAVE_1,NaveDTO.class)).thenReturn(NAVEDTO_1);

        NaveDTO response = service.save(NAVEDTO_1);

        Assertions.assertNotNull(response);
    }

    @Test
    public void updateTest() throws Exception{
        int ID = 1;
        // Configuramos el mock para que naveRepository.save guarde una nave
        Mockito.when(repo.findById(ID)).thenReturn(Optional.of(NAVE_1));

        Mockito.when(mapper.map(NAVEDTO_1,Nave.class)).thenReturn(NAVE_1);

        Mockito.when(repo.save(ArgumentMatchers.any())).thenReturn(NAVE_1);

        Mockito.when(mapper.map(NAVE_1,NaveDTO.class)).thenReturn(NAVEDTO_1);

        NaveDTO response = service.save(NAVEDTO_1);

        Assertions.assertNotNull(response);
    }

    @Test
    public void deleteTest() throws Exception{
        repo.deleteById(1);
        repo.deleteById(1);

        Mockito.verify(repo, Mockito.times(2)).deleteById(1);

    }

    @Test
    public void readByNameTest() throws Exception{
        String name = "wing";
        List<Nave> naveList = Arrays.asList(NAVE_2);

        // Configuramos el mock para que naveRepository.findById devuelva una Nave
        Mockito.when(repo.findByNombreContainsIgnoreCase(name)).thenReturn(naveList);
        Mockito.when(mapper.map(NAVE_2,NaveDTO.class)).thenReturn(NAVEDTO_2);

        List<NaveDTO> naves = service.findByNombreContainsIgnoreCase(name);

        Assertions.assertNotNull(naves);
        Assertions.assertEquals("X-Wing Starfighter", naves.get(0).getNombre()); // Verificar el nombre de la primera nave
    }
}
