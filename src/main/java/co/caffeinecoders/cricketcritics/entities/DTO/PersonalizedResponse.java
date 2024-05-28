package co.caffeinecoders.cricketcritics.entities.DTO;

public class PersonalizedResponse <T> {

    //TODO PENSARE A COME GENERALIZZARLA E DOVE UTILIZZARLA SE NEL SERVICE O NEL CONTROLLER
    private int status;
    private String message;
    private T entity;


    public PersonalizedResponse(int status, String message, T entity) {
        this.status = status;
        this.message = message;
        this.entity = entity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
