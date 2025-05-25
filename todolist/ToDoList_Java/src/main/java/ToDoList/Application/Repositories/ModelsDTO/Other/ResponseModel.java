package ToDoList.Application.Repositories.ModelsDTO.Other;

public class ResponseModel {

    public ResponseModel(){

    }

    public ResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
