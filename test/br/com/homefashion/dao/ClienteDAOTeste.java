package br.com.homefashion.dao;

import br.com.homefashion.builders.ClienteBuilderTest;
import br.com.homefashion.exception.ProjetoException;
import br.com.homefashion.model.Cliente;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class ClienteDAOTeste {

    private ClienteDAO clienteDAOFake;
    private Cliente cliente1;
    private Cliente cliente2;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void inicializarClasse(){
        clienteDAOFake = mock(ClienteDAO.class);
        cliente1 = ClienteBuilderTest.umClienteTeste1().agora();
        cliente2 = ClienteBuilderTest.umClienteTeste2().agora();
    }

    @Test
    public void listarClientes() {

        List<Cliente> listaClientes = Arrays.asList(cliente1, cliente2);

        when(clienteDAOFake.listarClientes()).thenReturn(listaClientes);

        assertEquals(2, listaClientes.size());
    }

    @Test
    public void buscarClientes() {

        List<Cliente> buscarClientes = Arrays.asList(cliente1);

        when(clienteDAOFake.buscarClientePorNome("1")).thenReturn(buscarClientes);

        assertEquals(1, buscarClientes.size());
    }

    @Test
    public void inserirCliente() throws ProjetoException {
        clienteDAOFake.inserirCliente(cliente1);

        verify(clienteDAOFake, times(1)).inserirCliente(cliente1);
    }

    @Test
    public void alterarCliente() throws ProjetoException {
        clienteDAOFake.alterarCliente(cliente1);

        verify(clienteDAOFake, times(1)).alterarCliente(cliente1);
    }

    @Test
    public void deletarCliente() throws ProjetoException {
        clienteDAOFake.deletarCliente(cliente1);

        verify(clienteDAOFake, times(1)).deletarCliente(cliente1);
    }

}
