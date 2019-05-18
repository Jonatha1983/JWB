package jwb_api;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class JWBUserData implements Serializable {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String email;

}
