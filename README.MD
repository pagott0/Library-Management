Grupo:
Enzo Vasconcellos Pagotto - 13632844
Gabriel Pietro Leone - 13874729
Rafael Romanhole Borrozino - 14604607

### 1. Requisitos Complementares
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

### 2. Descrição do Projeto
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

### 3. Comentários sobre o Código
- O código está organizado em classes, cada uma com responsabilidades bem definidas, facilitando a manutenção e o entendimento.
- A classe Library é a classe principal, responsável por gerenciar todas as funcionalidades do sistema.
- As classes Book, Patron e Loan representam as entidades do sistema.
- A classe User representa um usuário do sistema, com informações de nome de usuário, senha e tipo.
- O código utiliza serialização para salvar e carregar os dados em arquivos.
- A interface gráfica foi implementada utilizando Swing, proporcionando uma interface amigável para o usuário.
- O código inclui comentários explicando a lógica e o funcionamento das diferentes partes do sistema.

### 4. Plano de testes
Para a realização dos testes, utilizamos da abordagem de testes manuais. Os testes começaram a ser feitos após a finalização da versão inicial da nossa aplicação. Decidimos começar com os testes manuais, que foram feitos da seguinte forma: primeiro um teste geral, simulando um usuário, testando as funcionalidades e alguns limites, mas nada procedural. A rodada de testes seguinte, seguiu um certo procedimento: rastreamos e anotamos os happy paths possíveis da aplicação e os possíveis erros nesses paths (imagem do caderno), a partir disso foi sendo testado caminho por caminho, partindo primeiro dos happy paths para garantir que o “certo” já estava funcionando. Após isso foi testado os possíveis erros nesses nos caminhos testados.

**Caminhos:**

> [!NOTE]
Para uma base, inputs inválidos são referências para um campo preenchido só com o caracter espaço. Nesses testes de inputs inválidos também foram feitos testes colocando espaços no começo e no final do input para ver se o trim() está funcionando corretamente.

**Registrar um admin:** clicar no botão de registrar na primeira tela -> preencher os inputs com dados corretos -> selecionar admin no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Registration successful. You can now log in.” e voltar para a tela inicial. **Resultado dentro do esperado no primeiro teste.**

**Tentar registar um admin sem preencher todos os campos:** clicar no botão de registrar na primeira tela -> preencher um ou dois dos três inputs -> selecionar admin no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Fill in all fields to register.” e voltar para a tela de cadastro. **Campos não estão verificando strings vazias, portanto está cadastrando admins sem nome ou com senhas vazias. - CORRIGIDO**

**Tentar registar um admin preenchendo senhas diferentes nos campos Password e Confirm Password:** clicar no botão de registrar na primeira tela -> preencher os três inputs, porém com valores diferente nos campos Password e Confirm Password -> selecionar admin no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Passwords do not match.” e voltar para a tela de cadastro. **Resultado dentro do esperado no primeiro teste.**

**Tentar registar um admin preenchendo os campos com inputs inválidos:** clicar no botão de registrar na primeira tela -> preencher inputs apenas utilizando o caracterer espaço -> selecionar admin no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Fill in all fields to register.” e voltar para a tela de cadastro. **Campos não estão verificando strings vazias, portanto está cadastrando admins sem nome ou com senhas vazias. - CORRIGIDO**

**Tentar registar um admin preenchendo todos os campos corretamente, porém sem selecionar o radio button:** clicar no botão de registrar na primeira tela -> preencher os inputs com dados corretos -> não selecionar nenhuma opção do radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Choose a role.” e voltar para a tela de cadastro. **Cadastrando usuários como Librarians. - CORRIGIDO**

**Tentar registar um admin com um username já cadastrado:** clicar no botão de registrar na primeira tela -> preencher os inputs com dados corretos, porém preencher username com um username já cadastrado -> selecionar admin no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Username already exists, change and try again.” e voltar para a tela de cadastro. **Cadastrando usuários com mesmo username. - CORRIGIDO**

