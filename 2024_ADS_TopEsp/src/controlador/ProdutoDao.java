/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import modelo.Produto;

/**
 *
 * @author Bruno
 */
public class ProdutoDao {
    public void inserir(Produto u) throws Exception {
        String sql = "INSERT INTO produto (nome,unidadeDeMedida) values(?,?)";
        Connection conexao = Conexao.getConexao();
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, u.getNomeProduto());
            ps.setString(2, u.getUnidadeDeMedida());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }

    }

    public List<Produto> buscar(String nome) throws Exception {
        List<Produto> lista = new ArrayList<>();

        String sql = "select * from produto";
        sql += (!nome.equals("")) ? " where nome like ?" : "";

        Connection conexao = Conexao.getConexao();

        //try-with-resourses fecha o recurso automaticamente, dispensa o uso de .close()
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            if (!nome.equals("")) {
                ps.setString(1, "%" + nome + "%");
            }

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produto u = new Produto();
                    u.setId(rs.getInt("id"));
                    u.setNomeProduto(rs.getString("nome"));
                    u.setUnidadeDeMedida(rs.getString("unidadeDeMedida"));
                    lista.add(u);
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
}
