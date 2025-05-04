package fr.erwil.Spricture.Application.User.Dtos.Adapters;

public interface IOutputAdapter<Entity, Dto> {
    Dto adapt(Entity entity);
}
