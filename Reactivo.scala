import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import java.util.Scanner
import scala.collection.mutable.ListBuffer
object Reactivo {
  def main(args: Array[String]): Unit = {
    var tecladoReactivo: Scanner = new Scanner(System.in)
    case class usuario(nombre: String, contra: String, edad: String, estudios: String, nacionalidad: String, civil: String, listaAmigos: ListBuffer[usuario], listaMensajes: ListBuffer[(String, String, usuario)], tuplaNotificacion: ListBuffer[(String, usuario)])
    case class messenger(contrasena: String, mensaje: String)
    var listMessenger: ListBuffer[messenger] = ListBuffer[messenger]()
    var baseDatos: ListBuffer[usuario] = ListBuffer[usuario]()
    var listEspecial: ListBuffer[usuario] = ListBuffer[usuario]()
    var listaPersona: ListBuffer[usuario] = ListBuffer[usuario]()
    var tuplaObjeto: ListBuffer[(String, usuario)] = ListBuffer[(String, usuario)]()
    var tuplaObjeto2: ListBuffer[(String, usuario)] = ListBuffer[(String, usuario)]()
    val miListaMensajes1: ListBuffer[(String, String, usuario)] = ListBuffer[(String, String, usuario)]()
    val miListaMensajes2: ListBuffer[(String, String, usuario)] = ListBuffer[(String, String, usuario)]()
    val listanula: ListBuffer[usuario] = ListBuffer[usuario]()
    val usu1: usuario = usuario("maria ortega", "123", "34", "utpl", "española", "soltera", listanula, miListaMensajes1, tuplaObjeto)
    val miLista: ListBuffer[usuario] = ListBuffer[usuario]()
    val usu2: usuario = usuario("carlos guaman", "0908", "20", "berlin", "ecuatoriana", "soltero", miLista, miListaMensajes2, tuplaObjeto2)
    baseDatos += usu1
    baseDatos += usu2
    var estiudent: String = "Actualmente=>Estudiando"
    var reaccion: Boolean = false
    var indiceTaken: Int = 0
    var indiceArray: Int = 0
    var followenIndice: Int = 0
    var flagIndice: Int = 0
    var flagLine: Boolean = true
    var flagLine2: Boolean = true
    var flagAdd: Boolean = true
    var notificacionesFlag: Boolean = false
    var enter: Boolean = true
    var filtroChats: Boolean = false
    var tamaUnico: Boolean = true
    var notSave: Boolean = false
    var justificacionChat: Boolean = true
    //////FUNCIONES REACTIVAS///////////
    def reactivoMessenger(usuarioMensaje: String, listaRecursiva: ListBuffer[usuario]): (String, String) = {
      var notification: String = "Nuevas Notificaciones Tienes Pendientes"
      var mensaje: String = null
      class ActorOne extends Actor {
        def receive(): Receive = {
          case _ =>
            context.stop(self)
        }
      }
      val system: ActorSystem = ActorSystem("SistemaMensajes")
      val myActor: ActorRef = system.actorOf(Props[ActorOne], "Objeto")
      myActor ! usuarioMensaje
      /////////////////////////////////
      if (usuarioMensaje.equals("Seguir")) {
        var corroborar: String = listaRecursiva(0).nombre
        println(s"el usuario ${corroborar} ha sido añadido a tu lista de amigos")
      } else {
        mensaje = usuarioMensaje
        var notiPendeientes: String = "Nuevos Mensajes Tienes Pendientes"
        notification = notiPendeientes
        println(s"El mensaje ha sido enviado al usuario ${listaRecursiva(0).nombre} Correctamente")
      }
      //////////////////////////////////////
      system.terminate()
      (notification, mensaje)
    }
    ////////////////////////////////////
    def perfilBaseDatos(baseDatos: ListBuffer[usuario]): Unit = {
      if (reaccion) {
        println(" _______")
        println("|       |" + " " + baseDatos(0).nombre)
        println("|   O   |" + " " + "EDAD=>" + " " + baseDatos(0).edad)
        println("|  /|\\  |" + " " + "ESTUDIOS=>" + " " + baseDatos(0).estudios)
        println("|  / \\  |" + " " + "NACIONALIDAD=>" + " " + baseDatos(0).nacionalidad)
        println("|_______|" + " " + "ESTADO CIVIL=>" + " " + baseDatos(0).civil)
        println("Escriba si para volver Atras: ")
        tecladoReactivo.nextLine()
        var retroceso: String = tecladoReactivo.nextLine()
        if (retroceso.toLowerCase().equals("si")) {
          reaccion = false
          menuReactivo()
        } else {
          println("error debe escribir si o no")
          perfilBaseDatos(baseDatos)
        }
      } else {
        println(" _______")
        println("|       |" + " " + baseDatos(0).nombre)
        println("|   O   |" + " " + "EDAD=>" + " " + baseDatos(0).edad)
        println("|  /|\\  |" + " " + "ESTUDIOS=>" + " " + baseDatos(0).estudios)
        println("|  / \\  |" + " " + "NACIONALIDAD=>" + " " + baseDatos(0).nacionalidad)
        println("|_______|" + " " + "ESTADO CIVIL=>" + " " + baseDatos(0).civil)
        tecladoReactivo.nextLine()
        println("Desea Seguir a este Perfil o mandar Un mensaje")
        println("Si desea volver Atras solo escriba si:")
        var perfil: String = tecladoReactivo.nextLine()
        if (perfil.toLowerCase().equals("seguir")) {
          var guardarUsuario: usuario = listEspecial(0).copy(contra = " ")
          var notificacion: (String, usuario) = (addFriends(baseDatos), guardarUsuario)
          baseDatos(0).tuplaNotificacion += notificacion
          if (notificacionesFlag) {
            notificacionesFlag = false
            menuReactivo()
          } else {
            followenIndice += 1
            baseDatos.clear()
            evalucion(baseDatos)
          }
        } else if (perfil.toLowerCase().equals("mensaje")) {
          //llamada de funcion mensaje y funcion reactiva
        } else if (perfil.toLowerCase().equals("si")) {
          if (notificacionesFlag) {
            notificacionesFlag = false
            menuReactivo()
          } else {
            followenIndice += 1
            baseDatos.clear()
            evalucion(baseDatos)
          }
        } else {
          println("Error solo puede seguir al usuario o mandarle un Mensaje")
          perfilBaseDatos(baseDatos)
        }
      }
    }
    def searchPerson(caseClasUsuarios: ListBuffer[usuario], argumento: String): ListBuffer[usuario] = {
      var listaLocalSearch: ListBuffer[usuario] = ListBuffer[usuario]()
      val nombreDesfragmentado: ListBuffer[Array[Char]] = caseClasUsuarios.map(datos => datos.nombre.toCharArray.take(4))
      val desfragmentacion: String = argumento.toCharArray.take(4).mkString
      nombreDesfragmentado.foreach { array =>
        if (array.mkString.equals(desfragmentacion)) {
          listaLocalSearch += caseClasUsuarios(indiceArray)
          indiceArray += 1
        } else {
          indiceArray += 1
        }
      }
      indiceArray = 0
      listaLocalSearch
    }
    def evalucion(obtenerTupla: ListBuffer[usuario]): Unit = {
      var tamaFollowen: Int = 0
      var pocicionDelChat: Int = 0
      if (filtroChats) {
        if (tamaUnico) {
          tamaFollowen = listEspecial(0).listaMensajes.length
          tamaUnico = false
        }
      } else {
        tamaFollowen = listEspecial(0).tuplaNotificacion.length
      }
      if (followenIndice == tamaFollowen) {
        println("Tienes 0 Notificaciones => Estas Al Dia")
        followenIndice = 0
        listEspecial(0).tuplaNotificacion.clear()
        listEspecial(0).listaMensajes.clear()
        filtroChats = false
        menuReactivo()
      } else {
        if (filtroChats) {
          def mensajes(): Unit = {
            if (followenIndice == tamaFollowen){
              println("Tienes 0 Mensajes => Estas Al Dia")
              followenIndice=0
              filtroChats=false
              evalucion(obtenerTupla)
            }else{
              println(s"Tienes ${listEspecial(0).listaMensajes.length} Mensajes de=>")
              listEspecial(0).listaMensajes.foreach(pocicionTupla => println(s"${flagIndice + 1}.${pocicionTupla._3.nombre}"))
              flagIndice = 0
              println(s"Escoge del 1 al ${listEspecial(0).listaMensajes.length} para elegir la persona para Ver el mensaje: ")
              var seleccion: Int = tecladoReactivo.nextInt()
              if (seleccion <= listEspecial(0).listaMensajes.length && seleccion > 0) {
                println("-------------------------------------------------------------")
                println("                           MENSAJE                           ")
                println("-------------------------------------------------------------")
                println(s"${listEspecial(0).listaMensajes(seleccion-1)._3.nombre} => ${listEspecial(0).listaMensajes(seleccion - 1)._2}")
                println("--------------------------------------------------------------")
                println("\n")
                println("Conteste el Mensaje si desea volver atras escriba atras")
                tecladoReactivo.nextLine()
                var respuestas: String = tecladoReactivo.nextLine()
                if (respuestas.toLowerCase().equals("atras")) {
                  listEspecial(0).listaMensajes.remove(seleccion - 1)
                  followenIndice+=1
                  mensajes()
                } else {
                  whatssap(listEspecial(0).listaMensajes(seleccion - 1)._3, respuestas)
                  followenIndice+=1
                  mensajes()
                }
              } else {
                println("Error numero no valido")
                mensajes()
              }
            }
          }
          mensajes()
        } else {
          def notificaciones(): Unit = {
            obtenerTupla += listEspecial(0).tuplaNotificacion(followenIndice)._2
            println(s"Te acaba de Seguir el Usuario =>${" " + obtenerTupla(0).nombre}")
            eleccion()
          }
          notificaciones()
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////
        def eleccion(): Unit = {
          println("Desea ver el perfil o seguir con las notificacion escriba si/no si para ver el perfil")
          tecladoReactivo.nextLine()
          var perfil: String = tecladoReactivo.nextLine()
          if (perfil.toLowerCase().equals("si")) {
            perfilBaseDatos(obtenerTupla)
          } else if (perfil.toLowerCase().equals("no")) {
            followenIndice += 1
            obtenerTupla.clear()
            evalucion(obtenerTupla)
          } else {
            println("error seleciona si o no")
            eleccion()
          }
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////
      }
    }
    def addFriends(perfilSeleccionado: ListBuffer[usuario]): String = {
      var mensajeString: String = null
      var guardarUsuario: usuario = perfilSeleccionado(0).copy(contra = " ")
      listEspecial(0).listaAmigos += guardarUsuario
      mensajeString= reactivoMessenger("Seguir", perfilSeleccionado)._1
      mensajeString
    }
    def whatssap(usuarioMensaje: usuario, mensaje: String): Unit = {
      var guardarUsuario: ListBuffer[usuario] = ListBuffer[usuario]()
      if (filtroChats) {
        var emisorYo: usuario = listEspecial(0).copy(contra = " ")
        guardarUsuario += usuarioMensaje
        var devolucionReactiva: (String, String) = reactivoMessenger(mensaje, guardarUsuario)
        var tupla3: (String, String, usuario) = (devolucionReactiva._1, devolucionReactiva._2, emisorYo)
        usuarioMensaje.listaMensajes += tupla3
        guardarUsuario.clear()
      } else {
        ///aqui cuando se envie el mensaje desde el perfil osea que no es su amigo
      }
    }
    ///////////////////////////////////
    def menuReactivo(): Unit = {
      println("------------------------------------")
      println("Bienbenido=> " + " " + listEspecial(0).nombre)
      println("------------------------------------")
      if (listEspecial(0).tuplaNotificacion.nonEmpty) {
        println(s"(${listEspecial(0).tuplaNotificacion.length})" + " " + listEspecial(0).tuplaNotificacion(0)._1)
        println("------------------------------------")
      }
      if (listEspecial(0).listaMensajes.nonEmpty) {
        println(s"(${listEspecial(0).listaMensajes.length})" + " " + listEspecial(0).listaMensajes(0)._1)
        println("------------------------------------")
      }
      println("1.Mandar Un mensaje a tus Contactos")
      println("------------------------------------")
      println("2.Buscar Personas                   ")
      println("------------------------------------")
      println("3.Eliminar Contactos                ")
      println("-------------------------------------")
      println("4.Ver perfil de tu Cuenta            ")
      println("-------------------------------------")
      println("5.Publicar una Hisoria               ")
      println("-------------------------------------")
      println("6.Cerrar Seccion                     ")
      println("-------------------------------------")
      if (listEspecial(0).tuplaNotificacion.nonEmpty || listEspecial(0).listaMensajes.nonEmpty) {
        println("7.ver la Notificacion                ")
        println("-------------------------------------")
      }
      println("Eliga una Opcion: ")
      var eleccion: Int = tecladoReactivo.nextInt()
      eleccion match {
        case 1 => {
          if (listEspecial(0).listaAmigos.nonEmpty) {
            var decicionTma: Int = listEspecial(0).listaAmigos.length
            listEspecial(0).listaAmigos.foreach(listaFriends => println(s"${flagIndice + 1}.${listaFriends.nombre}" + "\n"))
            flagIndice = 0

            def listaAmigos(): Unit = {
              println(s"Escoge del 1 al ${decicionTma} para elegir a quien enviarle el mensaje")
              var mensaje: Int = tecladoReactivo.nextInt()
              if (mensaje <= decicionTma && mensaje > 0) {
                var whatssapUsuario: usuario = listEspecial(0).listaAmigos(mensaje - 1)
                println("SI QUIERE REGRESAR AL MENU SOLO ESCRIBA MENU=>")
                println("Texto=> ")
                tecladoReactivo.nextLine()
                var mensajeDirecto: String = tecladoReactivo.nextLine()
                if (mensajeDirecto.toLowerCase().equals("menu")) {
                  menuReactivo()
                } else {
                  filtroChats = true
                  whatssap(whatssapUsuario, mensajeDirecto)
                  menuReactivo()
                }
              } else {
                println(s"Error. solo hay $decicionTma Resultados Seleciona cualquiera de ellas")
                listaAmigos()
              }
            }

            listaAmigos()
          } else {
            println("No tienes Ningun Amigo Agregado")
            menuReactivo()
          }
        }
        case 2 => {
          def busqueda(): Unit = {
            var perfilSeleccionado: ListBuffer[usuario] = ListBuffer[usuario]()
            println("Escribe el Nombre de la persona para Buscar: ")
            tecladoReactivo.nextLine()
            var argumento: String = tecladoReactivo.nextLine()
            var devolucion: ListBuffer[usuario] = searchPerson(baseDatos, argumento)
            if (!devolucion.isEmpty) {
              var tam: Int = devolucion.length
              devolucion.foreach(usuario => println(s"${flagIndice + 1}.${"NOMBRE:" + " " + usuario.nombre + "\n"} => ${"EDAD:" + " " + usuario.edad + "\n"} => ${"NACIONALIDAD:" + " " + usuario.nacionalidad + "\n"}"))

              def forRecursivo(): Unit = {
                println(s"Escoge del 1 al $tam para elegir la persona para ver su perfil: ")
                var eleccion: Int = tecladoReactivo.nextInt()
                if (eleccion <= tam && eleccion > 0) {
                  perfilSeleccionado += devolucion(eleccion - 1)
                  flagIndice = 0
                  perfilBaseDatos(perfilSeleccionado)
                } else {
                  println(s"Error. solo hay $tam Resultados Seleciona cualquiera de ellas")
                  forRecursivo()
                }
              }

              forRecursivo()
            } else {
              println("Se Encontraron 0 Resultados")
              busqueda()
            }
          }
          notificacionesFlag = true
          busqueda()
        }
        case 3 => {
        }
        case 4 => {
          reaccion = true
          perfilBaseDatos(listEspecial)
        }
        case 5 => {
        }
        case 6 => {
          println("Cerrando Seccion")
          listEspecial.clear()
          menuPrincipal()
        }
        case 7 => {
          if (listEspecial(0).tuplaNotificacion.nonEmpty || listEspecial(0).listaMensajes.nonEmpty) {
            if (listEspecial(0).listaMensajes.nonEmpty) {
              println("MENSAJES NUEVOS")
              filtroChats = true
            }
            if (listEspecial(0).tuplaNotificacion.nonEmpty) {
              println("NOTIFICACION")
              var obtenerTupla: ListBuffer[usuario] = ListBuffer[usuario]()
              evalucion(obtenerTupla)
            }
            /////funcion de ha publicado una historia////
          } else {
            println("Error eleccion no Valida")
            menuReactivo()
          }
        }
      }
    }
    def menuPrincipal(): Unit = {
      println("---------------------------------")
      println("MENU REACTIVO=>Elige Una Opcion ")
      println("---------------------------------")
      println("1.Entrar a la Cuenta             ")
      println("---------------------------------")
      println("2.Registrarte en la pagina       ")
      println("---------------------------------")
      println("3.Salir                          ")
      println("---------------------------------")
      println("Eliga una Opcion: ")
      var eleccion: Int = tecladoReactivo.nextInt()
      eleccion match {
        case 1 => {
          def oneSecion(): Unit = {
            tecladoReactivo.nextLine()
            println("BIENBENIDO A =>SPACE_X")
            println("escribe tu Contraseña: ")
            var contrSecion: String = tecladoReactivo.nextLine()
            var tamList: Int = baseDatos.length
            def busquedaSecion(): Unit = {
              if (indiceTaken == tamList) {
                println("lo siento Usuario No Registrado")
                indiceTaken = 0
                menuPrincipal()
              } else {
                var encontrado: String = baseDatos(indiceTaken).contra
                if (contrSecion.equals(encontrado)) {
                  listEspecial += baseDatos(indiceTaken)
                  indiceTaken = 0
                  menuReactivo()
                }
                indiceTaken += 1
                busquedaSecion()
              }
            }
            busquedaSecion()
          }
          oneSecion()
        }
        case 2 => {
          var edad: String = "0"
          println("Registro=>Opcion Numero 2")
          println("Escribe Tu Nombre Completo: ")
          tecladoReactivo.nextLine()
          var nombre: String = tecladoReactivo.nextLine()
          println("crea Una Contraseña: ")
          var contrase: String = tecladoReactivo.nextLine()

          def ageLimit(): Unit = {
            println("Escribe tu edad: ")
            edad = tecladoReactivo.nextLine()
            if (edad.toInt <= 60) {
              return edad
            } else {
              println("edad no admitida intenta de Nuevo")
              ageLimit()
            }
          }
          ageLimit()
          println("donde Estudiastes o eres estudiante: ")
          var estudiante: String = tecladoReactivo.nextLine()
          if (estudiante.toLowerCase().equals("soy estudiante")) {
            estudiante = estiudent
          }
          println("Escribe tu nacionalidad: ")
          var nacionalidad: String = tecladoReactivo.nextLine()

          def civilFuntion(): Unit = {
            println("Estado Civil Actual: ")
            var civil: String = tecladoReactivo.nextLine()
            if (civil.toLowerCase().equals("casado") || civil.toLowerCase().equals("soltero") || civil.toLowerCase().equals("divorsiado")) {
              var listaAmigos: ListBuffer[usuario] = ListBuffer[usuario]()
              var listaMensajes: ListBuffer[(String, String, usuario)] = ListBuffer[(String, String, usuario)]()
              var notificacion: String = null
              var tuplaObjeto: ListBuffer[(String, usuario)] = ListBuffer[(String, usuario)]()
              var instancia: usuario = usuario(nombre, contrase, edad, estudiante, nacionalidad, civil, listaAmigos, listaMensajes, tuplaObjeto)
              baseDatos += instancia
              listEspecial += instancia
              println("Registro Exitoso.Redirigiendo Pagina")
              menuReactivo()
            } else {
              println("Error estado civil Invalido")
              civilFuntion()
            }
          }
          civilFuntion()
        }
        case 3 => {
        }
      }
    }
    menuPrincipal()
  }
}
