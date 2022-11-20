package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.ConsultaDao;
import com.dh.consultorioOdontologico.model.Consulta;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ConsultaService {
    IDao<Consulta> consultaIDao = new ConsultaDao();

    public Consulta cadastrar(Consulta consulta) throws SQLException {
        return consultaIDao.cadastrar(consulta);
    }

    public Consulta modificar(Consulta consulta) throws SQLException {
        return consultaIDao.modificar(consulta);
    }

    public void excluir(Consulta consulta) throws SQLException{
        consultaIDao.excluir(consulta);
    }

    public List<Consulta> buscarTodos() throws SQLException{
        ConsultaDao consultaDao= new ConsultaDao();
        return consultaDao.buscarTodos();
    }

    public Optional<Consulta> buscarPorId(int id) throws SQLException {
        return consultaIDao.buscarPorId(id);
    }
}