**Realizar login como um admin:** preencher os campos de Username e Password corretamente na primeira tela -> clicar no botão de Login. **Esperado:** Abrir a segunda tela, cujo title é Library Management System. **Resultado dentro do esperado no primeiro teste.**

**Tentar realizar login como um admin com dados inválidos:** preencher os campos de Username e Password com valores errados na primeira tela -> clicar no botão de Login. **Esperado:** Aparecer mensagem: “invalid username or password” e voltar para tela de login. **Resultado dentro do esperado no primeiro teste.**

**Adicionar um livro como um admin:** realizar login com uma conta admin -> preencher os quatro primeiros campos da segunda tela corretamente -> clicar no botão Add Book. **Esperado:** Aparecer a mensagem: “Book added successfully!”. **Resultado dentro do esperado no primeiro teste.**

**Tentar adicionar um livro como um admin sem preencher todos os campos:** realizar login com uma conta admin -> preencher um, dois, ou três dos quatro primeiros campos -> clicar no botão Add Book. **Esperado:** Aparecer a mensagem: “All fields are required.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Tentar adicionar um livro como um admin preenchendo os campos com inputs inválidos:** realizar login com uma conta admin -> preencher inputs apenas utilizando o caracterer espaço -> clicar no botão Add Book. **Esperado:** Aparecer a mensagem: “All fields are required.” e voltar para a tela de Books. **Está adicionando livro com espaço vazio. - CORRIGIDO**

**Tentar adicionar um livro como um admin utilizando um ISBN já existente:** realizar login com uma conta admin -> preencher os campos Title, Author e Category corretamente e, preencher o campo ISBN com um ISBN já utilizado -> clicar no botão Add Book. **Esperado:** Aparecer a mensagem: “Book with this ISBN already exists.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Atualizar livro como um admin:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN válido -> clicar no botão Update Book -> fazer as alterações desejadas nos campos na tela que aparecer, com informações válidas -> clicar no botão Update. **Esperado:** Aparecer a mensagem “Book updated successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Atualizar livro como um admin sem alterar nenhum campo:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN válido -> clicar no botão Update Book -> clicar no botão Update. **Esperado:** Aparecer a mensagem “Book updated successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Atualizar livro, adicionado por um outro usuário, como um admin:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN válido de um livro cadastrado por um outro usuário -> clicar no botão Update Book -> fazer as alterações desejadas nos campos na tela que aparecer, com informações válidas -> clicar no botão Update. **Esperado:** Aparecer a mensagem “Book updated successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Tentar atualizar um livro como um admin sem preencher todos os campos:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN válido -> clicar no botão Update Book -> preencher um ou dois dos três inputs, ou apagar alguns dos campos para deixa-lo vazio -> clicar no botão Update. **Esperado:** Aparecer a mensagem: “All fields are required.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Tentar atualizar um livro como um admin preenchendo os campos com inputs inválidos:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN válido -> clicar no botão Update Book -> preencher inputs apenas utilizando o caracterer espaço -> clicar no botão Update. **Esperado:** Aparecer a mensagem: “All fields are required.” e voltar para a tela de Books. **Está atualizando livro com espaço vazio. - CORRIGIDO**

