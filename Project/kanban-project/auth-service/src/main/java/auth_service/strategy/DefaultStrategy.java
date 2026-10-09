package auth_service.strategy;

public class DefaultStrategy implements Strategy {

    @Override
    public String execute() {
        return "Default strategy executed";
    }
}