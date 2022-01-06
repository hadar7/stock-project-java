package engineClasses;

public class Notification {

     private String message;
     private User toUser;

     public Notification(String message,User user) {
         this.message=message;
         this.toUser=user;
     }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getToUser() {
        return toUser;
    }
}
