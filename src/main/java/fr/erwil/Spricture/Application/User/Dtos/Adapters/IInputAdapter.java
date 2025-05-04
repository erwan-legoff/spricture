package fr.erwil.Spricture.Application.User.Dtos.Adapters;

public interface IInputAdapter<Entity, Dto> {
    Entity toEntity(Dto dto);
}
