package br.com.homefashion.shared;

public class Queries {

    private Queries() {
    }

    public static final String SELECT_LISTAR_CLIENTES = "SELECT id, nome, telefone1, telefone2 FROM vendas.clientes WHERE usuario = ? ORDER BY nome";

    public static final String SELECT_BUSCAR_CLIENTE_POR_NOME = "SELECT id, nome, telefone1, telefone2 FROM vendas.clientes WHERE upper(nome) LIKE upper(?) AND usuario = ? ORDER BY nome";

    public static final String INSERIR_CLIENTE = "INSERT INTO vendas.clientes (nome, telefone1, telefone2, usuario) VALUES (?,?,?,?)";

    public static final String ALTERAR_CLIENTE = "UPDATE vendas.clientes SET nome=?, telefone1=?, telefone2=? WHERE id=?";

    public static final String DELETAR_CLIENTE = "DELETE FROM vendas.clientes WHERE id=?";

    public static final String SELECT_LOGIN = "SELECT id, nome, login, senha, ativo FROM vendas.usuario WHERE login = ? AND senha = ?";

    public static final String INSERIR_USUARIO = "INSERT INTO vendas.usuario (nome, login, senha, ativo) VALUES (?,?,?,TRUE)";

    public static final String INSERIR_VENDA = "INSERT INTO vendas.venda (id_cliente, valor, qtd, data, usuario) VALUES (?,?,?,?,?)";

    public static final String SELECT_LISTAR_VENDAS = "SELECT v.id, v.id_cliente, c.nome, v.data, v.valor, v.qtd, "
            + "COALESCE(sum(p.valor_pago),0) AS total_pago, COALESCE((v.valor - sum(p.valor_pago)),v.valor) AS em_aberto, "
            + "CASE WHEN COALESCE((v.valor - sum(p.valor_pago)),v.valor) = 0 THEN 'PAGO' "
            + "WHEN COALESCE((v.valor - sum(p.valor_pago)),v.valor) > 0 THEN 'ABERTO' "
            + "END AS situacao "
            + "FROM vendas.venda v "
            + "LEFT JOIN vendas.clientes c ON (v.id_cliente = c.id) "
            + "LEFT JOIN vendas.pagamentos p ON (v.id = p.id_venda) "
            + "WHERE v.id_cliente = ? "
            + "GROUP BY v.id, v.id_cliente, c.nome, v.valor, v.qtd, v.data "
            + "ORDER BY v.data DESC";

    public static final String INSERIR_PAGAMENTOS = "INSERT INTO vendas.pagamentos (id_venda, valor_pago, data_pagamento, usuario) VALUES (?,?,?,?)";

    public static final String SELECT_LISTAR_PAGAMENTOS = "SELECT p.id_venda, p.valor_pago, p.data_pagamento, v.data "
            + "FROM vendas.pagamentos p "
            + "LEFT JOIN vendas.venda v ON (p.id_venda = v.id) "
            + "WHERE v.id = ? "
            + "ORDER BY v.data DESC, p.data_pagamento DESC";

    public static final String SELECT_CALCULAR_VALOR_EM_ABERTO = "SELECT v.id, (v.valor - sum(COALESCE(p.valor_pago, 0))) AS em_aberto "
            + "FROM vendas.venda v "
            + "LEFT JOIN vendas.pagamentos p ON (v.id = p.id_venda) "
            + "WHERE v.id = ? AND v.usuario = ? GROUP BY v.id ";

    public static final String SELECT_VERIFICAR_SEM_PAGAMENTOS = "SELECT count(id_venda) AS qtd FROM vendas.pagamentos WHERE id_venda = ?";

    public static final String SELECT_LISTAR_VENDAS_POR_CLIENTE = "SELECT v.id_cliente, c.nome, sum(v.valor) AS total "
            + "FROM vendas.venda v "
            + "LEFT JOIN vendas.clientes c ON (v.id_cliente = c.id) "
            + "WHERE v.usuario = ?"
            + "GROUP BY v.id_cliente, c.nome "
            + "ORDER BY total DESC";

    public static final String SELECT_CONSULTAR_VENDAS_POR_PERIODO = "SELECT sum(valor) AS soma FROM vendas.venda WHERE DATA BETWEEN ? AND ? AND usuario = ?";

    public static final String SELECT_CALCULAR_VENDAS_TOTAL = "SELECT sum(valor) AS soma FROM vendas.venda WHERE usuario = ?";

    public static final String SELECT_CALCULAR_VALOR_RECEBER_GERAL = "SELECT sum(valor_pago) AS valor FROM vendas.pagamentos WHERE usuario = ?";

    public static final String SELECT_LISTAR_VALOR_A_RECEBER = "SELECT v.id, v.data, v.id_cliente, c.nome, v.valor, "
            + "COALESCE((v.valor - sum(p.valor_pago)), v.valor) AS em_aberto "
            + "FROM vendas.venda v "
            + "LEFT JOIN vendas.clientes c ON (v.id_cliente = c.id) "
            + "LEFT JOIN vendas.pagamentos p ON (v.id = p.id_venda) "
            + "GROUP BY v.id, v.id_cliente, c.nome, v.valor, v.data "
            + "HAVING COALESCE(v.valor - sum(p.valor_pago), v.valor) > 0 AND v.usuario = ? "
            + "ORDER BY em_aberto DESC ";


}
