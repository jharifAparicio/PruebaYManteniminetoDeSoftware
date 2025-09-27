const { validar } = require('../src/validadorExtranjero');

test('debe devolver false si es null', ()=>{
    expect(validar(null)).toBe(false);
})

test("El CE tiene 10 dígitos pero sin E-", ()=>{
    expect(validar("1234567890")).toBe(false);
})

test("El CE tiene 9 dígitos", ()=>{
    expect(validar("123456789")).toBe(false);
})

test("El CE debe comenzar con E-", ()=>{
    expect(validar("E-12345678")).toBe(true);
})