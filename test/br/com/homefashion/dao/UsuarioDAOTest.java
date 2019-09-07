package br.com.homefashion.dao;

import br.com.homefashion.builders.UsuarioBuilderTest;
import br.com.homefashion.exception.ProjetoException;
import br.com.homefashion.model.Usuario;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class UsuarioDAOTest {

    private UsuarioDAO usuarioDAOFake;
    private Usuario usuario1;

    @Before
    public void inicializarClasse() {
        usuarioDAOFake = mock(UsuarioDAO.class);
        usuario1 = UsuarioBuilderTest.usuarioTesteInserir().agora();
    }

    @Test
    public void inserirUsuario() throws ProjetoException {
        usuarioDAOFake.inserirUsuario(usuario1);

        verify(usuarioDAOFake, times(1)).inserirUsuario(usuario1);

    }

    @Test
    public void alterarUsuario() throws ProjetoException {
        usuarioDAOFake.alterarUsuario(usuario1);

        verify(usuarioDAOFake, times(1)).alterarUsuario(usuario1);

    }

}
