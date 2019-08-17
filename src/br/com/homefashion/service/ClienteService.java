package br.com.homefashion.service;

import br.com.homefashion.dao.ClienteDAO;
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

    public Boolean inserirCliente(Cliente cliente) {
        return clienteDAO.inserirCliente(cliente);
    }

    public Boolean alterarCliente(Cliente cliente) {
        return clienteDAO.alterarCliente(cliente);
    }

    public Boolean deletarCliente(Cliente cliente) {
        return clienteDAO.deletarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

}