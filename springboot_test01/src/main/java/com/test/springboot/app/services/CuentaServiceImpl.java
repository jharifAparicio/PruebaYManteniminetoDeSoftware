package com.test.springboot.app.services;

import com.test.springboot.app.models.Banco;
import com.test.springboot.app.models.Cuenta;
import com.test.springboot.app.repositories.BancoRepository;
import com.test.springboot.app.repositories.CuentaRepository;

import java.math.BigDecimal;

public class CuentaServiceImpl  implements CuentaService {

    private CuentaRepository cuentaRespository;

    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRespository, BancoRepository bancoRepository) {
        this.cuentaRespository = cuentaRespository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {
        return cuentaRespository.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
         Cuenta cuenta = cuentaRespository.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen=cuentaRespository.findById(numCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaRespository.update(cuentaOrigen);

        Cuenta cuentaDestino= cuentaRespository.findById(numCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaRespository.update(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();
        //banco.setTotalTransferencias(banco.getTotalTransferencias() + totalTransferencias);
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.update(banco);
    }
}
