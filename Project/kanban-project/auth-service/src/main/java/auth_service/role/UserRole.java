package auth_service.role;

public class UserRole extends Role {

    @Override
    public String getRoleName() {
        return "USER";
    }
}