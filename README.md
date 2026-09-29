# Ciudad en Peligro — RPG por turnos de Superhéroes

Proyecto Integrador Final · Informática I · UPA (Universidad Paraguayo Alemana)
Grupo 8 · Categoría: RPG por turnos (combate) · Ambientación: Superhéroes

**Integrantes:** Monique Salustre · _(nombre de tu pareja)_
**Docente:** Prof. Lic. Gustavo Galeano

## De qué se trata

Creás a tu superhéroe (nombre + superpoder) y tenés que defender la ciudad de una fila de
villanos, uno tras otro, en combate por turnos. En cada turno podés atacar, usar tu superpoder
(gasta energía), usar un ítem del inventario, ver el inventario ordenado o huir.
Cada villano derrotado deja un ítem de recompensa. Ganás si derrotás al jefe final
(Capitán Caos); perdés si tu vida llega a 0.

## Diseño

El diagrama de clases UML está en [`docs/diagrama-clases.png`](docs/diagrama-clases.png)
(versión editable: `docs/diagrama-clases.drawio`, se abre en https://app.diagrams.net).

| Requisito | Dónde está |
|---|---|
| Clases encapsuladas | `Personaje` (abstracta), `Heroe`, `Villano`, `Item` |
| Estructura de datos | `ArrayList<Item>` (inventario) y `Queue<Villano>` (orden de villanos) |
| Excepción propia | `AccionInvalidaException` |
| Búsqueda | búsqueda lineal en `Heroe.buscarItem()` |
| Ordenamiento | burbuja en `Heroe.ordenarInventarioPorPoder()` |
| Menú por consola | `Juego` (Scanner) |

## Cómo compilar y jugar

Desde la carpeta del proyecto:

```bash
javac -encoding UTF-8 -d bin src/*.java
java -cp bin Main
```

También se puede abrir la carpeta en IntelliJ / NetBeans / VS Code y ejecutar `Main`.
