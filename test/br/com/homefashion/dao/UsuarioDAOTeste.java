package br.com.homefashion.dao;

import br.com.homefashion.builders.UsuarioBuilderTest;
import br.com.homefashion.exception.ProjetoException;
import br.com.homefashion.model.Usuario;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class UsuarioDAOTeste {

    private UsuarioDAO usuarioDAO;
    private Usuario usuario1;

    @Before
    public void inicializarClasse(){
        usuarioDAO = new UsuarioDAO();
        usuario1 = UsuarioBuilderTest.usuarioTesteInserir().agora();
    }

    @Test
    public void inserirUsuario() throws ProjetoException {

        try {
            usuarioDAO.inserirUsuario(usuario1);
            assertTrue(true);
        }
        catch (ProjetoException ex){
            throw new ProjetoException(ex);
        }

    }

}
