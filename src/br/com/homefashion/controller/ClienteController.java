package br.com.homefashion.controller;

import br.com.homefashion.dao.ClienteDAO;
import br.com.homefashion.model.ClienteBean;
import br.com.homefashion.model.VendaBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.homefashion.util.JSFUtil;

@ViewScoped
@ManagedBean
public class ClienteController {

	private ClienteBean cliente;
	private List<ClienteBean> listaCliente;
	private String campoBusca;
	private VendaBean venda;
	private ClienteDAO cDao = new ClienteDAO();

	public ClienteController() {
		cliente = new ClienteBean();
		venda = new VendaBean();
		campoBusca = "";		
	}

	public void limparBusca() {
		listaCliente = null;
		campoBusca = "";
	}

	public void limparCampos() {
		listaCliente = null;
		cliente = new ClienteBean();
	}

	public void buscaNome() {
		listaCliente = cDao.buscaNome(campoBusca);
	}

	public void deleteCliente() {
		boolean deletou = cDao.deleteCliente(cliente);

		if (deletou) {
			limparCampos();
			JSFUtil.fecharDialog("dlgDeleteCliente");
			JSFUtil.adicionarMensagemSucesso("Cliente deletado com sucesso!", "Sucesso");
		} else {
			JSFUtil.adicionarMensagemErro("Erro ao deletar o cliente!", "Erro");
		}
	}

	public void editeCliente() {
		boolean alterou = cDao.editeCliente(cliente);

		if (alterou) {
			limparCampos();
			JSFUtil.fecharDialog("dlgAltCliente");
			JSFUtil.adicionarMensagemSucesso("Cliente alterado com sucesso!", "Sucesso");
		} else {
			JSFUtil.adicionarMensagemErro("Erro ao alterar o cliente!", "Erro");
		}
	}

	public void insereCliente() {
		boolean cadastrou = cDao.insereCliente(cliente);

		if (cadastrou) {
			limparCampos();
			JSFUtil.fecharDialog("dlgCadCliente");
			JSFUtil.adicionarMensagemSucesso("Cliente cadastrado com sucesso!", "Sucesso");
		} else {
			JSFUtil.adicionarMensagemErro("Erro ao cadastrar o cliente!", "Erro");
		}
	}

	public ClienteBean getCliente() {
		return cliente;
	}

	public void setP(ClienteBean cliente) {
		this.cliente = cliente;
	}

	public List<ClienteBean> getListaCliente() {
		if (listaCliente == null) {
			listaCliente = cDao.listarClientes();
		}
		return listaCliente;
	}

	public void setListaCliente(List<ClienteBean> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public String getCampoBusca() {
		return campoBusca;
	}

	public void setCampoBusca(String campoBusca) {
		this.campoBusca = campoBusca;
	}

	public VendaBean getVenda() {
		return venda;
	}

	public void setVenda(VendaBean venda) {
		this.venda = venda;
	}

}