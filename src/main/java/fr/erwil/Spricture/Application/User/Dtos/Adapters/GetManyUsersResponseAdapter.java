package fr.erwil.Spricture.Application.User.Dtos.Adapters;

import fr.erwil.Spricture.Application.User.Dtos.Responses.GetUserResponseDto;
import fr.erwil.Spricture.Application.User.User;

import java.util.List;

public class GetManyUsersResponseAdapter {
   public static List<GetUserResponseDto> adapt(List<User> users){
       return users.stream().map(GetUserResponseAdapter::adapt).toList();
   }
}
