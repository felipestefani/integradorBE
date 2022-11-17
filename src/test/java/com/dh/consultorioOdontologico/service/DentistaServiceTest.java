package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.model.Dentista;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DentistaServiceTest {

    Dentista felipe = new Dentista("Felipe", "Stefani", 123);
    Dentista gabriella = new Dentista("Gabriella", "Faria", 456);
    DentistaService dentistaService = new DentistaService();

    @Test
    public void cadastrarDentista() throws SQLException {
        dentistaService.cadastrar(felipe);
        dentistaService.cadastrar(gabriella);
    }

    @Test
    public void buscarDentistas() throws SQLException {
        dentistaService.buscarDentistas();
    }

}