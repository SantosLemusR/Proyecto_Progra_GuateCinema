package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Pelicula;
import org.example.guatecinemaapi.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private DataSource dataSource;  // Asegúrate de que tienes configurado un DataSource

    public List<Pelicula> getAllPeliculas() {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> getPeliculaById(Integer id) {
        return peliculaRepository.findById(id);
    }

    public Pelicula createPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    // Método para obtener películas por cine usando JDBC
    public List<Pelicula> obtenerPeliculasPorCine(Integer cineId) {
        String sql = "SELECT p.* FROM pelicula p JOIN cine_pelicula cp ON p.id = cp.pelicula_id WHERE cp.cine_id = ?";
        List<Pelicula> peliculas = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, cineId);  // Asignamos el cineId al primer "?"
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));  // Asume que tienes el campo "id" en la tabla "pelicula"
                pelicula.setTitulo(rs.getString("titulo"));  // Y el campo "titulo"
                // Extrae más campos de la tabla "pelicula" según sea necesario
                peliculas.add(pelicula);  // Agregamos la película a la lista
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejo básico de excepciones, mejor manejar con un logger o excepciones personalizadas
        }

        return peliculas;  // Retornamos la lista de películas obtenida
    }

    public Pelicula updatePelicula(Integer id, Pelicula peliculaDetails) {
        Optional<Pelicula> pelicula = peliculaRepository.findById(id);
        if (pelicula.isPresent()) {
            Pelicula existingPelicula = pelicula.get();
            existingPelicula.setTitulo(peliculaDetails.getTitulo());
            existingPelicula.setGenero(peliculaDetails.getGenero());
            existingPelicula.setClasificacion(peliculaDetails.getClasificacion());
            existingPelicula.setSinopsis(peliculaDetails.getSinopsis());
            existingPelicula.setTrailer(peliculaDetails.getTrailer());
            existingPelicula.setReparto(peliculaDetails.getReparto());
            existingPelicula.setHora(peliculaDetails.getHora());
            existingPelicula.setSala(peliculaDetails.getSala());
            existingPelicula.setPosterUrl(peliculaDetails.getPosterUrl());
            return peliculaRepository.save(existingPelicula);
        } else {
            throw new RuntimeException("Película no encontrada con ID: " + id);
        }
    }

    public void deletePelicula(Integer id) {
        peliculaRepository.deleteById(id);
    }
}
