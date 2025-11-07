package com.rrhh.Recursos_Humanos.Modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

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

    @NotBlank(message = "El DNI no puede estar vacío")
    @Pattern(
            regexp = "^[0-9]{8}$",
            message = "El DNI debe contener exactamente 8 dígitos numéricos"
    )
    private String dni;

    // Nuevo atributo: Fecha de nacimiento (formato dd/MM/yyyy)
    @NotBlank(message = "La fecha de nacimiento no puede estar vacía")
    @Pattern(
            regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$",
            message = "La fecha de nacimiento debe tener el formato dd/MM/yyyy"
    )
    private String fechaNacimiento;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido (ejemplo@dominio.com)")
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "El teléfono debe contener exactamente 10 dígitos numéricos"
    )
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "puesto_id", nullable = false)
    private Puesto puesto;

    @NotNull(message = "El salario no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor que 0")
    private Double salario;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Denuncia> denuncias = new ArrayList<>();

    // Constructores
    public Empleado() {}

    public Empleado(String nombre, String apellido, String email, String telefono, Puesto puesto, Double salario) {
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

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Puesto getPuesto() { return puesto; }
    public void setPuesto(Puesto puesto) { this.puesto = puesto; }

    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }

    public List<Denuncia> getDenuncias() { return denuncias; }
    public void setDenuncias(List<Denuncia> denuncias) { this.denuncias = denuncias; }

    public void addDenuncia(Denuncia denuncia) {
        denuncias.add(denuncia);
        denuncia.setEmpleado(this);
    }

    public void removeDenuncia(Denuncia denuncia) {
        denuncias.remove(denuncia);
        denuncia.setEmpleado(null);
    }
}

