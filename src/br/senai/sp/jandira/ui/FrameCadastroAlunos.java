package br.senai.sp.jandira.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import br.senai.sp.jandira.model.Aluno;
import br.senai.sp.jandira.model.Periodo;
import br.senai.sp.jandira.repository.AlunoRepository;

public class FrameCadastroAlunos extends JFrame {

	
	private JPanel contentPane;
	private JTextField txtMatricula;
	private JTextField txtNome;
	
	private int posicao = 0; 

	public FrameCadastroAlunos() {
		setBackground(new Color(0, 0, 0));
		
//		Vetores (array, coleção)
		
//		int[] numeros = new int[5];
//		numeros[0] = 8;
//		numeros[1] = 4;
//		numeros[2] = 48;
//		numeros[3] = 85;
//		numeros[4] = 79;

//		String[] diasDaSemana = {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta"};
//		
//		for (String dia : diasDaSemana) {
//			System.out.println(dia);
//		}
		
		setTitle("Cadastro de Alunos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 281);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMatricula = new JLabel("Matr\u00EDcula:");
		lblMatricula.setBounds(30, 32, 56, 14);
		contentPane.add(lblMatricula);
		
		txtMatricula = new JTextField();
		txtMatricula.setBackground(new Color(255, 255, 255));
		txtMatricula.setBounds(96, 29, 115, 20);
		contentPane.add(txtMatricula);
		txtMatricula.setColumns(10);
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(96, 70, 115, 20);
		contentPane.add(txtNome);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(30, 73, 56, 14);
		contentPane.add(lblNome);
		
		JLabel lblPeriodo = new JLabel("Per\u00EDodo:");
		lblPeriodo.setBounds(30, 112, 56, 14);
		contentPane.add(lblPeriodo);
		
		JComboBox comboPeriodo = new JComboBox();
		comboPeriodo.setBackground(new Color(255, 255, 255));
		DefaultComboBoxModel<String> modelPeriodo = new DefaultComboBoxModel<String>();
		
		for (Periodo p : Periodo.values()) {
			modelPeriodo.addElement(p.getDescricao());

		}
		
		comboPeriodo.setModel(modelPeriodo);
		
		comboPeriodo.setBounds(96, 108, 86, 22);
		contentPane.add(comboPeriodo);
		
		JButton btnSalvarAluno = new JButton("Salvar Aluno");
		btnSalvarAluno.setForeground(new Color(0, 0, 0));
		btnSalvarAluno.setBackground(new Color(144, 238, 144));
		btnSalvarAluno.setBounds(30, 153, 181, 31);
		contentPane.add(btnSalvarAluno);
		
		JLabel lblListaDeAlunos = new JLabel("Lista de Alunos:");
		lblListaDeAlunos.setBounds(228, 28, 126, 14);
		contentPane.add(lblListaDeAlunos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(228, 56, 148, 170);
		contentPane.add(scrollPane);
		
		JList listAlunos = new JList();
		listAlunos.setBackground(new Color(255, 250, 250));
		
		DefaultListModel<String> listaModel = new DefaultListModel<String>();
		
		listAlunos.setModel(listaModel);
		
		scrollPane.setViewportView(listAlunos);
		
		JButton btnMostrarAlunos = new JButton("Exibir Alunos");
		btnMostrarAlunos.setBackground(new Color(173, 216, 230));
		btnMostrarAlunos.setBounds(30, 195, 181, 31);
		contentPane.add(btnMostrarAlunos);
		
		
		
		AlunoRepository turma = new AlunoRepository(3);
		
		btnSalvarAluno.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Aluno aluno = new Aluno();
				aluno.setMatricula(txtMatricula.getText());
				aluno.setNome(txtNome.getText());
				
				// Definir o horário em que o aluno estuda
				aluno.setPeriodo(determinarPeriodo(comboPeriodo.getSelectedIndex()));
				
				turma.gravar(aluno, posicao);
				
				posicao++;
				
				// Adicionar o nome do Aluno ao model da lista
				listaModel.addElement(aluno.getNome());
				
				if (posicao == turma.getTamanho()) {
					btnSalvarAluno.setEnabled(false);
					JOptionPane.showMessageDialog(null, "Turma cheia!", "Cheio", JOptionPane.INFORMATION_MESSAGE);;
				}
				
			}
		});
		
		btnMostrarAlunos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for (Aluno aluno : turma.listarTodos()) {
					
					System.out.println(aluno.getMatricula());
					System.out.println(aluno.getNome());
					System.out.println(aluno.getPeriodo().getDescricao());
					System.out.println("--------------------------------------");
					
				}
				
				
			}
		});
		
		listAlunos.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				Aluno aluno = turma.listarAluno(listAlunos.getSelectedIndex());
				txtMatricula.setText(aluno.getMatricula());
				txtNome.setText(aluno.getNome());
				
				comboPeriodo.setSelectedIndex(aluno.getPeriodo().ordinal());
				
			}
		});
		
		
		
	}
	
	private Periodo determinarPeriodo(int periodoSelecionado) { 
		if(periodoSelecionado == 0 ) {
			return Periodo.MANHA;
		} else if(periodoSelecionado == 1 ) {
			return Periodo.TARDE;
		} else if(periodoSelecionado == 2 ) {
			return Periodo.NOITE;
		} else {
			return Periodo.SABADO;
		}
	}
	
	
}
