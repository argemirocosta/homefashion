package br.com.homefashion.controller;

import br.com.homefashion.dao.ClienteDAO;
import br.com.homefashion.model.ClienteBean;
import br.com.homefashion.model.VendaBean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

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
			RequestContext.getCurrentInstance().execute("PF('dlgDeleteCliente').hide();");
			
			FacesMessage msg = new FacesMessage("Cliente deletado");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("erro ao deletar");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		}
	}

	public void editeCliente() {
		boolean alterou = cDao.editeCliente(cliente);

		if (alterou) {
			limparCampos();

			RequestContext.getCurrentInstance().execute("PF('dlgAltCliente').hide();");

			FacesMessage msg = new FacesMessage("Cliente Alterado");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("erro ao alterar");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		}
	}

	public void insereCliente() {
		boolean cadastrou = cDao.insereCliente(cliente);

		if (cadastrou) {
			limparCampos();
			
			RequestContext.getCurrentInstance().execute("PF('dlgCadCliente').hide();");

			FacesMessage msg = new FacesMessage("Cliente cadastrado");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("erro ao cadastrar");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
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