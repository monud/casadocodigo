package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@ComponentScan
@RequestMapping("/produtos")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO dao;
	
	@Autowired
	private FileSaver fileSaver;
	
	//initBinder é um metodo que vincula o Controller com o validador
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}
	
	/*Metodo alterado para enviar um objeto do model para o view
	 * public String addLivro() {
		//Permite pegar um objeto que está no model e enviar para o view
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("tipos", TipoPreco.values());
		return "produtos/form";
	}*/
	
	@RequestMapping("/addLivro")
	public ModelAndView addLivro(Produto produto) {
		//Permite pegar um objeto que está no model e enviar para o view
		ModelAndView modelAndView = new ModelAndView("produtos/add");
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}
	
	/*@RequestMapping(method = RequestMethod.POST)
	 * Foi adicionado o parametro result para que o java saiba o que acontece quando se valida
	public ModelAndView gravar(@Valid Produto produto,RedirectAttributes ra) {
		System.out.println(produto);
		dao.gravar(produto);
		return "produtos/sucessoAdd";
		 * O retorno foi alterado, porque antes não havia a página de listas, é bem mais interessante
		 * após um cadastro aparecer uma lista que uma página com uma mensagem
		 * só que o médoto lista retorna uma ModelAndView por isso o tipo foi alterado de String para ModelAndView
		ra.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!!!");
		RedirectAttributes trabalha considerando os atributos de retirect e o flash pega o atributo da primeira requisição
		e no nosso caso imprime na tela, o segundo atributo de requisição vem do metodo listar, assim os dois aparecem 
		na tela
		return new ModelAndView("redirect:produtos");
	}*/
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario,@Valid Produto produto,BindingResult result,RedirectAttributes ra) {
		
		if(result.hasErrors()) {
			return addLivro(produto);
		}
		
		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setPathSumario(path);
		dao.gravar(produto);
		ra.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!!!");
		return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	/* O código anterior era esse abaixo:
	 * @RequestMapping(value="produtos/",method = RequestMethod.GET)
	 * Foi alterado porque estava tendo muita repetição de "produtos/",
	 * colocando o mapeamento de "produtos" lá na classe está se dizendo que todos os metodos com mapemanto
	 * receberão o "produtos/" antes*/
	public ModelAndView listar() {
		List<Produto> produtos = dao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhar(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		Produto produto = dao.buscaId(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
}
