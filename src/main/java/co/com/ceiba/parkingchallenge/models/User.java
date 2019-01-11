package co.com.ceiba.parkingchallenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class User {
	@NonNull private String identification;
	@NonNull private String password;
	@NonNull  private String name;
	@NonNull  private String lastName;
}
