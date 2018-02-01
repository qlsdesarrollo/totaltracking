function fLimpiarLogin() {
    document.getElementById("frmLogin:musuario").focus();
    frmLoginConta
    document.getElementById("frmLogin:musuario").value = "";

}
function fLocalStore() {
    // Store
    //localStorage.setItem("mcuenta", document.getElementById("frmLogin:mcuenta").value);
}

function redireccionarconta()
{
    var pagina = "/qlsnet/faces/login.xhtml"
    location.href = pagina
}
function redireccionarfactu()
{
    var pagina = "/qlsnet/faces/qls.xhtml"
    location.href = pagina
}
//<![CDATA[
// Definir lista de extensiones y el tipo de fichero de audio asociado. Puedes añadir más
var html5_audiotypes = {
    "mp3": "audio/mpeg",
    "mp4": "audio/mp4",
    "ogg": "audio/ogg",
    "wav": "audio/wav"
}

function createsoundbite(sound) {
    var html5audio = document.createElement('audio')
    if (html5audio.canPlayType) { //Comprobar soporte para audio HTML5
        for (var i = 0; i < arguments.length; i++) {
            var sourceel = document.createElement('source')
            sourceel.setAttribute('src', arguments[i])
            if (arguments[i].match(/.(w+)$/i))
                sourceel.setAttribute('type', html5_audiotypes[RegExp.$1])
            html5audio.appendChild(sourceel)
        }
        html5audio.load()
        html5audio.playclip = function () {
            html5audio.pause()
            html5audio.currentTime = 0
            html5audio.play()
        }
        return html5audio
    }
    else {
        return {playclip: function () {
                throw new Error('Su navegador no soporta audio HTML5')
            }}
    }
}

//Inicializar sonidos
var hover1 = createsoundbite('ButtonClick1.ogg');
//Nótese que se pueden añadir ficheros con otras extensiones para mayor compatibilidad
var click1 = createsoundbite('ButtonClick2.ogg');
//]]>

function redireccionar()
{
    var pagina = "/qlsnet"
    location.href = pagina
}

function valida(e) {
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla == 8) {
        return true;
    }

    // Patrón de entrada, en este caso solo acepta números
    patron = /[0-9.]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}

function valida2(e) {
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla == 8) {
        return true;
    }

    // Patrón de entrada, en este caso solo acepta números
    patron = /[0-9]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}
function validafecha(e) {
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla == 8) {
        return true;
    }

    // Patrón de entrada, en este caso solo acepta números
    patron = /[0-9/]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}

function verificadorusuario() {
    setInterval(function () {
        alert('Hello')
    }, 3000);
}

next = 0;
function nextel(event) {
    if (event.keyCode == 13) {
        if (next > 2) {
            next = 0
        }
        document.forms[0].elements[next].focus();
        next++;
    }
}
