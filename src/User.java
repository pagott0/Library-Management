import java.io.Serializable;

// A classe User é dividida em 2 tipos de usuários: admins e bibliotecários (librarians)
// Bibliotecários são os principais usuários do sistema, eles podem adicionar/ler/editar/excluir livros (books), clientes (patrons) e empréstimos (loans), sendo que cara bibliotecário tem acesso apenas aos seus próprios dados.
// Os admins, por sua vez, tem acesso a adicionar/ler/editar/excluir os livros, clientes e empréstimos de todos os bibliotecários existentes no sistema.
// Note: a abordagem para desenvolver esse sistema é muito subjetiva, escolhemos fazer dessa maneira, mas existem inumeras outras possiveis.
public class User implements Serializable {
    private String username;
    private String password;
    private String role; //admin ou bibliotecário (admin / librarian)

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
