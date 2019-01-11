package co.com.ceiba.parkingchallenge.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="USERS")
@Data
@AllArgsConstructor
public class UserEntity {
	
	@Id
	@Column(name="ID_USER")
	private String identification;
	
	@Column(name="PASSWORD_USER", nullable=false)
	private String password;
	
	@Column(name="NAME_USER", nullable=false)
	private String name;
	
	@Column(name="LAST_NAME_USER", nullable=false)
	private String lastName;
	
	public UserEntity() {
		super();
	}
}
