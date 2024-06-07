import java.io.Serializable;

public class Patron implements Serializable {
  private String name;
  private String contactInfo;
  private String userDataOwner;

  public Patron(String name, String contactInfo, String userDataOwner) {
      this.name = name;
      this.contactInfo = contactInfo;
      this.userDataOwner = userDataOwner;
  }

  // Getters and Setters
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

  public String getUserDataOwner() {
      return userDataOwner;
  }

  public void setUserDataOwner(String userDataOwner) {
      this.userDataOwner = userDataOwner;
  }

  @Override
  public String toString() {
      return "Patron: " + name + " | Contact Info: " + contactInfo;
  }

}
