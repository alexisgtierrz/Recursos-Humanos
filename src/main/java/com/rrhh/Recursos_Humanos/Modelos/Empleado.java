package com.rrhh.Recursos_Humanos.Modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$",
            message = "El nombre solo puede contener letras sin espacios ni caracteres especiales"
    )
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$",
            message = "El apellido solo puede contener letras sin espacios ni caracteres especiales"
    )
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido (ejemplo@dominio.com)")
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "El teléfono debe contener exactamente 10 dígitos numéricos"
    )
    private String telefono;

    @NotBlank(message = "El puesto no puede estar vacío")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$",
            message = "El puesto solo puede contener letras sin espacios ni caracteres especiales"
    )
    private String puesto;

    @NotNull(message = "El salario no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El salario debe tener hasta 10 dígitos enteros y 2 decimales")
    private Double salario;

    // Constructores
    public Empleado() {}

    public Empleado(String nombre, String apellido, String email, String telefono, String puesto, Double salario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.puesto = puesto;
        this.salario = salario;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
}

