package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;

@Repository
 /*@Component = Repository, mas o Repository semanticamente para o DAO está correto
 pois indica que irá acessar os objetos no banco
 @Component = permite que o objeto/classe seja gerenciado pelo Spring e o Repository
 herda de Component, mas significa que é uma classe que meche com o acesso ao banco*/
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	public List<Produto> listar() {
		String sql = "SELECT p FROM Produto p";
		return manager.createQuery(sql,Produto.class).getResultList();
	}

	public Produto buscaId(Integer id) {
		String sql = "SELECT DISTINCT(p) FROM Produto p JOIN FETCH p.precos preco WHERE p.id = :id";
		return manager.createQuery(sql,Produto.class).setParameter("id",id).getSingleResult();
	}
}

