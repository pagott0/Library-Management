1. Requisitos Complementares
Requisitos adicionais para a implementação do sistema de gerenciamento de biblioteca em Java:

  Validação de dados: Implementação de validações para garantir a integridade dos dados inseridos no sistema.
  Exemplos:
    - ISBN deve conter apenas números.
    - Campos obrigatórios como título, autor e categoria do livro.
    - Datas de empréstimo e devolução.
    - Tratamento de datas: Utilização de classes e métodos adequados para manipulação de datas, como Date, SimpleDateFormat e Calendar, incluindo validação de datas.
    - Interface gráfica amigável: A interface gráfica deve ser intuitiva e fácil de usar, com mensagens claras e organização adequada dos componentes.
    - Formato de dados: Definição de um formato padrão para exibição de informações de livros, usuários, clientes e empréstimos.
    - Persistência de dados: Implementação de persistência de dados utilizando arquivos.
    - Controle de acesso: O sistema deve implementar controle de acesso para diferentes tipos de usuários (bibliotecário e administrador), restringindo as - funcionalidades de acordo com o tipo de usuário.

2. Descrição do Projeto
O projeto implementa um sistema de gerenciamento de biblioteca em Java com interface gráfica utilizando Swing, que permite o controle de livros, clientes (patrons), empréstimos e usuários. O sistema oferece as seguintes funcionalidades:
Diagrama de Classes:

classDiagram
    Book <|-- Loan
    Patron <|-- Loan
    User <|-- Librarian
    User <|-- Admin
    Library --o Book
    Library --o Patron
    Library --o Loan
    Library --o User

    class Book {
        -String title
        -String author
        -String ISBN
        -String category
        -boolean isAvailable
        +Book(title, author, ISBN, category)
        +getters/setters()
        +toString()
    }

    class Patron {
        -String name
        -String contactInfo
        +Patron(name, contactInfo)
        +getters/setters()
        +toString()
    }

    class Loan {
        -Book book
        -Patron patron
        -Date loanDate
        -Date returnDate
        -boolean isReturned
        -static double FINE_AMOUNT
        +Loan(book, patron, loanDate)
        +getters/setters()
        +returnBook(returnDate)
        +isOverdue()
        +getFine()
        +toString()
    }

    class User {
        -String username
        -String password
        -String role
        +User(username, password, role)
        +getters/setters()
        +toString()
    }

    class Library {
        -List~Book~ books
        -List~Patron~ patrons
        -List~Loan~ loans
        -User loggedInUser
        +Library()
        +loadUsersFromFile()
        +addUser(user)
        +authenticateUser(username, password)
        +getLoggedInUser()
        +logout()
        +registerUser(username, password, role)
        +getAllBooks()
        +addBook(book)
        +searchBook(ISBN)
        +deleteBook(isbn)
        +updateBook(updatedBook)
        +searchBookByTitle(title)
        +searchBookByAuthor(author)
        +searchBookByISBN(ISBN)
        +searchBookByCategory(category)
        +getAllLoans()
        +checkOutBook(isbn, patronId, dueDate)
        +checkInBook(isbn, patronId)
        +deleteOverdueFine(id)
        +getAllPatrons()
        +searchPatron(name, contactInfo)
        +deletePatron(name, contactInfo)
        +addPatron(patron)
        +editPatron(name, newName, newContactInfo)
        +searchPatronByName(name)
        +searchPatronByContactInfo(contactInfo)
        +saveBooksToFile(filename)
        +loadBooksFromFile(filename)
        +savePatronsToFile(filename)
        +loadPatronsFromFile(filename)
        +saveLoansToFile(filename)
        +loadLoansFromFile(filename)
        +saveUsersToFile(filename)
        +loadUsersFromFile(filename)
    }

  Detalhes da Implementação:

    - Gerenciamento de livros: Permite adicionar, editar, excluir e pesquisar livros por título, autor, ISBN ou categoria.
    - Gerenciamento de clientes: Permite adicionar, editar, excluir e pesquisar clientes por nome ou informações de contato.
    - Gerenciamento de empréstimos: Permite realizar o checkout e check-in de livros, gerenciar multas por atraso e visualizar empréstimos ativos.
    - Interface gráfica: Interface gráfica intuitiva e amigável implementada com Swing, facilitando a interação do usuário com o sistema.
    - Persistência de dados: Os dados são salvos em arquivos utilizando serialização, garantindo a persistência dos dados entre as sessões.
    Controle de acesso: Implementa um sistema de autenticação simples com dois tipos de usuários: bibliotecário e administrador, restringindo o acesso às funcionalidades de acordo com o tipo de usuário. Bibliotecários podem gerenciar clientes e empréstimos, enquanto administradores podem fazer isso e também gerenciar livros.
    - O sistema simula o funcionamento de uma biblioteca, onde é possível gerenciar livros, clientes e empréstimos.
    - Para atribuir um empréstimo (check out de um livro), deve ser selecionado um livro, um cliente e uma data de devolução. Para devolver um livro (check in), deve ser selecionado o livro e o cliente, a data de check in é automaticamente recebida. Se a data de check-in for superior à data de devolução, uma multa será aplicada e o cliente irá para a sessão Overdue Fines.
    - Definimos uma taxa fixa de 100 U$ para multas por atraso.

