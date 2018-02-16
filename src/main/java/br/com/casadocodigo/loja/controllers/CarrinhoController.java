package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private CarrinhoCompras carrinho;

	@RequestMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		modelAndView.addObject("produto",produtoId);
		CarrinhoItem carrinhoItem = criaItem(produtoId,tPreco);
		carrinho.add(carrinhoItem);
		return modelAndView;
	}

	@RequestMapping("/remover")
	public ModelAndView remover(Integer produtoId, TipoPreco tipoPreco) {
		carrinho.remover(produtoId,tipoPreco);
		return new ModelAndView("redirect:/carrinho");
	}
	
	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tPreco) {
		Produto produto = produtoDao.buscaId(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto,tPreco);		
		
		return carrinhoItem;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView itens() {
		return new ModelAndView("/carrinho/itens");
	}

}
