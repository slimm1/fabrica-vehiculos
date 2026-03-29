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

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
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

    public Trabajador crearTrabajador(String nombre, String apellidos, String dni, String direccion,
                                      String numSS, String salario, String tipo) {
        try {
            if(nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || direccion.isEmpty() || numSS.isEmpty()
                    || salario.isEmpty()) {
                throw new EmptyFieldException();
            }
            double salarioFinal = Double.parseDouble(salario);
            LocalDateTime fechaIngreso = LocalDateTime.now();
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
            JOptionPane.showMessageDialog(this,
                    "Por favor, revisa el formulario. Parece que hay campos sin completar.",
                    "Formulario incompleto",
                    JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, introduce un salario válido (ejemplo: 1200.50).\nUsa el punto para los decimales.",
                    "Formato de salario incorrecto",
                    JOptionPane.WARNING_MESSAGE);

            txtSalario.requestFocus();
            txtSalario.selectAll();
        }
        return null;
    }

    public Trabajador getTrabajador() {
        return this.trabajador;
    }

}