**Tentar atualizar um livro como um admin utilizando um ISBN que não existe:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN inexistente -> clicar no botão Update Book. **Esperado:** Aparecer a mensagem: “Book with this ISBN does not exist.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Deletar um livro como um admin:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN válido -> clicar no botão Delete Book. **Esperado:** Aparecer a mensagem: “Book deleted successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Deletar um livro, adicionado por um outro usuário, como um admin:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN válido de um livro cadastrado por um outro usuário -> clicar no botão Delete Book. **Esperado:** Aparecer a mensagem: “Book deleted successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Tentar deletar um livro como um admin utilizando um ISBN que não existe:** realizar login com uma conta admin -> preencher o campo ISBN com um ISBN inexistente -> clicar no botão Delete Book. **Esperado:** Aparecer a mensagem: “Book with this ISBN does not exist.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado no título como um admin:** realizar login com uma conta admin -> selecionar Title no campo Criteria -> digitar um nome de livro válido no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado no título como um admin:** realizar login com uma conta admin -> selecionar Title no campo Criteria -> digitar um nome de livro válido, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado no título como admin, mas colocando um título que não existe:** realizar login com uma conta admin -> selecionar Title no campo Criteria -> digitar um nome de livro inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado no autor como um admin:** realizar login com uma conta admin -> selecionar Author no campo Criteria -> digitar um autor de livro válido no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado no autor como um admin:** realizar login com uma conta admin -> selecionar Author no campo Criteria -> digitar um autor de livro válido, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado no autor como admin, mas colocando um autor que não existe:** realizar login com uma conta admin -> selecionar Author no campo Criteria -> digitar um autor de livro inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado na categoria como um admin:** realizar login com uma conta admin -> selecionar Category no campo Criteria -> digitar uma categoria de livro válida no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado na categoria como um admin:** realizar login com uma conta admin -> selecionar Category no campo Criteria -> digitar uma categoria de livro válida, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado na categoria como admin, mas colocando uma categoria que não existe:** realizar login com uma conta admin -> selecionar Category no campo Criteria -> digitar uma categoria de livro inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado no ISBN como um admin:** realizar login com uma conta admin -> selecionar ISBN no campo Criteria -> digitar um ISBN válido no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado no ISBN como um admin:** realizar login com uma conta admin -> selecionar ISBN no campo Criteria -> digitar um ISBN válido, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado no ISBN como admin, mas colocando um ISBN que não existe:** realizar login com uma conta admin -> selecionar ISBN no campo Criteria -> digitar um ISBN inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado na categoria como um admin:** Realizar login com uma conta admin -> selecionar Category no campo Criteria -> digitar uma categoria de livro válido no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado na categoria como um admin:** Realizar login com uma conta admin -> selecionar Category no campo Criteria -> digitar uma categoria de livro válido, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado na categoria como admin, mas colocando uma categoria que não existe:** Realizar login com uma conta admin -> selecionar Category no campo Criteria -> digitar uma categoria de livro inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem: “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Mostrar todos os livros como um admin:** Realizar login como um admin -> clicar no botão Show all books. **Esperado:** Aparecer todos os livros cadastrados no banco de dados na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Adicionar cliente como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher os dois primeiros inputs com dados válidos -> clicar no botão Add Patron. **Esperado:** Aparecer a mensagem: “Patron added successfully!” e voltar para a tela de Patrons. **Resultado dentro do esperado no primeiro teste.**

**Tentar adicionar cliente, com nome ou contato já existente, como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher os dois primeiros inputs com dados já existentes -> clicar no botão Add Patron. **Esperado:** Aparecer a mensagem: “Patron with this name or contact info already exists.” e voltar para a tela de Patrons. **Resultado dentro do esperado no primeiro teste.**

**Tentar adicionar cliente, com nome ou contato vazios, como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher apenas um dos campos, ou nenhum -> clicar no botão Add Patron. **Esperado:** Aparecer a mensagem: “Name and contact info cannot be empty.” e voltar para a tela de Patrons. **Resultado dentro do esperado no primeiro teste.**

**Tentar adicionar cliente, com dados inválidos, como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher campos com dados inválidos -> clicar no botão Add Patron. **Esperado:** Aparecer a mensagem: “Name and contact info cannot be empty.” e voltar para a tela de Patrons. **Adicionando cliente com nome e contato inválidos. - CORRIGIDO**

**Atualizar cliente como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher o terceiro e quarto input com dados válidos -> clicar no botão Update Patron -> Preencher o dialog de username corretamente -> clicar no botão OK -> Preencher o dialog password corretamente -> Clicar Ok. **Esperado:** Aparecer a mensagem: “Patron updated successfully!” e voltar para a tela de Patrons. **Resultado dentro do esperado no primeiro teste.**

