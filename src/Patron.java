import java.io.Serializable;

/**
 * A classe Patron representa um patrono (cliente) na biblioteca.
 *
 * <p>Um patrono contém informações sobre o nome, informações de contato e o dono dos dados do usuário
 * (que pode ser um bibliotecário responsável por gerenciar os dados deste patrono).</p>
 *
 * <p>Esta classe implementa a interface Serializable para permitir a serialização dos objetos Patron.</p>
 */
public class Patron implements Serializable {
    private String name;
    private String contactInfo;

    public Patron(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    //Faço um Override no toString dessa classe, uma vez que quero printar as informações dela de uma maneira especifica.
    @Override
    public String toString() {
        return "Patron: " + name + " | Contact Info: " + contactInfo;
    }

}
