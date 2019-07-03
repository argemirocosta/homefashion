package br.com.homefashion.model;

public class ClienteBean {

	private Integer id;
	private String nome;
	private Integer telefone1;
	private Integer telefone2;
	private Usuario usuario;
	
	public ClienteBean(){
		usuario = new Usuario();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(Integer telefone1) {
		this.telefone1 = telefone1;
	}

	public Integer getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(Integer telefone2) {
		this.telefone2 = telefone2;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}