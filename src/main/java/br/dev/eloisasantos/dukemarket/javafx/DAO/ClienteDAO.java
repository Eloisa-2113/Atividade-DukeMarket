package br.dev.eloisasantos.dukemarket.javafx.DAO;

import br.dev.eloisasantos.dukemarket.javafx.model.Cliente;
import connection.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sesi2dia
 */
public class ClienteDAO {

    private static final String SQL_INSERT = "INSERT INTO cliente (nome, "
            + "endereco, cidade, uf, cpf) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL = "SELECT * FROM cliente";
    private static final String SQL_SELECT_ID = "SELECT * FROM cliente "
            + "WHERE id = ?";

    private static final String SQL_UPDATE = "UPDATE cliente SET nome = ?, "
            + "endereco = ?, cidade = ?, uf = ?, cpf = ? "
            + "WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM cliente WHERE id = ?";

    /**
     * Insere um cliente na base de dados cliente
     *
     * @param c
     */
    public void create(Cliente c) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEndereco());
            stmt.setString(3, c.getCidade());
            stmt.setString(4, c.getUf());
            stmt.setString(5, c.getCpf());

            // Executa a query
            int auxRetorno = stmt.executeUpdate();

            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null,
                    "Inclusao: " + auxRetorno);

        } catch (SQLException sSQLException) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null,
                    sSQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }
    }

    /**
     * Retorna todos os registros da tabela cliente
     *
     * @return
     */
    public List<Cliente> getResults() {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente c = null;
        List<Cliente> listaClientes = null;

        try {
            // Prepara a string de SELECT e executa a query.
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();

            // Carrega os dados do ResultSet rs, converte em Cliente e
            // adiciona na lista de retorno.
            listaClientes = new ArrayList<>();

            while (rs.next()) {
                c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setCidade(rs.getString("cidade"));
                c.setUf(rs.getString("uf"));
                c.setCpf(rs.getString("cpf"));
                c.setDataCadastro(rs.getString("dataCadastro"));
                listaClientes.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQLConnection.closeConnection(conn, stmt, rs);
        }
        return listaClientes;
    }

    /**
     * Atualiza um registro na tabela cliente.
     *
     * @param c Cliente a ser atualizado.
     */
    public void update(Cliente c) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEndereco());
            stmt.setString(3, c.getCidade());
            stmt.setString(4, c.getUf());
            stmt.setString(5, c.getCpf());
            stmt.setInt(6, c.getId());

            // Executa a query
            int auxRetorno = stmt.executeUpdate();

            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null,
                    "Update: " + auxRetorno);

        } catch (SQLException sSQLException) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null,
                    sSQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }
    }

    /**
     * Exclui um cliente com base no ID fornecido.
     *
     * @param id Identificação do cliente.
     */
    public void delete(int id) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);

            // Executa a query
            int auxRetorno = stmt.executeUpdate();

            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null,
                    "Delete: " + auxRetorno);

        } catch (SQLException sSQLException) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null,
                    sSQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }
    }

    public Cliente getResultById(int id) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente c = null;

        try {
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setCidade(rs.getString("cidade"));
                c.setUf(rs.getString("uf"));
                c.setCpf(rs.getString("cpf"));
                c.setDataCadastro(rs.getString("dataCadastro"));
            }

        } catch (SQLException sSQLException) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null,
                    sSQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt, rs);
        }

        return c;
    }
    
}

