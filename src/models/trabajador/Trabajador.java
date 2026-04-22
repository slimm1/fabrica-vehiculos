package models.trabajador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase base de todos los trabajadores. Contiene los datos personales y
 * laborales comunes (nombre, DNI, salario, fecha de ingreso) y delega en
 * las subclases la implementación de trabajar(). El puesto se resuelve
 * por instanceof sobre la jerarquía concreta.
 */
public abstract class Trabajador {
    protected String nombre;
    protected String apellidos;
    protected String dni;
    protected String direccion;
    protected String numSeguridadSocial;
    protected double salario;
    protected LocalDateTime fechaIngreso;

    public Trabajador(String nombre, String apellidos, String dni, String direccion,
                      String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.direccion = direccion;
        this.numSeguridadSocial = numSeguridadSocial;
        this.salario = salario;
        this.fechaIngreso = fechaIngreso;
    }

    public abstract void trabajar();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNumSeguridadSocial() {
        return numSeguridadSocial;
    }

    public double getSalario() {
        return salario;
    }

    // Imprimir el puesto del trabajador en función del tipo de instancia
    public String getPuesto() {
        if(this instanceof TrabajadorOperario) {
            return "Operario";
        } else if (this instanceof TrabajadorMecanicoCinta) {
            return "Mecánico";
        } else if (this instanceof TrabajadorAdministrador) {
            return "Administrador";
        } else if (this instanceof TrabajadorGestorPlanta) {
            return "Gestor de Planta";
        }
        return "Desconocido";
    }

    public String getPrintableFechaIngreso() {
        if (this.fechaIngreso == null) {
            return "Fecha no disponible";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.fechaIngreso.format(formatter);
    }
}
