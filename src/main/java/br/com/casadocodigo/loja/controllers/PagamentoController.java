package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;

@Controller
@RequestMapping("/pagamento")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*Esse metodo foi acrescentado para que ao finalizar a compra entre na uri de verificação, que usa JSON
	 * para que transpareça como se fosse enviado para um paypal da vida, como se houvesse uma transição bancária
	 * @RequestMapping(value = "/finalizar",method = RequestMethod.POST)
	public ModelAndView finalizar(RedirectAttributes model) {
		System.out.println(carrinho.getTotalTudo());
		model.addFlashAttribute("sucesso","Pagamento realizado com sucesso!");
		
		return new ModelAndView("redirect:/produtos");
	}*/
	/*
	 * Para deixar essa aplicação assincrona usa-se a classe Callabe, assim um usuário espera enquanto o outro continua
	 * sem um interferir no outro caso esse outro mande finalizar a compra tambem
	 * @RequestMapping(value = "/finalizar",method = RequestMethod.POST)
	public ModelAndView finalizar(RedirectAttributes model) {
		String uri = "http://book-payment.herokuapp.com/payment";
		try {
			String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotalTudo()),String.class);
			System.out.println(response);
			
			model.addFlashAttribute("sucesso",response);
			return new ModelAndView("redirect:/produtos");
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			model.addFlashAttribute("falha","Valor maior que o permitido");
			return new ModelAndView("redirect:/produtos");
		}
	}*/
	@RequestMapping(value = "/finalizar",method = RequestMethod.POST)
	public Callable<ModelAndView> finalizar(RedirectAttributes model) {
		String uri = "http://book-payment.herokuapp.com/payment";
		/*Callabe é uma classe anônima para retornar uma classe anonima como recurso do java 8
		usa-se como esta abaixo o retorno*/
		return () -> {
			try {
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotalTudo()),String.class);
				System.out.println(response);
				
				model.addFlashAttribute("sucesso",response);
				return new ModelAndView("redirect:/produtos");
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				model.addFlashAttribute("falha","Valor maior que o permitido");
				return new ModelAndView("redirect:/produtos");
			}
		};
	}
}
