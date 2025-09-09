<h1>Biblioteca básica construida com Java e Java JDBC:</h1>

<h2>Sobre</h2>
<p>Projeto desenvolvido como prática para consolidar os conhecimentos adquiridos no curso de Java e JDBC da plataforma Alura. O objetivo principal foi aplicar conceitos 
  de programação orientada a objetos, manipulação de banco de dados MySQL e integração via JDBC, permitindo explorar a ferramenta na prática e compreender o fluxo de operações 
  em um sistema completo. </p>

## Como usar
  <p>Por não conter uma interface gráfica, o programa roda inteiramente no terminal. Como este projeto utiliza o banco de dados MySQL, certifique-se de que
  possui o mesmo instaladao em sua máquina. Importe as tabelas do projeto para seu banco de dados e faça o download das classes. Após isso, abra o projeto na IDE de sua escolha,
  atualize as configurações de conecão com o banco de dados, fazendo os seguintes ajustes: <\p>
  <li>
    <ul>Acesse o arquivo <I>ConnectionFactory</I> em src -> java -> com.rodrigo.biblioteca</ul>
    <ul><i>config.setJdbcUrl("jdbc:mysql://localhost:3306/biblioteca")</i> troque <strong>biblioteca</strong> pelo nome do banco de dados que você importou.</ul>
    <ul><i>config.setUsername("root")</i> troque <strong>root</strong> pelo noem do seu usuário no MySQL</ul>
    <ul><i>config.setPassword("******")</i> troque os <strong>****</strong> pela senha do seu MySQL</ul>
  </li>
    <p>Após esses ajustes, o programa deverá funcionar.</p>
  Bom uso!!

## Tecnologias
<div>
  <img src="https://img.shields.io/badge/JAVA-239120?style=for-the-badge&logo=java&logoColor=white">
</div>
