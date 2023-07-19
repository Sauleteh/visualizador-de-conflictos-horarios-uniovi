# Visualizador de horarios de la Universidad de Oviedo
Un programa que te permite visualizar en forma de calendario los PDF de los horarios de las carreras que se cursan en la EPI Gijón de la Universidad de Oviedo con objeto de observar la distribución de las horas y si se produciría un conflicto de horarios entre asignaturas, siendo posible escoger en qué grupos (de teoría, de PL y de TG) y así poder optar por unos grupos u otros en función de si te vienen bien o no.

**Disclaimer**: Este programa no está afiliado con la Universidad de Oviedo, es un programa creado con la intención de facilitar la observación de los horarios antes de escoger y pagar qué asignaturas se quieren cursar durante un año.

## Instrucciones de uso
Al abrir el programa, aparecerá la siguiente ventana:

![image](https://github.com/Sauleteh/visualizador-de-conflictos-horarios-uniovi/assets/22859905/458ca15d-99de-4f33-9e59-c54ff65ca8d3)

Para empezar a añadir horarios al programa, haz click en el botón *Añadir* y escoge el PDF que contenga un horario. Puedes añadir un número ilimitado de horarios. Los archivos a introducir son PDF proporcionados de forma pública en la página de la Universidad de Oviedo, en el apartado *Información académica*. No está diseñado para usarse con otros horarios que no estén creados por la EPI Gijón.
(Un ejemplo sería la Ing. Informática en TI: https://epigijon.uniovi.es/infoacademica/grados/informatica/infoacademica).

Una vez cargados los horarios, se simularán las horas que indiquen los horarios proporcionados en el calendario situado a la izquierda. Para moverse a través de las semanas, utilizar los botones con forma de flechas. Para borrar un horario, seleccionarlo en la lista y pulsar el botón *Borrar*.

Si en una hora hay más de una asignatura, entonces se produce un conflicto y esto se puede observar de dos formas: de forma manual, observando las celdas (cada fila de una celda indica una asignatura) o de forma automática pulsando sobre el botón *Detectar conflictos*, el cual recorrerá todo el calendario en busca de conflictos y lo mostrará por pantalla como a continuación:

![image](https://github.com/Sauleteh/visualizador-de-conflictos-horarios-uniovi/assets/22859905/32acda9b-9570-405d-ae7b-9edf2d2f6bd3)

Al principio del programa es común que aparezcan muchos conflictos pues aún no se ha activado el filtrado de grupos. Para ello, marca la casilla de verificación *Filtrar grupos*. Para escoger de qué grupos quieres ser, pulsa en el botón *Elegir grupos* y configúralo a tu gusto.