**Tentar atualizar cliente, sem preencher um dos dialogs, como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher o terceiro e quarto input com dados válidos -> clicar no botão Update Patron -> Apagar o conteúdo do campo username do dialog que aparecer -> clicar no botão OK -> Preencher o dialog password corretamente -> Clicar Ok. **Esperado:** Aparecer a mensagem: “Name and contact cannot be blank.” e voltar para a tela de Patrons. **Resultado dentro do esperado no primeiro teste.**

**Tentar atualizar cliente, preenchendo um dos dialogs com inputs inválidos, como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher o terceiro e quarto input com dados válidos -> clicar no botão Update Patron -> Preencher o dialog de username com caracter de espaço -> clicar no botão OK -> Preencher o dialog password corretamente -> Clicar Ok. **Esperado:** Aparecer a mensagem: “Name and contact cannot be blank.” e voltar para a tela de Patrons. **Resultado dentro do esperado no primeiro teste.**

**Cancelar uma atualização de cliente no primeiro dialog como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher o terceiro e quarto input com dados válidos -> clicar no botão Update Patron -> Clicar no botão Cancel. **Esperado:** Voltar para a tela de cliente. **Aparece o dialog de password.**

**Deletar cliente como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher o terceiro e quarto input com dados válidos -> clicar no botão Delete Patron. **Esperado:** Aparecer a mensagem: “Patron deleted successfully!” e voltar para a tela de Patrons. **Resultado dentro do esperado no primeiro teste.**

**Tentar deletar cliente, preenchendo o campo com valores inválidos ou nulos, como admin:** Realizar login como admin -> clicar na aba Patrons -> preencher o terceiro e quarto input com dados inválidos -> clicar no botão Delete Patron. **Esperado:** Aparecer a mensagem: “Patron with this name and contact info does not exist.” e voltar para a tela de Patrons. **Resultado dentro do esperado no primeiro teste.**

**Procurar um cliente, baseado no nome, como um admin:** Realizar login com uma conta admin -> clicar na aba Patrons -> selecionar Name no campo Criteria -> digitar um nome de um cliente válido no campo Search -> clicar no botão Search. **Esperado:** Aparecer os dados do cliente pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um cliente, preenchendo o campo nome com valor inválido ou nulo, como um admin:** Realizar login com uma conta admin -> clicar na aba Patrons -> selecionar Name no campo Criteria -> digitar um nome de um cliente inválido no campo Search -> clicar no botão Search. **Esperado:** Aparecer a mensagem: “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um cliente, baseado no contato, como um admin:** Realizar login com uma conta admin -> clicar na aba Patrons -> selecionar Contact Information no campo Criteria -> digitar um contato de um cliente válido no campo Search -> clicar no botão Search. **Esperado:** Aparecer os dados do cliente pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um cliente, preenchendo o campo contato com valor inválido ou nulo, como um admin:** Realizar login com uma conta admin -> clicar na aba Patrons -> selecionar Contact Information no campo Criteria -> digitar um contato de um cliente inválido no campo Search -> clicar no botão Search. **Esperado:** Aparecer a mensagem: “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Realizar um empréstimo como um admin:** Realizar login com uma conta admin -> clicar na aba Loans -> preencher os campos com valores válidos -> clicar no botão Check Out. **Esperado:** Aparecer a mensagem: “Book checked out successfully!”, aparecer os dados do empréstimo na box abaixo e o livro tem que ter mudado o status de Available para false. **Resultado dentro do esperado no primeiro teste.**

**Tentar realizar um empréstimo, preenchendo um ISBN inválido, como um admin:** Realizar login com uma conta admin -> clicar na aba Loans -> preencher os campos com valores válidos, com exceção do ISBN -> clicar no botão Check Out. **Esperado:** Aparecer a mensagem: “Failed to check out book.” **Resultado dentro do esperado no primeiro teste.**

**Tentar realizar um empréstimo, preenchendo um cliente inválido, como um admin:** Realizar login com uma conta admin -> clicar na aba Loans -> preencher os campos com valores válidos, com exceção do nome do cliente -> clicar no botão Check Out. **Esperado:** Aparecer a mensagem: “Failed to check out book.” **Resultado dentro do esperado no primeiro teste.**

