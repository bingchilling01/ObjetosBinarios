#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <stdbool.h>

int tuberia[2];
pid_t cliente;
int OPC_MIN = 0;
int OPC_MAX = 6;

void manejadorRestaurante(int senal);
void manejadorCliente(int senal);
void manejadorSalida(int senal);
char *numeroPlato(int numero);
int tiempoPreparacionPlatos(int plato);


int main() {
	
	printf("Bienvenido al restaurante Mesa Paco\n\n"); // Mensaje de bienvenida
    if (pipe(tuberia) == -1) {
        printf("No se ha podido crear la tubería.");
        exit(1);
    }

    switch (cliente = fork()) {
        case -1:
            printf("No se ha podido crear el cliente.");
            exit(1);
        case 0: // Proceso CLIENTE
            close(tuberia[1]); // Se cierra la tubería para evitar errores
            signal(SIGUSR2, manejadorCliente); // Se ejecuta la función "manejadorCliente" si se recibe la SIGUSR2
            signal(SIGTERM, manejadorSalida); // Se ejecuta la función "manejadorSalida" si se recibe la SIGTERM
            kill(getppid(), SIGUSR1);
            while (1) {
                pause();
            }
            break;
        default: // Proceso RESTAURANTE
            close(tuberia[0]); // Se cierra la tubería para evitar errores
            signal(SIGUSR1, manejadorRestaurante); // Se ejecuta la función "manejadorRestaurante" si se recibe la SIGUSR1
            while (1) {
                pause();
            }
			break;
    }
    return 0;
}

// Manejador de señales para SIGUSR1 (pedido recibido por el restaurante)
void manejadorRestaurante(int senal) {
	int platoElegido;
	bool correcto = false;
	
	while (!correcto) {
		printf("¿Qué quieres comer hoy?\n\n");
		for (int i = OPC_MIN+1; i <= OPC_MAX; i++) {
			printf("%d. %s\n", i, numeroPlato(i));
		}
		printf("\n0. Salir del restaurante\n\n");

		// Solicitar la elección al usuario
		printf("Elección: ");
		scanf("%d", &platoElegido);
		if(platoElegido < OPC_MIN || platoElegido > OPC_MAX) {
			printf("Incorrecto, vuelve a elegir\n\n");
		} else {
			correcto = true;
		}
	}
	
	if (platoElegido > 0) {
		printf("RESTAURANTE: Pedido recibido. Estamos preparando tu plato: '%s'. Tardará %d segundos en prepararse...\n", numeroPlato(platoElegido), tiempoPreparacionPlatos(platoElegido));
		sleep(tiempoPreparacionPlatos(platoElegido));
		printf("RESTAURANTE: ¡Ya tenemos tu pedido!\n");
		sleep(1);
		char *plato = numeroPlato(platoElegido);
		write(tuberia[1], plato, strlen(plato));
		kill(cliente, SIGUSR2); // Manda la señal SIGUSR2 al cliente indicando que el pedido está listo
	} else if (platoElegido == 0) {
		printf("RESTAURANTE: Gracias por su visita\n");
		kill(cliente, SIGTERM);
		exit(0);
	}
}

// Manejador de señales para SIGUSR2 (pedido ya preparado para el cliente)
void manejadorCliente(int senal) {
    sleep(1);
    char nombrePlato[40];
    read(tuberia[0], nombrePlato, sizeof(nombrePlato));
    printf("CLIENTE: Acabo de recibir mi plato: '%s' ¡Gracias!\n", nombrePlato);
    sleep(1);
    printf("\n----------------------\n\n");
    kill(getppid(), SIGUSR1); // Simular nuevo pedido
}

// Manejador de la señal SIGTERM
void manejadorSalida(int senal) {
    printf("CLIENTE: ¡Adiós!\n");
}

// Función que convierte el número de elección en el nombre del plato correspondiente
char *numeroPlato(int numero) {
    char *nombrePlato;
    switch (numero) {
		case 1:
			nombrePlato = "Tortilla de patatas";
			break;
        case 2:
            nombrePlato = "Patatas bravas";
            break;
        case 3:
            nombrePlato = "Carne con tomate";
            break;
        case 4:
            nombrePlato = "Calamares fritos";
            break;
        case 5:
            nombrePlato = "Pinchitos de pollo";
            break;
        case 6:
            nombrePlato = "Ensalada de pasta";
            break;
    }
    return nombrePlato;
}

// Función que define el tiempo que se tarda en preparar cada plato
int tiempoPreparacionPlatos(int plato) {
    int segundos;
    switch (plato) {
        case 1:
            segundos = 10;
            break;
        case 2:
            segundos = 6;
            break;
        case 3:
            segundos = 9;
            break;
        case 4:
            segundos = 5;
            break;
        case 5:
            segundos = 7;
            break;
		case 6: 
			segundos = 3;
    }
    return segundos;
}