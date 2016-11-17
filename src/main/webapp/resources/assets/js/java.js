$(document).ready(function(){
    $('#1').click(AbiPrueba);
   $('#cargarInforme').click(function(){
		$('#ven_sel_reporte').fadeIn();
		$('#ven_sel_reporte').draggable();
    });
    $('#cargarInforme2').click(function(){
                $('#ven_sel_reporte').fadeOut();
		$('#ven_sel_reporte2').fadeIn();
		$('#ven_sel_reporte2').draggable();
    });
    $('#cerrarForm1').click(function(){
        
               // $('#ven_sel_reporte').toggle();
		$('#ven_sel_reporte').toggle();
		//$('#ven_sel_reporte2').draggable();
    });
    $('#cancelarInforme2').click(function(){
        
               // $('#ven_sel_reporte').toggle();
		$('#ven_sel_reporte2').toggle();
		//$('#ven_sel_reporte2').draggable();
    });
    $('#cerrarForm2').click(function(){
        
               // $('#ven_sel_reporte').toggle();
		$('#ven_sel_reporte2').toggle();
		//$('#ven_sel_reporte2').draggable();
    });
    
  } );
   
    function mostrar_fil(id){
	//alert(id)
	if (id=="f") {
		$('#fil-estado').slideDown();
	}else{
		$('#fil-estado').slideUp();
	};	
}
function mostrar_inp(id){
	if (id) {
		$('#input_meses').slideDown();
	}else{
		$('#input_meses').slideUp();
	};
}
function AbiPrueba(f){
  
    var x = document.getElementById('ediF'+f).getAttribute("class");
    
    if (x=="ti-pencil-alt") {
        $( "#sample_1 #ediF"+f ).removeClass( "ti-pencil-alt" ).addClass( "ti-write" );
        $( "#sample_1 #eliF"+f ).removeClass( "ti-trash" ).addClass( "ti-archive" );
        $('#ediF'+f).attr("title","Guardar");
        $('#eliF'+f).attr("title","Archivar");
        //$('#pru_'+f).removeAttr('disabled');
        document.getElementById('pru_'+f).disabled = false;
    }else{
        $( "#sample_1 #ediF"+f ).removeClass( "ti-write" ).addClass( "ti-pencil-alt" );
        $( "#sample_1 #eliF"+f ).removeClass( "ti-archive" ).addClass( "ti-trash" );
        $('#ediF'+f).attr("title","Editat");
        $('#eliF'+f).attr("title","Eliminar");
          document.getElementById('pru_'+f).disabled = true;
          
    }
    
}
function buscarUsuario(){
    var ced = $('#criterio').val();
   var rad = ($('input:radio[name=optradio]:checked').val());
    if (ced>0) {
        
    if (ced=="123" && rad=="bus_ced") {
        $('#cont-resul-busqueda table').fadeIn();
        $("#botones-crud").fadeIn();
        $(".botones-crud").fadeIn();
    }else if (ced!="123" && rad=="bus_ced"){
        $('#cont-resul-busqueda table').fadeOut();
        alert("El paciente no existe, debera agregar los nuevos datos...");
        
        $('#form-paciente').css("display","block");
        $( "#profile" ).removeClass( "in active" );
        $("#form-eps").removeClass("active");
        $('#home').addClass("in active")
        $( '#form-paciente' ).addClass( "active" );
        $(".botones-crud").fadeOut();
        $("#botones-crud").fadeIn();
    }
    }else{
        alert("Ingrese un  numero");
        $("#botones-crud").fadeOut();
    }
}
function cargarInforme(){
   $('#ven_sel_reporte').fadeIn();
    $('#ven_sel_reporte').draggable();
}
function cambBus(id){
    
    if (id!="bus_ced") {
        $('#criterio').attr("placeholder","Numero de orden");
        $('#form-paciente').css("display","none");
        $( "#form-eps" ).addClass( "in active" );
        $( "#profile" ).addClass( "in active" );
        $('#home').removeClass("in active");
        $('#criterio').val("");
    }else{
       $('#criterio').attr("placeholder","Numero de cedula");
        $('#form-paciente').css("display","none");
        $( "#form-eps" ).addClass( "in active" );
        $( "#profile" ).addClass( "in active" );
        $('#home').removeClass("in active");
        $('#criterio').val(""); 
    }
}
///Presina enter
//Esta línea llama a la funcion InicializarEventos
addEvent(window,'load',inicializarEventos,false);


function inicializarEventos()
{
// Aquie obtienes mediante DOM el control a traves de ID 
  var ob1=document.getElementById('criterio');

// Se le agrega al objeto el evento (keypress), y la funcion que se va a ejecutar al presionar cualquie tecla...('presionar')
  addEvent(ob1,'keypress',presionar,false);
}


function presionar(e)
	{
	//Esta parrte es para IE
	if (window.event)
	  {
	           if (window.event.keyCode==13)
		{
                    buscarUsuario();
                 }// Aqui escribe el nombre tu funcion que hace la busqueda...
	  }
	  else
                    //Esto es para Firefox y creo otros navegadores
		if (e)
		{
		  if(e.which==13)
		  	{}//Igual que arriba
		}
	}
	

//Lo que hace la funcion addEvent es agregar la funcion para IE u otros navegadores, en IE es attachEvent y en los otros navegadores es addEventListener, fijense como se antepone el "on" para hacer referencia al evento para IE mientras que en los otros navegadores no es necesario...

function addEvent(elemento,nomevento,funcion,captura)
{
  if (elemento.attachEvent)
  {
    elemento.attachEvent('on'+nomevento,funcion);
    return true;
  }
  else  
    if (elemento.addEventListener)
    {
      elemento.addEventListener(nomevento,funcion,captura);
      return true;
    }
    else
      return false;
}
//fin