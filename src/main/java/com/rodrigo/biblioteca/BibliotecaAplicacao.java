package com.rodrigo.biblioteca;

import com.rodrigo.biblioteca.Service.ClienteService;
import com.rodrigo.biblioteca.Service.EmprestimoService;
import com.rodrigo.biblioteca.Service.LivroService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class BibliotecaAplicacao {

    private static final Scanner in = new Scanner(System.in);
    private static final ClienteService serviceCliente = new ClienteService();
    private static final EmprestimoService serviceEmprestimo = new EmprestimoService();
    private static final LivroService serviceLivro = new LivroService();

    public static void main(String[] args) {

        int op = exibirMenu();
        while(op != 0){
            try{
                switch (op) {
                    case 1:{
                        listarLivros();
                        break;
                    }
                    case 2:{
                        verificarLivro();
                        break;
                    }
                    case 3:{
                        realizarEmpresimo();
                        break;
                    }
                    case 4:{
                        cadastrarCliente();
                        break;
                    }
                    case 5:{
                        devolverLivro();
                        break;
                    }
                    case 6:{
                        System.out.println("Digite sua senha: ");
                        int senha = in.nextInt();
                        if(senha != 123456789){
                           throw new RegraDeNegocioException("Senha incorreta");
                        }
                        opcaoFuncionario();
                        break;
                    }
                }
            }catch (RegraDeNegocioException e){
                System.out.println("Opção inválida! Tente novamente!");
                System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
                in.nextLine();
            }
            op = exibirMenu();

        }

    }


    private static int exibirMenu() {
        System.out.println("\nBem vindo a biblioteca Mundo dos Sonhos!");
        System.out.println("""
                Selecione uma das opções abaixo:
                1 - Listar livros
                2 - Verificar livro
                3-  Realizar empréstimo de livro
                4 - Cadastrar novo usuário
                5 - Devolver livro
                6 - Opções de funcionário
                0 - Sair
                """);
        return in.nextInt();
    }

    private static int exibirMenuFuncionario() {
        System.out.println("""
                Selecione uma das opções abaixo:
                1 - Excluir cliente
                2 - Listar cliente
                3 - Excluir livro
                4 - Cadastrar novo livro
                5 - Relatório
                0 - Sair
                """);
        return in.nextInt();
    }

    public static void listarLivros(){
        System.out.println("Livros:");
        var livros = serviceLivro.listarLivros();
        livros.forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.nextLine();
    }

    public static void verificarLivro(){
        in.nextLine();
        System.out.println("Digite o nome do livro: ");
        String titulo = in.nextLine();

        var retornoLivro = serviceLivro.verificaLivro(titulo);
        System.out.println(retornoLivro.toString());

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.nextLine();

    }

    public static void realizarEmpresimo(){
        in.nextLine();
        System.out.println("Informe o cpf do cliente: ");
        String cpf = in.nextLine();

        System.out.println("Informe o titulo do livro: ");
        String titulo = in.nextLine();

        java.sql.Date dataEmprestimo = Date.valueOf(LocalDate.now());
        serviceEmprestimo.realizarEmprestimo(cpf, titulo, dataEmprestimo);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.nextLine();

    }

    //Ainda não feito
    public static void devolverLivro(){
        in.nextLine();
        System.out.println("Informe o CPF do cliente: ");
        String cpfCliente = in.nextLine();

        System.out.println("Informe o titulo do livro: ");
        String titulo = in.nextLine();

        serviceEmprestimo.atualizarEmprestimo(cpfCliente, titulo);
    }

    public static void cadastrarCliente(){
        in.nextLine();
        System.out.println("Insira seu nome: ");
        String nome = in.nextLine();

        System.out.println("Insira seu cpf: ");
        String cpf = in.nextLine();

        System.out.println("Insira seu email: ");
        String email = in.nextLine();

        serviceCliente.cadastrarCliente(nome, email, cpf);
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.nextLine();

    }

    public static void excluirCliente(){
        in.nextLine();
        System.out.println("Insira o cpf do cliente: ");
        String cpf = in.nextLine();
        serviceCliente.excluirCliente(cpf);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.nextLine();

    }

    public static void excluirLivro(){
        System.out.println("Indique o nome do livro a ser exluido: ");
        String titulo = in.nextLine();
        serviceLivro.excluirLivro(titulo);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.next();

    }

    public static void relatorio(){
        System.out.println("Lista dos empréstimos: ");

        var emprestimos = serviceEmprestimo.listarEmprestimos();
        emprestimos.forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.nextLine();

    }
    public static void cadastrarLivro(){
        in.nextLine();
        System.out.println("Informe o nome do livro: ");
        String titulo = in.nextLine();

        System.out.println("Informe o nome do autor: ");
        String autor = in.nextLine();

        System.out.println("Informe a categoria do livro: ");
        String categoria = in.nextLine();

        serviceLivro.adicionaLivro(titulo, autor, categoria);
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.nextLine();
    }

    public static void listarCliente(){
        System.out.println("Cliente cadastrados:");
        var cliente = serviceCliente.listarCliente();
        cliente.forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        in.nextLine();
    }

    public static void opcaoFuncionario(){
        int opFuncionario = exibirMenuFuncionario();

        switch (opFuncionario){
            case 1:{
                excluirCliente();
                break;
            }
            case 2:{
                listarCliente();
                break;
            }
            case 3:{
                excluirLivro();
                break;
            }
            case 4:{
                cadastrarLivro();
                break;
            }
            case 5:{
                relatorio();
                break;
            }
        }

    }
}