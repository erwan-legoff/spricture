package fr.erwil.Spricture.Configuration.Security;

import fr.erwil.Spricture.Configuration.Security.Dtos.LoginDto;

public interface IAuthService {
    public String login(LoginDto loginDto);
}