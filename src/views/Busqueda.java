package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alura.jdbc.DAO.HuespedDAO;
import com.alura.jdbc.DAO.ReservaDAO;
import com.alura.jdbc.modelo.Huesped;
import com.alura.jdbc.modelo.Reserva;
import com.alura.nuevaReserva.TipoBusqueda;

import sql.connection.ConnectionFactory;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.management.timer.Timer;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloH;
	private JLabel labelAtras;
	private JLabel labelExit;
	TipoBusqueda tipoBusqueda;
	int xMouse, yMouse;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas,
				null);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("ID_persona");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de pago");
		modelo.addColumn("Tipo de Habitacion");

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes,
				null);
		modeloH = (DefaultTableModel) tbHuespedes.getModel();
		modeloH.addColumn("Numero de Huesped");
		modeloH.addColumn("Nombre");
		modeloH.addColumn("Apellido");
		modeloH.addColumn("Fecha de Nacimiento");
		modeloH.addColumn("Nacionalidad");
		modeloH.addColumn("Telefono");
		modeloH.addColumn("Numero de Reserva");

//		logica de mostrar huespedes

		HuespedDAO huespedDAO = new HuespedDAO(new ConnectionFactory().recuperarCenexion());

		huespedDAO.listar().forEach(a -> modeloH.addRow(new Object[] { a.getID(), a.getNombre(), a.getApellido(),
				a.getFechaNacimiento(), a.getNacionalidad(), a.getTelefono() }));

//		fin de logica.

//		logica de mostrar reservas.

		ReservaDAO reservaDAO = new ReservaDAO(new ConnectionFactory().recuperarCenexion());

		reservaDAO.listar().forEach(a -> modelo.addRow(new Object[] { a.getID(), a.getID_persona(),
				a.getFecha_entrada(), a.getFecha_salida(), a.getValor(), a.getForma_pago(),a.getTipoHabitacion() }));

//		fin logica de mostrar reservas.

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 if(panel.getSelectedIndex() == 0) {
					 int filas = tbReservas.getRowCount();
					 
				    for (int a = 0; filas > a; a++) {
				       modelo.removeRow(0);
				    }
				ReservaDAO reservaDAO = new ReservaDAO(new ConnectionFactory().recuperarCenexion());
				reservaDAO.listarBusqueda(tipoBusqueda.itemSelected(),txtBuscar.getText()).forEach(a -> modelo.addRow(new Object[] { a.getID(), a.getID_persona(),
						a.getFecha_entrada(), a.getFecha_salida(), a.getValor(), a.getForma_pago(),a.getTipoHabitacion()}));
				 }else {
					 int filas = tbHuespedes.getRowCount();
					 for(int a=0; filas>a;a++) {
						 modeloH.removeRow(0);
					 }
					 HuespedDAO huespedDAO = new HuespedDAO(new ConnectionFactory().recuperarCenexion());
				 
					 huespedDAO.listarBusqueda(tipoBusqueda.itemSelected(), txtBuscar.getText()).forEach(a -> modeloH.addRow(new Object[] { a.getID(), a.getNombre(), a.getApellido(),
								a.getFechaNacimiento(), a.getNacionalidad(), a.getTelefono() }));
				 
				 }
			}
		});
		
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		tipoBusqueda = new TipoBusqueda();
		contentPane.add(tipoBusqueda.selector());

		JPanel btnReserva = new JPanel();
		btnReserva.setLayout(null);
		btnReserva.setBackground(new Color(12, 138, 199));
		btnReserva.setBounds(490, 508, 122, 35);
		btnReserva.setVisible(false);
		btnReserva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnReserva);

		JLabel lblReserva = new JLabel("Reservar");
		lblReserva.setHorizontalAlignment(SwingConstants.CENTER);
		lblReserva.setForeground(Color.WHITE);
		lblReserva.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblReserva.setBounds(0, 0, 122, 35);
		btnReserva.add(lblReserva);

		btnReserva.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ReservasView reserva = new ReservasView();
				reserva.setVisible(true);
				reserva.btnReserva.setVisible(true);

				reserva.setID_persona((int) modeloH.getValueAt(tbHuespedes.getSelectedRow(), 0));
//			dispose();
			}
		});

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				tipoBusqueda.change(panel.getSelectedIndex());
				if (panel.getSelectedIndex() == 0) {
					btnReserva.setVisible(false);
				} else {
					btnReserva.setVisible(true);
				}
			}
		});

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(panel.getSelectedIndex() == 0) {
					editarReserva();
				}else {
					editarHuesped();
				}
			}
		});

		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);

		MouseListener oyenteMouse = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getSelectedIndex() == 0) {
					ReservaDAO reservaDAO = new ReservaDAO(new ConnectionFactory().recuperarCenexion());
					reservaDAO.eliminarReserva((int) modelo.getValueAt(tbReservas.getSelectedRow(), 0));
					modelo.removeRow(tbReservas.getSelectedRow());
				} else {
					HuespedDAO huesped = new HuespedDAO(new ConnectionFactory().recuperarCenexion());
					Object e1 = modeloH.getValueAt(tbHuespedes.getSelectedRow(), 0);
					huesped.eliminarHuesped(e1);
					modeloH.removeRow(tbHuespedes.getSelectedRow());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		};
		btnEliminar.addMouseListener(oyenteMouse);
	}

	public void editarHuesped() {
		int ID = (int) modeloH.getValueAt(tbHuespedes.getSelectedRow(), 0);
		String nombre = modeloH.getValueAt(tbHuespedes.getSelectedRow(), 1).toString();
		String apellido = modeloH.getValueAt(tbHuespedes.getSelectedRow(), 2).toString();
		String fechaNacimiento = modeloH.getValueAt(tbHuespedes.getSelectedRow(), 3).toString();
		String nacionalidad = modeloH.getValueAt(tbHuespedes.getSelectedRow(), 4).toString();
		String telefono = modeloH.getValueAt(tbHuespedes.getSelectedRow(), 5).toString();

		Huesped huesped = new Huesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono);
		huesped.setID(ID);

		RegistroHuesped registro = new RegistroHuesped();
		registro.setVisible(true);
		registro.completeInputs(huesped);
	}
	
	public void editarReserva() {
		int ID  = (int) modelo.getValueAt(tbReservas.getSelectedRow(), 0);
		int IDPersona = (int) modelo.getValueAt(tbReservas.getSelectedRow(),1);
		String fechaEntrada = modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString();
		String fechaSalida = modelo.getValueAt(tbReservas.getSelectedRow(),3).toString();
		float valor = (float) modelo.getValueAt(tbReservas.getSelectedRow(),4);
		String formaPago = modelo.getValueAt(tbReservas.getSelectedRow(), 5).toString();
		String tipoHabitacion = modelo.getValueAt(tbReservas.getSelectedRow(), 6).toString();
		
		Reserva reserva = new Reserva(fechaEntrada, fechaSalida,valor,formaPago,tipoHabitacion);
		reserva.setID(ID);
		reserva.setID_persona(IDPersona);
		
		ReservasView reservaView = new ReservasView();
		reservaView.setVisible(true);
		reservaView.btnReserva.setVisible(true);
		reservaView.lblReserva.setText("Editar");
		reservaView.completarInputs(reserva);
		
		dispose();
	
	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
