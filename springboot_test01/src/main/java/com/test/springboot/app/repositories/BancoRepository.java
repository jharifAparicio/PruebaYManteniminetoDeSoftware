package com.test.springboot.app.repositories;

import com.test.springboot.app.models.Banco;
import com.test.springboot.app.models.Cuenta;

import java.util.List;

public interface BancoRepository {
    List<Banco> findAll();
    Banco findById(Long id);
    void update(Banco banco);

}
