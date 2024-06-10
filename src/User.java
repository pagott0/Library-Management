import java.io.Serializable;

/**
 * A classe User representa um usuário no sistema de biblioteca.
 *
 * <p>Existem dois tipos de usuários: admins e bibliotecários (librarians).
 * </p>
 * <ul>
 * <li>Bibliotecários são os principais usuários do sistema. Eles podem adicionar, ler, editar e excluir livros, clientes (patrons) e empréstimos (loans). Cada bibliotecário tem acesso apenas aos seus próprios dados.</li>
 * <li>Admins têm acesso para adicionar, ler, editar e excluir livros, clientes e empréstimos de todos os bibliotecários existentes no sistema.</li>
 * </ul>
 *
 * <p>Nota: A abordagem para desenvolver este sistema é muito subjetiva. Escolhemos fazê-lo desta maneira, mas existem inúmeras outras possíveis.</p>
 *
 * <p>Esta classe implementa a interface Serializable para permitir a serialização dos objetos User.</p>
 */
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
