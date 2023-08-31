package com.digitalers.spring.boot.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class TokenRequestDTO implements Serializable {
  private static final long serialVersionUID = -2504597694290589185L;
  private String username;
  private String password;

  public boolean usernameIsBlank() {
    return username == null || username.isBlank();
  }

  public boolean passwordIsBlank() {
    return password == null || password.isBlank();
  }

  public boolean passwordLengthIsWrong() {
    return password.length() > 12 || password.length() < 8;
  }

  public boolean usernameLengthIsWrong() {
    return username.length() > 20;
  }
}