3. Comentários sobre o Código
- O código está organizado em classes, cada uma com responsabilidades bem definidas, facilitando a manutenção e o entendimento.
- A classe Library é a classe principal, responsável por gerenciar todas as funcionalidades do sistema.
- As classes Book, Patron e Loan representam as entidades do sistema.
- A classe User representa um usuário do sistema, com informações de nome de usuário, senha e tipo.
- O código utiliza serialização para salvar e carregar os dados em arquivos.
- A interface gráfica foi implementada utilizando Swing, proporcionando uma interface amigável para o usuário.
- O código inclui comentários explicando a lógica e o funcionamento das diferentes partes do sistema.

4. Plano de testes
  Para a realização dos testes, utilizamos da abordagem de testes manuais. Os testes começaram a ser feitos após a finalização da versão inicial da nossa aplicação. Decidimos começar com os testes manuais, que foram feitos da seguinte forma: primeiro um teste geral, simulando um usuário, testando as funcionalidades e alguns limites, mas nada procedural. A rodada de testes seguinte, seguiu um certo procedimento: rastreamos e anotamos os happy paths possíveis da aplicação e os possíveis erros nesses paths (imagem do caderno), a partir disso foi sendo testado caminho por caminho, partindo primeiro dos happy paths para garantir que o “certo” já estava funcionando. Após isso foi testado os possíveis erros nesses nos caminhos testados.
5. Resultados dos testes

6. Procedimentos de Construção
Para executar o código, siga os seguintes passos:

  Instalação do software:
  - Certifique-se de ter o Git e o Java Development Kit (JDK) instalados em sua máquina.
  - Download do programa:
    - Abra o terminal ou prompt de comando e navegue até o diretório onde deseja clonar o repositório.
    - Clone o repositório do projeto com o seguinte comando Git (substitua <URL_DO_REPOSITÓRIO> pela URL real do seu repositório no GitHub):
    - git clone <URL_DO_REPOSITÓRIO>
    - Configuração do ambiente e execução:
    - Importe o projeto para sua IDE favorita (Eclipse, IntelliJ, NetBeans, etc.):
  - Eclipse:
    - Vá em "File" > "Import..." > "Existing Projects into Workspace" > selecione o diretório do projeto clonado > "Finish".
  - IntelliJ:
    - Vá em "File" > "Open..." > selecione o diretório do projeto clonado > "OK".
  - NetBeans:
    - Vá em "File" > "Open Project..." > selecione o diretório do projeto clonado > "Open Project".
  - Compile o projeto na IDE.
  - Execute a classe Main.java para iniciar o programa.

7. Problemas Encontrados
  - Dificuldade na implementação da interface gráfica com Swing: Swing pode ser um pouco verboso para criar interfaces gráficas complexas.
  - Gerenciamento de datas: A manipulação de datas em Java pode ser um pouco complexa, exigindo atenção na formatação e conversão de datas.
  - Dificuldade em definir funções e responsabilidades dos cargos de bibliotecário e administrador: A definição clara das funções e permissões de cada tipo de usuário é essencial para garantir a segurança e a integridade do sistema.
  - Desafio em definir como funcionario a questão dos empréstimos: A lógica de empréstimos e devoluções pode ser complexa, especialmente ao lidar com multas por atraso e controle de disponibilidade de livros.

8. Comentários Adicionais
  - O sistema de gerenciamento de biblioteca implementado é um projeto básico e pode ser expandido com mais funcionalidades, como:
    - Sistema de busca avançada.
    - Geração de relatórios.
    - Integração com banco de dados.
  - A interface gráfica pode ser aprimorada com um design mais moderno.
  - A implementação de testes unitários é recomendada para garantir a qualidade do código.
  - O ISBN dos livros foi limitado apenas em números, mas para uma aplicação real, seria necessário considerar o formato completo do ISBN.
