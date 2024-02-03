const atchivos = require('fs');
const { log } = require('console');
const readline = require('readline');
//este seria mi interfaz de escritura pero al final decide utilizar la otra
const teclado = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});
/////////////////////////////////////////////////////////////////////////////////
///traduzco el evento no lo quiero en ingles pero hace lo mismo
function traducirEvento(evento) {
    switch (evento) {
        case 'change':
            return 'cambio';
        case 'rename':
            return 'renombrar';
        default:
            return evento;
    }
}
const rutaObservada = 'C:/Gabriela Rio_C/Ruta_Observable/BaseDatos.txt';
//no quiero manejar la ruta actual quiero otra Ruta por lo que utilizo la Constante de arriba
const currentDirectory = process.cwd();
///escribirmos una interfaz para escribir el exit para salir de la monotirizacion
const escribir = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});
//funcion principal
function monitorDirectory(directory) {
    console.log("Monitoreando el directorio=>"+" "+directory);
    const watcher = atchivos.watch(directory,{ recursive: true }, (eventoInformar, filename) => {
        console.log('Archivo'+filename);
        console.log('Evento'+traducirEvento(eventoInformar))
    });
    escribir.on('line', (salir) => {
        if (salir=="exit") {
            console.log("Deteniendo el monitoreo");
            watcher.close();
            escribir.close();
        }
    });
}

monitorDirectory(rutaObservada);


