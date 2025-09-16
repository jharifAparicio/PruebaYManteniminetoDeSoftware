package com.test.springboot.app;

import com.test.springboot.app.models.Banco;
import com.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;

public class Datos {
    public static final Cuenta CUENTA_001= new Cuenta(1L,"Juan",new BigDecimal("1000"));
    public static final Cuenta CUENTA_002= new Cuenta(2L,"Carlos",new BigDecimal("2000"));
    public static final Banco BANCO= new Banco(1L,"Banco Union",0);

}
