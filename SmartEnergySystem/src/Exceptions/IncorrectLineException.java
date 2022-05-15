package Exceptions;

public class IncorrectLineException extends Exception {

    /**
     * Exceção para uma linha incorreta.
     */
    public IncorrectLineException(){
        super();
    }

    /**
     * Exceção para uma linha incorreta que envia uma mensagem.
     * @param s
     */
    public IncorrectLineException(String s){
        super(s);
    }
}