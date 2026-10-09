package auth_service.strategy;

public class AdminStrategy implements Strategy {

    @Override
    public String execute() {
        return "Admin strategy executed";
    }
}