**Tentar realizar um empréstimo, preenchendo uma data anterior a data atual, como um admin:** Realizar login com uma conta admin -> clicar na aba Loans -> preencher os campos com valores válidos, com exceção do data -> clicar no botão Check Out. **Esperado:** Aparecer a mensagem: “Select an valid date.” **Adicionando empr´stimo com data inválida.**


**Registrar um librarian:** clicar no botão de registrar na primeira tela -> preencher os inputs com dados corretos -> selecionar librarian no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Registration successful. You can now log in.” e voltar para a tela inicial. **Resultado dentro do esperado no primeiro teste.**

**Tentar registar um librarian sem preencher todos os campos:** clicar no botão de registrar na primeira tela -> preencher um ou dois dos três inputs -> selecionar librarian no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Fill in all fields to register.” e voltar para a tela de cadastro. **Campos não estão verificando strings vazias, portanto está cadastrando librarians sem nome ou com senhas vazias. - CORRIGIDO**

**Tentar registar um librarian preenchendo senhas diferentes nos campos Password e Confirm Password:** clicar no botão de registrar na primeira tela -> preencher os três inputs, porém com valores diferente nos campos Password e Confirm Password -> selecionar librarian no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Passwords do not match.” e voltar para a tela de cadastro. **Resultado dentro do esperado no primeiro teste.**

**Tentar registar um librarian preenchendo os campos com inputs inválidos:** clicar no botão de registrar na primeira tela -> preencher inputs apenas utilizando o caracterer espaço -> selecionar librarian no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Fill in all fields to register.” e voltar para a tela de cadastro. **Campos não estão verificando strings vazias, portanto está cadastrando librarians sem nome ou com senhas vazias. - CORRIGIDO**

**Tentar registar um librarian preenchendo todos os campos corretamente, porém sem selecionar o radio button:** clicar no botão de registrar na primeira tela -> preencher os inputs com dados corretos -> não selecionar nenhuma opção do radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Choose a role.” e voltar para a tela de cadastro. **Cadastrando usuários como Librarians. - CORRIGIDO**

**Tentar registar um librarian com um username já cadastrado:** clicar no botão de registrar na primeira tela -> preencher os inputs com dados corretos, porém preencher username com um username já cadastrado -> selecionar librarian no radio button -> clicar no botão Register. **Esperado:** Aparecer a mensagem: “Username already exists, change and try again.” e voltar para a tela de cadastro. **Cadastrando usuários com mesmo username. - CORRIGIDO**

**Realizar login como um librarian:** preencher os campos de Username e Password corretamente na primeira tela -> clicar no botão de Login. **Esperado:** Abrir a segunda tela, cujo title é Library Management System. **Resultado dentro do esperado no primeiro teste.**

**Tentar realizar login como um librarian com dados inválidos:** preencher os campos de Username e Password com valores errados na primeira tela -> clicar no botão de Login. **Esperado:** Aparecer mensagem: “invalid username or password” e voltar para tela de login. **Resultado dentro do esperado no primeiro teste.**

**Adicionar um livro como um librarian:** realizar login com uma conta librarian -> preencher os quatro primeiros campos da segunda tela corretamente -> clicar no botão Add Book. **Esperado:** Aparecer a mensagem: “Book added successfully!”. **Resultado dentro do esperado no primeiro teste.**

**Tentar adicionar um livro como um librarian sem preencher todos os campos:** realizar login com uma conta librarian -> preencher um, dois, ou três dos quatro primeiros campos -> clicar no botão Add Book. **Esperado:** Aparecer a mensagem: “All fields are required.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Tentar adicionar um livro como um librarian preenchendo os campos com inputs inválidos:** realizar login com uma conta librarian -> preencher inputs apenas utilizando o caracterer espaço -> clicar no botão Add Book. **Esperado:** Aparecer a mensagem: “All fields are required.” e voltar para a tela de Books. **Está adicionando livro com espaço vazio. - CORRIGIDO**

