package br.com.homefashion.service;

import br.com.homefashion.dao.ClienteDAO;
import br.com.homefashion.exception.ProjetoException;
import br.com.homefashion.model.Cliente;

import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService() {
        clienteDAO = new ClienteDAO();
    }

    public List<Cliente> buscarClientePorNome(String campoBusca) {
        return clienteDAO.buscarClientePorNome(campoBusca);
    }

    public void inserirCliente(Cliente cliente) throws ProjetoException {
        clienteDAO.inserirCliente(cliente);
    }

    public void alterarCliente(Cliente cliente) throws ProjetoException {
        clienteDAO.alterarCliente(cliente);
    }

    public void deletarCliente(Cliente cliente) throws ProjetoException {
        clienteDAO.deletarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

}