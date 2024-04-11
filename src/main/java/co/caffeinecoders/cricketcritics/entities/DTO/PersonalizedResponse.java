package co.caffeinecoders.cricketcritics.entities.DTO;

public class PersonalizedResponse {

    //TODO PENSARE A COME GENERALIZZARLA E DOVE UTILIZZARLA SE NEL SERVICE O NEL CONTROLLER
    private int status;
    private String message;
    private Object entity;


    public PersonalizedResponse(int status, String message, Object entity) {
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

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
