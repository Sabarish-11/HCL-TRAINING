package auth_service.role;

public class AdminRole extends Role {

    @Override
    public String getRoleName() {
        return "ADMIN";
    }
}