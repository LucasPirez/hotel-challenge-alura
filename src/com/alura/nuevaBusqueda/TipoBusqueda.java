package com.alura.nuevaBusqueda;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;

public class TipoBusqueda {
	
	private String[] values1 = {"ID","ID_persona","Fecha_entrada","Fecha_salida"};
	private String[] values2 = {"ID","Nombre","Apellido"};
	private JComboBox<String> combo;
	
	
	public JComboBox<String> selector(){
		 combo = new JComboBox<String>();
		
		combo.setBounds(380, 125, 122, 35);
		combo.setBackground(SystemColor.text);
		combo.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		combo.setFont(new Font("Roboto", Font.PLAIN, 16));
		combo.setModel(new DefaultComboBoxModel<String>(values2));
	
		return combo;
	}
	
	public void change(int index) {
		if(index == 0) {
			combo.setModel(new DefaultComboBoxModel<String>(values1));
		}else {
			combo.setModel(new DefaultComboBoxModel<String>(values2));
		}
	}
	
	public String itemSelected() {
		return (String) combo.getSelectedItem();
	}
	
	
	
	
	
	
}
