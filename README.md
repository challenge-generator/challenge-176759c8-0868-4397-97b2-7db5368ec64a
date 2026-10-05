# Normalización de Modelo de Datos en Sistema de Gestión de Préstamos

Como desarrollador senior de backend en un sistema de gestión de préstamos, necesitas implementar un proceso de normalización en una base de datos según las formas normales. El sistema actual utiliza un modelo de datos no normalizado, lo que ha llevado a redundancias y anomalías en la actualización. Debes diseñar y aplicar un modelo de datos normalizado utilizando un modelo Entidad-Relación para mejorar la eficiencia y consistencia de la base de datos. Los actores involucrados son el originador de créditos, el motor antifraude y el buró de riesgos. El sistema debe manejar un volumen de 1 500 solicitudes por segundo en hora pico, con un SLA del 99.9% y una latencia máxima de 200ms.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Diseño e Implementación de un Modelo de Datos Normalizado |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 10 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Análisis del Modelo Actual

**Objetivo:** Identificar las debilidades y redundancias en el modelo de datos actual.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Revisa el modelo de datos existente y documenta las redundancias y anomalías encontradas.
- Identifica las entidades y relaciones actuales y cómo contribuyen a las ineficiencias.

**Entregable:** Documento que detalla las debilidades y redundancias en el modelo de datos actual.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo las relaciones entre entidades pueden ser mejoradas.
- Piensa en cómo las anomalías de actualización afectan la consistencia de los datos.

</details>

### Fase 2: Diseño del Modelo Normalizado

**Objetivo:** Crear un modelo de datos normalizado que elimine las redundancias y mejore la consistencia.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Diseña un modelo de datos normalizado siguiendo las formas normales (1NF, 2NF, 3NF).
- Documenta las entidades, relaciones y atributos en el nuevo modelo.

**Entregable:** Modelo de datos normalizado documentado con entidades, relaciones y atributos.

<details>
<summary>Pistas de conocimiento</summary>

- Recuerda que la normalización busca eliminar redundancias y anomalías.
- Piensa en cómo las relaciones entre entidades pueden ser representadas de manera más eficiente.

</details>

### Fase 3: Implementación y Validación

**Objetivo:** Implementar el modelo de datos normalizado y validar su funcionamiento.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Implementa el modelo de datos normalizado en la base de datos.
- Realiza pruebas para validar que el nuevo modelo elimina las redundancias y mejora la consistencia.

**Entregable:** Base de datos con el modelo de datos normalizado implementado y pruebas de validación documentadas.

<details>
<summary>Pistas de conocimiento</summary>

- Verifica que las relaciones entre entidades sean correctas y eficientes.
- Asegúrate de que el nuevo modelo cumpla con los requisitos de rendimiento y consistencia.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la normalización de datos y por qué es importante en un sistema de gestión de préstamos?
- **paraQueSirve**: ¿Cómo se aplica la normalización en el diseño de un modelo de datos?
- **comoSeUsa**: ¿Cuáles son las formas normales y cómo se aplican en la práctica?
- **erroresComunes**: ¿Cuáles son los errores comunes al normalizar un modelo de datos y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones clave se deben tomar al diseñar un modelo de datos normalizado?

## Criterios de Evaluacion

- Análisis detallado del modelo de datos actual con identificación de redundancias y anomalías.
- Diseño de un modelo de datos normalizado siguiendo las formas normales.
- Implementación y validación del modelo de datos normalizado con pruebas documentadas.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
