package driver.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Buyer {
    private String _id;

    private String email;
    private String password;
    private String surname;
    private String name;
    private AccountState state;

    public void makeConfirmed(){
        if (this.state.equals(AccountState.NOT_CONFIRMED)) {
            this.state = AccountState.CONFIRMED;
        } else {
            throw new IllegalStateException();
        }
    }
}
