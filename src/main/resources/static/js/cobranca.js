// Ação de mostrar a tela modal de exclusão
// Ativada quando clicar no xis
$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {

	// Captura o botão que efetuou o evento da chamada do MODAL
	button = $(event.relatedTarget);	
	
	// Id do registro no atributo data do botão
	var codigoTitulo = button.data('codigo');
	var descTitulo = button.data('descricao');
	
	
	// Inserindo o id no action do nosso form da janela MODAL
	// -----------------------------------------------------
	var modal = $(this);
	
	// Encontrando nosso form e sua action...
	var form = modal.find('form');
	//var action = form.attr('action');
	var action = form.data('url-base');
	
	// Validando se a action do form já tem ou não uma barra
	if (!action.endsWith('/')) {
		action += '/';
	}
	
	// Debug
	console.log('A action do form será :' + action + codigoTitulo);
	
	// Alterando a action do form da MODAL
	form.attr('action', action + codigoTitulo);
	// Alterando a mensagem de confirmação de exclusão
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o título <strong>' + descTitulo + '</strong>?');
	
});

// Toda vez que a página carregar eu executo algumas funcionalidades
$(function() {
	
	// Procures os elementos que possuem rel igual a tooltip e 
	// ligue os tooltips
	$('[rel=tooltip]').tooltip();
	
	// Ativando a máscara para campos Money
	$('.js-currency').maskMoney({
		 
				 thousands: '.' 
				,decimal:   ',' 
				,allowZero: true
	});
	
	// Máscara para datas 
	$('.js-databrasil').mask('00/00/0000');
	
	
	// Botão receber
	$('.js-atualizar-status').on('click', function(event) {
		
		// Ignorando o post da página pelo clique do clique de receber
		event.preventDefault();
		 
		// Captura os dados de url para receber o título
		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href'); 
		
		// Chama em nosso CONTROLLER o método receber
		var response = $.ajax({
			 url: urlReceber
		   ,type: 'PUT'
		});
		
		// Se ocorrer tudo bem, essa função será executada
		response.done(function(e) {
			
			// Id do Título no botão receber
			var codigoTitulo = botaoReceber.data('codigo');
			
			// Captura o indicador de Recebido/Pendente que aponta para o Título selecionado
			// e marca como recebido. Esconde o botão receber também
			$('[data-role=' + codigoTitulo + ']').html('<span class="label label-success">Recebido</span>');
			botaoReceber.hide();
		});
		
		// Se ocorrer algum erro, essa função será executada
		response.fail(function(e) {
			console.log(e);
			aler('Erro ao receber a cobrança');			
		});
		
	});
	
});