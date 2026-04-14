package app.empleados;

import exception.EmptyFieldException;
import models.trabajador.Trabajador;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorGestorPlanta;
import models.trabajador.TrabajadorMecanicoCinta;
import models.trabajador.TrabajadorOperario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * Diálogo Swing con formulario para dar de alta un nuevo trabajador.
 * Valida campos obligatorios y formato de salario, e instancia el tipo
 * concreto según el combo (Operario, Mecánico, Administrador, Gestor de Planta).
 */
public class FormularioNuevoEmpleado extends JDialog {
    private JTextField txtNombre, txtApellidos, txtDni, txtDireccion, txtNumSS, txtSalario;
    private Trabajador trabajador;
    private JComboBox<String> comboTipo;

    // Constructor. Inicializa el panel y añade los campos del formulario
    public FormularioNuevoEmpleado(Frame padre) {
        super(padre, "Nuevo Trabajador", true);
        setLayout(new BorderLayout(10, 10));
        setSize(300, 400);

        JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCampos.add(new JLabel("Tipo de empleado:"));
        String[] tipos = {"Operario", "Administrador", "Mecánico", "Gestor de Planta"};
        comboTipo = new JComboBox<>(tipos);
        panelCampos.add(comboTipo);

        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelCampos.add(txtApellidos);

        panelCampos.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelCampos.add(txtDni);

        panelCampos.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelCampos.add(txtDireccion);

        panelCampos.add(new JLabel("Num. SS:"));
        txtNumSS = new JTextField();
        panelCampos.add(txtNumSS);

        panelCampos.add(new JLabel("Salario:"));
        txtSalario = new JTextField();
        panelCampos.add(txtSalario);

        // Botón de guardado y event listener
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            // Inicializa this.trabajador con un nuevo objeto trabajador a partir del formulario.
            this.trabajador = crearTrabajador(txtNombre.getText(), txtApellidos.getText(), txtDni.getText(),
                txtDireccion.getText(), txtNumSS.getText(), txtSalario.getText(), (String) comboTipo.getSelectedItem());
            if(this.trabajador != null) {
                dispose();
            }
        });

        add(panelCampos, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);

        setLocationRelativeTo(padre);
    }

    // Función auxiliar para crear un objeto TRrabajador a partir de los datos del formulario.
    public Trabajador crearTrabajador(String nombre, String apellidos, String dni, String direccion,
                                      String numSS, String salario, String tipo) {
        try {
            // Validaciones básicas de cadena vacía.  Si falla lanza excepción
            if(nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || direccion.isEmpty() || numSS.isEmpty()
                    || salario.isEmpty()) {
                throw new EmptyFieldException();
            }
            // Validación de parseo de Double. Si falla va al catch.
            double salarioFinal = Double.parseDouble(salario);
            LocalDateTime fechaIngreso = LocalDateTime.now();
            // Instancia Trabajador según el tipo de trabajador. Este parámetro se recoge del formulario.
            return switch (tipo) {
                case "Operario" ->
                        new TrabajadorOperario(nombre, apellidos, dni, direccion, numSS, salarioFinal, fechaIngreso, 0);
                case "Mecánico" ->
                        new TrabajadorMecanicoCinta(nombre, apellidos, dni, direccion, numSS, salarioFinal, fechaIngreso);
                case "Administrador" ->
                        new TrabajadorAdministrador(nombre, apellidos, dni, direccion, numSS, salarioFinal, fechaIngreso);
                case "Gestor de Planta" ->
                        new TrabajadorGestorPlanta(nombre, apellidos, dni, direccion, numSS, salarioFinal, fechaIngreso);
                default -> null;
            };
        } catch(EmptyFieldException ex) {
            // Muestra un diálogo con la información del error
            JOptionPane.showMessageDialog(this,
                    "Por favor, revisa el formulario. Parece que hay campos sin completar.",
                    "Formulario incompleto",
                    JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException e) {
            // Muestra un diálogo con la información del error
            JOptionPane.showMessageDialog(this,
                    "Por favor, introduce un salario válido (ejemplo: 1200.50).\nUsa el punto para los decimales.",
                    "Formato de salario incorrecto",
                    JOptionPane.WARNING_MESSAGE);
            // focus en el elemento que ha fallado
            txtSalario.requestFocus();
            txtSalario.selectAll();
        }
        return null;
    }

    // Devuelve la instancia de Trabajador creada a partir del formulario,
    public Trabajador getTrabajador() {
        return this.trabajador;
    }

}