**Tentar adicionar um livro como um librarian utilizando um ISBN já existente:** realizar login com uma conta librarian -> preencher os campos Title, Author e Category corretamente e, preencher o campo ISBN com um ISBN já utilizado -> clicar no botão Add Book. **Esperado:** Aparecer a mensagem: “Book with this ISBN already exists.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Atualizar livro como um librarian:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN válido -> clicar no botão Update Book -> fazer as alterações desejadas nos campos na tela que aparecer, com informações válidas -> clicar no botão Update. **Esperado:** Aparecer a mensagem “Book updated successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Atualizar livro como um librarian sem alterar nenhum campo:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN válido -> clicar no botão Update Book -> clicar no botão Update. **Esperado:** Aparecer a mensagem “Book updated successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Atualizar livro, adicionado por um outro usuário, como um librarian:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN válido de um livro cadastrado por um outro usuário -> clicar no botão Update Book -> fazer as alterações desejadas nos campos na tela que aparecer, com informações válidas -> clicar no botão Update. **Esperado:** Aparecer a mensagem “Book updated successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Tentar atualizar um livro como um librarian sem preencher todos os campos:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN válido -> clicar no botão Update Book -> preencher um ou dois dos três inputs, ou apagar alguns dos campos para deixa-lo vazio -> clicar no botão Update. **Esperado:** Aparecer a mensagem: “All fields are required.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Tentar atualizar um livro como um librarian preenchendo os campos com inputs inválidos:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN válido -> clicar no botão Update Book -> preencher inputs apenas utilizando o caracterer espaço -> clicar no botão Update. **Esperado:** Aparecer a mensagem: “All fields are required.” e voltar para a tela de Books. **Está atualizando livro com espaço vazio. - CORRIGIDO**

**Tentar atualizar um livro como um librarian utilizando um ISBN que não existe:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN inexistente -> clicar no botão Update Book. **Esperado:** Aparecer a mensagem: “Book with this ISBN does not exist.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Deletar um livro como um librarian:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN válido -> clicar no botão Delete Book. **Esperado:** Aparecer a mensagem: “Book deleted successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Deletar um livro, adicionado por um outro usuário, como um librarian:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN válido de um livro cadastrado por um outro usuário -> clicar no botão Delete Book. **Esperado:** Aparecer a mensagem: “Book deleted successfully!” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Tentar deletar um livro como um librarian utilizando um ISBN que não existe:** realizar login com uma conta librarian -> preencher o campo ISBN com um ISBN inexistente -> clicar no botão Delete Book. **Esperado:** Aparecer a mensagem: “Book with this ISBN does not exist.” e voltar para a tela de Books. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado no título como um librarian:** realizar login com uma conta librarian -> selecionar Title no campo Criteria -> digitar um nome de livro válido no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado no título como um librarian:** realizar login com uma conta librarian -> selecionar Title no campo Criteria -> digitar um nome de livro válido, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado no título como librarian, mas colocando um título que não existe:** realizar login com uma conta librarian -> selecionar Title no campo Criteria -> digitar um nome de livro inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado no autor como um librarian:** realizar login com uma conta librarian -> selecionar Author no campo Criteria -> digitar um autor de livro válido no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado no autor como um librarian:** realizar login com uma conta librarian -> selecionar Author no campo Criteria -> digitar um autor de livro válido, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado no autor como librarian, mas colocando um autor que não existe:** realizar login com uma conta librarian -> selecionar Author no campo Criteria -> digitar um autor de livro inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado na categoria como um librarian:** realizar login com uma conta librarian -> selecionar Category no campo Criteria -> digitar uma categoria de livro válida no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado na categoria como um librarian:** realizar login com uma conta librarian -> selecionar Category no campo Criteria -> digitar uma categoria de livro válida, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado na categoria como librarian, mas colocando uma categoria que não existe:** realizar login com uma conta librarian -> selecionar Category no campo Criteria -> digitar uma categoria de livro inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro baseado no ISBN como um librarian:** realizar login com uma conta librarian -> selecionar ISBN no campo Criteria -> digitar um ISBN válido no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Procurar um livro, adicionado por outro usuário, baseado no ISBN como um librarian:** realizar login com uma conta librarian -> selecionar ISBN no campo Criteria -> digitar um ISBN válido, adicionado por outro usuário, no campo Search -> clicar no botão Search book. **Esperado:** Aparecer os dados do livro pesquisado na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Tentar procurar um livro baseado no ISBN como librarian, mas colocando um ISBN que não existe:** realizar login com uma conta librarian -> selecionar ISBN no campo Criteria -> digitar um ISBN inexistente no campo Search -> clicar no botão Search book. **Esperado:** Aparecer a mensagem “No results found.” na box abaixo. **Resultado dentro do esperado no primeiro teste.**

