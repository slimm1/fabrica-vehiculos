package models.trabajador;


import models.trabajador.mecanico.TrabajadorMecanicoCinta;
import models.trabajador.operario.TrabajadorOperario;
import models.trabajador.operario.TrabajadorOperarioEficiente;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Trabajador {
    protected String nombre;
    protected String apellidos;
    protected String dni;
    protected String direccion;
    protected String numSeguridadSocial;
    protected double salario;
    protected LocalDateTime fechaIngreso;
    private String puesto;

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

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumSeguridadSocial() {
        return numSeguridadSocial;
    }

    public void setNumSeguridadSocial(String numSeguridadSocial) {
        this.numSeguridadSocial = numSeguridadSocial;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

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
