package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.CreateUserResponseDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.GetUserResponseDto;

import java.util.List;

public interface IUserService {
    CreateUserResponseDto create(CreateUserRequestDto user);
    List<GetUserResponseDto> getMany();
}
