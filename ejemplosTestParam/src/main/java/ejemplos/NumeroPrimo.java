package ejemplos;

public class NumeroPrimo {
    public NumeroPrimo() {
    }
    public Boolean validate(final Integer numeroPrimo){
        for(int i=2;i<(numeroPrimo/2);i++){
            if(numeroPrimo % i ==0){
                return false;
            }
        }
        return true;
    }
}
