package br.senai.sp.jandira.repository;

import br.senai.sp.jandira.model.Aluno;

public class AlunoRepository {
	
	private Aluno[] Turma;
	
	public AlunoRepository() {
		Turma = new Aluno[32];
	}
	
	public AlunoRepository(int quantidadeAlunos) {
		Turma = new Aluno[quantidadeAlunos];
	}
	
	public void gravar(Aluno aluno, int posicao) {
		Turma[posicao] = aluno;
	}
	
	public Aluno listarAluno(int posicao){
		return Turma[posicao];
	}
	
	public Aluno[] listarTodos(){
		return Turma;
	}

}