**Mostrar todos os livros como um librarian:** Realizar login como um librarian -> clicar no botão Show all books. **Esperado:** Aparecer todos os livros cadastrados. **Resultado dentro do esperado no primeiro teste.**


### 5. Resultados dos testes
Como foi descrito no Test Plan, utilizamos testes manuais. Os testes manuais já tiveram resultado na primeira passagem de testes, na qual simulava um usuário, mas sem procedimentos. Nessa passagem descobrimos que o campo de insersão da data de devolução estava aceitando datas anteriores a data atual, comportamento no qual não deveria acontecer. Após isso discutimos e pesquisamos se era possível a utilização de um Date Picker ou apenas fazer uma validação do input e optamos por utilizar o Date Picker, porém na hora da implementação tivemos problemas para adicionar gradle/maven para fazer a importação do JDatePicker, portanto deixamos como um know issue. Após essa primeria rodada de testes, passamos para os testes procedurais, no qual rastreamos os caminhos possiveis da aplicação e seus possíveis erros. Nessa etapa, fomos descobrindo vários detalhes de verificação de input, como por exemplo inserir valores vazios, valores apenas com o caracter espaço ou usuários com espaços no começo ou final da string. Outro ponto observado após rodar o programa várias vezes foi que a dimensão do JFrame poderia ser um pouco maior na vertical para o usuário não ter que remensionar a janela para consguir ver a box com os dados dos livros. Porém fomos percebendo que existiam algumas inconsistências nas funções de adim e bibliotecário. Estavámos fugindo do escopo inicial proposto no pdf disponibilizado no eDisciplinas, nessa primeira versão que montamos, os bibliotecários na verdade eram bibliotecas e o admin era um administrador das bibliotecas. Depois de identificar esse problema resolvemos ajustar as funcionalidades de cada usuário: Bibliotecários podem gerenciar clientes e empréstimos, enquanto administradores podem fazer isso e também gerenciar livros.
### 6. Procedimentos de Construção
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

### 7. Problemas Encontrados
  - Dificuldade na implementação da interface gráfica com Swing: Swing pode ser um pouco verboso para criar interfaces gráficas complexas.
  - Gerenciamento de datas: A manipulação de datas em Java pode ser um pouco complexa, exigindo atenção na formatação e conversão de datas.
  - Dificuldade em definir funções e responsabilidades dos cargos de bibliotecário e administrador: A definição clara das funções e permissões de cada tipo de usuário é essencial para garantir a segurança e a integridade do sistema.
  - Desafio em definir como funcionario a questão dos empréstimos: A lógica de empréstimos e devoluções pode ser complexa, especialmente ao lidar com multas por atraso e controle de disponibilidade de livros.
  - Tentamos adicionar o gradle e o maven no nosso prejeto para importamos biblioteca de Date Picker porém tivemos muitas dificuldades nesse processo e optamos por deixar assim.

### 8. Comentários Adicionais
  - O sistema de gerenciamento de biblioteca implementado é um projeto básico e pode ser expandido com mais funcionalidades, como:
    - Sistema de busca avançada.
    - Geração de relatórios.
    - Integração com banco de dados.
  - A interface gráfica pode ser aprimorada com um design mais moderno.
  - A implementação de testes unitários é recomendada para garantir a qualidade do código.
  - O ISBN dos livros foi limitado apenas em números, mas para uma aplicação real, seria necessário considerar o formato completo do ISBN.
  - Adicionar maven ou gradle para o porjeto
