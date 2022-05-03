package Exceptions;

public class LinhaIncorretaException extends Exception {

    /**
     * Exceção para uma linha incorreta.
     */
    public LinhaIncorretaException(){
        super();
    }

    /**
     * Exceção para uma linha incorreta que envia uma mensagem.
     * @param s
     */
    public LinhaIncorretaException(String s){
        super(s);
    }
}