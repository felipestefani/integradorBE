package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.DentistaDao;
import com.dh.consultorioOdontologico.model.Dentista;

import java.sql.Connection;
import java.sql.SQLException;

public class DentistaService {

    public Dentista cadastrar(Dentista dentista) throws SQLException{
        IDao<Dentista> dentistaIDao = new DentistaDao();
        return dentistaIDao.cadastrar(dentista);
    }

    public void excluir(Dentista dentista) throws  SQLException {
        IDao<Dentista> dentistaIDao = new DentistaDao();
        dentistaIDao.excluir(dentista);
    }
}