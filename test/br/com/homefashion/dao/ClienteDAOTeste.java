package br.com.homefashion.dao;

import br.com.homefashion.builders.ClienteBuilderTest;
import br.com.homefashion.model.Cliente;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;


public class ClienteDAOTeste {

    private ClienteDAO clienteDAO;
    private Cliente cliente1;
    private Cliente cliente2;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void inicializarClasse(){
        clienteDAO = mock(ClienteDAO.class);
        cliente1 = ClienteBuilderTest.umClienteTeste1().agora();
        cliente2 = ClienteBuilderTest.umClienteTeste2().agora();
    }

    @Test
    public void listarClientes() {

        List<Cliente> listaClientes = Arrays.asList(cliente1, cliente2);

        when(clienteDAO.listarClientes()).thenReturn(listaClientes);

        assertEquals(2, listaClientes.size());
    }

    @Test
    public void buscarClientes() {

        List<Cliente> buscarClientes = Arrays.asList(cliente1);

        when(clienteDAO.buscarClientePorNome("1")).thenReturn(buscarClientes);

        assertEquals(1, buscarClientes.size());
    }

}
