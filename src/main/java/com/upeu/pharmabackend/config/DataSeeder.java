package com.upeu.pharmabackend.config;

import com.upeu.pharmabackend.categoria.CategoriaRepository;
import com.upeu.pharmabackend.categoria.CategoriaRequestDTO;
import com.upeu.pharmabackend.categoria.CategoriaService;
import com.upeu.pharmabackend.cliente.ClienteRepository;
import com.upeu.pharmabackend.cliente.ClienteRequestDTO;
import com.upeu.pharmabackend.cliente.ClienteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaService categoriaService;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    public DataSeeder(CategoriaRepository categoriaRepository, CategoriaService categoriaService,
                       ClienteRepository clienteRepository, ClienteService clienteService) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaService = categoriaService;
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() == 0) {
            categoriaService.crear(new CategoriaRequestDTO("Analgésicos", "Medicamentos para el dolor", true));
            categoriaService.crear(new CategoriaRequestDTO("Antibióticos", "Requieren receta médica", true));
            categoriaService.crear(new CategoriaRequestDTO("Vitaminas", null, true));
        }

        if (clienteRepository.count() == 0) {
            clienteService.crear(new ClienteRequestDTO("71234567", "Ana", "Quispe Rojas", "ana.quispe@gmail.com", "987654321", "Av. Los Álamos 123", true));
            clienteService.crear(new ClienteRequestDTO("70112233", "Luis", "Fernández Vega", "luis.fernandez@gmail.com", null, null, true));
            clienteService.crear(new ClienteRequestDTO("68899001", "María", "Torres Díaz", "maria.torres@gmail.com", "912345678", "Jr. Las Palmeras 456", true));
            clienteService.crear(new ClienteRequestDTO("65544332", "Carlos", "Ramírez Soto", "carlos.ramirez@gmail.com", null, null, false));
            for (int i = 1; i <= 3; i++) {
                clienteService.crear(new ClienteRequestDTO(
                        String.format("70430%03d", i),
                        "Cliente" + i,
                        "Apellido" + i,
                        "cliente" + i + "@gmail.com",
                        null, null, true));
            }
        }
    }
}
