package fr.erwil.Spricture.Application.User.Dtos.Adapters;


public interface IAdapter<Entity, Dto> extends IOutputAdapter<Entity, Dto>, IInputAdapter<Entity, Dto> {
}
