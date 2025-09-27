function validar(input) {
    // debe iniciar con E-
    if (!/^E-\d{8}$/.test(input)) {
        return false;
    }
    return true;
}

module.exports = { validar };