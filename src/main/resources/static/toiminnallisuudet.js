		var kayttis = $("#kayttis").attr("data-kayttaja");
		haeKayttaja(kayttis);
		var hetki = new Date();
		var nytten = [hetki.getFullYear(), hetki.getMonth()+1, hetki.getDate(), hetki.getHours()];
		var nyt = [];
		var koskettu, muokataan = false;
		// lisätään vanhoille tunneille .wanha class sivunlatauksen yhteydessä
		var suoritaladatessa = function() {
			for(var k of nytten) {
				if (k < 10) {
					k = "0" + k;
				}
				nyt.push(k);
			}
			nyt = nyt.join("");
			
			$(".tunti").each(function() {
				var elemid = $(this).attr("id");
				var tunninid = elemid.split("/").join("");
				if(tunninid < nyt) {
					$(this).addClass("wanha");
				}
			});
		}
		
		suoritaladatessa();
		
		// värit varauksille ja huolloille
		$('.huolto').css('background-color', '#baa29d');
		$('.lentovaraus').each(function(){
			var varijono = ['#DAB5FF', '#ffb593', '#b3f0c8', '#ff93d5', '#93daff', '#ff9393', '#a293ff', '#fffb93'];
			var varaajanid = $(this).attr("data-varaaja");
			$(this).css('background-color', varijono[varaajanid-1]);
		});
		
		// hover-simulointi; nitoo yhteen kaikki samaan varaukseen liittyvät divit
		$('.lentovaraus, .huolto').mouseover(function() {
			var varauksenid = $(this).attr("rel");
			$('div[rel='+varauksenid+']').css('opacity', '1');
		});
		
		$('.lentovaraus, .huolto').mouseout(function() {
			var varauksenid = $(this).attr("rel");
			if ($(this).parent().is(".wanha")) {
				$('div[rel='+varauksenid+']').css('opacity', '.6');
			} else {
				$('div[rel='+varauksenid+']').css('opacity', '.9');
			}

		});

		// jos klikattu varausta, hae varauksen tiedot
		$('.lentovaraus, .huolto').click(function() {
			var varauksenid = $(this).attr("rel");
			haeVaraus(varauksenid);
		});
		
		// tyhjennä & piilota jos klikattu jotain näistä
		$(".verho, #suljenappi i, #peruutamuutokset").click(function(e) {
			if(e.target !== this) {
				return;
			}
			tyhjennaVerho();
			$("#varaustable").show();
			// muokkausikkunan tyhjäys
			muokataan = false;
			$("#muokkaustable").hide();
			$("#malkudate, #mloppudate").datepicker("setDate", null);
			$("malkupicker, #mloppupicker").val(null);
		});
		
		// tyhjennä & piilota jos klikattu jotain näistä
		$(".varausverho, #suljevaraus i, #peruutavaraus").click(function(e) {
			if(e.target !== this) {
				return;
			}
			tyhjennaVarausverho();
		});
		
		// varausnapin klikki
		$(document).on('click', '#varausnappi', function(e){
			koskettu = true;
			$(".varausverho").fadeIn();
			var vikapv = $("#vikapv").text();
			
			var alkuv = $(this).parent().attr("id").split("/")[0];
			var alkukk = $(this).parent().attr("id").split("/")[1];
			var alkupv = $(this).parent().attr("id").split("/")[2];
			var alkuh = $(this).parent().attr("id").split("/")[3];
			
			// datepickerin alustus klikatun päivän perusteella
			$( "#alkudate" ).datepicker( "setDate", alkupv + "." + alkukk + "." + alkuv );
			$( "#loppudate" ).datepicker( "setDate", alkupv + "." + alkukk + "." + alkuv );
			
			// timepickerin alustus klikatun tunnin perusteella
			var aika = alkupv + "." + alkukk + "." + alkuv + " " + alkuh + ":30";
			if ($( "div[data-loppu='" + aika + "']").length > 0) {
				$( "#alkupicker" ).val( alkuh + ":30" );
				$( "#loppupicker" ).val( (parseInt(alkuh) + 1) + ":30" );
			} else {
				$( "#alkupicker" ).val( alkuh + ":00" );
				$( "#loppupicker" ).val( (parseInt(alkuh) + 1) + ":00" );
			}

		});
		
		// tuntisolun klikkifunktio
		$(".tunti").not(':parent').click(function(e) {
			// älä suorita funktiota jos klikattu jotain child-elementtiä
			if(e.target !== this) {
				return;
			}
			var elemid = $(this).attr("id");
			var tunninid = elemid.split("/").join("");
			// jos tunti on jo mennyt, älä suorita loppuja
			if(tunninid < nyt) {
				return;
			}
			var muodostaaika = elemid.split("/");
			var dataloppu = muodostaaika[2] + "." + muodostaaika[1] + "." + muodostaaika[0] + " " + muodostaaika[3] + ":30";
			$("#varausnappi").remove();
			if ($( "div[data-loppu='" + dataloppu + "']").length > 0) {
				$(this).append("<button id=\"varausnappi\" class=\"btn btn-sm btn-success\" style=\"margin-left:50%;\">VARAA</button>");
			} else {
				$(this).append("<button id=\"varausnappi\" class=\"btn btn-sm btn-success\">VARAA</button>");
			}
		});
		
		// muokkausnapin klikkifunktio
		$(document).on('click', '#muokkaavarausta', function(e){
			$("#poistoloota").html("");
			$("#varaustable").hide();
			$("#muokkaustable").show();
			muokataan = true;
		});

		function haeVaraus(id) {
			$.ajax({
			    url: "/varaukset/"+id,
			    type: "GET",
			    dataType: "json",
			    data: {},
			    success: function(data){
			        naytaVaraus(data);
			    },
			    error: function(error){
			         alert("Error, errorin kuvaus consolessa");
			         console.log(error);
			    }
			});
		}
		
		function haeKayttaja(uname) {
			$.ajax({
			    url: "/kayttajat/"+uname,
			    type: "GET",
			    dataType: "json",
			    data: {},
			    success: function(data){
			        $("#kayttis").html(data.etunimi + " " + data.sukunimi);
			    },
			    error: function(error){
			         alert("Käyttäjätietojen hakeminen ei onnistunut. Ota yhteys järjestelmän ylläpitäjään.");
			         console.log(error);
			    }
			});
		}
		
		function naytaVaraus(data) {
			// verrataan onko varauksen loppuaika jo mennyt
			var loppuolio = new Date(data.loppu);
			var onkovanha = hetki > loppuolio;
			
			// jos varaus ei ole vanha, tulosta muokkaus- ja poistopainike jos valtuudet riittää
			if ((kayttis == data.user.username || kayttis == "admin") && !onkovanha) {
				$("#poistoloota").append("<div><button class=\"btn btn-outline-primary\" id=\"muokkaavarausta\">Muokkaa varausta</button> <button class=\"btn btn-outline-danger\" onclick=\"vahvistaPoisto()\">Poista varaus</button></div>")
			}
			// täytetään muokkausikkuna
			$("#mid").val(data.id);
			// muokkaus-datepickerin alustus varauksen päivän perusteella
			var alkud = new Date(data.alku);
			var loppud = new Date(data.loppu);
			$( "#malkudate" ).datepicker( "setDate", alkud );
			$( "#mloppudate" ).datepicker( "setDate", loppud );
			
			// muuokkaus-timepickerin alustus varauksen tunnin perusteella
			var varausah = alkud.getHours();
			var varausamin = alkud.getMinutes();
			var varauslh = loppud.getHours();
			var varauslmin = loppud.getMinutes();
				$( "#malkupicker" ).val( varausah + ":" + (varausamin<10?'0':'') + varausamin );
				$( "#mloppupicker" ).val( varauslh + ":" + (varauslmin<10?'0':'') + varauslmin );
			
			
			// sijoitetaan varauksen tiedot paikoilleen ja feidataan ikkuna näkyviin
			$("#varausid").html(data.id);
			$("#varausotsikko").append("<span>"+ data.kategoria.name +"</span>");
			$("#varaajaetu").append("<span>" + data.user.etunimi + " " + data.user.sukunimi + "</span>")
			$("#varaajapuhelin").append("<span>p. " + data.user.phone + ", " + data.user.email + "</span>")
			$("#selite").append("<span>" + data.selitys + "</span>")
			$("#mvarauksenkuvaus").val(data.selitys); // muokkausikkunaan
			var valku = formatoiPaivays(data.alku);
			var vloppu = formatoiPaivays(data.loppu);
			$("#varausalku").append("<span>" + valku + "</span>");
			$("#varausloppu").append("<span>" + vloppu + "</span>");
			$(".verho").fadeIn();
		}
		
		// tyhjennysfunktio varauksen tarkasteluikkunalle
		function tyhjennaVerho() {
			$(".verho").hide();
			$("#varausid, #varaajaetu, #varausotsikko, #varaajapuhelin, #varausalku, #varausloppu, #selite, #poistoloota").html("");
		}
		
		// tyhjennysfunktio varauksen lisäysikkunalle
		function tyhjennaVarausverho() {
			$(".varausverho").hide();
		}
		
		function formatoiPaivays(input) {
			  function pad(s) { return (s < 10) ? '0' + s : s; }
			  var d = new Date(input)
			  var paivaysosa = [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('.')
			  var tuntiosa = [pad(d.getHours()), pad(d.getMinutes())].join(':')
			  return paivaysosa + " klo " + tuntiosa
		}
		
		function vahvistaPoisto() {
			var poistettavaid = $("#varausid").html();
			if (confirm('Haluatko varmasti poistaa tämän varauksen?')) {
			    location.href = '/delete/'+poistettavaid;
			} else {
			    return;
			}
		}
		
		// vahtifunktio sille ettei varauksen loppu voi olla ennen alkua
		function vahtaaKestoa(val) {
			if (koskettu == true) {
				var alkupaiva = $( "#alkudate" ).datepicker( "getDate" );
				var loppupaiva = $( "#loppudate" ).datepicker( "getDate" );
				var alkutunnit = $( "#alkupicker" ).val();
				var lopputunnit = $( "#loppupicker" ).val();
				let [ahour, aminute] = alkutunnit.split(':');
				let [lhour, lminute] = lopputunnit.split(':');
				alkupaiva.setHours(ahour);
				alkupaiva.setMinutes(aminute);
				loppupaiva.setHours(lhour);
				loppupaiva.setMinutes(lminute);
				
				if(loppupaiva < alkupaiva) {
					alert("Varauksen loppu ei voi olla ennen sen alkua!\nMuokkaa äsken antamaasi arvoa.");
					$("#vahvistavaraus").prop("disabled", true);
					return false;
				} else if ((loppupaiva-alkupaiva)/1000/60 < 60) { // jos kesto alle 1h
					alert("Varauksen minimikesto on 1 tunti! Muokkaa äsken antamaasi arvoa.");
					$("#vahvistavaraus").prop("disabled", true);
					return false;
				} else {
					$("#vahvistavaraus").prop("disabled", false);
					return true;
				}
			} else if (muokataan == true) {
				var alkupaiva = $( "#malkudate" ).datepicker( "getDate" );
				var loppupaiva = $( "#mloppudate" ).datepicker( "getDate" );
				var alkutunnit = $( "#malkupicker" ).val();
				var lopputunnit = $( "#mloppupicker" ).val();
				let [ahour, aminute] = alkutunnit.split(':');
				let [lhour, lminute] = lopputunnit.split(':');
				alkupaiva.setHours(ahour);
				alkupaiva.setMinutes(aminute);
				loppupaiva.setHours(lhour);
				loppupaiva.setMinutes(lminute);
				
				if(loppupaiva < alkupaiva) {
					alert("Varauksen loppu ei voi olla ennen sen alkua!\nMuokkaa äsken antamaasi arvoa.");
					$("#tallennamuutokset").prop("disabled", true);
					return false;
				} else if ((loppupaiva-alkupaiva)/1000/60 < 60) { // jos kesto alle 1h
					alert("Varauksen minimikesto on 1 tunti! Muokkaa äsken antamaasi arvoa.");
					$("#tallennamuutokset").prop("disabled", true);
					return false;
				} else {
					$("#tallennamuutokset").prop("disabled", false);
					return true;
				}
			}
			
		}
		// datepicker alustus
		function kutsuPickeria() {
		    $( ".datepicker" ).datepicker({ minDate: -0, maxDate: "+2Y", onSelect: function(e) { if (vahtaaKestoa(e) == false && e != null) { $(this).datepicker("setDate", null) }} });
		    $( ".datepicker" ).datepicker( "option", "showAnim", "fadeIn" );
		    $( ".datepicker" ).datepicker( "option", "firstDay", 1 );
		    $( ".datepicker" ).datepicker( "option", "dateFormat", "dd.mm.yy" );
		}
		kutsuPickeria();
		  
		// timepicker alustus
		$('.timepickerit').timepicker({
		    timeFormat: 'HH:mm',
		    interval: 30,
		    minTime: '12:00am',
		    maxTime: '11:59pm',
		    startTime: '00:00',
		    dynamic: false,
		    dropdown: true,
		    scrollbar: true,
		    change: function(e) { if (vahtaaKestoa(e) == false && e != null) { $(this).val(null) }}
		});
		
		// add: varauksen muuntaminen tietokantaan lähetystä varten
		$("#vahvistavaraus").click(function(){
			var alkudeitti = new Date();
			var alkuarvo = $("#alkudate").datepicker( "getDate" );
			var alkutiima = $("#alkupicker").timepicker( "getTime" );
			var loppudeitti = new Date();
			var loppuarvo = $("#loppudate").datepicker( "getDate" );
			var lopputiima = $("#loppupicker").timepicker("getTime");
			
			alkudeitti.setFullYear(alkuarvo.getFullYear(), alkuarvo.getMonth(), alkuarvo.getDate());
			alkudeitti.setHours(alkutiima.getHours(), alkutiima.getMinutes(), 0, 0);
			loppudeitti.setFullYear(loppuarvo.getFullYear(), loppuarvo.getMonth(), loppuarvo.getDate());
			loppudeitti.setHours(lopputiima.getHours(), lopputiima.getMinutes(), 0, 0);
			
			// muunnetut arvot lopullisiin kenttiin
			$("#salku").val(alkudeitti.toISOString());
			$("#sloppu").val(loppudeitti.toISOString());
			
			$("#varausform").submit();
		});
		
		// edit: varauksen muuntaminen tietokantaan lähetystä varten
		$("#tallennamuutokset").click(function(){
			var alkudeitti = new Date();
			var alkuarvo = $("#malkudate").datepicker( "getDate" );
			var alkutiima = $("#malkupicker").timepicker( "getTime" );
			var loppudeitti = new Date();
			var loppuarvo = $("#mloppudate").datepicker( "getDate" );
			var lopputiima = $("#mloppupicker").timepicker("getTime");
			
			alkudeitti.setFullYear(alkuarvo.getFullYear(), alkuarvo.getMonth(), alkuarvo.getDate());
			alkudeitti.setHours(alkutiima.getHours(), alkutiima.getMinutes(), 0, 0);
			loppudeitti.setFullYear(loppuarvo.getFullYear(), loppuarvo.getMonth(), loppuarvo.getDate());
			loppudeitti.setHours(lopputiima.getHours(), lopputiima.getMinutes(), 0, 0);
			
			// muunnetut arvot lopullisiin kenttiin
			$("#malku").val(alkudeitti.toISOString());
			$("#mloppu").val(loppudeitti.toISOString());
			
			$("#muokkausform").submit();
		});