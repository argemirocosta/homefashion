package br.com.homefashion.exception;


import br.com.homefashion.util.JSFUtil;

public class ProjetoException extends Exception {

    private static final long serialVersionUID = -8768268699640382537L;

    public ProjetoException() {
    }

    public ProjetoException(String arg) {
        JSFUtil.adicionarMensagemErro(arg, "ERRO");
    }

    public ProjetoException(Throwable arg) {
        super(arg);
    }

    public ProjetoException(String arg, Throwable arg1) {
        super(arg, arg1);
    }

}
