# Gestión de varitas Harry Potter

Aplicación Android desarrollada en Kotlin para gestionar varitas mágicas del universo de Harry Potter.

## Índice

- [Stack tecnológico y arquitectura](#stack-tecnológico-y-arquitectura)
- [Funcionalidades principales](#funcionalidades-principales)

## **Stack tecnológico y arquitectura**

### **Stack tecnológico**

<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" height="30"> <img src="https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android&logoColor=white" height="30"> <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" height="30">

### **Arquitectura MVC**

- **Modelo**: Implementación de DTO y clase de datos para organizar la información.
- **Vista**: Ficheros XML para gestionar la vista de los componentes y la interfaz gráfica.
- **Controlador**: Gestiona la lógica, la comunicación con la API y asegura la persistencia de datos.

## **Funcionalidades principales**

- Gestionar las varitas a través de la selección de una de las presentes en el listado.
- Crear una nueva varita.

### **Listado de varitas**

<figure>
  <img src="https://github.com/user-attachments/assets/2e5b9b4d-058d-451f-b611-e0e3261003e5" width="200" height="400">
  <figcaption align="center"><b>Figura 1:</b> pantalla con el listado de varitas. Texto: madera - subtexto: núcleo</figcaption>
</figure>

### **Creacion de varitas**

<figure>
<img width="200" height="400" src="https://github.com/user-attachments/assets/f0c11d38-c389-4764-8171-4a89b071cc22" />
  <figcaption align="center"><b>Figura 2:</b> pantalla con la creacion de una varita. </figcaption>
</figure>

<br>

### **Romper varita**

- Solo aparece la opcion de "romper" desde el listado de varitas
- En caso de que provenga de que la varita ya este rota, se mostrará un toast de que ya esta rota.
<figure>
<img width="200" height="400" alt="Captura de pantalla 2026-07-09 214312" src="https://github.com/user-attachments/assets/3452f60a-8932-43d4-81d4-4157e4b4c73d" />
  <figcaption align="center"><b>Figura 3:</b> pantalla para romper una varita. </figcaption>
</figure>
