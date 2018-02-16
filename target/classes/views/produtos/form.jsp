<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros em Geral - Casa do Código</title>
</head>
<body>
<!-- A tag form do Spring tem o parametro commandName que permite falar que se esta utilizando 
o produto dentro dessa tag e que portanto não é necessário colocar ela abaixo que 
funciona e tem os mesmos parametros que o form do html  
Esse parametro informa que o formulário trabalha tanto com texto quanto com arquivo, enctype="multipart/form-data"-->
	<form:form action="${s:mvcUrl('PC#gravar').build()}" method="post" commandName="produto" enctype="multipart/form-data">
	<%-- <form:form action="/casadocodigo/produtos" method="post" commandName="produto">
	Substituido para dar semantica e usar a funcionalidade do spring --%>
		<div>
			<label for="titulo">Título</label> 
			<!-- Todos os inputs serão substituidos para que o srping controle a inserção dos dados,faz-se isso
			utilizando da tag form nos inputs, não possuem type, nem name, apenas path 
			<input type="text" name="titulo" id="titulo">-->
			<form:input path="titulo" id="titulo"/>
			<form:errors path="titulo"/>
		</div>
		<br>
		<div>
			<label for="descricao">Descrição</label>
			<!-- <textarea rows="10" cols="20" name="descricao" id="descricao"></textarea> -->
			<form:textarea path="descricao" rows="10" cols="20" id="descricao"/>
			<form:errors path="descricao"/>
		</div>
		<br>
		<div>
			<label for="paginas">Páginas</label> 
			<!-- <input type="text" name="paginas" id="paginas"> -->
			<form:input path="paginas" id="paginas" />
			<form:errors path="paginas"/>
		</div>
		<!-- Substituido pelo código abaixo, para ser mais dinâmico, pois utilizamos
		um enum para especificar os tipos de preço 
		<div>
			<label for="ebook">E-book</label>
			<input type="text" name="ebook" id="ebook">
		</div>
		<div>
			<label for="impresso">Impresso</label>
			<input type="text" name="impresso" id="impresso">
		</div>
		<div>
			<label for="combo">Combo</label>
			<input type="text" name="combo" id="combo">
		</div> -->
		<br>
		<div>
			<label for="dataLancamento">Data de Lançamento</label>
			<!-- <input type="text" name="dataLancamento" id="dataLancamento"> -->
			<form:input path="dataLancamento" id="dataLancamento"/>
			<form:errors path="dataLancamento"/>
		</div>
		<br>
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div>
				<label for="tipo">${tipoPreco}</label> 
				<%-- <input type="text" name="precos[${status.index}].valor" id="tipo"> 
				<input type="hidden" name="precos[${status.index}].tipo" value="${tipoPreco}"> --%>
				<form:input path="precos[${status.index}].valor" id="tipo"/> 
				<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}"/>
			</div>
			<br>
		</c:forEach>
		<br>
		<br>
		<div>
			<label for="sumario">Sumário</label>
			<input name="sumario" type="file" id="sumario" >
		</div>
		<br>
		<button type="submit">Cadastrar Livro</button>
	</form:form>
</body>
</html>
